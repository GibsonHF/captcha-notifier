package me.gibson.captchanotifier.client.autotype;

import me.gibson.captchanotifier.client.CaptchaNotifierClient;
import me.gibson.captchanotifier.config.ModConfig;
import net.minecraft.client.MinecraftClient;

import java.util.concurrent.CompletableFuture;

public class AutoTyper {
    
    public enum GameType {
        REACTION,
        MATH,
        SCRAMBLE,
        TRIVIA
    }

    public static CompletableFuture<Void> typeWord(String word) {
        return typeWord(word, GameType.MATH);
    }

    public static CompletableFuture<Void> typeWord(String word, boolean isReactionGame) {
        return typeWord(word, isReactionGame ? GameType.REACTION : GameType.MATH);
    }

    public static CompletableFuture<Void> typeWord(String word, GameType gameType) {
        return CompletableFuture.runAsync(() -> {
            try {
                long delayMs = calculateDelay(gameType);
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

    private static long calculateDelay(GameType gameType) {
        ModConfig config = CaptchaNotifierClient.getConfig();
        int min, max;

        switch (gameType) {
            case REACTION:
                min = config.getReactionMinDelay();
                max = config.getReactionMaxDelay();
                break;
            case MATH:
                min = config.getMathMinDelay();
                max = config.getMathMaxDelay();
                break;
            case SCRAMBLE:
                min = config.getScrambleMinDelay();
                max = config.getScrambleMaxDelay();
                break;
            case TRIVIA:
                min = config.getTriviaMinDelay();
                max = config.getTriviaMaxDelay();
                break;
            default:
                min = config.getMathMinDelay();
                max = config.getMathMaxDelay();
                break;
        }

        return min + (long) (Math.random() * (max - min));
    }
}

