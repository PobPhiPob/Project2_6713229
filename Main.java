/*
6713230_Natwarit Chuboonsub
6713229_Nutthapat Techapornhiran
6713235_Teetath Prapasanon
6713239_Nitich Uanjityanon
6713243_Puttipong Chutipongwanit
 */

package Project2_6713229;

import java.util.*;

/**
 * workflow of Main:
 * 1. ask for N (Done)
 * 2. ask for initial light state (N*N bits) which one is off or on (Done)
 * 3. ask for Is there a broken light or not if its has then ask for a position (Done)
 * 4. solve and display the moves  (ส่วนของนิว) in solver.jave us need to find the shortest way to make it 0000
 *    (BFS already have in gamegraph.java pob use to build every state that exist )
 */

public class Main {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        boolean playAgain = true;

        while (playAgain) {
            runGame();
            System.out.println();
            System.out.print("Play again? (Y/N): ");
            String again = sc.next().trim().toUpperCase();
            playAgain = again.equals("Y");
        }

        System.out.println("\nThanks for playing! Goodbye.");
        sc.close();
    }
    //Gamerunner
    private static void runGame() {
        System.out.println("\n" + "=".repeat(55));

        int n = readInt("Enter number of rows for square grid = ", 2, 20);

        //initial state
        int[] initialState = readGrid(n);

        LightGrid normgrid = new LightGrid(n, initialState,-1);
        System.out.println("\nStates in bits = " + normgrid.toKey());
        normgrid.print();

        //broken light
        int brokenIndex = readBrokenLight(n);

        //Display initial grid
        LightGrid brokegrid = new LightGrid(n, initialState, brokenIndex);
        System.out.println("\nStates in bits = " + brokegrid.toKey());
        brokegrid.print();
    }

    //To get the valid input integer
    private static int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int v = sc.nextInt();
                if (v >= min && v <= max) return v;
                System.out.printf("  Please enter a number between %d and %d.%n", min, max);
            } catch (InputMismatchException e) {
                System.out.println("  Invalid input. Please enter an integer.");
                sc.next(); // discard bad token
            }
        }
    }

    private static int[] readGrid(int n) {
        int total = n * n;
        while (true) {
            System.out.printf("Enter initial states (%d bits, left to right, line by line): ", total);
            String input = sc.next().trim();

            if (input.length() != total) {
                System.out.printf("  Expected exactly %d bits\n", total);
                continue;
            }

            int[] state = new int[total];
            boolean valid = true;
            for (int i = 0; i < total; i++) {
                char ch = input.charAt(i);
                if (ch == '0') state[i] = 0;
                else if (ch == '1') state[i] = 1;
                else {
                    System.out.println("  Only '0' and '1' are allowed. Try again.");
                    valid = false;
                    break;
                }
            }
            if (valid) return state;
        }
    }

    private static int readBrokenLight(int n) {
        while (true) {
            System.out.print("\nIs there a broken light? (Y/N): ");
            String yn = sc.next().trim().toUpperCase();

            if (yn.equals("N")) return -1;
            if (yn.equals("Y")) {
                int row = readInt(
                        String.format("  Enter row of broken light (0-%d): ", n - 1), 0, n - 1);
                int col = readInt(
                        String.format("  Enter col of broken light (0-%d): ", n - 1), 0, n - 1);
                return row * n + col;
            }
            System.out.println("  Please enter Y or N.");
        }
    }
}