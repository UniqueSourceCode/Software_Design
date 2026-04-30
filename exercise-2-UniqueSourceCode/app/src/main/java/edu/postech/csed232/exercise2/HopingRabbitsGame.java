package edu.postech.csed232.exercise2;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Two teams of N rabbits each are placed facing each other in a row with 2N + 1
 * positions. Initially, the x team occupies the first N positions, the o team
 * occupies the last N positions, and the middle position is empty. The goal is
 * to swap the positions of the two teams by moving the rabbits. A rabbit can
 * move to an empty position or jump over a rival to an empty position.
 */
public class HopingRabbitsGame {
    /**
     * The state of the game, represented as an array of rabbits, where
     * the empty position is represented as EMPTY.
     */
    private Rabbit[] rabbits;
    private int number_of_rabbits;
    private int empty_index;
    private Rabbit current_turn = Rabbit.X;

    // class, if necessary. But do not change the signature of the public methods,
    // or do not add any public method, including constructors.

    private boolean currentTurnStuck() {
        if (current_turn == Rabbit.X) {
            if (empty_index - 1 > -1 && rabbits[empty_index-1] == Rabbit.X) return false;
            if (empty_index - 2 > -1 && rabbits[empty_index-2] == Rabbit.X) return false;
            return true;
        }

        if (current_turn == Rabbit.O) {
            if (empty_index + 1 < rabbits.length && rabbits[empty_index+1] == Rabbit.O) return false;
            if (empty_index + 2 < rabbits.length && rabbits[empty_index+2] == Rabbit.O) return false;
            return true;
        }

        return true;
    }

    /**
     * Create a game with n rabbits on each team. For example, if n = 3, the initial
     * state is xxx_yyy, where x represents a rabbit from the x team, y represents a
     * rabbit from the o team, and _ represents an empty position.
     *
     * @param n the number of rabbits on each team
     */
    HopingRabbitsGame(int n) {
        number_of_rabbits = n;
        empty_index = n;
        rabbits = new Rabbit[2 * n + 1];
        for (int i = 0; i < n; i++) {
            rabbits[i] = Rabbit.X;
        }
        rabbits[empty_index] = Rabbit.EMPTY;
        for (int i = n + 1; i < rabbits.length; i++) {
            rabbits[i] = Rabbit.O;
        }

    }

    /**
     * Move a rabbit to an empty position. Rabbits from the x team can only move to
     * the right, and rabbits from the o team can only move to the left. A rabbit is
     * allowed to advance one position if that position is empty. A rabbit can jump
     * over a rival if the position behind the rival is empty. For example, if the
     * current state is xxxo_oo, the x team can move to xx_oxoo.
     *
     * @param rabbit a rabbit
     * @return true if the rabbit can move, false otherwise
     */
    
    boolean move(Rabbit rabbit) {
        current_turn = rabbit;
        if (currentTurnStuck()) return false;

        if (current_turn == Rabbit.X) {
            if (rabbits[empty_index - 1] == Rabbit.X) {
                rabbits[empty_index] = Rabbit.X;
                rabbits[empty_index-1] = Rabbit.EMPTY;

                empty_index = empty_index-1;
                return true;
            } else {
                rabbits[empty_index] = Rabbit.X;
                rabbits[empty_index-2] = Rabbit.EMPTY;

                empty_index = empty_index-2;
                return true;
            }
        }

        if (current_turn == Rabbit.O) {
            if (rabbits[empty_index + 1] == Rabbit.O) {
                rabbits[empty_index] = Rabbit.O;
                rabbits[empty_index+1] = Rabbit.EMPTY;

                empty_index = empty_index+1;
                return true;
            } else {
                rabbits[empty_index] = Rabbit.O;
                rabbits[empty_index+2] = Rabbit.EMPTY;

                empty_index = empty_index+2;
                return true;
            }
        }
        
        return false;
    }

    /**
     * Return true if the game is over. The game is over if the two teams have
     * swapped their initial positions: e.g., ooo_xxx, when N = 3.
     *
     * @return true if the game is over, false otherwise
     */
    boolean isGoal() {
        boolean achievedGoal = true;
        for (int i = 0; i < number_of_rabbits; ++i) {
            if (rabbits[i] != Rabbit.O) achievedGoal = false;
            if (achievedGoal == false) return achievedGoal;
        }
        for (int i = number_of_rabbits + 1; i < rabbits.length; ++i) {
            if (rabbits[i] != Rabbit.X) achievedGoal = false;
            if (achievedGoal == false) return achievedGoal;
        }
        return achievedGoal;
    }

    /**
     * Return true if the game is stuck. The game is stuck if no rabbit can move.
     *
     * @return true if the game is stuck, false otherwise
     */
    boolean isStuck() {
        //Either A : there is an X on the left of EMPTY
        //Or B : there is an O then an X on the left of EMPTY.
        //vice versa

        if (empty_index - 1 > -1 && rabbits[empty_index-1] == Rabbit.X) return false;
        if (empty_index - 2 > -1 && rabbits[empty_index-2] == Rabbit.X) return false;
        if (empty_index + 1 < rabbits.length && rabbits[empty_index+1] == Rabbit.O) return false;
        if (empty_index + 2 < rabbits.length && rabbits[empty_index+2] == Rabbit.O) return false;

        return true;
    }

    /**
     * Return the string representation of the game state. The string has length 
     * 2N + 1 and consists of x, o, and _.
     *
     * @return a string of length 2N + 1 consisting of x, o, and _.
     */
    @Override
    public String toString() {
        return Arrays.stream(rabbits).map(rabbit -> switch (rabbit) {
            case X -> "x";
            case O -> "o";
            case EMPTY -> "_";
        }).collect(Collectors.joining());
    }

    public static void main(String[] args) {
        var game = new HopingRabbitsGame(3);

        System.out.println(game);
        game.move(Rabbit.X);
        System.out.println(game);
        game.move(Rabbit.O);
        System.out.println(game);
    }

}
