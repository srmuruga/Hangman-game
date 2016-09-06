/**
 * Represents a single clue for use in the Hangman game. It is made up of two
 * Strings - the first is the category to which the clue refers, and the second
 * is the actual phrase for the user to guess
 * 
 * @author Sakthi Rajan Murugan
 */
public class Clue {

    /** Category for the phrase */
    private String category;
    /** Phrase for the current game being played */
    private String phrase;

    /**
     * Constructs the Clue object
     * 
     * @param category category for the current game
     * @param phrase phrase for the current game
     */
    public Clue(String category, String phrase) {
        this.category = category;
        this.phrase = phrase;
    }

    /**
     * @return category for game
     */
    public String getCategory() {
        return category;

    }

    /**
     * @return phrase for game
     */
    public String getPhrase() {
        return phrase;
    }

    /**
     * This method returns a String containing the category followed by the
     * phrase, separated by a hyphen (-)
     * 
     * @return String containing category-phrase
     */
    public String toString() {
        return category + "-" + phrase;
    }

    /**
     * This method returns whether this Clue and o are equal.
     * 
     * @param o object being passed in
     * @return boolean value true if clue and o are equal and false if they are
     *         not.
     */
    public boolean equals(Object o) {

        if (o instanceof Clue) {
            Clue b = (Clue) o;
            return category.equals(b.getCategory())
                    && phrase.equals(b.getPhrase());
        } else {
            return false;
        }
    }
}
