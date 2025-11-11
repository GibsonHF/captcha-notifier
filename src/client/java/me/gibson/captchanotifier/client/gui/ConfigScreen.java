package me.gibson.captchanotifier.client.gui;

import me.gibson.captchanotifier.client.CaptchaNotifierClient;
import me.gibson.captchanotifier.client.webhook.DiscordWebhook;
import me.gibson.captchanotifier.config.ModConfig;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.text.Text;

public class ConfigScreen extends Screen {
    private static final long DEBUG_MESSAGE_DURATION = 5000;
    private static final int SECTION_LABEL_COLOR = 0x55FFFF;
    private static final int SUBTEXT_COLOR = 0xAAAAAA;

    private final Screen parent;
    private final ModConfig config;

    private TextFieldWidget webhookUrlField;
    private TextFieldWidget discordUserIdField;

    private boolean webhookEnabled;
    private boolean pingEnabled;
    private boolean autoTypeEnabled;
    private boolean votePartyEnabled;
    private boolean captchaAutoSolveEnabled;
    private boolean autoFisherEnabled;

    private String statusMessage = "";
    private long statusMessageTime = 0L;

    private int scrollY = 0;
    private int maxScroll = 0;
    private int contentHeight = 450;
    private java.util.List<Widget> scrollableWidgets = new java.util.ArrayList<>();
    private java.util.List<Integer> originalYs = new java.util.ArrayList<>();

    public ConfigScreen(Screen parent) {
        super(Text.literal("Captcha Notifier Config"));
        this.parent = parent;
        this.config = CaptchaNotifierClient.getConfig();

        this.webhookEnabled = config.isWebhookEnabled();
        this.pingEnabled = config.isPingEnabled();
        this.autoTypeEnabled = config.isAutoTypeEnabled();
        this.votePartyEnabled = config.isVotePartyEnabled();
        this.captchaAutoSolveEnabled = config.isCaptchaAutoSolveEnabled();
        this.autoFisherEnabled = config.isAutoFisherEnabled();
    }

    @Override
    protected void init() {
        super.init();
        this.clearChildren();
        scrollableWidgets.clear();
        originalYs.clear();

        int centerX = this.width / 2;
        int y = 55;

        webhookUrlField = new TextFieldWidget(this.textRenderer, centerX - 150, y, 300, 20, Text.literal("Webhook URL"));
        webhookUrlField.setMaxLength(512);
        webhookUrlField.setText(config.getWebhookUrl());
        webhookUrlField.setEditable(webhookEnabled);
        this.addDrawableChild(webhookUrlField);
        this.addSelectableChild(webhookUrlField);
        scrollableWidgets.add(webhookUrlField);
        originalYs.add(y);

        discordUserIdField = new TextFieldWidget(this.textRenderer, centerX - 150, y + 35, 300, 20, Text.literal("Discord User ID"));
        discordUserIdField.setMaxLength(32);
        discordUserIdField.setText(config.getDiscordUserId());
        discordUserIdField.setEditable(pingEnabled);
        this.addDrawableChild(discordUserIdField);
        this.addSelectableChild(discordUserIdField);
        scrollableWidgets.add(discordUserIdField);
        originalYs.add(y + 35);

        int toggleY = y + 80;
        int toggleX = centerX - 155;
        addToggleButton(toggleX, toggleY, "Webhook",
            () -> webhookEnabled,
            value -> {
                webhookEnabled = value;
                config.setWebhookEnabled(value);
                config.save();
                webhookUrlField.setEditable(value);
            });

        addToggleButton(toggleX + 80, toggleY, "Ping",
            () -> pingEnabled,
            value -> {
                pingEnabled = value;
                config.setPingEnabled(value);
                config.save();
                discordUserIdField.setEditable(value);
            });

        addToggleButton(toggleX + 160, toggleY, "AutoType",
            () -> autoTypeEnabled,
            value -> {
                autoTypeEnabled = value;
                config.setAutoTypeEnabled(value);
                config.save();
            });

        addToggleButton(toggleX + 240, toggleY, "VoteParty",
            () -> votePartyEnabled,
            value -> {
                votePartyEnabled = value;
                config.setVotePartyEnabled(value);
                config.save();
            });

        addToggleButton(toggleX, toggleY + 30, "CaptchaSolve",
            () -> captchaAutoSolveEnabled,
            value -> {
                captchaAutoSolveEnabled = value;
                config.setCaptchaAutoSolveEnabled(value);
                config.save();
            });

        addToggleButton(toggleX + 80, toggleY + 30, "AutoFisher",
            () -> autoFisherEnabled,
            value -> {
                autoFisherEnabled = value;
                config.setAutoFisherEnabled(value);
                config.save();
            });

        int sliderStartY = toggleY + 80;
        initDelaySliders(centerX, sliderStartY);
        initActionButtons();
        maxScroll = Math.max(0, contentHeight - (this.height - 40));
    }

    private void initDelaySliders(int centerX, int startY) {
        int leftX = centerX - 155;
        int rightX = centerX + 5;

        addDelaySlider(leftX, startY, "Reaction Min", config.getReactionMinDelay(), config::setReactionMinDelay);
        addDelaySlider(rightX, startY, "Reaction Max", config.getReactionMaxDelay(), config::setReactionMaxDelay);

        addDelaySlider(leftX, startY + 30, "Math Min", config.getMathMinDelay(), config::setMathMinDelay);
        addDelaySlider(rightX, startY + 30, "Math Max", config.getMathMaxDelay(), config::setMathMaxDelay);

        addDelaySlider(leftX, startY + 60, "Scramble Min", config.getScrambleMinDelay(), config::setScrambleMinDelay);
        addDelaySlider(rightX, startY + 60, "Scramble Max", config.getScrambleMaxDelay(), config::setScrambleMaxDelay);

        addDelaySlider(leftX, startY + 90, "Trivia Min", config.getTriviaMinDelay(), config::setTriviaMinDelay);
        addDelaySlider(rightX, startY + 90, "Trivia Max", config.getTriviaMaxDelay(), config::setTriviaMaxDelay);

        addDelaySlider(leftX, startY + 120, "Captcha Min", config.getCaptchaMinDelay(), config::setCaptchaMinDelay);
        addDelaySlider(rightX, startY + 120, "Captcha Max", config.getCaptchaMaxDelay(), config::setCaptchaMaxDelay);

        addDelaySlider(leftX, startY + 150, "Fisher Min", config.getAutoFisherMinDelay(), config::setAutoFisherMinDelay);
        addDelaySlider(rightX, startY + 150, "Fisher Max", config.getAutoFisherMaxDelay(), config::setAutoFisherMaxDelay);

        addDelaySlider(centerX - 75, startY + 180, "Reel Delay", config.getAutoFisherReelDelay(), config::setAutoFisherReelDelay);
    }

    private void addDelaySlider(int x, int y, String label, int initialValue, java.util.function.IntConsumer setter) {
        SliderWidget slider = new SliderWidget(x, y, 150, 20, Text.literal(label + ": " + initialValue + "ms"), (initialValue - 100) / 19900.0) {
            @Override
            protected void updateMessage() {
                int value = 100 + (int) (this.value * 19900);
                this.setMessage(Text.literal(label + ": " + value + "ms"));
            }

            @Override
            protected void applyValue() {
                int value = 100 + (int) (this.value * 19900);
                setter.accept(value);
                config.save();
                this.setMessage(Text.literal(label + ": " + value + "ms"));
            }
        };
        this.addDrawableChild(slider);
        scrollableWidgets.add(slider);
        originalYs.add(y);
    }

    private void addToggleButton(int x, int y, String label,
                                 java.util.function.BooleanSupplier getter,
                                 java.util.function.Consumer<Boolean> setter) {
        ButtonWidget button = ButtonWidget.builder(Text.literal(getToggleText(getter.getAsBoolean(), label)), btn -> {
            boolean newValue = !getter.getAsBoolean();
            setter.accept(newValue);
            btn.setMessage(Text.literal(getToggleText(newValue, label)));
        }).dimensions(x, y, 75, 20).build();
        this.addDrawableChild(button);
        scrollableWidgets.add(button);
        originalYs.add(y);
    }

    private String getToggleText(boolean enabled, String label) {
        return (enabled ? "[✓] " : "[✗] ") + label;
    }

    private void updateWidgetPositions() {
        for (int i = 0; i < scrollableWidgets.size(); i++) {
            scrollableWidgets.get(i).setY(originalYs.get(i) - scrollY);
        }
    }

    private void initActionButtons() {
        int bottomY = this.height - 30;
        int totalWidth = 75 * 3 + 20 * 2;
        int startX = this.width / 2 - totalWidth / 2;

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Save"), button -> saveConfig())
            .dimensions(startX, bottomY, 75, 20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Test"), button -> testWebhook())
            .dimensions(startX + 95, bottomY, 75, 20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Close"), button -> this.client.setScreen(parent))
            .dimensions(startX + 190, bottomY, 75, 20).build());
    }

    private void saveConfig() {
        config.setWebhookUrl(this.webhookUrlField.getText());
        config.setDiscordUserId(this.discordUserIdField.getText());
        config.setWebhookEnabled(webhookEnabled);
        config.setPingEnabled(pingEnabled);
        config.setAutoTypeEnabled(autoTypeEnabled);
        config.setVotePartyEnabled(votePartyEnabled);
        config.setCaptchaAutoSolveEnabled(captchaAutoSolveEnabled);
        config.setAutoFisherEnabled(autoFisherEnabled);
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

        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 30, 0xFFFFFF);
        context.drawTextWithShadow(this.textRenderer, "Discord Integration", this.width / 2 - 150, 45 - scrollY, SECTION_LABEL_COLOR);
        context.drawTextWithShadow(this.textRenderer, "Webhook URL", this.width / 2 - 150, 57 - scrollY, SUBTEXT_COLOR);
        context.drawTextWithShadow(this.textRenderer, "Discord User ID", this.width / 2 - 150, 92 - scrollY, SUBTEXT_COLOR);
        context.drawTextWithShadow(this.textRenderer, "Feature Toggles", this.width / 2 - 155, 135 - scrollY, SECTION_LABEL_COLOR);
        context.drawTextWithShadow(this.textRenderer, "Delay Settings (ms)", this.width / 2 - 155, 200 - scrollY, SECTION_LABEL_COLOR);

        renderStatusMessage(context);
    }

    private void renderStatusMessage(DrawContext context) {
        if (statusMessage.isEmpty()) {
            return;
        }

        long elapsed = System.currentTimeMillis() - statusMessageTime;
        if (elapsed < DEBUG_MESSAGE_DURATION) {
            int color = statusMessage.contains("✓") ? 0x00FF00 : 0xFF0000;
            context.drawCenteredTextWithShadow(this.textRenderer, statusMessage, this.width / 2, this.height - 50, color);
        } else {
            statusMessage = "";
        }
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        scrollY += (int) (verticalAmount * -20);
        scrollY = Math.max(0, Math.min(maxScroll, scrollY));
        updateWidgetPositions();
        return true;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (webhookUrlField.mouseClicked(mouseX, mouseY, button)) return true;
        if (discordUserIdField.mouseClicked(mouseX, mouseY, button)) return true;
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (webhookUrlField.keyPressed(keyCode, scanCode, modifiers)) return true;
        if (discordUserIdField.keyPressed(keyCode, scanCode, modifiers)) return true;
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        if (webhookUrlField.charTyped(chr, modifiers)) return true;
        if (discordUserIdField.charTyped(chr, modifiers)) return true;
        return super.charTyped(chr, modifiers);
    }
}
