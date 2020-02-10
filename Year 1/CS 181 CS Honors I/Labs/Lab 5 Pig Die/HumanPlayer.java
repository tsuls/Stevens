package Stevens.CS181.Lab5;

import java.util.Scanner;

public class HumanPlayer extends PigPlayer {
    protected Scanner kbd;

    public HumanPlayer(Pig owner, String name) {
	super (owner, name);
	kbd = new Scanner (System.in);
    }

    public HumanPlayer(Pig owner) 
    {	this (owner, "Human " + numPlayers); }

    /**
     * This class implements the input interface of the 
     * game from the user
     */
    protected boolean wantsHandOver() {
	boolean flag = false;

	System.out.println("\tYour current score: " + getCurrentScore ());
	System.out.println("\tRoll again? (yes or no)");

	flag = (0 != kbd.next().compareToIgnoreCase("yes"));

	kbd.nextLine (); // to eat up the leftover newline

	return flag;
  }
}
