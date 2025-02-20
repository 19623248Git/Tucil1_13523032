package iqpuzzlerpro;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileHandling {

    public void debugPrint(int[] nmp, String mode, Block2D[] blocks, char[] char_int_corr){
        System.out.println("");
        System.out.println("n:_" + nmp[0] + "_");
        System.out.println("m:_" + nmp[1] + "_");
        System.out.println("p:_" + nmp[2] + "_");
        System.out.println("mode:_" + mode + "_");
        for (int i = 0; i  < nmp[2]; i++){
            System.out.println("int: " + (i+1) + " char: " + char_int_corr[i]);
            blocks[i].printBlock2D();
        }
    }

    public void handle(int[] nmp, String mode, Block2D[] blocks, char[] char_int_corr){

        try (FileInputStream fis = new FileInputStream("test/input_1/input.txt")) {

            // variables

            // input number variables ahh
            // store variable n, m, and p in an array of integer
            nmp = new int[3];
            for (int i = 0; i < 3; i++){nmp[i] = 0;}
            int nmp_itr = 0;
            boolean nmp_read = false;

            // mode selection ahh variables
            mode = "";
            boolean mode_read = false;

            // blocks input ahh variables
            List<List<Integer>> buffer2D = null;
            blocks = null;
            char unique = ' ';
            int[] yx = new int[2];
            yx[0] = 0;
            yx[1] = -1;

            // array of characters, index is character's correlated int
            // empty declaration, declare in the loop
            char_int_corr = null;
            int idx_cic = 0;

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
                            nmp[nmp_itr] = nmp[nmp_itr]*10 + (data - 48);
                            // debug print
                            // System.out.print(data + " ");
                            data = fis.read();
                        }
                        nmp_itr++;

                        if (nmp_itr != 3 && data == 10){
                            System.err.println("Hey only three funny numbers at the beginning please >:(");
                            return;
                        }
                    }
                    if (nmp_itr == 3){
                        nmp_read = true;

                        // initialize input blocks and character correlation 
                        char_int_corr = new char[nmp[2]];
                        if (nmp[2] == 0) {nmp[2] = 1;}

                        blocks = new Block2D[nmp[2]];
                        for(int i = 0; i < nmp[2]; i++){
                            blocks[i] = new Block2D();
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
                            blocks[idx_cic-1].setBlock2D(buffer2D);
                        }

                        // update char_int_corr
                        char_int_corr[idx_cic] = unique;
                        idx_cic+=1;

                        yx[0] = 0;
                        yx[1] = -1;

                        buffer2D = new ArrayList<>();
                        buffer2D.add(new ArrayList<>());
                    }
                    if ((char)data == unique){
                        yx[1] += 1;

                        buffer2D.get(yx[0]).add(idx_cic);

                        //debug print
                        // System.err.print("(" + yx[0] + " , " +  yx[1] + ")");
                    }

                    else if (data == 10){
                        yx[0] += 1;
                        yx[1] = -1;

                        buffer2D.add(new ArrayList<>());
                    }

                    else if (data == 32){
                        yx[1] += 1;
                        buffer2D.get(yx[0]).add(0);
                    }
                }
            }

            blocks[idx_cic-1].setBlock2D(buffer2D);

            // debug print
            // debugPrint(nmp, mode, board, blocks, char_int_corr);

        }

        catch (IOException e) {
            System.err.println("Oopsie input file not found :(");
            // e.printStackTrace();
        }
    }
}
