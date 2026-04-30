package edu.postech.csed232.exercise2;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A number translator that converts numbers to words in English. For example,
 * 1234567890 is translated to "one billion two hundred thirty four million five
 * hundred sixty seven thousand eight hundred ninety".
 * <p>
 * This class provides the English-specific hooks required by
 * {@link AbstractNumberTranslator}: chunk size, chunk translation, unit labels,
 * and final joining strategy.
 * <p>
 * Feel free to add more private methods and fields, if necessary. However, do
 * not change public method signatures, and do not add any public method.
 */


public class EnglishNumberTranslator extends AbstractNumberTranslator {

    private String[] units = {"", " thousand", " million", " billion", " trillion", " quadrillion", " quintillion"};
    private String[] digits = {"", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
    private String[] teens = {"", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"};
    private String[] tens = {"", "ten", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"};

    EnglishNumberTranslator() {
        super(Locale.ENGLISH);
    }

    @Override
    protected int getChunkSize() {
        return 3;
    }

    @Override
    protected String zeroWord() {
        return "zero";
    }

    @Override
    protected String chunkToWords(int chunk) {
        List<String> list = new ArrayList<String>();
        if ((11 <= chunk%100) && (chunk%100 <= 19)) {
            list.add(0,teens[chunk%10]);
            chunk /= 100;
        } else {
            list.add(0, digits[chunk%10]);
            chunk /= 10;
            list.add(0, tens[chunk%10]);
            chunk /= 10;
        }

        if (chunk%10 != 0) {
            list.add(0, "hundred");
            list.add(0, digits[chunk%10]);
        }
        list.removeIf(s -> s.equals(""));
        return joinChunks(list);
    }

    @Override
    protected String chunkUnit(int index) {
        return units[index];
    }

    @Override
    protected String joinChunks(List<String> translatedChunks) {
        String joined = "";
        for (int i = 0; i < translatedChunks.size(); ++i) {
            if (!joined.equals("")) {
                joined += " ";
            }
            joined += translatedChunks.get(i);
        }
        return joined;
    }

    public static void main(String[] args) {
        var input = 10000001201L;
        var translator = new EnglishNumberTranslator();

        System.out.println(input + " -> " + translator.toWords(input));
    }
}
