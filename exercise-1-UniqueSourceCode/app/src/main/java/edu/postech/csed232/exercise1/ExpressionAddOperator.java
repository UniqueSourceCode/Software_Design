package edu.postech.csed232.exercise1;

/**
 * A solver that checks whether a given number can be formed by inserting '+' or '-' between the digits of another number.
 * <p>
 * Example:
 * Given 1234 and 20, determine if we can insert '+' or '-' between digits of 1234 to get 20.
 * e.g., 1 + 23 - 4 = 20 (Valid), 12 + 3 + 4 = 19 (Invalid)
 * <p>
 * Approach:
 * - Consider different ways to insert '+' or '-' between digits.
 * - Evaluate the resulting expressions.
 * - Return true if any of them evaluates to the target.
 */
public class ExpressionAddOperator {
    /**
     * Determines if it is possible to insert '+' or '-' between the digits of 'number' to form 'target'.
     * Both 'number' and 'target' are non-negative integers.
     *
     * @param number the original number
     * @param target the desired result
     * @return true if target can be formed, false otherwise
     * @throws IllegalArgumentException if number or target is negative
     */
    String input;

    private boolean search(int prev_number, int target, int index) {
        //If reached end, check if matches target
        if (index >= input.length()) {
            if (prev_number == target) return true;
            else return false;
        }

        //length of the number we are going to + or -
        int starting_num_length = 1;

        while (index + starting_num_length <= input.length()) {
            int starting_number = 0;
            for (int i = index; i < index + starting_num_length; ++i) {
                starting_number *= 10;
                starting_number += input.charAt(i) - '0';
            }

            //add to the previous number, and change the index accordingly.
            if (search(prev_number+starting_number, target, index + starting_num_length)) return true;
            
            //Just add for the first index
            if (index != 0) {
                if (search(prev_number-starting_number, target, index + starting_num_length)) return true;
            }
            starting_num_length++;
        }
        return false;
    }
    public boolean solve(int number, int target) {
        if (number < 0 || target < 0) {
            throw new IllegalArgumentException("number and target are negative");
        }
        input = String.valueOf(number);
        return search(0, target, 0);
    }

    public static void main(String[] args) {
        var number = 1234;
        var target = 20;
        var checker = new ExpressionAddOperator();

        System.out.println("Can " + number + " form " + target + "? " + checker.solve(number, target));
    }
}
