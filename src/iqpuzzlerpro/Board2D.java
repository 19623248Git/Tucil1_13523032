package iqpuzzlerpro;
import java.util.*;

public class Board2D {
    
    // Define board as a contiguous linear array of width * height
    // Reflects the true definition of list in memory
    private List<List<Integer>> board;
    private int width;
    private int height;
    private int pivotRow = 0;
    private int pivotCol = 0;
    private int iter;


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

    // Print-er
    public void printBoard2D(){
        System.out.println(this.board);
    }

    // Check if block is fit in board
    public boolean isFit(Block2D block, int pivotRow, int pivotCol){
        
        if(pivotRow >= block.getRow() || pivotCol >= block.getCol() || pivotRow < 0 || pivotCol < 0){
            return false;
        }
        
        for (int i = 0; i < block.getRow(); i++){
            for (int j = 0; j < block.getCol(); j++){
                if(this.board.get(i+pivotRow).get(j+pivotCol)!=0){
                    return false;
                }
                if(i+pivotRow >= block.getRow() || j+pivotCol >= block.getCol() || i+pivotRow < 0 || j+pivotCol < 0){
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
                this.board.get(i+pivotRow).set(j+pivotCol,0);
            }
        }
    }

    // Find Top Left Empty Corner
    public void findCorner(){
        for(int j = 0; j <  this.width; j++){
            for(int i = 0; i < this.height; i++){
                if(this.board.get(i).get(j) == 0){
                    this.pivotRow = i;
                    this.pivotCol = j;
                    break;
                }
            }
        } 
    }

    // Check if board is full
    public boolean isFull(List<List<Integer>> board){
        for(int j = 0; j <  this.width; j++){
            for(int i = 0; i < this.height; i++){
                if(board.get(i).get(j) == 0){
                    return false;
                }
            }
        } 
        return true;
    }

    public void solve(Block2D[] block){
        //todo: implement
    }

}
