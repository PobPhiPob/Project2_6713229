/*
6713230_Natwarit Chuboonsub
6713229_Nutthapat Techapornhiran
6713235_Teetath Prapasanon
6713239_Nitich Uanjityanon
6713243_Puttipong Chutipongwanit
 */

package Project2_6713229;

import org.jgrapht.graph.DefaultEdge;

public class MoveEdge extends DefaultEdge {
    private final int row;
    private final int col;

    public MoveEdge(int row, int col) {
        this.row = row;
        this.col = col;
    }

    //Getter
    public int getRow() { return row; }
    public int getCol() { return col; }

    @Override
    public String toString() {
        return "toggle(" + row + "," + col + ")";
    }
}
