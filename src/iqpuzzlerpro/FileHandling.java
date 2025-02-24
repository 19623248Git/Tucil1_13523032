package iqpuzzlerpro;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileHandling {

    private String path;
    private int[] nmp;
    private String mode;
    private Block2D[] blocks;
    private char[] char_int_corr;
    
    // debug printing for file handling
    public void debugPrint(){
        System.out.println("");
        System.out.println("n:_" + this.nmp[0] + "_");
        System.out.println("m:_" + this.nmp[1] + "_");
        System.out.println("p:_" + this.nmp[2] + "_");
        System.out.println("mode:_" + this.mode + "_");
        for (int i = 0; i  < this.nmp[2]; i++){
            System.out.println("int: " + (i+1) + " char: " + this.char_int_corr[i]);
            this.blocks[i].printBlock2D();
        }
    }

    public void inputPath(){
        String currentDir = System.getProperty("user.dir");
        System.out.println("\nCurrent directory: " + currentDir);
        String expectedDir = currentDir + "/test/[dir_name]/input.txt";
        System.out.println("\nExpected directory: " + expectedDir);
        
        try (Scanner myObj = new Scanner(System.in)) {
            System.out.print("\nInput dir_name: ");
            String dir_name = myObj.nextLine();
            this.path = currentDir + "/test/" + dir_name;
            File f = new File(this.path);
            if (f.exists()){
                System.out.println("\nInput Directory Exists!");
            }
            else{
                System.out.println("\n"  +  this.path + "Doesn't Directory Exists!");
            }
        }
    }

    // Get path
    public String getPath(){
        return this.path;
    }

    // file handling
    public void handle(){

        try (FileInputStream fis = new FileInputStream(this.path + "/input.txt")) {

            // variables

            // input number variables ahh
            // store variable n, m, and p in an array of integer
            this.nmp = new int[3];
            for (int i = 0; i < 3; i++){this.nmp[i] = 0;}
            int nmp_itr = 0;
            boolean nmp_read = false;

            // mode selection ahh variables
            this.mode = "";
            boolean mode_read = false;

            // blocks input ahh variables
            List<List<Integer>> buffer2D = null;
            this.blocks = null;
            char unique = (char)(1);
            int[] yx = new int[2];
            yx[0] = 0;
            yx[1] = -1;

            // array of characters, index is character's correlated int
            // empty declaration, declare in the loop
            this.char_int_corr = null;
            int idx_cic = 0;

            // indentation ahh
            int indent = 0;

            // file handling ahh
            int data;
            while ((data = fis.read()) != -1) {

                // space -> " " in ascii is 32
                // newline -> "\n" in ascii is 10
                // '0' - '9' -> in ascii is 48 - 57

                // Debug the file handling
                // System.out.print((char) data);
                // System.out.print("(" +  data + ") ");

                if (!nmp_read){
                    if(data >= 48 && data <= 57){
                        while(data != 32 && data != 10){
                            this.nmp[nmp_itr] = this.nmp[nmp_itr]*10 + (data - 48);
                            // debug print
                            // System.out.print(data + " ");
                            data = fis.read();
                        }
                        nmp_itr++;

                        if (nmp_itr != 3 && data == 10){
                            System.err.println("Only three numbers at the beginning of the file");
                            return;
                        }
                    }
                    if (nmp_itr == 3){
                        nmp_read = true;

                        // initialize input blocks and character correlation 
                        this.char_int_corr = new char[this.nmp[2]];
                        if (this.nmp[2] == 0) {this.nmp[2] = 1;}

                        this.blocks = new Block2D[this.nmp[2]];
                        for(int i = 0; i < this.nmp[2]; i++){
                            this.blocks[i] = new Block2D();
                        } 
                    }
                }
                else if (!mode_read){

                    // append empty string with character until newline
                    if (data != 10){
                        mode += (char)data;
                    }
                    else{
                        mode_read = true;
                    }
                }
                else if (true) {

                    // cursed syntax 
                    // doesn't need boolean to determine when block input
                    if ((char)data != unique && data != 10 && data != 32){
                        unique = (char)data;

                        if (buffer2D != null){
                            buffer2D.remove(yx[0]);
                            this.blocks[idx_cic-1].setBlock2D(buffer2D);
                        }

                        // update char_int_corr
                        this.char_int_corr[idx_cic] = unique;
                        idx_cic+=1;

                        yx[0] = 0;
                        yx[1] = -1;

                        buffer2D = new ArrayList<>();
                        buffer2D.add(new ArrayList<>());
                        for(int i = 0; i < indent; i++){
                            buffer2D.get(yx[0]).add(0);
                        }
                        indent = 0;
                    }
                    if ((char)data == unique){
                        yx[1] += 1;

                        for(int i = 0; i < indent; i++){
                            buffer2D.get(yx[0]).add(0);
                        }
                        indent = 0;

                        buffer2D.get(yx[0]).add(idx_cic);

                        //debug print
                        // System.err.print("(" + yx[0] + " , " +  yx[1] + ")");
                    }

                    else if (data == 10){
                        yx[0] += 1;
                        yx[1] = -1;
                        indent = 0;

                        buffer2D.add(new ArrayList<>());
                    }

                    else if (data == 32){
                        yx[1] += 1;
                        indent+=1;
                    }
                }
            }

            this.blocks[idx_cic-1].setBlock2D(buffer2D);

            // debug print
            // debugPrint();    

        }

        catch (IOException e) {
            System.err.println("Oopsie input file not found in directory: " + this.path + " :(");
            // e.printStackTrace();
        }
    }

    // Getter
    public int[] getNmp(){
        return this.nmp;
    }

    public String getMode(){
        return this.mode;
    }

    public Block2D[] getBlocks(){
        return this.blocks;
    }

    public char[] getCharIntCorr(){
        return this.char_int_corr;
    }

    // Setter
    public void setNmp(int[] nmp){
        this.nmp = nmp;
    }

    public void setMode(String mode){
        this.mode = mode;
    }

    public void setBlocks(Block2D[] blocks){
        this.blocks = blocks;
    }

    public void setCharIntCorr(char[] char_int_corr){
        this.char_int_corr = char_int_corr;
    }
}
