/*
6713230_Natwarit Chuboonsub
6713229_Nutthapat Techapornhiran
6713235_Teetath Prapasanon
6713239_Nitich Uanjityanon
6713243_Puttipong Chutipongwanit
 */

package Project2_6713229;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.util.*;

public class GameGraph {

    public static class MoveEdge extends DefaultEdge {
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


    private final Graph<String, MoveEdge> graph;
    private final Map<String, LightGrid> stateMap;
    private final int n;

    //Constructor
    public GameGraph(LightGrid initial) {
        this.n = initial.getN();
        this.graph = new DefaultDirectedGraph<>(MoveEdge.class);
        this.stateMap = new HashMap<>();
        BuildGraph(initial);
    }

    //To build every exist state like in 2*2 all possible ways is toggle r,c : [0,0] [0,1] [1,0] [1,1]
    public void BuildGraph (LightGrid initial) {
        String startKey = initial.toKey();
        graph.addVertex(startKey);
        stateMap.put(startKey, initial);

        Queue<String> queue = new ArrayDeque<>();
        queue.add(startKey);

        while (!queue.isEmpty()) {    //do this until the queue was empty
            String currentKey = queue.poll();
            LightGrid current = stateMap.get(currentKey);

            for (int r = 0; r < n; r++) {
                for (int c = 0; c < n; c++) {
                    LightGrid next = current.toggle(r, c);
                    String nextKey = next.toKey();

                    if (!graph.containsVertex(nextKey)) {
                        graph.addVertex(nextKey);
                        stateMap.put(nextKey, next);
                        queue.add(nextKey);
                    }
                    if (!graph.containsEdge(currentKey, nextKey)) {
                        graph.addEdge(currentKey, nextKey, new MoveEdge(r, c));
                    }
                }
            }
        }
    }



    //Getter
    public Graph<String, MoveEdge> getGraph() { return graph; }
    public LightGrid getGrid(String key) { return stateMap.get(key); }
    public boolean hasState(String key) { return graph.containsVertex(key); }

    public String goalKey() {
        return "0".repeat(n * n);
    }
}