package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class that contains all the words of the game and that can return a random word
 */
public class Wording {
    ArrayList<String> words;  // list of all the words
    File myObj = new File("./src/resources/words.txt");  // file containing all the words
    Scanner myReader;  // scanner to read the file

    /*
    Initialize the list of words
     */
    private Wording(){
        words = new ArrayList<>();
    }

    /*
        Singleton pattern to have only one instance of the class
     */
    public static Wording getInstance(){
        return new Wording();
    }

    /*
        Method to read the file and add all the words to the list
     */
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

    /*
        Method to return a random word from the list
     */
    public String getRandomWord() {
        read();
        return words.get((int) (Math.random() * words.size()));
    }


}
