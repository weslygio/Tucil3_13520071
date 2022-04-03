import java.util.*;

public class FifteenPuzzle {
    /* FifteenPuzzle is a class of fifteen puzzle game, or generally a sliding game
     * with custom size of rows x cols and custom goal position.
     *
     * Main function of this class is to solve fifteen puzzle problem with
     * branch and bound algorithm.
     */

    private static final int rows = 4;
    private static final int cols = 4;
    private static final int[][] goalMatrix =
        {{  1 ,  2 ,  3 ,  4 },
         {  5 ,  6 ,  7 ,  8 },
         {  9 , 10 , 11 , 12 },
         { 13 , 14 , 15 ,  0 }};

    private static final PuzzlePrioQueue queue = new PuzzlePrioQueue();
    private static int nodesCount = 0;

    private final int[][] matrix;
    private int blankRow;
    private int blankCol;
    private final int level;
    private final int cost;
    private ArrayList<String> path;     // Path (moves) traversed from initial state to
                                        // reach current state.
    private final String illegalMove;   // Move that is the reverse of last move to reach current state
                                        // to prevent moving back and forth.

    // Constructor for standard FifteenPuzzle
    public FifteenPuzzle(int[][] matrix) {
        this(matrix, 0, 0, 0, null);

        // Calculate blankRow and blankCol
        boolean found = false;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] == 0) {
                    blankRow = i; blankCol = j;
                    found = true;
                    break;
                }
            }
            if (found)
                break;
        }
    }

    // Constructor for custom FifteenPuzzle
    private FifteenPuzzle(int[][] matrix, int blankRow, int blankCol, int level, String illegalMove) {
        // Refresh generatedPuzzleCount
        if (level == 0)
            nodesCount = 1;

        this.matrix = matrix;
        this.blankRow = blankRow;
        this.blankCol = blankCol;
        this.level = level;
        this.cost = costEstimator();
        this.path = new ArrayList<>();
        this.illegalMove = illegalMove;
    }

    public int getCost() {
        return cost;
    }

    public ArrayList<String> getPath() {
        return path;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public static int getNodesCount() {
        return nodesCount;
    }

    // Returns number of lower numbered tiles or equivalently in lecture slide "KURANG(i)"
    public int lowerNumberedTiles(int x) {
        int count = 0;  
        boolean found = false;
        int val;

        if (x == 0)
            x = rows * cols;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // Ignore blank tiles
                if (matrix[i][j] == 0)
                    val = rows*cols;
                else
                    val = matrix[i][j];
                if (val == x)
                    found = true;
                else if (found && val < x)
                    count += 1;
            }
        }

        return count;
    }

    // Returns the value of sum of lower numbered tiles added by 0/1 based on blank position
    public int isSolvableValue() {
        int val = 0;
        for (int i = 1; i <= rows*cols; i++)
            val += lowerNumberedTiles(i);
        val += (blankRow + blankCol) % 2;
        return val;
    }

    // Returns true if the puzzle is solvable
    public boolean isSolvable() {
        return isSolvableValue() % 2 == 0;
    }

    // Returns true if the puzzle's position is equivalent of goal's
    private boolean equalGoal() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] != goalMatrix[i][j])
                    return false;
            }
        }
        return true;
    }

    // Returns the estimated cost to the goal
    private int costEstimator() {
        int misplacedCount = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] != 0)
                    if (matrix[i][j] != goalMatrix[i][j])
                        misplacedCount += 1;
            }
        }
        return level + misplacedCount;
    }

    // Return a new FifteenPuzzle that has been moved from current position
    public FifteenPuzzle move(String moveName) {
        int[][] newMatrix = new int[rows][cols];
        int newBlankRow = blankRow;
        int newBlankCol = blankCol;
        String newIllegalMove = null;
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                newMatrix[i][j] = matrix[i][j];
            }
        }

        switch (moveName) {
            case "up" -> {
                newBlankRow = blankRow - 1;
                if (newBlankRow < 0 || newBlankRow >= rows)
                    return null;
                newIllegalMove = "down";
            }
            case "down" -> {
                newBlankRow = blankRow + 1;
                if (newBlankRow < 0 || newBlankRow >= rows)
                    return null;
                newIllegalMove = "up";
            }
            case "left" -> {
                newBlankCol = blankCol - 1;
                if (newBlankCol < 0 || newBlankCol >= cols)
                    return null;
                newIllegalMove = "right";
            }
            case "right" -> {
                newBlankCol = blankCol + 1;
                if (newBlankCol < 0 || newBlankCol >= cols)
                    return null;
                newIllegalMove = "left";
            }
        }

        newMatrix[blankRow][blankCol] = matrix[newBlankRow][newBlankCol];
        newMatrix[newBlankRow][newBlankCol] = 0;

        FifteenPuzzle newPuzzle = new FifteenPuzzle(newMatrix, newBlankRow, newBlankCol, level+1, newIllegalMove);
        newPuzzle.path = new ArrayList<>(this.path);
        newPuzzle.path.add(moveName);
        nodesCount += 1;
        return newPuzzle;
    }

    // Return new FifteenPuzzle that is solved
    // Moves taken to reach the new FifteenPuzzle can be accessed with getPath()
    public FifteenPuzzle solve() {
        queue.push(this);
        
        FifteenPuzzle puzzle = null, newPuzzle;
        while (queue.getLength() > 0) {
            puzzle = queue.pop();
            if (puzzle.equalGoal())
                queue.prune();
            else {
                ArrayList<String> possibleMoves = new ArrayList<>(Arrays.asList("up", "down", "left", "right"));

                if (puzzle.illegalMove != null)
                    possibleMoves.remove(puzzle.illegalMove);

                for (String moveName : possibleMoves) {
                    newPuzzle = puzzle.move(moveName);
                    if (newPuzzle != null) {
                        queue.push(newPuzzle);
                    }
                }
            }
        }

        return puzzle;
    }
}
