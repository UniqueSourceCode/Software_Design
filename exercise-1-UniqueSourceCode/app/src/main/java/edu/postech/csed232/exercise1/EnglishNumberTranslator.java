package edu.postech.csed232.exercise1;
import java.util.Stack;
/**
 * A number translator that converts numbers to words in English.
 */
public class EnglishNumberTranslator {

    /**
     * Convert a number to words in English. The number should be in the range
     * [0, Long.MAX_VALUE], and otherwise an exception should be thrown. For example,
     * 1234567890 should be translated to "one billion two hundred thirty four million
     * five hundred sixty seven thousand eight hundred ninety".
     *
     * @param number a number
     * @return a string of words
     * @throws IllegalArgumentException if the number is not in the range
     */
    private String[] units = {"", "thousand", "million", "billion", "trillion", "quadrillion", "quintillion"};
    private String[] digits = {"", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
    private String[] teens = {"ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"};
    private String[] tens = {"", "", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"};
    private int curr_digit = 0;
    private Stack<String> words = new Stack<>();

    public String toWords(long number) {
        if (number < 0) {
            throw new IllegalArgumentException("number is not in range");
        }
        
        if (number%1000 != 0) {
            words.push(units[curr_digit]);
        }
        
        int one = (int)(number%10);
        number /= 10;
        int ten = (int)(number%10);
        number /= 10;
        if (ten == 1) {
            words.push(teens[one]);
        } else {
            words.push(digits[one]);
            words.push(tens[ten]);
        }
        //Now for the hundreds
        if (number%10 != 0) {
            words.push("hundred");
            words.push(digits[(int)(number%10)]);
        }
        number /= 10;

        //Trick to return to curr_digit = 0;
        curr_digit += 1;
        if (number != 0) toWords(number);
        curr_digit -= 1;

        if (curr_digit == 0) {
            String answer = "";
            while (!words.empty()) {
                String current = words.peek();
                if (current.equals("")) {
                    words.pop();
                    continue;
                }
                if (!answer.equals("")) answer += ' ';
                answer += current;
                words.pop();
            }
            if (answer.equals("")) return "zero";
            return answer;
        }
        return "";
    }

    public static void main(String[] args) {
        var input = 10000001201L;
        var translator = new EnglishNumberTranslator();

        System.out.println(input + " -> " + translator.toWords(input));
    }

}
