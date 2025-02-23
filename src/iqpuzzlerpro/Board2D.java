package iqpuzzlerpro;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Board2D {
    
    // Define board as a contiguous linear array of width * height
    // Reflects the true definition of list in memory
    private List<List<Integer>> board;
    private int width;
    private int height;
    private int pivotRow = 0;
    private int pivotCol = 0;
    private int iter = 0;
    private String mode;

    // Default constructor
    public Board2D(){
        this.board = null;
    }

    // Constructor with a given board
    public Board2D(List<List<Integer>> board){
        this.board = board;
    }

    /* 
    Setter with defined board
    DO NOT USE THIS!! NOT SUPPORTED
    */
    public void setBoard2D(List<List<Integer>> board){
        this.board = board;
    }

    // Setter with defined board size
    public void setBoard2D(int width, int height){

        if (width <= 0 || height <= 0){
            throw new IllegalArgumentException(
                "Width and height must be positive"
            );
        }
        
        this.width = width;
        this.height = height;
        
        this.board = new ArrayList<>(height);
        for(int i = 0; i < height; i++){
            this.board.add(new ArrayList<>());
            for(int j = 0; j < width; j++){
                this.board.get(i).add(0);
            }
        }

    }

    // Set Board2D element at certain 2D index
    public void setElmt(int row, int col, int value){

        if (board == null){
            throw new IllegalArgumentException(
                "Board2D is not initialized"
            );
        }

        if (row < 0 || row >= height || col < 0 || col >= width){
            throw new IllegalArgumentException(
                "row or column idx is out of bounds"
            );
        }

        this.board.get(row).set(col,value);

    }

    // Set Board2D height
    public void setHeight(int value){
        this.height = value;
    }

    // Set Board2D width
    public void setWidth(int value){
        this.width = value;
    }

    // Getter
    public List<List<Integer>> getBoard2D(){
        return this.board;
    }
    
    public int getIter(){
        return this.iter;
    }

    // Get Board2D element at certain 2D index
    public int getElmt(int row, int col){

        if (board == null){
            throw new IllegalArgumentException(
                "Board2D is not initialized"
            );
        }

        if (row < 0 || row >= height || col < 0 || col >= width){
            throw new IllegalArgumentException(
                "row or column idx is out of bounds"
            );
        }

        return this.board.get(row).get(col);

    }

    // Get Board2D height
    public int getHeight(){
        return(this.height);
    }

    // Get Board2D width
    public int getWidth(){
        return(this.width);
    }

    // Fit attributes
    public void fit(int[] nmp, String mode){
        
        setBoard2D(nmp[1], nmp[0]);
        
        if(mode.toLowerCase().equals("custom") || mode.toLowerCase().equals("custom")){
            System.out.println("Mode has not been implemented, converting to default mode");
        }
        else if(!mode.toLowerCase().equals("default")){
            System.out.println("Mode type not found, converting to default mode!");
        }
        mode = "default";
        this.mode = mode;
    }

    // Print-er
    public void printBoard2D(){
        System.out.println(this.board);
    }

    public void prettierPrintBoard2D(){
        System.out.println("---------------------------------");
        for(int i = 0; i < this.height; i++){
            System.out.println(board.get(i));
        }
        System.out.println("---------------------------------");
    }

    public void printCharCorr(char[] char_int_corr) {
        String[] colors = {
            "\u001B[38;5;196m", // Red
            "\u001B[38;5;220m", // Yellow
            "\u001B[38;5;51m",  // Cyan
            "\u001B[38;5;214m", // Gold
            "\u001B[38;5;57m",  // Dark Purple
            "\u001B[38;5;46m",  // Green
            "\u001B[38;5;201m", // Pink
            "\u001B[38;5;39m",  // Light Blue
            "\u001B[38;5;172m", // Dark Orange
            "\u001B[38;5;27m",  // Deep Blue
            "\u001B[38;5;129m", // Magenta
            "\u001B[38;5;178m", // Light Yellow
            "\u001B[38;5;88m",  // Brownish Red
            "\u001B[38;5;207m", // Hot Pink
            "\u001B[38;5;44m",  // Deep Turquoise
            "\u001B[38;5;136m", // Olive
            "\u001B[38;5;93m",  // Purple
            "\u001B[38;5;83m",  // Teal
            "\u001B[38;5;160m", // Dark Red
            "\u001B[38;5;94m",  // Dark Gold
            "\u001B[38;5;219m", // Light Pink
            "\u001B[38;5;140m", // Lavender
            "\u001B[38;5;202m", // Orange
            "\u001B[38;5;165m", // Violet
            "\u001B[38;5;21m",  // Blue
            "\u001B[38;5;255m"  // White
        };
    
        String reset = "\u001B[0m"; // Reset color
    
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                int boardValue = board.get(i).get(j) - 1;
                String color = colors[boardValue % colors.length]; // Cycle through 26 colors
                System.out.print(color + char_int_corr[boardValue] + reset);
            }
            System.out.println();
        }
    }

    // File output
    public void outFile(char[] char_int_corr){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"))) {
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    int boardValue = board.get(i).get(j) - 1;
                    writer.write(char_int_corr[boardValue]); // Write character without color codes
                }
                writer.newLine();
            }
            writer.newLine();
            writer.write("Number of iterations: " + getIter());
            System.out.println("Output file has been created!");
        } 
        catch (IOException e) {
            System.err.println("Error creating the file!");
            // e.printStackTrace();
        }
    }
    

    // Check if block is fit in board
    public boolean isFit(Block2D block, int pivotRow, int pivotCol){

        // block.printBlock2D();
        
        if (pivotRow < 0 || pivotCol < 0 || pivotRow + block.getRow() > getHeight() || pivotCol + block.getCol() > getWidth()) {
            // System.out.println("( " + (pivotRow) + " , " + (pivotCol) + " )");
            // System.out.println("fail #1");
            return false;
        }        
        
        for (int i = 0; i < block.getRow(); i++){
            for (int j = 0; j < block.getCol(); j++){
                if(i+pivotRow >= getHeight() || j+pivotCol >= getWidth() || i+pivotRow < 0 || j+pivotCol < 0){
                    // System.out.println("( " + (i+pivotRow) + " , " + (j+pivotCol) + " )");
                    // System.out.println("fail #2");
                    return false;
                }
                if(this.board.get(i+pivotRow).get(j+pivotCol)!=0 && block.getBlock2D().get(i).get(j)!=0){
                    // System.out.println("( " + (i+pivotRow) + " , " + (j+pivotCol) + " )");
                    // System.out.println("fail #3");
                    return false;
                }
            }
        }
        return true;
    }

    // Place block in board
    public void placeBlock(Block2D block, int pivotRow, int pivotCol){
        for (int i = 0; i < block.getRow(); i++){
            for (int j = 0; j < block.getCol(); j++){
                int element = block.getBlock2D().get(i).get(j);
                if (element!=0){
                    this.board.get(i+pivotRow).set(j+pivotCol,element);
                }
            }
        }
    }

    // Remove block in board
    public void removeBlock(Block2D block, int pivotRow, int pivotCol){
        for (int i = 0; i < block.getRow(); i++){
            for (int j = 0; j < block.getCol(); j++){
                if(block.getBlock2D().get(i).get(j)!=0){
                    this.board.get(i+pivotRow).set(j+pivotCol,0);
                }
            }
        }
    }

    // Find Top Left Empty Corner
    public void findCorner(){
        for(int i = 0; i <  this.height; i++){
            for(int j = 0; j < this.width; j++){
                // this.iter+=1;
                if(this.board.get(i).get(j) == 0){
                    this.pivotRow = i;
                    this.pivotCol = j;
                    return;
                }
            }
        } 
    }


    // Check if board is full
    public boolean isFull(){
        for(int j = 0; j <  this.width; j++){
            for(int i = 0; i < this.height; i++){
                if(this.board.get(i).get(j) == 0){
                    return false;
                }
            }
        } 
        return true;
    }

    // Solve the board with collection of block
    public boolean solve(Block2D[] block, boolean[] used, int blockCount) {

        // prettierPrintBoard2D();
    
        // Solved condition
        if (blockCount == block.length && isFull()) {
            return true;
        }
    
        if (blockCount > block.length) {
            return false;
        }
    
        for (int blockIndex = 0; blockIndex < block.length; blockIndex++) {
            if (used[blockIndex]) continue; // Skip used blocks
    
            Block2D[] block_orientations = new Block2D[8];
    
            for (int k = 0; k < 8; k++) {
                block_orientations[k] = new Block2D();
            }
    
            // for (int k = 0; k < 8; k++) {
            //     if (k < 4) {
            //         block_orientations[k].setBlock2D(block[blockIndex].getBlock2D());
            //         block[blockIndex].rotateCTR();
            //     } else if (k == 4) {
            //         block[blockIndex].flipH();
            //         block_orientations[k].setBlock2D(block[blockIndex].getBlock2D());
            //     } else {
            //         block_orientations[k].setBlock2D(block[blockIndex].getBlock2D());
            //         block[blockIndex].rotateCTR();
            //     }
            // }

            for (int k = 0; k < 8; k+=2) {
                block_orientations[k].setBlock2D(block[blockIndex].getBlock2D());
                Block2D temp = new Block2D();
                temp.setBlock2D(block[blockIndex].getBlock2D());
                temp.flipH();
                block_orientations[k+1].setBlock2D(temp.getBlock2D());
                block[blockIndex].rotateCTR();
            }

            findCorner();
    
            for (int i = this.pivotRow; i < this.height; i++) {
                for (int j = this.pivotCol; j < this.width; j++) {
                    for (int k = 0; k < block_orientations.length; k++) {
    
                        this.iter += 1;
    
                        if (isFit(block_orientations[k], i, j)) { 
                            
                            // System.out.println("Before: ");
                            // prettierPrintBoard2D();
                            // System.out.println("pivotRow: " + i + ", pivotCol: " + j);

                            placeBlock(block_orientations[k], i, j);
                            // System.out.println("After: ");
                            // prettierPrintBoard2D();
                            used[blockIndex] = true; // Mark block as used
    
                            if (solve(block, used, blockCount + 1)) {
                                return true;
                            }
    
                            removeBlock(block_orientations[k], i, j);
                            used[blockIndex] = false; // Unmark block after backtracking
                        }
                    }
                }
            }
        }
    
        return false;
    }

    // fit and solve
    public void fitSolve(int[] nmp, String mode, Block2D[] blocks, char[] char_int_corr){

        fit(nmp, mode);
        
        boolean used[] = new boolean[blocks.length];
        for(int i = 0; i < blocks.length; i++){
            used[i] = false;
        }

        long startTime = System.nanoTime();
        long endTime = 0;
        if(solve(blocks, used, 0)){
            endTime = System.nanoTime();
            System.out.println("Solution found!");
            printCharCorr(char_int_corr);
            long elapsedTime = endTime - startTime;
            System.out.println("Number of iterations: " + getIter()); 
            System.out.println("Elapsed time: " + elapsedTime / 1_000_000_000.0+ " seconds");
            outFile(char_int_corr);
        }
        else{
            System.out.println("Solution not found :(");
        }
    }
    

}
