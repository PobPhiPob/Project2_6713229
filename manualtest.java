package Project2_6713229;

/**
 * TEST FILE - ลบทิ้งได้หลังทดสอบเสร็จ
 * ทดสอบแบบ manual: เลือก toggle ช่องเองทีละครั้ง
*/

import org.jgrapht.Graph;
import java.util.InputMismatchException;
import java.util.Scanner;

public class manualtest {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║   Manual Test — LightGrid & GameGraph ║");
        System.out.println("╚══════════════════════════════════════╝");

        // 1. ตั้งค่า grid
        int n = readInt("กำหนดขนาด grid (N): ", 2, 6);
        int[] state = readGrid(n);
        int brokenIdx = readBroken(n);

        LightGrid grid = new LightGrid(n, state, brokenIdx);

        // 2. แสดงข้อมูล GameGraph
        System.out.println("\nกำลังสร้าง GameGraph...");
        GameGraph gg = new GameGraph(grid);
        Graph<String, GameGraph.MoveEdge> g = gg.getGraph();
        System.out.println("Graph: " + g.vertexSet().size() + " nodes, " + g.edgeSet().size() + " edges");
        System.out.println("Goal '000...0' อยู่ใน graph: " + g.containsVertex(gg.goalKey()));

        // 3. วน toggle ด้วยตัวเอง
        System.out.println("\nพิมพ์ row col เพื่อ toggle เช่น '1 2'  |  พิมพ์ 'q' เพื่อออก");
        int moveNum = 1;
        while (true) {
            System.out.println("\n─── สถานะปัจจุบัน ───");
            System.out.println("bits = " + grid.toKey() + "  allOff=" + grid.allOff());
            grid.print();

            // เช็คว่า state นี้อยู่ใน graph ไหม
            boolean inGraph = g.containsVertex(grid.toKey());
            System.out.println("State นี้อยู่ใน graph: " + inGraph);

            if (grid.allOff()) {
                System.out.println("\n*** ทุกช่องดับหมดแล้ว! ใช้ " + (moveNum - 1) + " moves ***");
                break;
            }

            System.out.print("\nMove " + moveNum + " → row col (หรือ q): ");
            String input = sc.next().trim();
            if (input.equalsIgnoreCase("q")) {
                System.out.println("ออกจาก manual test");
                break;
            }

            // parse row
            int row;
            try {
                row = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("  ใส่ตัวเลขเท่านั้น");
                continue;
            }

            // parse col
            int col;
            try {
                col = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("  ใส่ตัวเลขเท่านั้น");
                sc.next();
                continue;
            }

            if (row < 0 || row >= n || col < 0 || col >= n) {
                System.out.println("  ออกนอก grid! (0-" + (n-1) + ")");
                continue;
            }

            // toggle และแสดง edge ใน graph
            String before = grid.toKey();
            grid = grid.toggle(row, col);
            String after = grid.toKey();

            boolean edgeExists = g.containsEdge(before, after);
            System.out.println("  toggle(" + row + "," + col + "): "
                    + before + " → " + after
                    + "  [edge ใน graph: " + edgeExists + "]");
            moveNum++;
        }

        sc.close();
    }

    // ── helpers ───────────────────────────────────────────────────────────────

    static int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int v = sc.nextInt();
                if (v >= min && v <= max) return v;
                System.out.println("  ต้องอยู่ระหว่าง " + min + " ถึง " + max);
            } catch (InputMismatchException e) {
                System.out.println("  ใส่ตัวเลขเท่านั้น");
                sc.next();
            }
        }
    }

    static int[] readGrid(int n) {
        int total = n * n;
        while (true) {
            System.out.print("ใส่ initial state (" + total + " bits เช่น " + "1".repeat(total) + "): ");
            String s = sc.next().trim();
            if (s.length() != total) {
                System.out.println("  ต้องใส่ " + total + " ตัว");
                continue;
            }
            int[] arr = new int[total];
            boolean ok = true;
            for (int i = 0; i < total; i++) {
                if (s.charAt(i) == '0') arr[i] = 0;
                else if (s.charAt(i) == '1') arr[i] = 1;
                else { System.out.println("  ใช้แค่ 0 กับ 1"); ok = false; break; }
            }
            if (ok) return arr;
        }
    }

    static int readBroken(int n) {
        while (true) {
            System.out.print("มีช่อง broken ไหม? (Y/N): ");
            String yn = sc.next().trim().toUpperCase();
            if (yn.equals("N")) return -1;
            if (yn.equals("Y")) {
                int r = readInt("  row ของ broken (0-" + (n-1) + "): ", 0, n-1);
                int c = readInt("  col ของ broken (0-" + (n-1) + "): ", 0, n-1);
                return r * n + c;
            }
            System.out.println("  พิมพ์ Y หรือ N");
        }
    }
}