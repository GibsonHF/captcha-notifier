package me.gibson.captchanotifier.client.listener;

import me.gibson.captchanotifier.client.CaptchaNotifierClient;
import me.gibson.captchanotifier.config.ModConfig;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.c2s.play.CommandExecutionC2SPacket;
import net.minecraft.text.Text;

import java.util.concurrent.CompletableFuture;

public class VotePartyListener {
    private static long lastClaimTime = 0;
    private static final long CLAIM_COOLDOWN_MS = 30000;

    public static void register() {
        ClientReceiveMessageEvents.GAME.register((message, overlay) -> onChatMessage(message));
    }

    private static void onChatMessage(Text message) {
        ModConfig config = CaptchaNotifierClient.getConfig();

        if (!config.isVotePartyEnabled()) {
            return;
        }

        String cleanedMessage = stripColorCodes(message.getString());
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastClaimTime < CLAIM_COOLDOWN_MS) {
            return;
        }

        if (isVotePartyMessage(cleanedMessage)) {
            lastClaimTime = currentTime;
            claimVoteParty();
        }
    }

    private static boolean isVotePartyMessage(String message) {
        String lowerMessage = message.toLowerCase();
        return lowerMessage.contains("type /vp") && lowerMessage.contains("vote party");
    }

    private static String stripColorCodes(String text) {
        return text.replaceAll("§[0-9a-fk-or]", "");
    }

    private static void claimVoteParty() {
        CompletableFuture.runAsync(() -> {
            try {
                long delayMs = 1000 + (long) (Math.random() * 500);
                Thread.sleep(delayMs);

                MinecraftClient client = MinecraftClient.getInstance();
                if (client.getNetworkHandler() != null) {
                    client.getNetworkHandler().sendPacket(new CommandExecutionC2SPacket("vp"));
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }

    public static void reset() {
        lastClaimTime = 0;
    }
}
