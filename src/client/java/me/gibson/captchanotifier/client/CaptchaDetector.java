package me.gibson.captchanotifier.client;

import me.gibson.captchanotifier.config.ModConfig;
import me.gibson.captchanotifier.client.webhook.DiscordWebhook;
import net.minecraft.client.MinecraftClient;
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

            // Try to auto-solve if enabled
            tryAutoSolveCaptcha(handler);
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

    private void tryAutoSolveCaptcha(ScreenHandler handler) {
        ModConfig config = CaptchaNotifierClient.getConfig();
        if (!config.isCaptchaAutoSolveEnabled()) {
            return;
        }

        // Find the sign with the question
        String question = null;
        for (int i = 0; i < handler.slots.size(); i++) {
            ItemStack stack = handler.getSlot(i).getStack();
            if (!stack.isEmpty() && stack.getItem().toString().toLowerCase().contains("sign")) {
                String displayName = stack.getName().getString();
                String cleanName = stripColorCodes(displayName);
                question = extractQuestionFromDisplayName(cleanName);
                break;
            }
        }

        if (question == null || !question.toLowerCase().startsWith("solve")) {
            return;
        }

        // Extract math expression from question like "Solve 1x4"
        String expression = extractMathExpressionFromQuestion(question);
        if (expression == null || expression.isEmpty()) {
            return;
        }

        // Solve the expression
        String answer = solveMathExpression(expression);
        if (answer == null || answer.isEmpty()) {
            return;
        }

        // Find the slot with the head that matches the answer
        int targetSlot = findHeadSlotForAnswer(handler, answer);
        if (targetSlot == -1) {
            return;
        }

        // Click the slot with delay
        clickSlotWithDelay(targetSlot, config.getCaptchaMinDelay(), config.getCaptchaMaxDelay());
    }

    private String extractMathExpressionFromQuestion(String question) {
        // Remove "Solve " prefix and clean up
        if (!question.toLowerCase().startsWith("solve")) {
            return null;
        }

        String expr = question.substring(5).trim(); // Remove "Solve"
        // Clean up the expression - keep only numbers, operators, and letters
        expr = expr.replaceAll("[^0-9a-zA-Z+\\-*/]", "");
        return expr.isEmpty() ? null : expr;
    }

    private String solveMathExpression(String expression) {
        try {
            // Use JavaScript engine for evaluation
            javax.script.ScriptEngine engine = new javax.script.ScriptEngineManager().getEngineByName("JavaScript");
            if (engine != null) {
                Object result = engine.eval(expression);
                if (result instanceof Number) {
                    return String.valueOf(((Number) result).intValue());
                }
            }
        } catch (Exception e) {
            // Fall back to simple parsing
        }

        // Simple parsing for basic operations
        return parseSimpleExpression(expression);
    }

    private String parseSimpleExpression(String expr) {
        // Handle multiplication like "1x4" -> "1*4"
        expr = expr.replace('x', '*').replace('X', '*');

        try {
            // Split by operators
            String[] parts = expr.split("(?=[+\\-*/])|(?<=[+\\-*/])");
            if (parts.length == 3) {
                double a = Double.parseDouble(parts[0]);
                String op = parts[1];
                double b = Double.parseDouble(parts[2]);

                double result;
                switch (op) {
                    case "+": result = a + b; break;
                    case "-": result = a - b; break;
                    case "*": result = a * b; break;
                    case "/": result = a / b; break;
                    default: return null;
                }
                return String.valueOf((int) Math.round(result));
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    private int findHeadSlotForAnswer(ScreenHandler handler, String answer) {
        for (int i = 0; i < handler.slots.size(); i++) {
            ItemStack stack = handler.getSlot(i).getStack();
            if (!stack.isEmpty()) {
                String character = CaptchaHeadDatabase.getCharacter(stack);
                if (answer.equals(character)) {
                    return i;
                }
            }
        }
        return -1;
    }

    private void clickSlotWithDelay(int slotIndex, int minDelay, int maxDelay) {
        int delay = minDelay + (int) (Math.random() * (maxDelay - minDelay));

        // Schedule the click
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                MinecraftClient client = MinecraftClient.getInstance();
                if (client.player != null && client.player.currentScreenHandler != null) {
                    // Click the slot (left click)
                    client.interactionManager.clickSlot(client.player.currentScreenHandler.syncId, slotIndex, 0, net.minecraft.screen.slot.SlotActionType.PICKUP, client.player);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    public void reset() {
        hasNotified = false;
        lastCaptchaId = "";
    }
}

