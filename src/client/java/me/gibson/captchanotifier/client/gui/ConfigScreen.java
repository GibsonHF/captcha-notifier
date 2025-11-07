package me.gibson.captchanotifier.client.gui;

import me.gibson.captchanotifier.client.CaptchaNotifierClient;
import me.gibson.captchanotifier.client.webhook.DiscordWebhook;
import me.gibson.captchanotifier.config.ModConfig;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

public class ConfigScreen extends Screen {
    private static final long DEBUG_MESSAGE_DURATION = 5000;
    private static final int SLIDER_WIDTH = 145;
    private static final int SLIDER_HEIGHT = 20;

    private final Screen parent;
    private final ModConfig config;

    private TextFieldWidget webhookUrlField;
    private TextFieldWidget discordUserIdField;

    private boolean webhookEnabled;
    private boolean pingEnabled;
    private boolean autoTypeEnabled;
    private boolean votePartyEnabled;

    private String statusMessage = "";
    private long statusMessageTime = 0;

    public ConfigScreen(Screen parent) {
        super(Text.literal("Captcha Notifier Config"));
        this.parent = parent;
        this.config = CaptchaNotifierClient.getConfig();
        this.webhookEnabled = config.isWebhookEnabled();
        this.pingEnabled = config.isPingEnabled();
        this.autoTypeEnabled = config.isAutoTypeEnabled();
        this.votePartyEnabled = config.isVotePartyEnabled();
    }

    @Override
    protected void init() {
        super.init();
        initWebhookFields();
        initDelaySliders();
        initToggleButtons();
        initActionButtons();
    }

    private void initWebhookFields() {
        this.webhookUrlField = new TextFieldWidget(
            this.textRenderer, this.width / 2 - 150, this.height / 2 - 90, 300, 20,
            Text.literal("Webhook URL")
        );
        this.webhookUrlField.setMaxLength(512);
        this.webhookUrlField.setText(config.getWebhookUrl());
        this.webhookUrlField.setEditable(webhookEnabled);
        this.addSelectableChild(this.webhookUrlField);

        this.discordUserIdField = new TextFieldWidget(
            this.textRenderer, this.width / 2 - 150, this.height / 2 - 50, 300, 20,
            Text.literal("Discord User ID")
        );
        this.discordUserIdField.setMaxLength(32);
        this.discordUserIdField.setText(config.getDiscordUserId());
        this.discordUserIdField.setEditable(pingEnabled);
        this.addSelectableChild(this.discordUserIdField);
    }

    private void initDelaySliders() {
        this.addDrawableChild(createDelaySlider(
            this.width / 2 - 150, this.height / 2 + 10,
            "Reaction Min: ", config.getReactionMinDelay(),
            value -> config.setReactionMinDelay(value),
            () -> config.getReactionMinDelay()
        ));

        this.addDrawableChild(createDelaySlider(
            this.width / 2 + 5, this.height / 2 + 10,
            "Reaction Max: ", config.getReactionMaxDelay(),
            value -> config.setReactionMaxDelay(value),
            () -> config.getReactionMaxDelay()
        ));

        this.addDrawableChild(createDelaySlider(
            this.width / 2 - 150, this.height / 2 + 35,
            "Math Min: ", config.getMathMinDelay(),
            value -> config.setMathMinDelay(value),
            () -> config.getMathMinDelay()
        ));

        this.addDrawableChild(createDelaySlider(
            this.width / 2 + 5, this.height / 2 + 35,
            "Math Max: ", config.getMathMaxDelay(),
            value -> config.setMathMaxDelay(value),
            () -> config.getMathMaxDelay()
        ));

        this.addDrawableChild(createDelaySlider(
            this.width / 2 - 150, this.height / 2 + 60,
            "Scramble Min: ", config.getScrambleMinDelay(),
            value -> config.setScrambleMinDelay(value),
            () -> config.getScrambleMinDelay()
        ));

        this.addDrawableChild(createDelaySlider(
            this.width / 2 + 5, this.height / 2 + 60,
            "Scramble Max: ", config.getScrambleMaxDelay(),
            value -> config.setScrambleMaxDelay(value),
            () -> config.getScrambleMaxDelay()
        ));

        this.addDrawableChild(createDelaySlider(
            this.width / 2 - 150, this.height / 2 + 85,
            "Trivia Min: ", config.getTriviaMinDelay(),
            value -> config.setTriviaMinDelay(value),
            () -> config.getTriviaMinDelay()
        ));

        this.addDrawableChild(createDelaySlider(
            this.width / 2 + 5, this.height / 2 + 85,
            "Trivia Max: ", config.getTriviaMaxDelay(),
            value -> config.setTriviaMaxDelay(value),
            () -> config.getTriviaMaxDelay()
        ));
    }

    private SliderWidget createDelaySlider(int x, int y, String label, int initialValue,
                                           java.util.function.Consumer<Integer> setter,
                                           java.util.function.IntSupplier getter) {
        return new SliderWidget(x, y, SLIDER_WIDTH, SLIDER_HEIGHT,
            Text.literal(label + initialValue + "ms"), (initialValue - 100.0) / 19900.0) {
            @Override
            protected void updateMessage() {
                int value = 100 + (int) (this.value * 19900);
                this.setMessage(Text.literal(label + value + "ms"));
            }

            @Override
            protected void applyValue() {
                int value = 100 + (int) (this.value * 19900);
                setter.accept(value);
            }
        };
    }

    private void initToggleButtons() {
        addToggleButton(this.width / 2 - 155, this.height / 2 + 115, "Webhook",
            () -> webhookEnabled, value -> {
                webhookEnabled = value;
                webhookUrlField.setEditable(value);
            });

        addToggleButton(this.width / 2 - 75, this.height / 2 + 115, "Ping",
            () -> pingEnabled, value -> {
                pingEnabled = value;
                discordUserIdField.setEditable(value);
            });

        addToggleButton(this.width / 2 + 5, this.height / 2 + 115, "AutoType",
            () -> autoTypeEnabled, value -> autoTypeEnabled = value);

        addToggleButton(this.width / 2 - 155, this.height / 2 + 140, "VoteParty",
            () -> votePartyEnabled, value -> votePartyEnabled = value);
    }

    private void addToggleButton(int x, int y, String label,
                                java.util.function.BooleanSupplier getter,
                                java.util.function.Consumer<Boolean> setter) {
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal(getToggleText(getter.getAsBoolean(), label)),
            button -> {
                boolean newState = !getter.getAsBoolean();
                setter.accept(newState);
                button.setMessage(Text.literal(getToggleText(newState, label)));
            }
        ).dimensions(x, y, 70, 20).build());
    }

    private String getToggleText(boolean enabled, String label) {
        return (enabled ? "[✓] " : "[✗] ") + label;
    }

    private void initActionButtons() {
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Save"),
            button -> saveConfig())
            .dimensions(this.width / 2 - 65, this.height / 2 + 140, 70, 20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Test"),
            button -> testWebhook())
            .dimensions(this.width / 2 + 15, this.height / 2 + 140, 70, 20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Close"),
            button -> this.client.setScreen(parent))
            .dimensions(this.width / 2 + 95, this.height / 2 + 140, 70, 20).build());
    }

    private void saveConfig() {
        config.setWebhookUrl(this.webhookUrlField.getText());
        config.setDiscordUserId(this.discordUserIdField.getText());
        config.setWebhookEnabled(webhookEnabled);
        config.setPingEnabled(pingEnabled);
        config.setAutoTypeEnabled(autoTypeEnabled);
        config.setVotePartyEnabled(votePartyEnabled);
        config.save();
        showStatus("✓ Config saved successfully!", true);
    }

    private void testWebhook() {
        if (!webhookEnabled) {
            showStatus("✗ Webhook is disabled!", false);
            return;
        }

        String url = this.webhookUrlField.getText();
        if (url == null || url.isEmpty()) {
            showStatus("✗ Webhook URL is empty!", false);
            return;
        }

        if (!isValidWebhookUrl(url)) {
            showStatus("✗ Invalid webhook URL format!", false);
            return;
        }

        showStatus("Testing webhook...", true);
        String testMessage = buildTestMessage();

        DiscordWebhook.sendWebhook(url, testMessage).thenAccept(success -> {
            if (success) {
                showStatus("✓ Test message sent successfully!", true);
            } else {
                showStatus("✗ Failed to send test message. Check your webhook URL.", false);
            }
        });
    }

    private boolean isValidWebhookUrl(String url) {
        return url.startsWith("https://discord.com/api/webhooks/") ||
               url.startsWith("https://discordapp.com/api/webhooks/");
    }

    private String buildTestMessage() {
        String message = "🧪 Test message from Captcha Notifier! Your webhook is working correctly.";
        if (pingEnabled && !this.discordUserIdField.getText().isEmpty()) {
            message = "<@" + this.discordUserIdField.getText() + "> " + message;
        }
        return message;
    }

    private void showStatus(String message, boolean success) {
        this.statusMessage = message;
        this.statusMessageTime = System.currentTimeMillis();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        context.drawCenteredTextWithShadow(this.textRenderer, this.title,
            this.width / 2, this.height / 2 - 125, 0xFFFFFF);

        context.drawTextWithShadow(this.textRenderer, "Discord Webhook URL:",
            this.width / 2 - 150, this.height / 2 - 105, 0xAAAAAA);
        this.webhookUrlField.render(context, mouseX, mouseY, delta);

        context.drawTextWithShadow(this.textRenderer, "Discord User ID:",
            this.width / 2 - 150, this.height / 2 - 65, 0xAAAAAA);
        this.discordUserIdField.render(context, mouseX, mouseY, delta);

        context.drawTextWithShadow(this.textRenderer, "Response Delay (ms):",
            this.width / 2 - 150, this.height / 2 - 5, 0xAAAAAA);

        renderStatusMessage(context);
    }

    private void renderStatusMessage(DrawContext context) {
        if (statusMessage.isEmpty()) {
            return;
        }

        long elapsed = System.currentTimeMillis() - statusMessageTime;
        if (elapsed < DEBUG_MESSAGE_DURATION) {
            int color = statusMessage.contains("✓") ? 0x00FF00 : 0xFF0000;
            context.drawCenteredTextWithShadow(this.textRenderer, statusMessage,
                this.width / 2, this.height / 2 + 120, color);
        } else {
            statusMessage = "";
        }
    }

    @Override
    public void close() {
        this.client.setScreen(parent);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        this.webhookUrlField.mouseClicked(mouseX, mouseY, button);
        this.discordUserIdField.mouseClicked(mouseX, mouseY, button);
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (this.webhookUrlField.keyPressed(keyCode, scanCode, modifiers) ||
            this.discordUserIdField.keyPressed(keyCode, scanCode, modifiers)) {
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        if (this.webhookUrlField.charTyped(chr, modifiers) ||
            this.discordUserIdField.charTyped(chr, modifiers)) {
            return true;
        }
        return super.charTyped(chr, modifiers);
    }
}




