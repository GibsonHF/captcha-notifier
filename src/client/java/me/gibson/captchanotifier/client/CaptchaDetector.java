package me.gibson.captchanotifier.client;

import me.gibson.captchanotifier.CaptchaNotifier;
import me.gibson.captchanotifier.config.ModConfig;
import me.gibson.captchanotifier.client.webhook.DiscordWebhook;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;

public class CaptchaDetector {
    private static final CaptchaDetector INSTANCE = new CaptchaDetector();

    private boolean hasNotified = false;
    private String lastCaptchaId = "";

    private CaptchaDetector() {
    }

    public static CaptchaDetector getInstance() {
        return INSTANCE;
    }

    public void onScreenOpen(HandledScreen<?> screen) {
        ScreenHandler handler = screen.getScreenHandler();
        String title = screen.getTitle().getString();

        if (title.trim().equalsIgnoreCase("Captcha")) {
            processCaptchaScreen(handler);
        } else {
            reset();
        }
    }

    private void processCaptchaScreen(ScreenHandler handler) {
        String captchaId = "Captcha_" + System.currentTimeMillis() / 10000;

        if (!hasNotified || !lastCaptchaId.equals(captchaId)) {
            hasNotified = true;
            lastCaptchaId = captchaId;

            String loreInfo = extractLoreInfo(handler);
            sendCaptchaNotification(loreInfo);
        }
    }

    private void sendCaptchaNotification(String loreInfo) {
        ModConfig config = CaptchaNotifierClient.getConfig();
        String webhookUrl = config.getWebhookUrl();

        if (webhookUrl != null && !webhookUrl.isEmpty()) {
            String message = buildNotificationMessage(loreInfo);
            DiscordWebhook.sendWebhook(webhookUrl, message);
        }
    }

    private String buildNotificationMessage(String loreInfo) {
        StringBuilder message = new StringBuilder();
        ModConfig config = CaptchaNotifierClient.getConfig();

        if (config.isPingEnabled() && config.getDiscordUserId() != null && !config.getDiscordUserId().isEmpty()) {
            message.append("<@").append(config.getDiscordUserId()).append("> ");
        }

        message.append("🚨 **CAPTCHA DETECTED!** 🚨\n")
               .append("You need to solve the captcha!\n")
               .append(loreInfo);

        return message.toString();
    }

    private String extractLoreInfo(ScreenHandler handler) {
        StringBuilder lore = new StringBuilder();

        try {
            for (int i = 0; i < handler.slots.size(); i++) {
                ItemStack stack = handler.getSlot(i).getStack();
                if (!stack.isEmpty() && stack.getItem().toString().toLowerCase().contains("sign")) {
                    extractSignInfo(stack, lore);
                    break;
                }
            }
        } catch (Exception e) {
            return "";
        }

        return lore.toString();
    }

    private void extractSignInfo(ItemStack stack, StringBuilder lore) {
        String displayName = stack.getName().getString();
        String cleanName = stripColorCodes(displayName);
        
        // Try to extract the actual question from the sign's display name
        String question = extractQuestionFromDisplayName(cleanName);
        
        if (!question.isEmpty()) {
            lore.append("\n**Question:** ").append(question).append("\n");
        }

        LoreComponent loreComponent = stack.get(DataComponentTypes.LORE);
        if (loreComponent != null && !loreComponent.lines().isEmpty()) {
            for (Text line : loreComponent.lines()) {
                String lineText = stripColorCodes(line.getString());
                if (lineText.contains("Time Left") || lineText.contains("Tries Left")) {
                    lore.append(lineText).append("\n");
                }
            }
        }
    }

    private String extractQuestionFromDisplayName(String displayName) {
        // The display name IS the question - just return it cleaned
        if (displayName == null || displayName.isEmpty()) {
            return "";
        }
        
        return displayName.trim();
    }

    private String stripColorCodes(String text) {
        return text.replaceAll("§[0-9a-fk-or]", "");
    }

    public void reset() {
        hasNotified = false;
        lastCaptchaId = "";
    }
}

