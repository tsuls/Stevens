package Stevens.CS181.Lab5;

public class DicePair {
    private Die die1;
    private Die die2;

    public DicePair()
    { die1 = new Die(); die2 = new Die(); }

    /**
     * Operation
     */
    public void roll()
    { die1.roll(); die2.roll(); }

    /**
     * A "snake eyes" in the Pig game is a double 1
     */
    public boolean isSnakeEyes()
    { return (die1.getFaceValue () == 1) & (die2.getFaceValue () == 1); }

    /**
     * A single one looses the round points in the Pig game
     */
    public boolean hasAOne()
    { return (die1.getFaceValue () == 1) ^ (die2.getFaceValue () == 1); }

    public int getDiceTotal()
    { return (die1.getFaceValue () + die2.getFaceValue ()); }

    public String toString()
    { return "(" + die1 + "," + die2 + ")"; }
}
