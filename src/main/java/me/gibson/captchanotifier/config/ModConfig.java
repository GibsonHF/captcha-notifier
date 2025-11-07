package me.gibson.captchanotifier.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ModConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = new File(
        FabricLoader.getInstance().getConfigDir().toFile(),
        "captcha-notifier.json"
    );

    private String webhookUrl = "";
    private String discordUserId = "";
    private boolean webhookEnabled = true;
    private boolean pingEnabled = false;
    private boolean autoTypeEnabled = false;
    private boolean votePartyEnabled = false;
    private int reactionMinDelay = 1000;
    private int reactionMaxDelay = 1800;
    private int mathMinDelay = 1500;
    private int mathMaxDelay = 2700;

    public String getWebhookUrl() {
        return webhookUrl;
    }

    public void setWebhookUrl(String url) {
        this.webhookUrl = url;
    }

    public String getDiscordUserId() {
        return discordUserId;
    }

    public void setDiscordUserId(String userId) {
        this.discordUserId = userId;
    }

    public boolean isWebhookEnabled() {
        return webhookEnabled;
    }

    public void setWebhookEnabled(boolean enabled) {
        this.webhookEnabled = enabled;
    }

    public boolean isPingEnabled() {
        return pingEnabled;
    }

    public void setPingEnabled(boolean enabled) {
        this.pingEnabled = enabled;
    }

    public boolean isAutoTypeEnabled() {
        return autoTypeEnabled;
    }

    public void setAutoTypeEnabled(boolean enabled) {
        this.autoTypeEnabled = enabled;
    }

    public boolean isVotePartyEnabled() {
        return votePartyEnabled;
    }

    public void setVotePartyEnabled(boolean enabled) {
        this.votePartyEnabled = enabled;
    }

    public int getReactionMinDelay() {
        return reactionMinDelay;
    }

    public void setReactionMinDelay(int delay) {
        this.reactionMinDelay = Math.max(100, Math.min(delay, reactionMaxDelay));
    }

    public int getReactionMaxDelay() {
        return reactionMaxDelay;
    }

    public void setReactionMaxDelay(int delay) {
        this.reactionMaxDelay = Math.max(reactionMinDelay, Math.min(delay, 5000));
    }

    public int getMathMinDelay() {
        return mathMinDelay;
    }

    public void setMathMinDelay(int delay) {
        this.mathMinDelay = Math.max(100, Math.min(delay, mathMaxDelay));
    }

    public int getMathMaxDelay() {
        return mathMaxDelay;
    }

    public void setMathMaxDelay(int delay) {
        this.mathMaxDelay = Math.max(mathMinDelay, Math.min(delay, 5000));
    }

    public static ModConfig load() {
        if (CONFIG_FILE.exists()) {
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                return GSON.fromJson(reader, ModConfig.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ModConfig();
    }

    public void save() {
        try {
            CONFIG_FILE.getParentFile().mkdirs();
            try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
                GSON.toJson(this, writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

