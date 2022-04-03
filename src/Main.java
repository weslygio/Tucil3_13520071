import java.time.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int[][] mat;

        System.out.println("---------------------------");
        System.out.println("----||-- 15 Puzzle --||----");
        System.out.println("---------------------------");

        System.out.println("1. Input from file");
        System.out.println("2. Random matrix");

        System.out.print("Choose: ");
        String c1 = in.nextLine();
        if (c1.equals("1")) {
            System.out.print("Filepath: ");
            String fp = in.nextLine();
            mat = Matrix.read_matrix_from_file("test/" + fp, 4, 4);
        }
        else
            mat = Matrix.random(4, 4);

        in.close();

        Instant start = Instant.now();

        FifteenPuzzle p = new FifteenPuzzle(mat);


        System.out.println();
        System.out.println("=============Kurang(i)=============");
        for (int i = 0; i < 16; i++)
            System.out.printf("i: %2d,  less: %2d\n", i, p.lowerNumberedTiles(i));
        System.out.println("-----------------------------------");
        System.out.printf("sum(Kurang(i)) + X = %d\n", p.isSolvableValue());
        System.out.println("===================================");
        System.out.println();
        System.out.println("Input matrix:");
        Matrix.write_matrix(p.getMatrix());

        if (p.isSolvable()) {
            Instant startSolve = Instant.now();
            ArrayList<String> path = p.solve().getPath();
            Instant endSolve = Instant.now();
            int count = FifteenPuzzle.getNodesCount();
            for (String move : path) {
                System.out.println();
                System.out.printf("Move: %s\n", move);
                p = p.move(move);
                Matrix.write_matrix(p.getMatrix());
            }
            System.out.println();
            System.out.printf("Number of moves: %d\n", path.size());
            System.out.print("Moves list: "); System.out.println(path);
            System.out.printf("Generated nodes count: %d\n", count);
            System.out.println();
            System.out.printf("Solving time: %.3f seconds\n",
                    (double) Duration.between(startSolve, endSolve).toMillis()/1000);
        }
        else {
            System.out.println();
            System.out.println("Matrix is unsolvable\n");
        }

        Instant end = Instant.now();

        System.out.printf("Time elapsed: %.3f seconds\n", (double) Duration.between(start, end).toMillis()/1000);
    }
}
