package me.gibson.captchanotifier.client;

import me.gibson.captchanotifier.config.ModConfig;
import me.gibson.captchanotifier.mixin.client.FishingBobberEntityAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FishingRodItem;
import net.minecraft.util.Hand;

import java.util.Random;

public class AutoFisher {
    private static final AutoFisher INSTANCE = new AutoFisher();

    private int castDelayLeft = 0;
    private int catchDelayLeft = 0;
    private boolean wasHooked = false;

    private AutoFisher() {}

    public static AutoFisher getInstance() {
        return INSTANCE;
    }

    public void onClientTick() {
        ModConfig config = CaptchaNotifierClient.getConfig();
        if (!config.isAutoFisherEnabled()) {
            castDelayLeft = 0;
            catchDelayLeft = 0;
            wasHooked = false;
            return;
        }

        MinecraftClient client = MinecraftClient.getInstance();

        if (client.currentScreen != null && !(client.currentScreen instanceof ChatScreen)) {
            return;
        }

        PlayerEntity player = client.player;

        if (player == null || !(player.getMainHandStack().getItem() instanceof FishingRodItem)) {
            return;
        }

        tryCast(client, player, config);
        tryCatch(client, player, config);
    }

    private void tryCast(MinecraftClient client, PlayerEntity player, ModConfig config) {
        if (player.fishHook != null) return;

        if (castDelayLeft > 0) {
            castDelayLeft--;
            return;
        }

        useRod(client, player, config);
    }

    private void tryCatch(MinecraftClient client, PlayerEntity player, ModConfig config) {
        if (player.fishHook == null) return;

        if (player.fishHook.getHookedEntity() != null) {
            useRod(client, player, config);
            return;
        }

        if (!player.fishHook.isInFluid()) return;

        if (!wasHooked) {
            if (((FishingBobberEntityAccessor) player.fishHook).hasCaughtFish()) {
                catchDelayLeft = getRandomReelDelay(config);
                wasHooked = true;
            }
            return;
        }

        if (catchDelayLeft > 0) {
            catchDelayLeft--;
            return;
        }

        useRod(client, player, config);
    }

    private void useRod(MinecraftClient client, PlayerEntity player, ModConfig config) {
        // Always swing hand for anticheat
        player.swingHand(Hand.MAIN_HAND);

        if (Math.random() < 0.15) {
            player.swingHand(Hand.MAIN_HAND);
        }

        client.interactionManager.interactItem(player, Hand.MAIN_HAND);
        
        wasHooked = false;
        castDelayLeft = getRandomCastDelay(config);
    }

    private int getRandomCastDelay(ModConfig config) {
        int mean = (config.getAutoFisherMinDelay() + config.getAutoFisherMaxDelay()) / 2 / 50;
        int stdDev = (config.getAutoFisherMaxDelay() - config.getAutoFisherMinDelay()) / 4 / 50;
        Random rand = new Random();
        double gaussian = rand.nextGaussian();
        int delay = (int) (mean + gaussian * stdDev);
        return Math.max(2, delay);
    }

    private int getRandomReelDelay(ModConfig config) {
        int base = config.getAutoFisherReelDelay() / 50;
        int variance = 2;
        return base + (int)(Math.random() * variance);
    }
}