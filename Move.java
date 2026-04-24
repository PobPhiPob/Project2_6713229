/*
6713230_Natwarit Chuboonsub
6713229_Nutthapat Techapornhiran
6713235_Teetath Prapasanon
6713239_Nitich Uanjityanon
6713243_Puttipong Chutipongwanit
 */

package Project2_6713229;

public class Move {
    public final int row, col;
    public final LightGrid stateAfter ;
    public final boolean turnOff;
    public Move(int r,int c, LightGrid s,boolean off){
        this.row=r;
        this.col=c;
        this.stateAfter=s;
        this.turnOff=off;
    }
}
