package edu.postech.csed232.exercise2;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * An abstract superclass that provides a skeletal implementation for converting
 * numbers into words. The toWords method is declared final so that they cannot
 * be overridden by subclasses. This is because the overall algorithm for
 * translating numbers to words is the same for all locales, and only the
 * details of the translation differ between locales.
 * <p>
 * Implementation guide:
 * <ul>
 * <li>Keep the high-level control flow in this class (inside
 * {@code toWords(long)}).</li>
 * <li>Use the abstract helper methods declared in this class to encode
 * locale-specific rules in subclasses. Typical responsibilities include: chunk
 * size ({@code 10^3} for English, {@code 10^4} for Korean), per-chunk
 * translation, and large-unit labels (thousand/million/... or 만/억/조/...).</li>
 * <li>In each subclass, implement those helper methods only; do not duplicate
 * the whole-number orchestration logic.</li>
 * </ul>
 * <p>
 * Note: Feel free to add more methods and fields, if necessary. However, do not
 * change the signature of public methods, and do not add any public method.
 */
public abstract class AbstractNumberTranslator implements NumberTranslator {

    private final Locale locale; // the locale of this translator

    /**
     * Construct a translator bound to a specific locale: e.g.,
     * {@code Locale.ENGLISH} for English, {@code Locale.KOREAN} for Korean.
     *
     * @param locale locale associated with this translator
     */
    AbstractNumberTranslator(Locale locale) {
        this.locale = locale;
    }

    /**
     * Return the locale provided to the constructor.
     *
     * @return translator locale
     */
    @Override
    public Locale getLocale() {
        return locale;
    }

    /**
     * Convert a non-negative number to locale-specific words. If
     * {@code number == 0}, returns {@link #zeroWord()}. For positive numbers,
     * splits by {@code getChunkSize()}, translates each
     * non-zero chunk via {@link #chunkToWords(int)} and {@link #chunkUnit(int)},
     * then combines them via {@link #joinChunks(List)}.
     *
     * @param number input number
     * @return translated words for {@code number}
     * @throws IllegalArgumentException if {@code number < 0}
     * @throws IllegalStateException    if {@code getChunkSize() <= 0}
     */
    @Override
    final public String toWords(long number) {
        if (number < 0) {
            throw new IllegalArgumentException("number is not in range");
        }
        if (number == 0) return zeroWord();
        List<String> list = new ArrayList<>();
        int unit = 0;
        int chunkSize = (int) Math.pow(10, getChunkSize());
        while (number != 0L) {
            if (number%chunkSize != 0) {
                String chunk = "";
                chunk = chunkUnit(unit) + chunk;
                chunk = chunkToWords((int)(number%chunkSize)) + chunk;
                list.add(0, chunk);
            }
            number = number/chunkSize;
            unit++;
        }
        return joinChunks(list);
    }

    /**
     * Return the chunk size used to split the whole number.
     *
     * @return positive chunk size
     */
    protected abstract int getChunkSize();

    /**
     * Return the locale-specific representation of zero.
     *
     * @return zero word for this locale
     */
    protected abstract String zeroWord();

    /**
     * Translate one numeric chunk, where the input range is determined by
     * {@code getChunkSize()}.
     *
     * @param chunk one chunk value
     * @return words for that chunk (without higher-unit text for other chunks)
     */
    protected abstract String chunkToWords(int chunk);

    /**
     * Return the higher-unit label for a chunk index. Index 0 corresponds to the
     * lowest chunk, so this typically returns an empty string.
     *
     * @param index non-negative chunk position from low to high
     * @return unit label for the given chunk position
     */
    protected abstract String chunkUnit(int index);

    /**
     * Join translated chunk strings into a final result string. The list is
     * provided in high-to-low order and contains only non-empty strings.
     *
     * @param translatedChunks translated chunk strings in high-to-low order
     * @return final joined translation
     */
    protected abstract String joinChunks(List<String> translatedChunks);
}
