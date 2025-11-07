package me.gibson.captchanotifier.client.autotype;

import me.gibson.captchanotifier.client.CaptchaNotifierClient;
import me.gibson.captchanotifier.config.ModConfig;
import net.minecraft.client.MinecraftClient;

import java.util.concurrent.CompletableFuture;

public class AutoTyper {
    public static CompletableFuture<Void> typeWord(String word) {
        return typeWord(word, false);
    }

    public static CompletableFuture<Void> typeWord(String word, boolean isReactionGame) {
        return CompletableFuture.runAsync(() -> {
            try {
                long delayMs = calculateDelay(isReactionGame);
                Thread.sleep(delayMs);

                MinecraftClient client = MinecraftClient.getInstance();
                if (client.getNetworkHandler() != null) {
                    client.getNetworkHandler().sendChatMessage(word);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }

    private static long calculateDelay(boolean isReactionGame) {
        ModConfig config = CaptchaNotifierClient.getConfig();

        if (isReactionGame) {
            int min = config.getReactionMinDelay();
            int max = config.getReactionMaxDelay();
            return min + (long) (Math.random() * (max - min));
        } else {
            int min = config.getMathMinDelay();
            int max = config.getMathMaxDelay();
            return min + (long) (Math.random() * (max - min));
        }
    }
}

