package me.gibson.captchanotifier.client;

import me.gibson.captchanotifier.CaptchaNotifier;
import me.gibson.captchanotifier.client.gui.ConfigScreen;
import me.gibson.captchanotifier.client.listener.ChatListener;
import me.gibson.captchanotifier.client.listener.VotePartyListener;
import me.gibson.captchanotifier.client.util.Execution;
import me.gibson.captchanotifier.client.util.HeadTextureClipboard;
import me.gibson.captchanotifier.config.ModConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenKeyboardEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.lwjgl.glfw.GLFW;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

public class CaptchaNotifierClient implements ClientModInitializer {

    private static final String TARGET_SERVER_SUBSTRING = "opblocks";
    private static final String TARGET_COMMAND = "server skyblock";
    private static final int RETRY_EVERY_MS = 10_000;
    private static final int ATTEMPT_TIMEOUT_MS = 10_000;
    private static final int MAX_ATTEMPTS = 6;
    private static final int TICK_CHECK_INTERVAL = 100;

    private static ModConfig config;
    private static KeyBinding configKeyBinding;
    private static KeyBinding attackKeyBinding;
    private static KeyBinding copyTextureKeyBinding;
    private static boolean isAttackToggled = false;

    private static volatile Thread autoJoinThread;
    private static final AtomicBoolean autoJoinStop = new AtomicBoolean(false);
    private static int tickCounter = 0;

    @Override
    public void onInitializeClient() {
        CaptchaNotifier.LOGGER.info("[Auto-Join] Mod initializing...");
        config = ModConfig.load();

        configKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.captchanotifier.openconfig",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_K,
                "category.captchanotifier"
        ));

        attackKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.captchanotifier.attack",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_L,
                "category.captchanotifier"
        ));

        copyTextureKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.captchanotifier.copytexture",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_C,
                "category.captchanotifier"
        ));

        registerScreenEvents();
        registerConnectionEvents();
        registerClientTickEvents();

        ChatListener.register();
        VotePartyListener.register();
    }

    private void registerScreenEvents() {
        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if (screen instanceof HandledScreen<?> handledScreen) {
                CaptchaDetector.getInstance().onScreenOpen(handledScreen);

                ScreenKeyboardEvents.allowKeyPress(screen).register((scr, key, scancode, modifiers) -> {
                    if (copyTextureKeyBinding.matchesKey(key, scancode)) {
                        HeadTextureClipboard.copyFocusedSlotTexture();
                        return false;
                    }
                    return true;
                });
            }
        });
    }

    private void registerConnectionEvents() {
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            CaptchaNotifier.LOGGER.info("[Auto-Join] JOIN event triggered!");
            client.execute(() -> {
                if (client.player != null && client.getNetworkHandler() != null && isTargetServer(client)) {
                    startAutoJoinWorker(client);
                } else {
                    CaptchaNotifier.LOGGER.info("[Auto-Join] Conditions not met (player/network/server).");
                }
            });
        });

        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
            CaptchaNotifier.LOGGER.info("[Auto-Join] DISCONNECT; stopping worker.");
            stopAutoJoinWorker();
        });
    }

    private void registerClientTickEvents() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (configKeyBinding.wasPressed()) {
                MinecraftClient.getInstance().setScreen(new ConfigScreen(null));
            }

            if (attackKeyBinding.wasPressed()) {
                isAttackToggled = !isAttackToggled;
            }
            if (isAttackToggled) {
                client.options.attackKey.setPressed(true);
            }

            while (copyTextureKeyBinding.wasPressed()) {
                HeadTextureClipboard.copyFocusedSlotTexture();
            }

            tickCounter++;
            if (tickCounter >= TICK_CHECK_INTERVAL) {
                tickCounter = 0;

                if (client.player != null && client.getNetworkHandler() != null && isTargetServer(client)) {
                    if (!isWorkerRunning() && hasCompassInInventory(client)) {
                        CaptchaNotifier.LOGGER.info("[Auto-Join] Compass detected in tick, restarting worker!");
                        startAutoJoinWorker(client);
                    }
                }
            }
        });
    }

    private static void startAutoJoinWorker(MinecraftClient client) {
        stopAutoJoinWorker();
        autoJoinStop.set(false);

        autoJoinThread = new Thread(() -> runAutoJoinLoop(client), "AutoJoin-Worker");
        autoJoinThread.setDaemon(true);
        autoJoinThread.start();
        CaptchaNotifier.LOGGER.info("[Auto-Join] Worker started.");
    }

    private static void stopAutoJoinWorker() {
        autoJoinStop.set(true);
        Thread t = autoJoinThread;
        autoJoinThread = null;
        if (t != null) {
            t.interrupt();
        }
    }

    private static boolean isWorkerRunning() {
        Thread t = autoJoinThread;
        return t != null && t.isAlive();
    }

    private static boolean hasCompassInInventory(MinecraftClient client) {
        if (client.player == null) {
            return false;
        }

        int size = client.player.getInventory().size();
        for (int i = 0; i < size; i++) {
            ItemStack stack = client.player.getInventory().getStack(i);
            if (!stack.isEmpty() && stack.getItem() == Items.COMPASS) {
                return true;
            }
        }
        return false;
    }

    private static void runAutoJoinLoop(MinecraftClient client) {
        try {
            if (!queryHasSelector(client)) {
                CaptchaNotifier.LOGGER.info("[Auto-Join] Selector not present at start; nothing to do.");
                return;
            }

            int attempts = 0;
            while (!autoJoinStop.get()) {
                if (!isConnected(client) || !queryIsTargetServer(client)) {
                    CaptchaNotifier.LOGGER.info("[Auto-Join] Not connected or not target server; stopping.");
                    return;
                }

                attempts++;
                if (MAX_ATTEMPTS > 0 && attempts > MAX_ATTEMPTS) {
                    CaptchaNotifier.LOGGER.warn("[Auto-Join] Reached max attempts ({}); stopping.", MAX_ATTEMPTS);
                    return;
                }

                CaptchaNotifier.LOGGER.info("[Auto-Join] Attempt {} — sending /{}", attempts, TARGET_COMMAND);
                client.execute(() -> {
                    if (client.getNetworkHandler() != null) {
                        client.getNetworkHandler().sendChatCommand(TARGET_COMMAND);
                    }
                });

                boolean disappeared = Execution.sleepUntil(
                    () -> !safeQuery(() -> queryHasSelector(client), false),
                    ATTEMPT_TIMEOUT_MS
                );

                if (disappeared) {
                    CaptchaNotifier.LOGGER.info("[Auto-Join] Selector disappeared — success after attempt {}.", attempts);
                    return;
                }

                CaptchaNotifier.LOGGER.warn("[Auto-Join] Attempt {} timed out; selector still present. Retrying in {} ms.",
                    attempts, RETRY_EVERY_MS);

                if (autoJoinStop.get()) {
                    return;
                }

                Execution.sleep(RETRY_EVERY_MS);
            }
        } catch (Exception e) {
            CaptchaNotifier.LOGGER.error("[Auto-Join] Worker error", e);
        } finally {
            CaptchaNotifier.LOGGER.info("[Auto-Join] Worker exiting.");
        }
    }

    private static boolean queryHasSelector(MinecraftClient client) {
        return safeQuery(() -> {
            if (client == null || client.player == null) {
                return false;
            }

            int size = client.player.getInventory().size();
            for (int i = 0; i < size; i++) {
                ItemStack stack = client.player.getInventory().getStack(i);
                if (!stack.isEmpty() && stack.getItem() == Items.COMPASS) {
                    return true;
                }
            }
            return false;
        }, false);
    }

    private static boolean queryIsTargetServer(MinecraftClient client) {
        return safeQuery(() -> isTargetServer(client), false);
    }

    private static boolean isConnected(MinecraftClient client) {
        return safeQuery(() -> client != null && client.getNetworkHandler() != null, false);
    }

    private static <T> T safeQuery(java.util.concurrent.Callable<T> task, T fallback) {
        try {
            if (MinecraftClient.getInstance().isOnThread()) {
                return task.call();
            }

            final CompletableFuture<T> future = new CompletableFuture<>();
            MinecraftClient.getInstance().execute(() -> {
                try {
                    future.complete(task.call());
                } catch (Exception e) {
                    future.completeExceptionally(e);
                }
            });
            return future.get();
        } catch (Exception e) {
            CaptchaNotifier.LOGGER.warn("[Auto-Join] safeQuery failure", e);
            return fallback;
        }
    }

    public static ModConfig getConfig() {
        return config;
    }

    public static KeyBinding getCopyTextureKeyBinding() {
        return copyTextureKeyBinding;
    }

    private static boolean isTargetServer(MinecraftClient client) {
        if (client == null || client.isInSingleplayer()) {
            return false;
        }

        var entry = client.getCurrentServerEntry();
        if (entry == null) {
            return false;
        }

        String addr = entry.address;
        return addr != null && addr.toLowerCase().contains(TARGET_SERVER_SUBSTRING);
    }
}
