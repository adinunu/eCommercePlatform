package id.org.test.common.util;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

/**
 * Created by Muhammad Yusuf on 3/17/2018.
 *
 * <h1>Usage examples</h1>
 * <p>Create an insecure generator for 8-character identifiers:</p>
 * <pre>
 *     RandomString gen = new RandomString(8, ThreadLocalRandom.current());
 * </pre>
 *
 * <p>Create a secure generator for session identifiers:</p>
 * <pre>
 *     RandomString session = new RandomString();
 * </pre>
 *
 * <p>Create a generator with easy-to-read codes for printing. The strings are longer than full alphanumeric strings to compensate for using fewer symbols:</p>
 * <pre>
 *     String easy = RandomString.digits + "ACEFGHJKLMNPQRUVWXYabcdefhijkprstuvwx";
       RandomString tickets = new RandomString(23, new SecureRandom(), easy);
 * </pre>
 */
@Component
public class RandomString {

    /**
     * Generate a random string.
     */
    public String nextString() {
        for (int idx = 0; idx < buf.length; ++idx)
            buf[idx] = symbols[random.nextInt(symbols.length)];
        return new String(buf);
    }

    public static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static final String lower = upper.toLowerCase(Locale.ROOT);

    public static final String digits = "0123456789";

    public static final String alphanum = upper + lower + digits;

    private final Random random;

    private final char[] symbols;

    private final char[] buf;

    public RandomString(int length, Random random, String symbols) {
        if (length < 1) throw new IllegalArgumentException();
        if (symbols.length() < 2) throw new IllegalArgumentException();
        this.random = Objects.requireNonNull(random);
        this.symbols = symbols.toCharArray();
        this.buf = new char[length];
    }

    /**
     * Create an alphanumeric string generator.
     */
    public RandomString(int length, Random random) {
        this(length, random, alphanum);
    }

    /**
     * Create an alphanumeric strings from a secure generator.
     */
    public RandomString(int length) {
        this(length, new SecureRandom());
    }

    /**
     * Create session identifiers.
     */
    public RandomString() {
        this(21);
    }

}
