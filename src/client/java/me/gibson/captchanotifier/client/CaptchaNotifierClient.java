package me.gibson.captchanotifier.client;

import me.gibson.captchanotifier.client.gui.ConfigScreen;
import me.gibson.captchanotifier.client.listener.ChatListener;
import me.gibson.captchanotifier.client.listener.VotePartyListener;
import me.gibson.captchanotifier.config.ModConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class CaptchaNotifierClient implements ClientModInitializer {

    private static ModConfig config;
    private static KeyBinding configKeyBinding;
    private static KeyBinding attackKeyBinding;
    private static boolean isAttackToggled = false;

    @Override
    public void onInitializeClient() {
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

        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if (screen instanceof HandledScreen<?> handledScreen) {
                CaptchaDetector.getInstance().onScreenOpen(handledScreen);
            }
        });

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
        });

        ChatListener.register();
        VotePartyListener.register();
    }

    public static ModConfig getConfig() {
        return config;
    }
}

