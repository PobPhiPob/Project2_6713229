/*
6713230_Natwarit Chuboonsub
6713229_Nutthapat Techapornhiran
6713235_Teetath Prapasanon
6713239_Nitich Uanjityanon
6713243_Puttipong Chutipongwanit
 */

package Project2_6713229;

import java.util.*;

public class LightGrid {

    private final int n;
    private final int[] state;
    private final int brokenIndex;

    //Constructors
    public LightGrid(int n, int[] initialState, int brokenIndex) {
        this.n = n;
        this.state = Arrays.copyOf(initialState, n * n);
        this.brokenIndex = brokenIndex;
        if (brokenIndex >= 0) {
            this.state[brokenIndex] = 0;
        }
    }

    //Copy constructor for BFS (too not override the initial state)
    public LightGrid(LightGrid other) {
        this.n = other.n;
        this.state = Arrays.copyOf(other.state, other.state.length);
        this.brokenIndex = other.brokenIndex;
    }

    //Toggle
    public LightGrid toggle(int row, int col) {
        LightGrid next = new LightGrid(this);
        int idx = row * n + col;

        if (idx == brokenIndex) {
            // Broken light =  toggle diagonals
            int[][] diags = {{-1,-1},{-1,1},{1,-1},{1,1}};
            next.flipCell(row, col);          // toggle the broken cell itself
            for (int[] d : diags) {
                int r = row + d[0], c = col + d[1];
                if (inBounds(r, c)) next.flipCell(r, c);
            }
        } else {
            // Normal light = toggle self and orthogonal
            int[][] ortho = {{0,0},{-1,0},{1,0},{0,-1},{0,1}};
            for (int[] d : ortho) {
                int r = row + d[0], c = col + d[1];
                if (inBounds(r, c)) next.flipCell(r, c);
            }
        }
        return next;
    }

    private void flipCell(int r, int c) {
        int idx = r * n + c;
        state[idx] ^= 1;   // XOR
    }

    public boolean allOff() {
        for (int v : state) if (v == 1) return false;
        return true;
    }

    //Getter
    public int getN() { return n; }
    public int getCell(int r, int c) { return state[r * n + c]; }

    public String toKey() {
        StringBuilder sb = new StringBuilder();
        for (int v : state) sb.append(v);
        return sb.toString();
    }

    private boolean inBounds(int r, int c) {
        return r >= 0 && r < n && c >= 0 && c < n;
    }

    //Display the Table
    public void print() {
        System.out.printf("      ");
        for (int c = 0; c < n; c++) System.out.printf("| col %-2d", c);
        System.out.println(" ");
        for (int r = 0; r < n; r++) {
            System.out.printf("row %-2d", r);
            for (int c = 0; c < n; c++) {
                int idx = r * n + c;
                String cell = (idx == brokenIndex) ? (state[idx] == 1 ? "1x" : "0x") : String.valueOf(state[idx]);
                System.out.printf("|   %-3s ", cell);
            }
            System.out.println(" ");
        }
    }
}