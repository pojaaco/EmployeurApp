package vn.elca.backend.util.generator;

import java.util.concurrent.atomic.AtomicLong;

public class NumberGenerator {
    private static final AtomicLong COUNTER = new AtomicLong(1);

    public static String generateNumber() {
        return String.format("%06d", COUNTER.getAndIncrement());
    }
}
