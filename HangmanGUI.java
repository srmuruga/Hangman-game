import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Creates a GUI view/controller for the Hangman program. 
 * The GUI contains a Gallows, a panel with the 
 * clue category and currently visible word, and
 * a button for each letter in the alphabet.
 */
public class HangmanGUI extends JFrame implements ActionListener {

	public static final int NUMBER_OF_LETTERS = 26;
        public static final int WIDTH = 1000;
        public static final int HEIGHT = 600;
        public static final int X_LOCATION = 100;
        public static final int Y_LOCATION = 100;
        public static final Font CATEGORY_FONT = new Font("Times", 1, 30);
	public static final Font PHRASE_FONT = new Font("Monospaced", 0, 20);
        
        
        /** Array of buttons for alphabet. */
	private JButton[] btnLetters;
	/** Instance of Hangman model */
	private Hangman model;
	/** Instance of Gallows JPanel */
	private Gallows gallows;
	/** Label for the Clue's category text */
	private JLabel lblCategory;
	/** Label for the Clue's phrase */
	private JLabel lblPhrase;
	/** Button to start a new Hangman game */
	private JButton btnNewGame;
	/** Button to quit Hangman */
	private JButton btnQuit;

	/**
	 * Constructs the Hangman GUI
	 * @param seed a random seed for testing
	 */
	public HangmanGUI(int seed) {
		//initialize the GUI JFrame
                setSize(WIDTH, HEIGHT);
		setTitle("CSC116 Hangman");
		setLocation(X_LOCATION, Y_LOCATION);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//Check to see if valid seed for testing
		//Creates the Hangman model and starts a game
		if (seed == -1) {
			model = new Hangman();
		} else {
			model = new Hangman(seed);
		}
		model.newGame();

		//Create a new Gallows JPanel for inclusion
		//in GUI
		gallows = new Gallows();

		//Create remaining GUI elements
		btnNewGame = new JButton("New Game");
		btnNewGame.addActionListener(this);

		btnQuit = new JButton("Quit");
		btnQuit.addActionListener(this);

		btnLetters = new JButton[NUMBER_OF_LETTERS];

		int i = 0;
		
		for(char letter = 'A'; letter <= 'Z'; letter++, i++) {
			btnLetters[i] = new JButton("" + letter);
			btnLetters[i].addActionListener(this);
		}

		
		lblCategory = new JLabel(model.getCurrentCategory(), JLabel.CENTER);
		lblCategory.setFont(CATEGORY_FONT);
		lblPhrase = new JLabel(model.getVisiblePhrase(), JLabel.CENTER);
		lblPhrase.setFont(PHRASE_FONT);

		//Get the container and add components to GUI
		Container c = getContentPane();

		c.setLayout(new BorderLayout());

		JPanel center = new JPanel();

		JPanel n2 = new JPanel();
		n2.setLayout(new GridLayout(2,1));
		n2.add(lblCategory);
		n2.add(lblPhrase);

		center.setLayout(new GridLayout(1,2));
		center.add(gallows);
		center.add(n2);

		JPanel south = new JPanel();
		south.setLayout(new GridLayout(4,7));
		for(i = 0; i < NUMBER_OF_LETTERS; i++) {
			south.add(btnLetters[i]);
		}

		south.add(btnNewGame);
		south.add(btnQuit);
		
		c.add(center, BorderLayout.CENTER);
		c.add(south, BorderLayout.SOUTH);

		setVisible(true);
	}
	
	/**
	 * Performs actions based on the source of the
	 * ActionEvent as picked by the player
	 * @param e ActionEvent performed by user
	 */
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		//Check to see if event was from one of the
		//letter buttons
		for(int i = 0; i < NUMBER_OF_LETTERS; i++) {
			if(o == btnLetters[i]) {
				String guess = btnLetters[i].getText();
				btnLetters[i].setEnabled(false);
				boolean isCorrectGuess = model.isCorrectGuess(guess.charAt(0));
				//Add body part if incorrect guess
				if(!isCorrectGuess) {
					gallows.addBodyPart();
					//Check for a loss
					if(model.isGameLost()) {
						gallows.gameOver(false);
						lblPhrase.setText(model.getCurrentPhrase());
						disableButtons();
					}
				}
				//Update the phrase with guessed letter(s)
				else {
					lblPhrase.setText(model.getVisiblePhrase());
					//Check for a win
					if(model.isGameWon()) {
						gallows.gameOver(true);
						disableButtons();
					}
				}

			}
		}
		//Check for a user requesting a new game
		if(o == btnNewGame) {
			model.newGame();
			lblCategory.setText(model.getCurrentCategory());
			lblPhrase.setText(model.getVisiblePhrase());
			gallows.reset();
			enableButtons();
		}
		//Check for a user requesting to quit
		if(o == btnQuit) {
			System.exit(0);
		}
	}

	/**
	 * Disable all letter buttons
	 */
	private void disableButtons() {
		for(int i = 0; i < NUMBER_OF_LETTERS; i++) {
			btnLetters[i].setEnabled(false);
		}
	}

	/**
	 * Enable all letter buttons
	 */
	private void enableButtons() {
		for(int i = 0; i < NUMBER_OF_LETTERS; i++) {
			btnLetters[i].setEnabled(true);
		}
	}

	/**
	 * Start the HangmanGUI and read in the 
	 * command line argument (if any) with the test 
	 * seed
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length == 1) {
			new HangmanGUI(Integer.parseInt(args[0]));
		} else {
			new HangmanGUI(-1);
		}
	}

}
