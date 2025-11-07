package me.gibson.captchanotifier.client.listener;

import me.gibson.captchanotifier.client.CaptchaNotifierClient;
import me.gibson.captchanotifier.client.autotype.AutoTyper;
import me.gibson.captchanotifier.config.ModConfig;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.text.Text;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class ChatListener {
    private static boolean hasReactionTyped = false;

    public static void register() {
        ClientReceiveMessageEvents.GAME.register((message, overlay) -> onChatMessage(message));
    }

    private static void onChatMessage(Text message) {
        ModConfig config = CaptchaNotifierClient.getConfig();

        if (!config.isAutoTypeEnabled()) {
            return;
        }

        String messageText = message.getString();

        if (isReactionGame(messageText)) {
            if (hasReactionTyped) {
                return;
            }

            String word = extractWordFromMessage(messageText);
            if (word != null && !word.isEmpty()) {
                hasReactionTyped = true;
                AutoTyper.typeWord(word, true);
            }
        } else if (isMathGame(messageText)) {
            String expression = extractMathExpression(messageText);
            if (expression != null && !expression.isEmpty()) {
                try {
                    double result = evaluateMathExpression(expression);
                    String answer = String.valueOf((int) Math.round(result));
                    AutoTyper.typeWord(answer, false);
                } catch (Exception e) {
                    // Math evaluation failed, skip this round
                }
            }
        }
    }

    private static boolean isReactionGame(String message) {
        return message.contains("REACTION") && message.contains("First to type");
    }

    private static boolean isMathGame(String message) {
        return message.toUpperCase().contains("MATH") && message.contains("=");
    }

    private static String extractWordFromMessage(String message) {
        int startIndex = message.indexOf("First to type");
        if (startIndex == -1) {
            return null;
        }

        startIndex += "First to type ".length();
        int endIndex = message.indexOf("in the chat", startIndex);

        if (endIndex == -1) {
            return null;
        }

        String word = message.substring(startIndex, endIndex).trim();
        return word.replaceAll("[^a-zA-Z0-9]", "").trim();
    }

    private static String extractMathExpression(String message) {
        int startIndex = -1;

        if (message.contains("MATH »")) {
            startIndex = message.indexOf("MATH »") + "MATH »".length();
        } else if (message.toLowerCase().contains("math >>")) {
            startIndex = message.toLowerCase().indexOf("math >>") + "math >>".length();
        }

        if (startIndex == -1) {
            return null;
        }

        String expression = message.substring(startIndex).trim();
        int equalIndex = expression.indexOf("=");

        if (equalIndex != -1) {
            expression = expression.substring(0, equalIndex);
        }

        expression = expression.replaceAll("[?!.\\s]", "");
        expression = expression.replaceAll("[^0-9+\\-*/]", "");

        return expression.isEmpty() ? null : expression;
    }

    private static double evaluateMathExpression(String expression) throws Exception {
        try {
            ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
            if (engine != null) {
                return ((Number) engine.eval(expression)).doubleValue();
            }
        } catch (Exception e) {
            // Fall back to manual parsing
        }

        return parseExpression(expression);
    }

    private static double parseExpression(String expr) {
        String[] parts = expr.split("(?=[-+])|(?<=[-+])");
        double result = 0;
        String operator = "+";

        for (String part : parts) {
            part = part.trim();
            if (part.equals("+") || part.equals("-")) {
                operator = part;
            } else if (!part.isEmpty()) {
                double value = parseTerm(part);
                result = operator.equals("+") ? result + value : result - value;
            }
        }
        return result;
    }

    private static double parseTerm(String term) {
        String[] factors = term.split("(?=[*/])|(?<=[*/])");
        double result = 1;
        String operator = "*";

        for (String factor : factors) {
            factor = factor.trim();
            if (factor.equals("*") || factor.equals("/")) {
                operator = factor;
            } else if (!factor.isEmpty()) {
                double value = Double.parseDouble(factor);
                result = operator.equals("*") ? result * value : result / value;
            }
        }
        return result;
    }

    public static void reset() {
        hasReactionTyped = false;
    }
}

