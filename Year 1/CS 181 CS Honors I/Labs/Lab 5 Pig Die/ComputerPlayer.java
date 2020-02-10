package Stevens.CS181.Lab5;

public class ComputerPlayer extends PigPlayer {
    /** Constant specifying how many points to attempt per round */
    public static int DEFAULT_TARGET_POINTS_PER_ROUND = 20;
    private int targetPointsPerRound;

    public ComputerPlayer(Pig owner, String name, int target) 
    {	super(owner, name); targetPointsPerRound=target; }
    

    public ComputerPlayer(Pig owner, String name) 
    {	this(owner, name, DEFAULT_TARGET_POINTS_PER_ROUND); }
    

    public ComputerPlayer(Pig owner)
    {	this (owner, "Computer " + numPlayers);   }

    /**
     * This method implements a simple strategy whereby the player 
     * keeps rolling the dice until a certain fixed per-round target
     * of points has been accumulated
     */
    protected boolean wantsHandOver() 
    {   return (roundScore >= targetPointsPerRound); }
}
