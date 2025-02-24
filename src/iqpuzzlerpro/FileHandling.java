package iqpuzzlerpro;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
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
    public void handle() {
        try (BufferedReader reader = new BufferedReader(new FileReader(Paths.get(this.path, "input.txt").toString()))) {
            // Variables
            this.nmp = new int[3];
            int nmp_itr = 0;
            boolean nmp_read = false;

            this.mode = "";
            boolean mode_read = false;

            List<List<Integer>> buffer2D = null;
            this.blocks = null;
            char unique = (char) (1);
            int[] yx = {0, -1};
            
            this.char_int_corr = null;
            int idx_cic = 0;
            int indent = 0;

            int data;
            while ((data = reader.read()) != -1) {
                if (data == '\r') continue; // Skip carriage return in Windows

                if (!nmp_read) {
                    if (Character.isDigit(data)) {
                        while (data != ' ' && data != '\n' && data != -1) {
                            this.nmp[nmp_itr] = this.nmp[nmp_itr] * 10 + (data - '0');
                            data = reader.read();
                            if (data == '\r') data = reader.read(); // Handle Windows line endings
                        }
                        nmp_itr++;
                        if (nmp_itr != 3 && data == '\n') {
                            System.err.println("Only three numbers at the beginning of the file");
                            return;
                        }
                    }
                    if (nmp_itr == 3) {
                        nmp_read = true;
                        this.char_int_corr = new char[this.nmp[2] == 0 ? 1 : this.nmp[2]];
                        this.blocks = new Block2D[this.nmp[2]];
                        for (int i = 0; i < this.nmp[2]; i++) {
                            this.blocks[i] = new Block2D();
                        }
                    }
                } else if (!mode_read) {
                    if (data != '\n') {
                        this.mode += (char) data;
                    } else {
                        mode_read = true;
                    }
                } else {
                    if ((char) data != unique && data != '\n' && data != ' ') {
                        unique = (char) data;
                        if (buffer2D != null) {
                            buffer2D.remove(yx[0]);
                            this.blocks[idx_cic - 1].setBlock2D(buffer2D);
                        }
                        this.char_int_corr[idx_cic++] = unique;
                        yx[0] = 0;
                        yx[1] = -1;
                        buffer2D = new ArrayList<>();
                        buffer2D.add(new ArrayList<>());
                        for (int i = 0; i < indent; i++) {
                            buffer2D.get(yx[0]).add(0);
                        }
                        indent = 0;
                    }
                    if ((char) data == unique) {
                        yx[1]++;
                        for (int i = 0; i < indent; i++) {
                            buffer2D.get(yx[0]).add(0);
                        }
                        indent = 0;
                        buffer2D.get(yx[0]).add(idx_cic);
                    } else if (data == '\n') {
                        yx[0]++;
                        yx[1] = -1;
                        indent = 0;
                        buffer2D.add(new ArrayList<>());
                    } else if (data == ' ') {
                        yx[1]++;
                        indent++;
                    }
                }
            }
            this.blocks[idx_cic - 1].setBlock2D(buffer2D);
            // debugPrint();
        } catch (IOException e) {
            System.err.println("input file not found in directory: " + this.path);
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
