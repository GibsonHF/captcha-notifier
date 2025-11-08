package me.gibson.captchanotifier.client.util;

import me.gibson.captchanotifier.client.CaptchaHeadDatabase;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

/**
 * Utility for copying player head texture data to clipboard for captcha database building.
 */
public class HeadTextureClipboard {

    private static ItemStack lastHoveredStack = ItemStack.EMPTY;

    public static void setHoveredStack(ItemStack stack) {
        lastHoveredStack = stack;
    }

    private static long lastCopyTime = 0;
    private static final long COPY_COOLDOWN_MS = 500;

    public static boolean copyFocusedSlotTexture() {
        MinecraftClient client = MinecraftClient.getInstance();

        long currentTime = System.currentTimeMillis();
        if (currentTime - lastCopyTime < COPY_COOLDOWN_MS) {
            return false;
        }

        if (!(client.currentScreen instanceof HandledScreen<?>)) {
            return false;
        }

        ItemStack focusedStack = lastHoveredStack;
        if (focusedStack == null || focusedStack.isEmpty() || focusedStack.getItem() != Items.PLAYER_HEAD) {
            sendMessage(Text.literal("⚠ Not hovering over a player head!")
                .formatted(Formatting.RED));
            return false;
        }

        String textureValue = CaptchaHeadDatabase.getTextureValue(focusedStack);
        if (textureValue == null || textureValue.isEmpty()) {
            sendMessage(Text.literal("✗ This head has no texture data!")
                .formatted(Formatting.RED));
            return false;
        }

        String formattedEntry = String.format("TEXTURE_TO_CHAR.put(\"%s\", \"X\");", textureValue);

        System.out.println("DEBUG: About to copy to clipboard: " + formattedEntry);

        client.keyboard.setClipboard(formattedEntry);
        lastCopyTime = currentTime;

        String clipboardContent = client.keyboard.getClipboard();
        System.out.println("DEBUG: Clipboard now contains: " + clipboardContent);

        System.out.println("======================================");
        System.out.println("CAPTCHA HEAD TEXTURE COPIED!");
        System.out.println("Texture: " + textureValue);
        System.out.println("--------------------------------------");
        System.out.println("Copied to clipboard:");
        System.out.println(formattedEntry);
        System.out.println("--------------------------------------");
        System.out.println("Replace \"X\" with the character/number this head represents");
        System.out.println("======================================");

        String knownChar = CaptchaHeadDatabase.getCharacter(focusedStack);
        if (knownChar != null) {
            sendMessage(Text.literal("✓ Copied entry for: ")
                .formatted(Formatting.GREEN)
                .append(Text.literal(knownChar)
                    .formatted(Formatting.AQUA, Formatting.BOLD)));
            sendMessage(Text.literal("  Ready to paste - already has correct character!")
                .formatted(Formatting.GREEN, Formatting.ITALIC));
        } else {
            sendMessage(Text.literal("✓ Copied formatted entry!")
                .formatted(Formatting.GREEN));
            sendMessage(Text.literal("  Paste and replace \"X\" with the character")
                .formatted(Formatting.AQUA, Formatting.ITALIC));
        }

        return true;
    }

    private static void sendMessage(Text message) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null) {
            client.player.sendMessage(message, false);
        }
    }
}
