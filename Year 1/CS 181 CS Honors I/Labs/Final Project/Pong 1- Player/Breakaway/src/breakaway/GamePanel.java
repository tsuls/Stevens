//I pledge my honor that i have abided by the Stevens honor system
//Tyler Sulsenti
//Sara Dos Santos
package breakaway;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;//for mouse events
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

//Everything done except powerups and challenging angles
public class GamePanel extends JPanel 
{
	//Variables for the start menu
	private JLabel theTitle;
	
	int x;
	
	private JButton startButton;
	private JButton	exitButton;
	private JButton	scoreButton;
	
	private boolean isStartMenu;
	///////////////////////////////////////////////////////////////////
	//Variables for the Game
	private Paddle rect;
	private Ball circle, p1, p2;
	
	private FileReader scores;
	private BufferedReader bufferedReader;
	private String[] inScores = {"-1_", "-1_", "-1_", "-1_", "-1_"};
	private String[] playerNames = {"", "", "", "", ""};
		
	private String playerName;
	
	private int score;
	private int startDir;
	private final int TARGETCOUNT = 100;
	
	double initCX;
	double initCY;

	private int initRX, initRY;
	private int levelCounter;
	private int level;
	private int growScore;
	private int playerCount;
	private int blockCounter;
	private int width;
	private int height;

	private boolean isStart;
	private boolean firstHit;
	private boolean isDead;
	private boolean isGameOver;
	private boolean isHighScore;
	private boolean isGame;
	private boolean isClickNote;
	private boolean isP1;
	private boolean isP2;
	private boolean isBig;
	private boolean isShrink;
	
	private javax.swing.Timer timer;
	
	private JLabel theScore;
	private JLabel theLevel;
	private JLabel gameOverText;
	private JLabel clickNote;
	
	private ArrayList<Block> targets;
	private final ArrayList<Block> TARGETRESET;
	private ArrayList<Life>	 lives;
	private Life life;
	//////////////////////////////////////////////////////////////////
	//Variables for the High Score prompt
	private JLabel scorePrompt;
	private JLabel subScorePrompt;
	
	private JTextField namePrompt;
	
	private boolean isScorePrompt;		
	///////////////////////////////////////////////////////////////////
	//Variables for the HighScore Menu
	
	private JLabel theScoreTitle;
	private JLabel[] theScores;
	private JLabel[] playerScores;
	
	private JButton backButton;
	
	private boolean isScoreScreen;
	private boolean fromStart;
	
	//Ignore pieces that are commented out. They will be dealt with
	public GamePanel(Color backGroundColor, int inWidth, int inHeight)
	{
		setBackground(backGroundColor);
	
		//Set up the Menu
		theTitle = new JLabel();
		theTitle.setForeground(Color.WHITE);
		theTitle.setFont(new Font("Helvetica", Font.BOLD, 40));
		theTitle.setLocation(60,25);
		theTitle.setSize(300,80);
		
		startButton = new JButton("Start");
		exitButton = new JButton("Exit");
		scoreButton = new JButton("High Scores");
		
		startButton.addActionListener(actionListenerButton);
		exitButton.addActionListener(actionListenerButton);
		scoreButton.addActionListener(actionListenerButton);
		
		startButton.setBounds(143,175,90,50);
		exitButton.setBounds(68,175,75,50);
		scoreButton.setBounds(233,175,100,50);
		
		isStartMenu = true;
		isGame = false;
		
		this.add(startButton);
		this.add(exitButton);
		this.add(scoreButton);
		this.add(theTitle);
		
		///////////////////////////////////////////////////////////////////
		//Set up the Game
		score = 0;
		startDir = 90;
		levelCounter = 0;
		level = 1;
	
		theScore = new JLabel();
	    theScore.setForeground(Color.WHITE);
	    theScore.setFont(new Font("Helvetica", Font.BOLD, 15));
	    theScore.setLocation(300, 360);
	    theScore.setSize(70,20);
	    
	    
	    theLevel = new JLabel();
	    theLevel.setForeground(Color.WHITE);
	    theLevel.setFont(new Font("Helvetica", Font.BOLD, 15));
	    theLevel.setLocation(300, 340);
	    theLevel.setSize(70,20);
	    
	    gameOverText = new JLabel();
	    gameOverText.setForeground(Color.WHITE);
	    gameOverText.setFont(new Font("Helvetica", Font.BOLD, 40));
	    gameOverText.setLocation(75, 200);
	    gameOverText.setSize(300,80);
	    
	    clickNote = new JLabel();
	    clickNote.setForeground(Color.WHITE);
	    clickNote.setFont(new Font("Helvetica", Font.BOLD, 20));
	    clickNote.setLocation(85, 230);
	    clickNote.setSize(300,80);
	    
		width = inWidth; //Starting width and height of the panel
		height = inHeight;
		
		playerCount = 0;
		growScore = 0;
		
		rect = new Paddle(173,300,55,5, Color.RED);
		circle = new Ball(200, (rect.getLY() - 50),5,Color.RED);
		p1 = new Ball((int) ((Math.random()*370)+20), (int) ((Math.random()*(rect.getLY() - 160))+110),8 ,Color.MAGENTA, new JLabel("+10"));
		p2 = new Ball((int) ((Math.random()*370)+20), (int) ((Math.random()*(rect.getLY() - 160))+110),8,Color.BLUE, new JLabel("<->"));
		
		try {
			scores = new FileReader("src/breakaway/HighScores.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bufferedReader = new BufferedReader(scores);
		try {
			loadArrays(inScores, playerNames);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		initCX = circle.getCX();
		initCY = circle.getCY();
		initRX = rect.getLX();
		initRY = rect.getLY();
	
		targets = setTarget(1);
		TARGETRESET = setTarget(1);
		
		lives = new ArrayList<Life>();
			
		isStart = true;
		firstHit = false;
		isDead = false;
		isGameOver = false;
		isHighScore = false;
		isClickNote = false;
		isP1 = false;
		isP2 = false;		
		isBig = false;
		isShrink = true;
		
		timer = new javax.swing.Timer(5, new MoveListener()); //Involved in the moving of the circle
		timer.start();

		this.add(theScore);
		this.add(theLevel);
		this.add(gameOverText);
		this.add(clickNote);
		this.add(p1.getLabel());
		p1.getLabel().setVisible(true);
		
		addMouseListener(new PanelListener());
		addMouseMotionListener(new PanelMotionListener());
		addKeyListener(new PanelKeyBoardListener());
		setFocusable(true);
		///////////////////////////////////////////////////////////////////
		//Set up the High Score prompt
		scorePrompt = new JLabel();
		scorePrompt.setForeground(Color.WHITE);
		scorePrompt.setFont(new Font("Helvetica", Font.BOLD, 40));
		scorePrompt.setLocation(65,0);
		scorePrompt.setSize(300,80);
		
		subScorePrompt = new JLabel();
		subScorePrompt.setForeground(Color.WHITE);
		subScorePrompt.setFont(new Font("Helvetica", Font.BOLD, 15));
		subScorePrompt.setLocation(90, 35);
		subScorePrompt.setSize(300,80);
		
		namePrompt = new JTextFieldLimit(3);
		namePrompt.setBounds(175,275,50,50);
		namePrompt.setBackground(Color.GRAY);
		namePrompt.addActionListener(actionListenerTextInput);
		
		isScorePrompt = false;
		
		this.add(scorePrompt);
		this.add(subScorePrompt);
		this.add(namePrompt);
	
		///////////////////////////////////////////////////////////////////
		//Set up the High Score Page
		theScoreTitle = new JLabel();
		theScoreTitle.setForeground(Color.WHITE);
		theScoreTitle.setFont(new Font("Helvetica", Font.BOLD, 40));
		theScoreTitle.setLocation(50,0);
		theScoreTitle.setSize(300,80);
		
		backButton = new JButton("Back");
		backButton.addActionListener(actionListenerButton);
		backButton.setBounds(0,350,90,30);
		backButton.setVisible(false);
		
		theScores = new JLabel[inScores.length];
		playerScores = new JLabel[inScores.length];
		
		isScoreScreen = false;
		fromStart = false;
		
		this.add(theScoreTitle);
		this.add(backButton);
		
		int scoreY = 50;
		int pScoreY = 50;
		
		for(int i = 0; i < theScores.length; i++)
		{
			theScores[i] = new JLabel();
			theScores[i].setForeground(Color.WHITE);
			theScores[i].setFont(new Font("Helvetica", Font.BOLD, 40));
			theScores[i].setLocation(50,scoreY);
			theScores[i].setSize(300,80);				
			this.add(theScores[i]);
			scoreY += 50;
			
			playerScores[i] = new JLabel();
			playerScores[i].setForeground(Color.WHITE);
			playerScores[i].setFont(new Font("Helvetica", Font.BOLD, 40));
			playerScores[i].setLocation(300,pScoreY);
			playerScores[i].setSize(300,80);				
			this.add(playerScores[i]);
			pScoreY += 50;
		}
	}
	
	public void paintComponent (Graphics g)
	{
		super.paintComponent(g);
		if(isStartMenu)
		{
			startButton.setVisible(true);
			exitButton.setVisible(true);
			scoreButton.setVisible(true);
			theTitle.setVisible(true);
			theTitle.setText("BREAKAWAY");
			rect.fill(g);
			circle.fill(g);
			resetPaddle();
			
			//Turn off Score Prompt
			scorePrompt.setVisible(false);
			subScorePrompt.setVisible(false);
			namePrompt.setVisible(false);
			
			//Turn off High Score Menu Items
			theScoreTitle.setVisible(false);
			backButton.setVisible(false);
			for(int i = 0; i < theScores.length; i++)
			{
				theScores[i].setVisible(false);
				playerScores[i].setVisible(false);
			}
						
			//Turn Off Game items
			gameOverText.setVisible(false);
			clickNote.setVisible(false);
			theScore.setVisible(false);
			theLevel.setVisible(false);
			isBig = false;
			p1.getLabel().setVisible(false);
		}
		
		if(isGame)
		{
			//Turn off the start Menu Items
			startButton.setVisible(false);
			exitButton.setVisible(false);
			scoreButton.setVisible(false);
			theTitle.setVisible(false);
			
			//Turn off Score Prompt
			scorePrompt.setVisible(false);
			subScorePrompt.setVisible(false);
			namePrompt.setVisible(false);
			
			//Turn off High Score Menu Items
			theScoreTitle.setVisible(false);
			backButton.setVisible(false);
			for(int i = 0; i < theScores.length; i++)
			{
				theScores[i].setVisible(false);
				playerScores[i].setVisible(false);
			}
			
			//Controls Score Label
			theScore.setVisible(true);
			theScore.setText("Score: " + score);
	
			//Controls Level Label
			theLevel.setVisible(true);
			theLevel.setText("Level: " + level);
		
			//Fills in the paddle and ball
			rect.fill(g);
			circle.fill(g);
			if(isP1)
			{
				p1.fill(g);
				p1.getLabel().setVisible(true);
			}
			else
			{
				p1.getLabel().setVisible(false);
			}
			
			if(isP2)
			{
				p2.fill(g);
			}
		
			//Fills in the target blocks
			for(int i = 0; i < targets.size(); i++)
			{
				targets.get(i).fill(g);
			}

			//Fills in the Life icons
			//WHY. DOES. ONLY. ONE. ICON. APPEAR.
			for(int i = 0; i < lives.size(); i++)
			{
				lives.get(i).fill(g);
			}
			
			//Take care off only one of the lives appearing even though two should in the previous for loop
			if(lives.size() >= 1)
			{
				life.fill(g);
			}
			
			if(isGameOver)
			{
				gameOverText.setVisible(true);
				clickNote.setVisible(true);
				
				gameOverText.setText("GAME OVER");
				clickNote.setText("Cick to see High Scores");
			}
			else
			{	
				gameOverText.setVisible(false);
				clickNote.setVisible(false);
			}
		}
		
		if(isScorePrompt)
		{	
			//Turn off the start Menu Items
			startButton.setVisible(false);
			exitButton.setVisible(false);
			scoreButton.setVisible(false);
			theTitle.setVisible(false);
			
			//Turn off High Score Menu Items
			theScoreTitle.setVisible(false);
			backButton.setVisible(false);
						
			//Turn Off Game items
			gameOverText.setVisible(false);
			clickNote.setVisible(false);
			theLevel.setVisible(false);
			theScore.setVisible(false);
			p1.getLabel().setVisible(false);
			
			//Set up Score Prompt
			scorePrompt.setVisible(true);
			subScorePrompt.setVisible(true);
			namePrompt.setVisible(true);
			
			scorePrompt.setText("HIGH SCORE!!");
			subScorePrompt.setText("Please Enter Your Initials Below");	
		}
		
		if(isScoreScreen)
		{
			
			if(fromStart)
			{				
				theScore.setVisible(false);
				theLevel.setVisible(false);
			}
			else
			{
				theScore.setVisible(true);
				theLevel.setVisible(true);
			}
			//Turn off the start Menu Items
			startButton.setVisible(false);
			exitButton.setVisible(false);
			scoreButton.setVisible(false);
			theTitle.setVisible(false);
			
			//Turn Off Game items
			gameOverText.setVisible(false);
			clickNote.setVisible(false);
			p1.getLabel().setVisible(false);
			
			//Turn off Score Prompt
			scorePrompt.setVisible(false);
			subScorePrompt.setVisible(false);
			namePrompt.setVisible(false);
			
			//Set up the High Score Screen Items
			theScoreTitle.setVisible(true);
			theScoreTitle.setText("HIGH SCORES:");
			backButton.setVisible(true);
			
			for(int i = 0; i < theScores.length; i++)
			{
				theScores[i].setVisible(true);
				if(inScores[i].substring(0,1).equals("-"))
				{
					theScores[i].setText((i + 1) + ":		");
				}
				else
				{
					if(playerNames[i].indexOf("_") >= 0)
					{
						theScores[i].setText((i + 1) + ":	" + playerNames[i].substring(1));// + "\t\t\t" +  inScores[i]);
					}
					else
					{
						theScores[i].setText((i + 1) + ":	" + playerNames[i]); //+ "\t\t\t" +  inScores[i]);
					}
				}
				playerScores[i].setVisible(true);
				if(inScores[i].substring(0,1).equals("-"))
				{
					playerScores[i].setText("");
				}
				else
				{
					playerScores[i].setText(inScores[i]);
				}			
			}
		}
	}
	/**
	 * Gets Button Input
	 */
	ActionListener actionListenerButton = new ActionListener()
	 {
		/**
		 * Performs different actions based upon the button pressed
		 */
		public void actionPerformed(ActionEvent e) 
		{
			String getActionButton = e.getActionCommand();
	    	  
	    	  if(getActionButton.equals("Start"))
	    	  {
	    		  isStartMenu = false;
	    		  isGame = true;
	    		  isScoreScreen = false;
	    		  isGameOver = false;
	    		  fromStart = false;
	    		  isP1 = false;
	    		  isP2 = false;
	    		  blockCounter = 0;
	    		
	    		  //Add lives at game start
	    		  lives.add(new Life(10,360, 5, Color.RED));
	    		  lives.add(new Life(30,360, 5, Color.RED));
	    		  life = new Life(10,360, 5, Color.RED);
	    	  }
	    	  if(getActionButton.equals("Exit"))
	    	  {
	    		  SwingUtilities.getWindowAncestor(GamePanel.this).dispose();
		          System.exit(0);
	    	  }
	    	  if(getActionButton.equals("High Scores"))
	    	  {
	    		  isScoreScreen = true;
	    		  isGame = false;
	    		  isStartMenu = false;
	    		  fromStart = true;
	    	  }
	    	  if(getActionButton.equals("Back"))
	    	  {
	    		  isScoreScreen = false;
	    		  isGame = false;
	    		  isStartMenu = true;
	    		  isScorePrompt = false;
	    		  fromStart = false;
	    		  targets = setTarget(1);
	    		  level = 1;
	    		  score = 0;
	    		  resetPaddle();
	    	  }
		}	
	 };
	 /**
	  * Gets Text action
	  */
	 ActionListener actionListenerTextInput = new ActionListener()
	 {
		 /**
		  * Used the text input for the name of the player. Shifts the Arrays and writes the updated leaderboard to the .txt file
		  */
	      public void actionPerformed(ActionEvent actionEvent) 
	      {
	    	  playerName = actionEvent.getActionCommand(); //Get the Players name if they get a high score
	    	  playerCount++;
	    	  
	    	  namePrompt.setText("");
	  	  
	    	  isScorePrompt = false;
	    	  isScoreScreen = true;
	    	  shiftArrays(x);
	    	  outScore();
	    	
	      }
	 };
	
	 /**
	  * Sets up the colored target blocks.
	  * 
	  * @param l	the number to match to a corresponding color of block
	  * @return		an ArrayList of colored target blocks
	  */
	private ArrayList<Block> setTarget(int l)
	{
		ArrayList<Block> b = new ArrayList<Block>();
		
		int bLX = 0;
		int bLY = 0;
		int count = 1;
		
		for(int i = 0; i < TARGETCOUNT; i++)
		{
			b.add(new Block(bLX,bLY,40,10,l,circle));
			bLX += 40;
			if(count % 10 == 0)
			{
				bLY += 10;
				bLX = 0;
				l++;
			}
			count ++;
		}	
		return b;
	}
	
	/**
	 * Resets the paddle and ball
	 */
	private void resetPaddle()
	{
		circle.setX(initCX);
		circle.setY(initCY);
		circle.setDirection(startDir);
		rect.setLX(initRX);
		rect.setLY(initRY);
		firstHit = false;
		isStart = true;
		isBig = false;
		isShrink = true;
	}
	
	/**
	 * Loads the current .txt file scores into an two arrays, one for scores and one for names
	 * 
	 * @param x		the array of scores
	 * @param y		the array of names
	 * @throws IOException
	 */
	private void loadArrays(String[] x, String[] y) throws IOException
	{
		int i = 0;
		String nextLine;
		while ((nextLine = bufferedReader.readLine()) != null) 
		{
		  x[i] = nextLine.substring(0, nextLine.indexOf("_"));
		  if(nextLine.indexOf("_") > 0)
		  {
			  y[i] = nextLine.substring(nextLine.indexOf("_"));
		  }
		  i++;
		}
	}
	
	/**
	 * Determines if your score is a high score
	 * 
	 * @return		the location of your high score, for shifting purposes
	 */
	private int determineScore()
	{
		for(int i = 0; i < inScores.length; i++)
		{
			int toTest;
		
			if(inScores[i].substring(0, 1).equals("-"))
			{
				toTest = Integer.parseInt(inScores[i].substring(0, 2));
			}
			else
			{
				toTest = Integer.parseInt(inScores[i]);
			}
			
			if(toTest < score) //Is you score higher than a score on the board?
			{
				isHighScore = true;
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * A helper method to insert [different] elements into two arrays at the same position
	 * 
	 * @param i		the position to insert at
	 */
	private void shiftArrays(int i)
	{
		inScores = shift(inScores, i, score + "");
		playerNames = shift(playerNames, i, playerName);
	}
	
	/**
	 * Updates the .txt file of the current high scores
	 */
	private void outScore()
	{
		try {
			PrintWriter writer = new PrintWriter("src/breakaway/HighScores.txt", "UTF-8");
			for(int i = 0; i < inScores.length; i++)
			{
				if((inScores[i] + playerNames[i]).indexOf("_") < 0)
				{
					writer.println(inScores[i] + "_" + playerNames[i]);	
				}
				else
				{
					writer.println(inScores[i] + playerNames[i]);			
				}
			}
			writer.close();		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Inserts an element into an array and shifts its contents. 
	 *
	 * @param a		the array to shift 	
	 * @param pos	the index to insert at
	 * @param value	the value to be inserted
	 * @return		an array with the new value inserted and the contents shifted
	 */
	private String[] shift(String[] a, int pos, String value) 
	{
	    String[] result = new String[a.length];
	   
	    for(int i = 0; i < pos; i++)
	    {
	        result[i] = a[i];
	    }
	   
	    result[pos] = value;
	    for(int i = pos + 1; i < a.length; i++)
	    {
	        result[i] = a[i - 1];
	    }
	    
	    return result;
	}

	/**
	 * Controls KeyBoard input
	 */
	private class PanelKeyBoardListener extends KeyAdapter
	{
		/**
		 * Starts the game when the space bar is pressed
		 */
		public void keyPressed(KeyEvent e)
		{
			if(e.getKeyCode() == KeyEvent.VK_SPACE);
			{
				if(isStart && !isGameOver && !isStartMenu)
				{
					isStart = false;
					circle.setVelocity(2);
					circle.setDirection(startDir);
					circle.move();
				}
			}
			repaint();
		}	
	}
	
	/**
	 * Gets Mouse Clicking
	 */
	private class PanelListener extends MouseAdapter
	{
		/**
		 * Allows us to click past the game over screen
		 */
		public void mousePressed(MouseEvent e)
		{
			if(isClickNote && !isScorePrompt)
			{
				isScoreScreen = true;
	    		isGame = false;
	    		isStartMenu = false;
	    		isClickNote = false;
	    		isScorePrompt = false;
			}
		}
	}
	/**
	 * Controls the moving of the Paddle
	 */
	private class PanelMotionListener extends MouseMotionAdapter
	{	
		/**
		 * Makes the paddle move with the mouse
		 */
		public void mouseMoved(MouseEvent e)
		{	
			if(!isGameOver && !isStartMenu) //Can only move when the game is not over
			{	
				if(e.getX() >= 25 && e.getX() <= 375)
				{
					rect.move(e.getX() - rect.getMidX(), 0); //Paddle can only move in X direction
					if(isStart) //Ball will move with the paddle until it is shot
					{
						circle.move(e.getX() - circle.getCX(), 0);
					}
				}
			}
			repaint();
		}
	}
	
	/**
	 * Controls Collision for all objects in the game. Also controls the effects of the powerups.  
	 */
	private class MoveListener implements ActionListener
	{
		public void actionPerformed(ActionEvent a) 
		{		
			if(!isGameOver)
			{
				//All the possible collisions
				boolean right = circle.getRight() >= 400; //Right of frame
				boolean left = circle.getLeft() <= 0;			 //Left of frame
				boolean top = circle.getTop() <= 0;				 //Top of frame
				boolean bottom = circle.getBottom() >= 400;		//Bottom of frame, 
				
				boolean paddleCol = circle.getBounds().intersects(rect.getBounds());			
					
				//Controls Death and Falling
				if(bottom) 
				{
					isDead = true;
					if(isBig)
					{
						rect.setWidth(rect.getWidth()/4); //Shrink the Paddle
						rect.move(rect.getWidth()*2, 0); //Fluid Movement of the paddle
						isBig = false;
						isShrink = true;
						growScore = 0;
					}
					
				}
				if(isDead)
				{
					if(lives.size() > 0)
					{
						lives.remove(0);
					}
					else if(lives.size() == 0)
					{
						isGameOver = true;
						isClickNote = true;
					}
					resetPaddle();
					isDead = false;
				}
					//These Two if statements control the ball, its movement and collisions 
					if(!firstHit) //The ball has not hit the paddle for the fist time
					{
						if(paddleCol)
						{	
							circle.setDirection(45);
							firstHit = true;
							isDead = false;
						}
					}
					
					if(firstHit && !isDead) // The ball has hit the paddle for the first time
					{
						if(right || left || top || paddleCol) //bottom will be changed
						{
								int curDir = circle.getDir();
								circle.setDirection(curDir + 90);
						}
							
						//Detect Collisions with the target blocks
						for(int i = 0; i < targets.size(); i++)
						{
							if(targets.get(i).getBounds().intersects((circle.getBounds())))
							{				
								int curDir = circle.getDir();
								if(curDir == 225)
								{
									circle.setDirection(curDir  - 90);
								}
								else
								{
									circle.setDirection(curDir + 90);
								}
								targets.remove(i); 
								score++;
								blockCounter++;
								levelCounter++;
								if(isBig)
								{
									growScore++;
								}
							}
						}
					}
	
					//Make sure that the angle is capped and doesnt overflow
					if(circle.getDir() >= 360)
					{
						circle.setDirection(circle.getDir() - 360);
					}		
				
				//Game is Over
				if(targets.size() == 0)
				{
					isGameOver = true;
					isClickNote = true;
				}
				
				if(blockCounter != 0 && blockCounter % 23 == 0 && !isP1)
				{
					isP1 = true;
				}
				
				if(blockCounter != 0 && blockCounter % 14 == 0 && !isP2)
				{
					isP2 = true;
				}
							
				//Powerups
				if(circle.getBounds().intersects(p1.getBounds()) && isP1)
				{
					isP1 = false;
					score += 10;				
				}
			
				if(circle.getBounds().intersects(p2.getBounds()) && isP2)
				{
					isP2 = false;
					isShrink = false;
					rect.setWidth(rect.getWidth() * 4); //Grow the Paddle
					rect.move(-rect.getWidth()/2, 0); //Fluid movement of the paddle
					isBig = true;
				}
				if(isBig)
				{
					//Loose your powerup if you drop the ball or pass the score limit
					if(growScore == 7 || isDead || isShrink)
					{
						rect.setWidth(rect.getWidth()/4); //Shrink the Paddle
						rect.move(rect.getWidth()*2, 0); //Fluid Movement of the paddle
						isBig = false;
						isShrink = true;
						growScore = 0;		
					}
				}
							
				//Level up
				if(levelCounter == 15)
				{
					levelCounter = 0;
					level++;
					circle.setVelocity(circle.getVelocity() + 0.2);					
				}
				
				//Circle will stay stationary until game is started
				if(!isStart)
				{
					circle.move();
				}
				
				if(isGameOver)
				{
					 x = determineScore();
					if(isHighScore)
					{
						isScorePrompt = true;
						isGame = false;
						isHighScore = false;
					}
				}
				repaint();
			}
		}
	}
} 