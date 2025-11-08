package me.gibson.captchanotifier.client.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.BooleanSupplier;

public final class Execution {

    private static final Logger LOGGER = LoggerFactory.getLogger(Execution.class);

    private Execution() {
        throw new IllegalAccessError();
    }

    public static boolean sleep(int min, int max) {
        return sleep(Random.nextInt(min, max));
    }

    public static boolean sleep(int millis) {
        if (millis <= 0) {
            return false;
        }

        boolean success = true;

        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            LOGGER.warn("Execution#sleep interrupted", e);
            Thread.currentThread().interrupt();
            success = false;
        }

        return success;
    }

    public static boolean sleepUntil(BooleanSupplier condition, BooleanSupplier reset, int timeout) {
        long start = System.currentTimeMillis();
        while (System.currentTimeMillis() - start < timeout) {
            if (condition.getAsBoolean()) {
                return true;
            }

            if (reset.getAsBoolean()) {
                start = System.currentTimeMillis();
            }

            sleep(50, 100);
        }
        return false;
    }

    public static boolean sleepUntil(BooleanSupplier condition, int timeout) {
        return sleepUntil(condition, () -> false, timeout);
    }

    public static boolean sleepWhile(BooleanSupplier condition, BooleanSupplier reset, int timeout) {
        return sleepUntil(() -> !condition.getAsBoolean(), reset, timeout);
    }

    public static boolean sleepWhile(BooleanSupplier condition, int timeout) {
        return sleepWhile(condition, () -> false, timeout);
    }
}
