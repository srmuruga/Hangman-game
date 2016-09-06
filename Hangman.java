import java.io.File;
import java.util.Random;
import java.util.Scanner;

/**
 * The Hangman class represents the actual Hangman game being played. In the
 * classic Model/View/Controller design pattern, this class serves as the Model
 * for the GUI's View.
 * 
 * @author Sakthi Rajan Murugan
 */
public class Hangman {
    /** Constant representing the max value for the random number generated */
    public static final int MAX_INDEX_VALUE = 125;
    /** Constant representing the max number of Incorrect guesses */
    public static final int NUM_INCORRECT_GUESSES = 6;
    /** Array of clues used for this game */
    Clue[] myClues;
    /** Current index of myClues being used */
    int index;
    /**
     * A String representing an obscured version of the phrase being guessed.
     * This string will represent what the user sees during the game. It will be
     * identical to the phrase from the current game's Clue, but it will have
     * underscores for any letters the user hasn't yet selected.
     */
    String obsceuredPhrase;
    /**
     * An integer that stores the current number of correct guesses made by the
     * player
     */
    int numCorrectGuesses;
    /**
     * An integer that stores the current number of incorrect guesses made by
     * the player
     */
    int numIncorrectGuesses;
    /**
     * A Random object that will generate random integers to index into the Clue
     * array
     */
    Random obj;

    /**
     * Constructs the Hangman
     * 
     * @param seed
     *            a random seed for testing
     */
    public Hangman(int seed) {
        this();
        obj.setSeed(seed);

    }

    /**
     * Constructs the Hangman object
     */
    public Hangman() {        
        processFile();
        index = 0;
        obsceuredPhrase = "";
        numCorrectGuesses = 0;
        numIncorrectGuesses = 0;
        obj = new Random();        
    }

    /**
     * Processes clues.txt that contains the category and phrase pairs that make
     * up a Clue. The method will create a Clues array with a length of 125.
     * Then opens clues.txt file and read through it line by line. Each line
     * will be passed to the processLine method, which returns a Clue. The Clue
     * will then added to the Clues array.
     */

    private void processFile() {

        myClues = new Clue[MAX_INDEX_VALUE];
        int i = 0;

        try {

            File file = new File("clues.txt");
            Scanner fileReader = new Scanner(file);

            String line = "";
            // Process through clues.txt until all lines are retrieved
            while (fileReader.hasNextLine()) {

                myClues[i] = processLine(fileReader.nextLine());

                i++;

            }
            // Close File
            fileReader.close();
        } catch (Exception e) {
            System.out.println("File not Found" + e);
            System.exit(1);
        }
    }

    /**
     * Creates and returns a clue object
     * 
     * @param line
     *            : line that is passed to get the category and phrase values
     * @return clue object that is to be added to myClues array.
     */
    private Clue processLine(String line) {
        Scanner lineScanner = new Scanner(line);
        lineScanner.useDelimiter(":");
        String category = lineScanner.next();
        String phrase = lineScanner.next();
        Clue myClue = new Clue(category, phrase);
        return myClue;
    }

    /**
     * Sets up the instance variables of the class for a new game The Clue will
     * then added to the Clues array.
     */
    public void newGame() {

        numIncorrectGuesses = 0;
        numCorrectGuesses = 0;
        obsceuredPhrase = "";
        index = obj.nextInt(MAX_INDEX_VALUE);
        generateClue();

    }

    /**
     * Gets called any time a new game is started. fills in the initial,
     * obscured phrase. It will take the phrase from the current Clue and build
     * a new string in which all the letters are replaced by underscores.
     * assigns string to obsceuredPhrase
     */
    private void generateClue() {

        String phrase = myClues[index].getPhrase();
        // Create an initial obsceured phrase taht is seperated by underscores
        for (int i = 0; i < phrase.length(); i++) {
            if (phrase.charAt(i) == (' ')) {
                obsceuredPhrase += " ";
            } else {
                obsceuredPhrase += "_";
            }

        }

    }

    /**
     * Returns the category of the current index in myClues
     * 
     * @return String category
     */
    public String getCurrentCategory() {

        return myClues[index].getCategory();
    }

    /**
     * Returns the phrase of the current index in myClues
     * 
     * @return String phrase
     */
    public String getCurrentPhrase() {
        return myClues[index].getPhrase();
    }

    /**
     * Returns whether the letter the user provided is correct.
     * 
     * @param letter the user has guessed
     * @return boolean value which is true if user has guessed correctly, and
     *         false if user has guessed incorrectly
     */
    public boolean isCorrectGuess(char letter) {
        String currentPhrase = myClues[index].getPhrase();

        boolean value = false;
        char[] myObsceuredPhraseArray = obsceuredPhrase.toCharArray();
        for (int i = 0; i < currentPhrase.length(); i++) {
            // If the letter matches a chacter in the current phrase
            if (currentPhrase.charAt(i) == letter) {
                value = true;
                // Add this character to our current obsceured phrase array
                myObsceuredPhraseArray[i] = letter;

            }
        }

        if (value == true) {
            String temp = "";
            for (int i = 0; i < myObsceuredPhraseArray.length; i++) {
                temp += myObsceuredPhraseArray[i];
            }
            obsceuredPhrase = temp;

            numCorrectGuesses++;
        } else {
            numIncorrectGuesses++;
        }
        return value;
    }

    /**
     * Returns a version of the obscured phrase that's ready for display in the
     * GUI
     * 
     * @return String to display in GUI
     */
    public String getVisiblePhrase() {
        String myObsceuredPhrase = "";

        for (int i = 0; i < obsceuredPhrase.length(); i++) {
            myObsceuredPhrase += obsceuredPhrase.charAt(i);
            myObsceuredPhrase += " ";
        }

        return myObsceuredPhrase;
    }

    /**
     * Determines if user has lost the game by evaluating how many incorrect
     * guesses the user has provided
     * 
     * @return boolean value that is true if game is lost, and false if game is
     *         not lost yet lost
     */
    public boolean isGameLost() {
        if (numIncorrectGuesses >= NUM_INCORRECT_GUESSES) {
            return true;
        }

        return false;
    }

    /**
     * Determines if user has won the game by seeing if the phrase has been
     * completely solved
     * 
     * @return boolean value that is true if game is won, and false if game is
     *         not yet won.
     */
    public boolean isGameWon() {
        boolean value = true;
        for (int i = 0; i < obsceuredPhrase.length(); i++) {
            if (obsceuredPhrase.charAt(i) == '_') {
                value = false;
            }
        }
        return value;
    }

}
