package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Wording {
    ArrayList<String> words;
    File myObj = new File("C:\\Users\\remiz\\OneDrive\\UNI\\Semestre 7\\R�seaux\\TP2\\penduReseau\\src\\resources\\words.txt");
    Scanner myReader;

    private Wording(){
        words = new ArrayList<>();
    }

    public static Wording getInstance(){
        return new Wording();
    }

    public void read () {
        try {
            myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                words.add( myReader.nextLine());
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getRandomWord() {
        read();
        return words.get((int) (Math.random() * words.size()));
    }


}
