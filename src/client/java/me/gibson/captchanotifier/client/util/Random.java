package me.gibson.captchanotifier.client.util;

import java.util.concurrent.ThreadLocalRandom;

public final class Random {

    private Random() {
        throw new IllegalAccessError();
    }

    public static int nextInt(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    public static int nextInt(int bound) {
        return ThreadLocalRandom.current().nextInt(bound);
    }

    public static double nextDouble() {
        return ThreadLocalRandom.current().nextDouble();
    }

    public static double nextDouble(double min, double max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        return ThreadLocalRandom.current().nextDouble(min, max);
    }

    public static boolean nextBoolean() {
        return ThreadLocalRandom.current().nextBoolean();
    }
}
