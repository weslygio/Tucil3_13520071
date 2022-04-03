import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class Matrix {
    /* A class without attributes: contains static int[][] operations
     */

    public static int[][] read_matrix_from_file(String filepath, int rows, int cols) {
        int[][] matrix = new int[rows][cols];
        try {
            File matFile = new File(filepath);
            Scanner fileRead = new Scanner(matFile);

            for (int i = 0; i < rows; i++) {
                String data = fileRead.nextLine();
                Scanner lineRead = new Scanner(data);
                for (int j = 0; j < cols; j++) {
                    matrix[i][j] = lineRead.nextInt();
                }
                lineRead.close();
            }
            fileRead.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return matrix;
    }

    public static void write_matrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.printf("%2d", matrix[i][j]);
                if (j != matrix[i].length-1)
                    System.out.print(" ");
            }
            System.out.println();
        }
    }

    // Randomize a matrix from 0 to (rows * cols - 1)
    public static int[][] random(int rows, int cols) {
        int[][] matrix = new int[rows][cols]; 
        List<Integer> availableElements = new ArrayList<>();

        for (int i = 0; i < rows*cols; i++) {
            availableElements.add(i);
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int idx = ThreadLocalRandom.current().nextInt(0, availableElements.size());
                matrix[i][j] = availableElements.get(idx);
                availableElements.remove(idx);
            }
        }

        return matrix;
    }

}
