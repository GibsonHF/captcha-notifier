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
    private boolean captchaAutoSolveEnabled = false;
    private boolean autoFisherEnabled = false;
    private int reactionMinDelay = 1000;
    private int reactionMaxDelay = 1800;
    private int mathMinDelay = 1500;
    private int mathMaxDelay = 2700;
    private int scrambleMinDelay = 1200;
    private int scrambleMaxDelay = 2500;
    private int triviaMinDelay = 1300;
    private int triviaMaxDelay = 2600;
    private int captchaMinDelay = 2000;
    private int captchaMaxDelay = 3500;
    private int autoFisherMinDelay = 500;
    private int autoFisherMaxDelay = 1500;
    private int autoFisherReelDelay = 300;

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

    public boolean isCaptchaAutoSolveEnabled() {
        return captchaAutoSolveEnabled;
    }

    public void setCaptchaAutoSolveEnabled(boolean enabled) {
        this.captchaAutoSolveEnabled = enabled;
    }

    public boolean isAutoFisherEnabled() {
        return autoFisherEnabled;
    }

    public void setAutoFisherEnabled(boolean enabled) {
        this.autoFisherEnabled = enabled;
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
        this.reactionMaxDelay = Math.max(reactionMinDelay, Math.min(delay, 20000));
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
        this.mathMaxDelay = Math.max(mathMinDelay, Math.min(delay, 20000));
    }

    public int getScrambleMinDelay() {
        return scrambleMinDelay;
    }

    public void setScrambleMinDelay(int delay) {
        this.scrambleMinDelay = Math.max(100, Math.min(delay, scrambleMaxDelay));
    }

    public int getScrambleMaxDelay() {
        return scrambleMaxDelay;
    }

    public void setScrambleMaxDelay(int delay) {
        this.scrambleMaxDelay = Math.max(scrambleMinDelay, Math.min(delay, 20000));
    }

    public int getTriviaMinDelay() {
        return triviaMinDelay;
    }

    public void setTriviaMinDelay(int delay) {
        this.triviaMinDelay = Math.max(100, Math.min(delay, triviaMaxDelay));
    }

    public int getTriviaMaxDelay() {
        return triviaMaxDelay;
    }

    public void setTriviaMaxDelay(int delay) {
        this.triviaMaxDelay = Math.max(triviaMinDelay, Math.min(delay, 20000));
    }

    public int getCaptchaMinDelay() {
        return captchaMinDelay;
    }

    public void setCaptchaMinDelay(int delay) {
        this.captchaMinDelay = Math.max(100, Math.min(delay, captchaMaxDelay));
    }

    public int getCaptchaMaxDelay() {
        return captchaMaxDelay;
    }

    public void setCaptchaMaxDelay(int delay) {
        this.captchaMaxDelay = Math.max(captchaMinDelay, Math.min(delay, 20000));
    }

    public int getAutoFisherMinDelay() {
        return autoFisherMinDelay;
    }

    public void setAutoFisherMinDelay(int delay) {
        this.autoFisherMinDelay = Math.max(100, Math.min(delay, autoFisherMaxDelay));
    }

    public int getAutoFisherMaxDelay() {
        return autoFisherMaxDelay;
    }

    public void setAutoFisherMaxDelay(int delay) {
        this.autoFisherMaxDelay = Math.max(autoFisherMinDelay, Math.min(delay, 10000));
    }

    public int getAutoFisherReelDelay() {
        return autoFisherReelDelay;
    }

    public void setAutoFisherReelDelay(int delay) {
        this.autoFisherReelDelay = Math.max(0, Math.min(delay, 2000));
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

