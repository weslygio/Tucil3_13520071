import java.util.ArrayList;

class PuzzlePrioQueue {
    /* PuzzlePrioQueue is a priority queue of FifteenPuzzle-type elements.
     * The queue is sorted in ascending value of cost of the elements.
     */

    private ArrayList<FifteenPuzzle> queue;
    private int length;

    public PuzzlePrioQueue() {
        queue = new ArrayList<>();
        length = 0;
    }

    public int getLength() {
        return length;
    }

    // Insert puzzle to queue just before the next puzzle that has higher cost
    public void push(FifteenPuzzle puzzle) {
        int i = 0;
        boolean found = false;
        while (i < length && !found)
            if (queue.get(i).getCost() > puzzle.getCost())
                found = true;
            else
                i += 1;
        queue.add(i, puzzle);
        length += 1;
    }

    // Remove and return puzzle from front-most element of queue
    public FifteenPuzzle pop() {
        length -= 1;
        return queue.remove(0);
    }

    // Pruning assumption: we only want a single solution
    public void prune() {
        queue.clear();
        length = 0;
    }
}