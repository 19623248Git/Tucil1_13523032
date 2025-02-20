package iqpuzzlerpro;
import java.util.*;

public class Board2D {
    
    // Define board as a contiguous linear array of width * height
    // Reflects the true definition of list in memory
    private List<Integer> board;
    private int width;
    private int height;

    // Default constructor
    public Board2D(){
        this.board = null;
    }

    // Constructor with a given board
    public Board2D(List<Integer> board){
        this.board = board;
    }

    /* 
    Setter with defined board
    DO NOT USE THIS!! NOT SUPPORTED
    */
    public void setBoard2D(List<Integer> board){
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
        
        this.board = new ArrayList<>(width * height);
        for (int i = 0; i < width * height; i++) {
            this.board.add(0);
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

        int index = row*this.width + col;

        this.board.set(index, value);

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
    public List<Integer> getBoard2D(){
        return this.board;
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

        int index = row*this.width + col;

        return this.board.get(index);

    }

    // Get Board2D height
    public int getHeight(){
        return(this.height);
    }

    // Get Board2D width
    public int getWidth(){
        return(this.width);
    }

    // Print-er
    public void printBoard2D(){
        System.out.println(this.board);
    }
    

}
