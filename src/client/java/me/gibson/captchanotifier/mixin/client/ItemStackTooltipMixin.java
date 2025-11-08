package me.gibson.captchanotifier.mixin.client;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.gibson.captchanotifier.client.CaptchaHeadDatabase;
import me.gibson.captchanotifier.client.CaptchaNotifierClient;
import me.gibson.captchanotifier.client.util.HeadTextureClipboard;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ProfileComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;
import java.util.List;

@Mixin(ItemStack.class)
public class ItemStackTooltipMixin {

    @Inject(method = "getTooltip", at = @At("RETURN"))
    private void addPlayerHeadTooltip(Item.TooltipContext context, @Nullable PlayerEntity player, TooltipType type, CallbackInfoReturnable<List<Text>> cir) {
        ItemStack stack = (ItemStack) (Object) this;

        HeadTextureClipboard.setHoveredStack(stack);

        if (stack.getItem() != Items.PLAYER_HEAD) {
            return;
        }

        List<Text> tooltip = cir.getReturnValue();

        ProfileComponent profileComponent = stack.get(DataComponentTypes.PROFILE);

        if (profileComponent != null && profileComponent.gameProfile() != null) {
            GameProfile profile = profileComponent.gameProfile();

            tooltip.add(Text.literal(""));
            tooltip.add(Text.literal("═══ Player Head Info ═══")
                .formatted(Formatting.GOLD, Formatting.BOLD));

            String knownChar = CaptchaHeadDatabase.getCharacter(stack);
            if (knownChar != null) {
                tooltip.add(Text.literal(""));
                tooltip.add(Text.literal("✓ KNOWN CAPTCHA HEAD")
                    .formatted(Formatting.GREEN, Formatting.BOLD));
                tooltip.add(Text.literal("  Represents: ")
                    .formatted(Formatting.GRAY)
                    .append(Text.literal(knownChar)
                        .formatted(Formatting.AQUA, Formatting.BOLD)));
                tooltip.add(Text.literal(""));
            } else {
                tooltip.add(Text.literal(""));
                tooltip.add(Text.literal("⚠ UNKNOWN HEAD")
                    .formatted(Formatting.RED, Formatting.BOLD));
                tooltip.add(Text.literal("  Not in database yet")
                    .formatted(Formatting.GRAY, Formatting.ITALIC));
                tooltip.add(Text.literal(""));
            }

            if (profile.getName() != null && !profile.getName().isEmpty()) {
                tooltip.add(Text.literal("Player: ")
                    .formatted(Formatting.GRAY)
                    .append(Text.literal(profile.getName())
                        .formatted(Formatting.AQUA, Formatting.BOLD)));
            } else {
                tooltip.add(Text.literal("Player: ")
                    .formatted(Formatting.GRAY)
                    .append(Text.literal("Unknown")
                        .formatted(Formatting.RED)));
            }

            if (profile.getId() != null) {
                String uuid = profile.getId().toString();
                tooltip.add(Text.literal("UUID: ")
                    .formatted(Formatting.GRAY)
                    .append(Text.literal(uuid)
                        .formatted(Formatting.DARK_GRAY)));
            } else {
                tooltip.add(Text.literal("UUID: ")
                    .formatted(Formatting.GRAY)
                    .append(Text.literal("None")
                        .formatted(Formatting.RED)));
            }

            tooltip.add(Text.literal(""));

            if (profile.getProperties() != null && !profile.getProperties().isEmpty()) {
                tooltip.add(Text.literal("Properties: ")
                    .formatted(Formatting.GRAY)
                    .append(Text.literal(String.valueOf(profile.getProperties().size()))
                        .formatted(Formatting.YELLOW)));

                if (profile.getProperties().containsKey("textures")) {
                    Collection<Property> textures = profile.getProperties().get("textures");
                    tooltip.add(Text.literal("✓ Custom Skin")
                        .formatted(Formatting.GREEN, Formatting.BOLD));

                    for (Property texture : textures) {
                        if (texture.signature() != null && !texture.signature().isEmpty()) {
                            tooltip.add(Text.literal("  ✓ Signed Texture")
                                .formatted(Formatting.GREEN));
                        } else {
                            tooltip.add(Text.literal("  ○ Unsigned Texture")
                                .formatted(Formatting.YELLOW));
                        }

                        tooltip.add(Text.literal(""));

                        String textureValue = texture.value();
                        if (textureValue != null && !textureValue.isEmpty()) {
                            tooltip.add(Text.literal("  Texture ID (for solver):")
                                .formatted(Formatting.AQUA, Formatting.BOLD));

                            int lineLength = 45;
                            for (int i = 0; i < textureValue.length(); i += lineLength) {
                                int end = Math.min(i + lineLength, textureValue.length());
                                String line = textureValue.substring(i, end);
                                tooltip.add(Text.literal("  " + line)
                                    .formatted(Formatting.GRAY));
                            }

                            tooltip.add(Text.literal(""));
                            tooltip.add(Text.literal("  ^ Use this value in enum")
                                .formatted(Formatting.YELLOW, Formatting.ITALIC));

                            String keyBindText = CaptchaNotifierClient.getCopyTextureKeyBinding().getBoundKeyLocalizedText().getString();
                            tooltip.add(Text.literal("  Press ")
                                .formatted(Formatting.AQUA, Formatting.ITALIC)
                                .append(Text.literal(keyBindText)
                                    .formatted(Formatting.GOLD, Formatting.BOLD))
                                .append(Text.literal(" to copy")
                                    .formatted(Formatting.AQUA, Formatting.ITALIC)));
                        }
                    }
                } else {
                    tooltip.add(Text.literal("○ No Texture Data")
                        .formatted(Formatting.YELLOW));
                }

                for (String key : profile.getProperties().keys()) {
                    if (!key.equals("textures")) {
                        tooltip.add(Text.literal("  • " + key + ": ")
                            .formatted(Formatting.GRAY)
                            .append(Text.literal(String.valueOf(profile.getProperties().get(key).size()))
                                .formatted(Formatting.YELLOW)));
                    }
                }
            } else {
                tooltip.add(Text.literal("✗ No Properties")
                    .formatted(Formatting.RED));
                tooltip.add(Text.literal("  (Default/Steve Skin)")
                    .formatted(Formatting.DARK_GRAY, Formatting.ITALIC));
            }

            tooltip.add(Text.literal(""));

            boolean hasName = profile.getName() != null && !profile.getName().isEmpty();
            boolean hasId = profile.getId() != null;
            boolean hasTextures = profile.getProperties() != null && profile.getProperties().containsKey("textures");

            if (hasName && hasId && hasTextures) {
                tooltip.add(Text.literal("Status: ")
                    .formatted(Formatting.GRAY)
                    .append(Text.literal("Complete")
                        .formatted(Formatting.GREEN, Formatting.BOLD)));
            } else if (hasName || hasId) {
                tooltip.add(Text.literal("Status: ")
                    .formatted(Formatting.GRAY)
                    .append(Text.literal("Partial")
                        .formatted(Formatting.YELLOW, Formatting.BOLD)));
                if (!hasTextures) {
                    tooltip.add(Text.literal("  Missing: Skin Data")
                        .formatted(Formatting.DARK_GRAY, Formatting.ITALIC));
                }
            } else {
                tooltip.add(Text.literal("Status: ")
                    .formatted(Formatting.GRAY)
                    .append(Text.literal("Incomplete")
                        .formatted(Formatting.RED, Formatting.BOLD)));
            }

            tooltip.add(Text.literal("═══════════════════")
                .formatted(Formatting.DARK_GRAY));
        } else {
            tooltip.add(Text.literal(""));
            tooltip.add(Text.literal("⚠ No Profile Data")
                .formatted(Formatting.GOLD, Formatting.BOLD));
            tooltip.add(Text.literal("This head has no player info")
                .formatted(Formatting.GRAY, Formatting.ITALIC));
        }
    }
}
