package edu.postech.csed232.exercise2;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A number translator that converts numbers into words in Korean. For example,
 * 1234567890 is translated to "십이억삼천사백오십육만칠천팔백구십".
 * <p>
 * In this assignment, explicitly include "일"  for 100, 1000, 10000, and so on,
 * except for 10. For example, 10 is translated to "십", and 100 is translated
 * to "일백", and 1000 is translated to "일천".
 * <p>
 * This class should provide the Korean-specific hooks required by
 * {@link AbstractNumberTranslator}: chunk size, chunk translation, unit labels,
 * and final joining strategy.
 * <p>
 * Feel free to add more private methods and fields, if necessary. However, do
 * not change public method signatures, and do not add any public method.
 */
public class KoreanNumberTranslator extends AbstractNumberTranslator {
    private String[] units = {"", "만", "억", "조", "경", "해"};
    private String[] digits = {"", "일", "이", "삼", "사", "오", "육", "칠", "팔", "구"};
    private String[] tens = {"", "십", "이십", "삼십", "사십", "오십", "육십", "칠십", "팔십", "구십"};
    private String[] hundreds = {"", "일백", "이백", "삼백", "사백", "오백", "육백", "칠백", "팔백", "구백"};
    private String[] thousands = {"", "일천", "이천", "삼천", "사천", "오천", "육천", "칠천", "팔천", "구천"};


    KoreanNumberTranslator() {
        super(Locale.KOREAN);
    }

    @Override
    protected int getChunkSize() {
        return 4;
    }

    @Override
    protected String zeroWord() {
        return "영";
    }

    @Override
    protected String chunkToWords(int chunk) {
        List<String> list = new ArrayList<>();
        list.add(0, digits[chunk%10]);
        chunk /= 10;
        list.add(0, tens[chunk%10]);
        chunk /= 10;
        list.add(0, hundreds[chunk%10]);
        chunk /= 10;
        list.add(0, thousands[chunk%10]);
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
            joined += translatedChunks.get(i);
        }
        return joined;
    }

    public static void main(String[] args) {
        var input = 10000001201L;
        var translator = new KoreanNumberTranslator();

        System.out.println(input + " -> " + translator.toWords(input));
    }

}
