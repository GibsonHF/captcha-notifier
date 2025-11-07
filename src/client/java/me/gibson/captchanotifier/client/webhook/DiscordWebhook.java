package me.gibson.captchanotifier.client.webhook;

import com.google.gson.JsonObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

public class DiscordWebhook {

    public static CompletableFuture<Boolean> sendWebhook(String webhookUrl, String message) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                if (!isValidWebhook(webhookUrl)) {
                    return false;
                }

                HttpURLConnection connection = createConnection(webhookUrl);
                JsonObject payload = createPayload(message);

                sendPayload(connection, payload);

                int responseCode = connection.getResponseCode();
                connection.disconnect();

                return responseCode >= 200 && responseCode < 300;
            } catch (Exception e) {
                return false;
            }
        });
    }

    private static boolean isValidWebhook(String webhookUrl) {
        if (webhookUrl == null || webhookUrl.trim().isEmpty()) {
            return false;
        }

        return webhookUrl.startsWith("https://discord.com/api/webhooks/") ||
               webhookUrl.startsWith("https://discordapp.com/api/webhooks/");
    }

    private static HttpURLConnection createConnection(String webhookUrl) throws Exception {
        URL url = new URL(webhookUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("User-Agent", "CaptchaNotifier/1.0");
        connection.setDoOutput(true);
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        return connection;
    }

    private static JsonObject createPayload(String message) {
        JsonObject json = new JsonObject();
        json.addProperty("content", message);
        json.addProperty("username", "Captcha Notifier");
        return json;
    }

    private static void sendPayload(HttpURLConnection connection, JsonObject payload) throws Exception {
        byte[] data = payload.toString().getBytes(StandardCharsets.UTF_8);
        try (OutputStream stream = connection.getOutputStream()) {
            stream.write(data);
        }
    }
}

