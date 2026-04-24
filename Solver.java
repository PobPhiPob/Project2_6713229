/*
6713230_Natwarit Chuboonsub
6713229_Nutthapat Techapornhiran
6713235_Teetath Prapasanon
6713239_Nitich Uanjityanon
6713243_Puttipong Chutipongwanit
 */

package Project2_6713229;

import java.util.*;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.BFSShortestPath;

public class Solver {
    
    public static List <Move> solve(LightGrid initial){
        //case when start all off
        if(initial.allOff()) return new ArrayList<>();
        GameGraph gg = new GameGraph(initial);
        String goal = gg.goalKey();
        
        if(!gg.hasState(goal)) return null;
        
        //
        BFSShortestPath<String, MoveEdge> bfs = new BFSShortestPath<>(gg.getGraph());
        GraphPath<String, MoveEdge> path = bfs.getPath(initial.toKey(), goal);
        if (path == null) return null;

        List<Move> moves = new ArrayList<>();
        List<String> vertices = path.getVertexList();
        List<MoveEdge> edges = path.getEdgeList();

        for (int i = 0; i < edges.size(); i++) {
            MoveEdge e = edges.get(i);
            LightGrid before = gg.getGrid(vertices.get(i));
            LightGrid after  = gg.getGrid(vertices.get(i+1));
            int r = e.getRow(), c = e.getCol();
            boolean turnOff = before.getCell(r,c)==1 && after.getCell(r,c)==0;
            moves.add(new Move(r, c, after, turnOff));
            }
        return moves;
    }
}
