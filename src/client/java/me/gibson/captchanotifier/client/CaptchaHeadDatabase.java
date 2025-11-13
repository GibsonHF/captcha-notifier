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
 * Database mapping player head texture IDs to characters for auto-solving
 * captcha puzzles.
 */
public class CaptchaHeadDatabase {

        private static final Map<String, String> TEXTURE_TO_CHAR = new HashMap<>();

        private CaptchaHeadDatabase() {
        }

        static {
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2YwOTAxOGY0NmYzNDllNTUzNDQ2OTQ2YTM4NjQ5ZmNmY2Y5ZmRmZDYyOTE2YWVjMzNlYmNhOTZiYjIxYjUifX19",
                                "0");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2E1MTZmYmFlMTYwNThmMjUxYWVmOWE2OGQzMDc4NTQ5ZjQ4ZjZkNWI2ODNmMTljZjVhMTc0NTIxN2Q3MmNjIn19fQ==",
                                "1");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDY5OGFkZDM5Y2Y5ZTRlYTkyZDQyZmFkZWZkZWMzYmU4YTdkYWZhMTFmYjM1OWRlNzUyZTlmNTRhZWNlZGM5YSJ9fX0=",
                                "2");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmQ5ZTRjZDVlMWI5ZjNjOGQ2Y2E1YTFiZjQ1ZDg2ZWRkMWQ1MWU1MzVkYmY4NTVmZTlkMmY1ZDRjZmZjZDIifX19",
                                "3");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjJhM2Q1Mzg5ODE0MWM1OGQ1YWNiY2ZjODc0NjlhODdkNDhjNWMxZmM4MmZiNGU3MmY3MDE1YTM2NDgwNTgifX19",
                                "4");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDFmZTM2YzQxMDQyNDdjODdlYmZkMzU4YWU2Y2E3ODA5YjYxYWZmZDYyNDVmYTk4NDA2OTI3NWQxY2JhNzYzIn19fQ==",
                                "5");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2FiNGRhMjM1OGI3YjBlODk4MGQwM2JkYjY0Mzk5ZWZiNDQxODc2M2FhZjg5YWZiMDQzNDUzNTYzN2YwYTEifX19",
                                "6");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjk3NzEyYmEzMjQ5NmM5ZTgyYjIwY2M3ZDE2ZTE2OGIwMzViNmY4OWYzZGYwMTQzMjRlNGQ3YzM2NWRiM2ZiIn19fQ==",
                                "7");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWJjMGZkYTlmYTFkOTg0N2EzYjE0NjQ1NGFkNjczN2FkMWJlNDhiZGFhOTQzMjQ0MjZlY2EwOTE4NTEyZCJ9fX0=",
                                "8");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDZhYmM2MWRjYWVmYmQ1MmQ5Njg5YzA2OTdjMjRjN2VjNGJjMWFmYjU2YjhiMzc1NWU2MTU0YjI0YTVkOGJhIn19fQ==",
                                "9");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGU0MTc0ODEyMTYyNmYyMmFlMTZhNGM2NjRjNzMwMWE5ZjhlYTU5MWJmNGQyOTg4ODk1NzY4MmE5ZmRhZiJ9fX0=",
                                "a");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDJiOWUxNmUyNjIwNmE3MDliZjA3YzI0OTNjYTRjNWQyNGY1Njc1NjU0ZmMxMzBkMWQ1ZWM1ZThjNWJlNSJ9fX0=",
                                "b");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjJhNTg3NjExMzMyMmYzOWFhMmJiZWY0YmQ2Yjc5ZWM2YjUyYTk3YmI2ZmFiNjc0YmRkYmQ3YjZlYWIzYmEifX19",
                                "c");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmE2NjE0MTlkZTQ5ZmY0YTJjOTdiMjdmODY4MDE0ZmJkYWViOGRkN2Y0MzkyNzc3ODMwYjI3MTRjYWFmZDFmIn19fQ==",
                                "d");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWFlZWY4OGUyYzkyOGI0NjZjNmVkNWRlYWE0ZTE5NzVhOTQzNmMyYjFiNDk4ZjlmN2NiZjkyYTliNTk5YTYifX19",
                                "e");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2Q5ZDZlOTZiNWI5MmZmY2FmNDdkZDFjYWY2MWQzZjZlODQyOTEzZmM4ODg0OWYzZGU1NDhiZWVkNzFmYTgifX19",
                                "f");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjIwYzNiMmJiZmExZWQzYWM4YzM1YjNkZDM4MjQ3NDU2NTYzYzkyYWNlZmQ1OTI2YjEyNWNjYzY3ZDdkNWZkIn19fQ==",
                                "g");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2JhOWMzM2E5NWZhMWU1MTlmODVhNDFjYTU2Nzk5Mzg0ZGI0MWZlN2UxZDdhNzkxNzUxZWNlOWJiYWU1ZDI3ZiJ9fX0=",
                                "h");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWM5OWRmYjI3MDRlMWJkNmU3ZmFjZmI0M2IzZTZmYmFiYWYxNmViYzdlMWZhYjA3NDE3YTZjNDY0ZTFkIn19fQ==",
                                "i");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzgxZWZhYTliYzNiNjA3NDdhNzUwYTY0OGIxOTg3ODdmMTg2ZWI5Mzg1OWFlYTUyMDMxZDVhOGM4ODEwNzUifX19",
                                "j");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzVkYzZkNTEzY2YxNjMzMzcyZjQxY2FhMDI2MTM5NmU2Y2M2NzIwZTA1OTMwOGM2NzlkNDRiNDdlZDYifX19",
                                "k");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDRhZmZhNDU1YjdmNTgyMTdkZThhY2JiZDkyMDFjOWVhODdjMTM0YWEzNTYyNTQ5NGY1ZDNmNjVjZTk0NiJ9fX0=",
                                "l");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzhkZWM0NjY2YjRjNjdkODc1OTcxNGM4NTcxNGJlNmVhNGUzOWZmOTYyODg0OWY5OGI1MTRlZGYxYzNlNDY4MCJ9fX0=",
                                "m");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGEyMjFlNGY5NmJlZTYyNjE3NTIzOTZhMzI2NWZmYTRkZWRmOGZmNDgzOWFiZDE0ZjQ5ZWRlZTFlNTMwOTIifX19",
                                "n");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2JiMWQxN2NlYmM1ZjBlY2M5ODdiODBlZmMwM2UzMmVjYjFjYjQwZGJjNWJjZTJmYWYzZTYwNTQyYTQwIn19fQ==",
                                "o");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDAyYzEwYWRjMzFiMWMyMWNjOThlY2Y4MDkyMjVmODdlMjVlNzIzNzhjZjQxN2RiNGJlM2Y2N2U5MWJiMSJ9fX0=",
                                "p");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDUyNGQyOGM4MmYzNzExYTk3NTAxNDExZWNjM2NiNDY2ODc3NDgzYjEyMmEyNjU2YzhlZWFkZmI4ZDIxIn19fQ==",
                                "q");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzhhODFlZmRhZTQ3YmNiNDgwYTI1ZWQ5MWZmNmRlOTc3MmIwN2FlODdjM2M0ZTI3NzcwNWFiYmJkMzQxOSJ9fX0=",
                                "r");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDcxMDEzODQxNjUyODg4OTgxNTU0OGI0NjIzZDI4ZDg2YmJiYWU1NjE5ZDY5Y2Q5ZGJjNWFkNmI0Mzc0NCJ9fX0=",
                                "s");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTNmYjUwZmU3NTU5YmM5OWYxM2M0NzM1NmNjZTk3ZmRhM2FhOTIzNTU3ZmI1YmZiMTdjODI1YWJmNGIxZDE5In19fQ==",
                                "t");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDY5OTFkY2JhMjVlYWUyNDNlYjVjZWI4MzI1ZjRhYjc4ZjlmMTQxMjdjMzgyZjZjZDQyYzRjNzgwNDJkNGI1In19fQ==",
                                "u");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZThkNjgwMTg5OTRmMmYyZjU5ZDllNWYyZTNiYTNkNDZkOGIwYjllYTBlNjg0YmZiYjhlY2M3Yjg2ZWI5MjRjIn19fQ==",
                                "v");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODBjOTQ4M2Y1MWQxMjY3NDMyZTBmMmYzYmFhOGNkOTNlNjViNWVhYzc0ODJiMjdkYmNjZWJhZmI3MjE3NDhiIn19fQ==",
                                "w");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWQxYTNjOTY1NjIzNDg1MjdkNTc5OGYyOTE2MDkyODFmNzJlMTZkNjExZjFhNzZjMGZhN2FiZTA0MzY2NSJ9fX0=",
                                "x");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2RhOGI2NDczMDUyYWRhMjJlNmNhMzBjNDlmNmRjZTliOTk5MTZlNDIzYWM0ZmM2YjMwMWFkNzMzNjk3ZiJ9fX0=",
                                "y");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzBkNDRmZWUwMzAzZjZkMzdmYWNhN2U5YzMxNTMwOTU1NmZhM2RmMzc5YmRkNTgyMzE3YWEzNjhhYTg0M2UifX19",
                                "z");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTdkZDM0OTI0ZDJiNmEyMTNhNWVkNDZhZTU3ODNmOTUzNzNhOWVmNWNlNWM4OGY5ZDczNjcwNTk4M2I5NyJ9fX0=",
                                "a");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWVjYTk4YmVmZDBkN2VmY2E5YjExZWJmNGIyZGE0NTljYzE5YTM3ODExNGIzY2RkZTY3ZDQwNjdhZmI4OTYifX19",
                                "b");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTZiMTQ4NmUxZjU3NmJjOTIxYjhmOWY1OWZlNjEyMmNlNmNlOWRkNzBkNzVlMmM5MmZkYjhhYjk4OTdiNSJ9fX0=",
                                "c");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTlhYTY5MjI5ZmZkZmExODI4ODliZjMwOTdkMzIyMTVjMWIyMTU5ZDk4NzEwM2IxZDU4NDM2NDZmYWFjIn19fQ==",
                                "d");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2VkOWY0MzFhOTk3ZmNlMGQ4YmUxODQ0ZjYyMDkwYjE3ODNhYzU2OWM5ZDI3OTc1MjgzNDlkMzdjMjE1ZmNjIn19fQ==",
                                "e");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWQ3MTRiYWZiMGI1YWI5Y2ZhN2RiMDJlZmM4OTI3YWVkMWVmMjk3OTdhNTk1ZGEwNjZlZmM1YzNlZmRjOSJ9fX0=",
                                "f");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNThjMzM2ZGVkZmUxOTdiNDM0YjVhYjY3OTg4Y2JlOWMyYzlmMjg1ZWMxODcxZmRkMWJhNDM0ODU1YiJ9fX0=",
                                "g");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmRlNGE4OWJlMjE5N2Y4NmQyZTYxNjZhMGFjNTQxY2NjMjFkY2UyOGI3ODU0Yjc4OGQzMjlhMzlkYWVjMzIifX19",
                                "h");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzE0OGE4ODY1YmM0YWZlMDc0N2YzNDE1MTM4Yjk2YmJiNGU4YmJiNzI2MWY0NWU1ZDExZDcyMTlmMzY4ZTQifX19",
                                "i");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMThjOWRjM2QzOGE1NjI4MmUxZDkyMzM3MTk4ZmIxOWVhNjQxYjYxYThjNGU1N2ZiNGUyN2MxYmE2YTRiMjRjIn19fQ==",
                                "j");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTJiZmViMjQ2ZjY0OWI4NmYyMTJmZWVhODdhOWMyMTZhNjU1NTY1ZDRiNzk5MmU4MDMyNmIzOTE4ZDkyM2JkIn19fQ==",
                                "k");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2M1ODMyMWQ0YmZmYmVjMmRkZjY2YmYzOGNmMmY5ZTlkZGYzZmEyZjEzODdkYzdkMzBjNjJiNGQwMTBjOCJ9fX0=",
                                "l");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTAzNzZkYzVlM2M5ODFiNTI5NjA1NzhhZmU0YmZjNDFjMTc3ODc4OWJjZDgwZWMyYzJkMmZkNDYwZTVhNTFhIn19fQ==",
                                "m");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjEyYzdhZmVhNDhlNTMzMjVlNTEyOTAzOGE0NWFlYzUxYWZlMjU2YWJjYTk0MWI2YmM4MjA2ZmFlMWNlZiJ9fX0=",
                                "n");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWMyNzIzNWRlM2E1NTQ2NmI2Mjc0NTlmMTIzMzU5NmFiNmEyMmM0MzVjZmM4OWE0NDU0YjQ3ZDMyYjE5OTQzMSJ9fX0=",
                                "o");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzU4NGRjN2VjZjM2YjRmMDQ0ZjgyNjI1Mjc5ODU3MThiZjI0YTlkYWVmMDEyZGU5MmUxZTc2ZDQ1ODZkOTYifX19",
                                "p");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmY3MmNjZWI0YTU2NTQ3OGRlNWIwYjBlNzI3OTQ2ZTU0OTgzNGUzNmY2ZTBlYzhmN2RkN2Y2MzI3YjE1YSJ9fX0=",
                                "q");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2NiODgyMjVlZTRhYjM5ZjdjYmY1ODFmMjJjYmYwOGJkY2MzMzg4NGYxZmY3NDc2ODkzMTI4NDE1MTZjMzQ1In19fQ==",
                                "r");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWYyMmQ3Y2Q1M2Q1YmZlNjFlYWZiYzJmYjFhYzk0NDQzZWVjMjRmNDU1MjkyMTM5YWM5ZmJkYjgzZDBkMDkifX19",
                                "s");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmMyZmNiYzI0ZTczODJhYzExMmJiMmMwZDVlY2EyN2U5ZjQ4ZmZjYTVhMTU3ZTUwMjYxN2E5NmQ2MzZmNWMzIn19fQ==",
                                "t");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWZkYzRmMzIxYzc4ZDY3NDg0MTM1YWU0NjRhZjRmZDkyNWJkNTdkNDU5MzgzYTRmZTlkMmY2MGEzNDMxYTc5In19fQ==",
                                "u");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmRkMDE0M2Q4ZTQ0OWFkMWJhOTdlMTk4MTcxMmNlZTBmM2ZjMjk3ZGJjMTdjODNiMDVlZWEzMzM4ZDY1OSJ9fX0=",
                                "v");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzljYmM0NjU1MjVlMTZhODk0NDFkNzg5YjcyZjU1NGU4ZmY0ZWE1YjM5MzQ0N2FlZjNmZjE5M2YwNDY1MDU4In19fQ==",
                                "w");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzM4YWIxNDU3NDdiNGJkMDljZTAzNTQzNTQ5NDhjZTY5ZmY2ZjQxZDllMDk4YzY4NDhiODBlMTg3ZTkxOSJ9fX0=",
                                "x");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTcxMDcxYmVmNzMzZjQ3NzAyMWIzMjkxZGMzZDQ3ZjBiZGYwYmUyZGExYjE2NWExMTlhOGZmMTU5NDU2NyJ9fX0=",
                                "y");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzk5MmM3NTNiZjljNjI1ODUzY2UyYTBiN2IxNzRiODlhNmVjMjZiYjVjM2NjYjQ3M2I2YTIwMTI0OTYzMTIifX19",
                                "z");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmQ2ODM0M2JkMGIxMjlkZTkzY2M4ZDNiYmEzYjk3YTJmYWE3YWRlMzhkOGE2ZTJiODY0Y2Q4NjhjZmFiIn19fQ==",
                                "0");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDJhNmYwZTg0ZGFlZmM4YjIxYWE5OTQxNWIxNmVkNWZkYWE2ZDhkYzBjM2NkNTkxZjQ5Y2E4MzJiNTc1In19fQ==",
                                "1");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTZmYWI5OTFkMDgzOTkzY2I4M2U0YmNmNDRhMGI2Y2VmYWM2NDdkNDE4OWVlOWNiODIzZTljYzE1NzFlMzgifX19",
                                "2");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2QzMTliOTM0M2YxN2EzNTYzNmJjYmMyNmI4MTk2MjVhOTMzM2RlMzczNjExMWYyZTkzMjgyN2M4ZTc0OSJ9fX0=",
                                "3");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDE5OGQ1NjIxNjE1NjExNDI2NTk3M2MyNThmNTdmYzc5ZDI0NmJiNjVlM2M3N2JiZTgzMTJlZTM1ZGI2In19fQ==",
                                "4");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2ZiOTFiYjk3NzQ5ZDZhNmVlZDQ0NDlkMjNhZWEyODRkYzRkZTZjMzgxOGVlYTVjN2UxNDlkZGRhNmY3YzkifX19",
                                "5");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWM2MTNmODBhNTU0OTE4YzdhYjJjZDRhMjc4NzUyZjE1MTQxMmE0NGE3M2Q3YTI4NmQ2MWQ0NWJlNGVhYWUxIn19fQ==",
                                "6");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWUxOThmZDgzMWNiNjFmMzkyN2YyMWNmOGE3NDYzYWY1ZWEzYzdlNDNiZDNlOGVjN2QyOTQ4NjMxY2NlODc5In19fQ==",
                                "7");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODRhZDEyYzJmMjFhMTk3MmYzZDJmMzgxZWQwNWE2Y2MwODg0ODlmY2ZkZjY4YTcxM2IzODc0ODJmZTkxZTIifX19",
                                "8");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWY3YWEwZDk3OTgzY2Q2N2RmYjY3YjdkOWQ5YzY0MWJjOWFhMzRkOTY2MzJmMzcyZDI2ZmVlMTlmNzFmOGI3In19fQ==",
                                "9");
                // Mob captcha heads
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjIxMDI1NDM0MDQ1YmRhNzAyNWIzZTUxNGIzMTZhNGI3NzBjNmZhYTRiYTlhZGI0YmUzODA5NTI2ZGI3N2Y5ZCJ9fX0=",
                                "Guardian3");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmVlODUxNDg5MmYzZDc4YTMyZTg0NTZmY2JiOGM2MDgxZTIxYjI0NmQ4MmYzOThiZDk2OWZlYzE5ZDNjMjdiMyJ9fX0=",
                                "Pig");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzAxMjY4ZTljNDkyZGExZjBkODgyNzFjYjQ5MmE0YjMwMjM5NWY1MTVhN2JiZjc3ZjRhMjBiOTVmYzAyZWIyIn19fQ==",
                                "Skeleton");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWFmMzJmMTM0ZWYwYTY0YTkxNzQxM2Y4ZDQxYTdhYmFkNjQ1Yjg4N2NjYThmNzE2MjUwZDY0M2ZmZjM2ZGMxMCJ9fX0=",
                                "Panda");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzI4ZGI2YjA1NDIzNWU0NTFkNjY2ZmM2NDRhMDg2NjMyYzZhYjIyYzdjZDUzNTY1YWU4MjZlMWQ1Y2MwYjE3In19fQ==",
                                "Guardian1");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWUwODBhZjg4MWRlMjYyMjg5MGI5ZmJiNzEzNGMxZWE0Yzc4ZjdlZmYzNjM2YjQxN2YwYzQ3NWIwOWI2ODRkYSJ9fX0=",
                                "Duck");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTE4NGU3ZjczMDAzMThiN2E2MmJiMWVlNmZjMmUyOTY0YjA1MTYzZTNlYjQxZjE1YjMzMWQwZDJlYzY1YjlmOCJ9fX0=",
                                "Creeper");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmViNzIwZGJkY2VmMDdlYWNlOWEyMDNjN2RhYjllOTcyOTM2ZWY0NTBlOTdlYmEwMDY0ZTIzYWI4ZjJmODY0YSJ9fX0=",
                                "Pig");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmNhNDQ1NzQ5MjUxYmRkODk4ZmI4M2Y2Njc4NDRlMzhhMWRmZjc5YTE1MjlmNzlhNDI0NDdhMDU5OTMxMGVhNCJ9fX0=",
                                "Skeleton");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjIzYjIzN2EwZjI3ZDUwNWZjZDkzMWI2ZTljMjkzM2E3NDY3ODdjYzEwODQxNGY0ZjgyMzcwNzk1YmUwZmI5In19fQ==",
                                "Panda");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjY2N2MwZTEwN2JlNzlkNzY3OWJmZTg5YmJjNTdjNmJmMTk4ZWNiNTI5YTMyOTVmY2ZkZmQyZjI0NDA4ZGNhMyJ9fX0=",
                                "Cow");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmQwNWFkYmM3MDk3ODQ1ZWI0MjBkOGEyMjIxZDVhOGY5MzA3YzRmNDg2N2FhYTYyZGZmZTZmOTNlNjkwMjVlYyJ9fX0=",
                                "Creeper");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDk1MjkwZTA5MGMyMzg4MzJiZDc4NjBmYzAzMzk0OGM0ZDAzMTM1MzUzM2FjOGY2NzA5ODgyM2I3ZjY2N2YxYyJ9fX0=",
                                "Guardian2");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmYwZjUyMzAzMjdlZWZmYjc2MzFmMzdiZTU0OGFkN2JkYjMyY2E3NDNjMDY3OTFkY2I3NzQzYmI5NjE2Njk5MSJ9fX0=",
                                "Skeleton");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDJlYmEyYjM5YWU2ZWVhZGJiODg3YmQ1OWMxOGE4MzkwMjFjMWU5YTk4NjUzOGFkOGMzNzYwMDVkOGUwM2I5NSJ9fX0=",
                                "Chicken");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmI2Yzc1NjYzMTFiZTU1YmJlMTQyNDRlYTAxN2E4Zjc1ZDdlYzVjYTcyYjcwYTUwMGZiOWJjYA2YzRlYmUwIn19fQ==",
                                "Enderman");
                // Existing creeper/panda textures
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzlkZDgwM2QxZDg0MDRhOWEyNzZlNWU5OGEwY2UzY2RmMzAzZjgyNjU4MTI5YzljODAzZTcyNTYzODk1MmE0MyJ9fX0=",
                                "Creeper");
                TEXTURE_TO_CHAR.put(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGYwMDg1ODkyNmNkOGNkZjNmMWNmNzFlMjEwY2RlNWRhZjg3MDgzMjA1NDdiZDZkZjU3OTU4NTljNjhkOWIzZiJ9fX0=",
                                "Panda");
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
