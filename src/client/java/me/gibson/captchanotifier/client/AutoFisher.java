package me.gibson.captchanotifier.client;

import me.gibson.captchanotifier.config.ModConfig;
import me.gibson.captchanotifier.mixin.client.FishingBobberEntityAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FishingRodItem;
import net.minecraft.util.Hand;

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
        PlayerEntity player = client.player;

        if (player == null || !(player.getMainHandStack().getItem() instanceof FishingRodItem)) {
            return;
        }

        tryCast(client, player, config);
        tryCatch(client, player, config);
    }

    private void tryCast(MinecraftClient client, PlayerEntity player, ModConfig config) {
        // Don't cast if we already have a bobber
        if (player.fishHook != null) return;

        // Wait for cast delay to finish up so we dont spam
        if (castDelayLeft > 0) {
            castDelayLeft--;
            return;
        }

        // Cast the rod
        useRod(client, player, config);
    }

    private void tryCatch(MinecraftClient client, PlayerEntity player, ModConfig config) {
        // No bobber, nothing to catch
        if (player.fishHook == null) return;

        // Check if hooked an entity (instant reel)
        if (player.fishHook.getHookedEntity() != null) {
            useRod(client, player, config);
            return;
        }

        // Only check for fish if bobber is in water
        if (!player.fishHook.isInFluid()) return;

        // Check if fish caught
        if (!wasHooked) {
            if (((FishingBobberEntityAccessor) player.fishHook).hasCaughtFish()) {
                catchDelayLeft = getRandomReelDelay(config);
                wasHooked = true;
            }
            return;
        }

        // Wait for catch delay
        if (catchDelayLeft > 0) {
            catchDelayLeft--;
            return;
        }

        // Reel in the fish
        useRod(client, player, config);
    }

    private void useRod(MinecraftClient client, PlayerEntity player, ModConfig config) {
        // Swing hand for anticheat since interactitem just sends a packet
        player.swingHand(Hand.MAIN_HAND);
        client.interactionManager.interactItem(player, Hand.MAIN_HAND);
        
        // Reset state
        wasHooked = false;
        castDelayLeft = getRandomCastDelay(config);
    }

    private int getRandomCastDelay(ModConfig config) {
        int min = config.getAutoFisherMinDelay() / 50; // Convert ms to ticks
        int max = config.getAutoFisherMaxDelay() / 50;
        return min + (int)(Math.random() * (max - min + 1));
    }

    private int getRandomReelDelay(ModConfig config) {
        int base = config.getAutoFisherReelDelay() / 50; // Convert ms to ticks
        int variance = 2; // Add 0-2 ticks variance (0-100ms)
        return base + (int)(Math.random() * variance);
    }
}