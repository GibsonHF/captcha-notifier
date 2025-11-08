package me.gibson.captchanotifier.client;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ProfileComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Database mapping player head texture IDs to characters for auto-solving captcha puzzles.
 */
public class CaptchaHeadDatabase {

    private static final Map<String, String> TEXTURE_TO_CHAR = new HashMap<>();

    private CaptchaHeadDatabase() {
    }

    static {
        TEXTURE_TO_CHAR.put("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzk1ZDM3OTkzZTU5NDA4MjY3ODQ3MmJmOWQ4NjgyMzQxM2MyNTBkNDMzMmEyYzdkOGM1MmRlNDk3NmIzNjIifX19", "SkyblockHead");
        TEXTURE_TO_CHAR.put("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDAyYzEwYWRjMzFiMWMyMWNjOThlY2Y4MDkyMjVmODdlMjVlNzIzNzhjZjQxN2RiNGJlM2Y2N2U5MWJiMSJ9fX0=", "p");
        TEXTURE_TO_CHAR.put("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2VkOWY0MzFhOTk3ZmNlMGQ4YmUxODQ0ZjYyMDkwYjE3ODNhYzU2OWM5ZDI3OTc1MjgzNDlkMzdjMjE1ZmNjIn19fQ==", "e");
        TEXTURE_TO_CHAR.put("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWM5OWRmYjI3MDRlMWJkNmU3ZmFjZmI0M2IzZTZmYmFiYWYxNmViYzdlMWZhYjA3NDE3YTZjNDY0ZTFkIn19fQ==", "i");
        TEXTURE_TO_CHAR.put("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTNmYjUwZmU3NTU5YmM5OWYxM2M0NzM1NmNjZTk3ZmRhM2FhOTIzNTU3ZmI1YmZiMTdjODI1YWJmNGIxZDE5In19fQ==", "t");
        TEXTURE_TO_CHAR.put("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmRkMDE0M2Q4ZTQ0OWFkMWJhOTdlMTk4MTcxMmNlZTBmM2ZjMjk3ZGJjMTdjODNiMDVlZWEzMzM4ZDY1OSJ9fX0=", "v");
        TEXTURE_TO_CHAR.put("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2NiODgyMjVlZTRhYjM5ZjdjYmY1ODFmMjJjYmYwOGJkY2MzMzg4NGYxZmY3NDc2ODkzMTI4NDE1MTZjMzQ1In19fQ==", "r");
        TEXTURE_TO_CHAR.put("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWFlZWY4OGUyYzkyOGI0NjZjNmVkNWRlYWE0ZTE5NzVhOTQzNmMyYjFiNDk4ZjlmN2NiZjkyYTliNTk5YTYifX19", "e");
    }

    public static String getCharacter(ItemStack stack) {
        if (stack == null || stack.getItem() != Items.PLAYER_HEAD) {
            return null;
        }

        String textureValue = getTextureValue(stack);
        if (textureValue == null) {
            return null;
        }

        return TEXTURE_TO_CHAR.get(textureValue);
    }

    public static String getTextureValue(ItemStack stack) {
        if (stack == null || stack.getItem() != Items.PLAYER_HEAD) {
            return null;
        }

        ProfileComponent profileComponent = stack.get(DataComponentTypes.PROFILE);
        if (profileComponent == null || profileComponent.gameProfile() == null) {
            return null;
        }

        GameProfile profile = profileComponent.gameProfile();
        if (profile.getProperties() == null || !profile.getProperties().containsKey("textures")) {
            return null;
        }

        Collection<Property> textures = profile.getProperties().get("textures");
        if (textures == null || textures.isEmpty()) {
            return null;
        }

        return textures.iterator().next().value();
    }

    public static boolean isKnownHead(ItemStack stack) {
        return getCharacter(stack) != null;
    }

    public static int getDatabaseSize() {
        return TEXTURE_TO_CHAR.size();
    }

    public static void register(String textureValue, String character) {
        TEXTURE_TO_CHAR.put(textureValue, character);
    }

    public static String generateMapEntry(String textureValue, String character) {
        return String.format("TEXTURE_TO_CHAR.put(\"%s\", \"%s\");", textureValue, character);
    }
}
