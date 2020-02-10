package Stevens.CS181.Lab5;

import java.util.Scanner;

public class Pig 
{
	
    protected Scanner kbd;

    protected PigPlayer player1, player2, currentPlayer; 
    
    /** 
     * This constructor method allows to specify which player is the 
     * human and which one is the computer, along with names
     */
    public Pig(boolean isPlayer1Human, String name1,
	       boolean isPlayer2Human, String name2)
    {
    	kbd = new Scanner (System.in);
    	player1 = isPlayer1Human 
    			? new HumanPlayer (this, name1)
    			: new ComputerPlayer (this,name1);
    			player2 = isPlayer2Human 
    			? new HumanPlayer (this, name2)
    			: new ComputerPlayer (this, name2);
    			currentPlayer=player1;
    			run();
    }

    public Pig(String humanName, String computerName) 
    {   
    	this (true, humanName, false, computerName);  
    }

    public Pig() 
    {	
    	this ("Human 1", "Computer 2");   
    }
    

    protected void run()
    {
    	do playOnce();
    	while (wantsPlayAgain());
    }

    /**
     * This method queries the user for a new game after game-over
     */
    protected boolean wantsPlayAgain() 
    {
    	boolean flag = false;

    	assert(gameOver());

    	System.out.println("Play again? (yes or no)");
    	if (0 == kbd.next().compareToIgnoreCase("yes"))
    	{
    		flag = true;
    	}

    	kbd.nextLine (); // to eat up the leftover newline

    	return flag;
    }

    protected void displayHeader(PigPlayer p) 
    {
    	System.out.println("----------");
    	System.out.println(p.getName() + "\'s turn.");
    	System.out.println("----------");
    }

    protected boolean gameOver ()
    {	
    	return player1.hasWon () || player2.hasWon (); 
    }
    

    protected void swapTurn() 
    {
    	if (currentPlayer == player1)
    		currentPlayer = player2;
    	else 
    		currentPlayer = player1;
    }
    
    /**
     * This method implements the main logic of the game:
     * alternate players' turns, and shows partial score,
     * till the game is over
     */
    public void playOnce()
    {
    	player1.reset();
    	player2.reset();

    	// regarding whose turn it is, take off from where you left
    	// i.e., if player1 won, have player2 start
    	do 
    	{
    		displayHeader(currentPlayer);
    		currentPlayer.doTurn();
    		swapTurn();
	
    	} while (!gameOver());
	
    	displayWinner();
    }

    /**
     * The string representation is just the current score,
     * or the name of the winner if the current game is over
     */
    public String toString() 
    {
    	String s;

    	if (gameOver())
    	{
    		String winner = player1.hasWon () 
    								? player1.getName () 
    							: player2.getName ();
	
    		 s = winner + " won!";
    	} 
    	else
    	{
    		s = "Game status: " + "\n\t" + player1 + "\n\t" + player2;
    	}

    	return s;
    }

    protected void displayWinner () 
    {
    	assert (gameOver());

    	System.out.println("====================");
    	displayScores();
    	System.out.println("====================");
    }

    protected void displayScores ()
    {  
    	System.out.println(this); 
    }
    

    /**
     * All the logic is elsewhere, so not much really happens in the main
     */
    public static void main (String []args)
    {  	
    	new Pig ();  
    }
}
