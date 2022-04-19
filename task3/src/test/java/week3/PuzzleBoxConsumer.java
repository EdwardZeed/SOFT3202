package week3;

public class PuzzleBoxConsumer {
    private PuzzleBox puzzleBox;


    public PuzzleBoxConsumer(PuzzleBox puzzleBox) {
        this.puzzleBox = puzzleBox;
    }

    public void solvePuzzle() {
        puzzleBox.bopIt(10);
        puzzleBox.twistIt("yes");
        puzzleBox.pullIt(null);
    }
}
