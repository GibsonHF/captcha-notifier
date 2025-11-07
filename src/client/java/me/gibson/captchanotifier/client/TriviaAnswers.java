package me.gibson.captchanotifier.client;

import java.util.HashMap;
import java.util.Map;

public class TriviaAnswers {

    private static final Map<String, String> TRIVIA_DATABASE = new HashMap<>();

    static {
        TRIVIA_DATABASE.put("A Husk is a zombie found in only one place. Where is this?", "desert");
        TRIVIA_DATABASE.put("Axolotl can come in how many different colors?", "5");
        TRIVIA_DATABASE.put("How long in real time is 1 day in Minecraft?", "20m");
        TRIVIA_DATABASE.put("How many dyes are there in Minecraft?", "16");
        TRIVIA_DATABASE.put("How many full hearts will you have left after falling 19 blocks?", "2");
        TRIVIA_DATABASE.put("How many game modes are in Minecraft (e.g. survival)?", "5");
        TRIVIA_DATABASE.put("How many night creatures can you find in Minecraft?", "5");
        TRIVIA_DATABASE.put("How many timezones are there in the world?", "24");
        TRIVIA_DATABASE.put("How tall is a Ghast in blocks (not including the tentacles)?", "4");
        TRIVIA_DATABASE.put("If you spectate a creeper, what color would your vision become?", "green");
        TRIVIA_DATABASE.put("In what year was Minecraft fully released?", "2011");
        TRIVIA_DATABASE.put("What Item do you need to make all potions?", "waterbottle");
        TRIVIA_DATABASE.put("What animal was used to make Ghast sounds?", "cat");
        TRIVIA_DATABASE.put("What are Creepers scared of?", "ocelots");
        TRIVIA_DATABASE.put("What are Endermen afraid of?", "water");
        TRIVIA_DATABASE.put("What can be created from 2 snow blocks and a pumpkin?", "snow golem");
        TRIVIA_DATABASE.put("What can be created from 4 iron blocks and a pumpkin?", "iron golem");
        TRIVIA_DATABASE.put("What can be fed to a parrot to kill it instantly?", "cookie");
        TRIVIA_DATABASE.put("What can be used to tame a wolf in Minecraft?", "bones");
        TRIVIA_DATABASE.put("What can you wear to avoid Enderman ambushing you?", "pumpkin");
        TRIVIA_DATABASE.put("What color eyes did Endermen have originally?", "green");
        TRIVIA_DATABASE.put("What does a creeper drop upon being killed?", "gunpowder");
        TRIVIA_DATABASE.put("What ingredient is needed to make a potion of swiftness?", "sugar");
        TRIVIA_DATABASE.put("What ingredient is needed to make a potion of water breathing?", "pufferfish");
        TRIVIA_DATABASE.put("What is another word for 'craftingtable?'", "workbench");
        TRIVIA_DATABASE.put("What is the primary ingredient needed to craft a cake in Minecraft?", "milk");
        TRIVIA_DATABASE.put("What tool do you use to gather stone and ore?", "pickaxe");
        TRIVIA_DATABASE.put("What version of Minecraft added horses?", "1.6");
        TRIVIA_DATABASE.put("What version of Minecraft added parrots?", "1.12");
        TRIVIA_DATABASE.put("What version of Minecraft added polar bears?", "1.10");
        TRIVIA_DATABASE.put("What version of Minecraft added rabbits?", "1.8");
        TRIVIA_DATABASE.put("What was Minecraft called initially (as one word)?", "cavegame");
        TRIVIA_DATABASE.put("When you first visit The End what terrifying creature is waiting for you?", "enderdragon");
        TRIVIA_DATABASE.put("Which enchantment conflicts with Depth Strider?", "frostwalker");
        TRIVIA_DATABASE.put("Which enchantment conflicts with Silk Touch?", "fortune");
        TRIVIA_DATABASE.put("Which passive mob was the Creeper originally meant to be?", "pig");
        TRIVIA_DATABASE.put("Who owns OPB?", "saito");
    }

    public static String getAnswer(String question) {
        if (question == null || question.isEmpty()) {
            return null;
        }

        String answer = TRIVIA_DATABASE.get(question);
        if (answer != null) {
            return answer;
        }

        String normalizedQuestion = normalizeQuestion(question);
        for (Map.Entry<String, String> entry : TRIVIA_DATABASE.entrySet()) {
            if (normalizeQuestion(entry.getKey()).equals(normalizedQuestion)) {
                return entry.getValue();
            }
        }

        return null;
    }

    public static boolean hasAnswer(String question) {
        return getAnswer(question) != null;
    }

    private static String normalizeQuestion(String question) {
        String normalized = question.trim().toLowerCase();
        if (normalized.endsWith("?")) {
            normalized = normalized.substring(0, normalized.length() - 1).trim();
        }
        return normalized;
    }

    public static int getQuestionCount() {
        return TRIVIA_DATABASE.size();
    }
}
