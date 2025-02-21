package iqpuzzlerpro;
import java.util.*;

public class Block2D {

    private List<List<Integer>> block;
    private int n_row;
    private int n_col;

    // Default constructor
    public Block2D(){
        this.block = null; // init with empty list
    }

    // Constructor with a given block
    public Block2D(List<List<Integer>> block){
        this.block = block;
    }

    // Setter
    public void setBlock2D(List<List<Integer>> block){
        this.block = transformBlock2D(block);
    }

    // Transform
    public List<List<Integer>> transformBlock2D(List<List<Integer>> block){

        // Get row size
        this.n_row = block.size();

        // Define n_col INT_MIN first
        this.n_col = Integer.MIN_VALUE;

        for (int i = 0; i < n_row; i++){
            int n = block.get(i).size();
            if (this.n_col < n){
                this.n_col = n;
            }
        }

        List<List<Integer>> buffer2D = new ArrayList<>();

        // define the matrix of 0s first
        for(int i = 0; i < this.n_row; i++){
            buffer2D.add(new ArrayList<>());
            for (int j = 0; j < this.n_col; j++){
                buffer2D.get(i).add(0);
            }
        }

        // re-define the matrix data type
        for(int i = 0; i < block.size(); i++){
            for(int j = 0; j < block.get(i).size(); j++){
                if(block.get(i).get(j) != 0){
                    buffer2D.get(i).set(j,block.get(i).get(j));
                }
            }
        }

        return buffer2D;
    }

    // Set Block Size
    public void setSize(int row, int col){
        this.n_row = row;
        this.n_col = col;
    }

    // Getter
    public List<List<Integer>> getBlock2D(){
        return this.block;
    }

    // Get Block Size
    public int getRow(){
        return this.n_row;
    }

    public int getCol(){
        return this.n_col;
    }

    // Print-er
    public void printBlock2D(){
        System.out.println(this.block);
    }
    
    // Rotate block counterclockwise
    public void rotateCTR(){
        // mat[i][j] to mat[n-j-1][i]
        // n is this.n_col
        List<List<Integer>> copyBlock = new ArrayList<>();

        // Initialize empty ArrayList
        for(int i = 0; i < this.n_col; i++){
            copyBlock.add(new ArrayList<>());
            for(int j = 0; j < this.n_row;j++){
                copyBlock.get(i).add(j,0);
            }
        }

        // Rotate the block
        for(int i = 0; i < this.n_row; i++){
            for(int j = 0; j < this.n_col; j++){
                copyBlock.get(this.n_col-j-1).set(i,this.block.get(i).get(j));  
            }
        }
        this.block = copyBlock;

        // swap row and col
        int swap = this.n_col;
        this.n_col = this.n_row;
        this.n_row = swap;

    }

    // Flip block
    // Flip on the y-axis
    public void flipH(){

        List<List<Integer>> copyBlock = new ArrayList<>();

        for (List<Integer> row : this.block) {
            copyBlock.add(new ArrayList<>(row));
        }

        for (int i = 0; i < this.n_row; i++){
            for (int j = 0; j < this.n_col/2; j++){
                copyBlock.get(i).set(j, this.block.get(i).get(this.n_col - 1 - j));
                copyBlock.get(i).set(this.n_col - 1 - j, this.block.get(i).get(j));
            }
        }

        this.block = copyBlock;
        
    }

    public void flipV(){

        List<List<Integer>> copyBlock = new ArrayList<>();

        for (List<Integer> row : this.block) {
            copyBlock.add(new ArrayList<>(row));
        }

        for (int i = 0; i < this.n_row/2; i++){
            List<Integer> temp = copyBlock.get(i);
            copyBlock.set(i, copyBlock.get(this.n_row - 1 - i));
            copyBlock.set(this.n_row - 1 - i, temp);
        }

        this.block = copyBlock;

    }
    
}
