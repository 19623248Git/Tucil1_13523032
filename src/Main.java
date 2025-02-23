import iqpuzzlerpro.Block2D;
import iqpuzzlerpro.Board2D;
import iqpuzzlerpro.FileHandling;

class Main {

    public static void main(String[] args) {

        // variables

        // input number variables ahh
        // store variable n, m, and p in an array of integer
        int[] nmp = new int[3];

        // mode selection ahh variables
        String mode = "";

        // board ahh object
        Board2D board = new Board2D();

        // blocks input ahh variables
        Block2D[] blocks = null;

        // array of characters, index is character's correlated int
        // empty declaration, declare in the loop
        char[] char_int_corr = null;

        // file handling
        FileHandling FileHandler = new FileHandling();
        FileHandler.inputPath();
        FileHandler.handle();

        nmp = FileHandler.getNmp();
        mode = FileHandler.getMode();
        blocks = FileHandler.getBlocks();
        char_int_corr = FileHandler.getCharIntCorr();

        board.fitSolve(nmp, mode, blocks, char_int_corr);
    }
}
