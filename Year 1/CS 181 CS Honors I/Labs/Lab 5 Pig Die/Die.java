package Stevens.CS181.Lab5;

public class Die {

    public static final int MAX = 6;

    private int faceValue;

    //empty constructor, initial value 1
    public Die() 
    { faceValue=1; }
    
    //roll the die
    public int roll() {
	faceValue = (int)(Math.random () * MAX) +1;
	return faceValue;
    }

    //setter method
    public void setFaceValue(int faceValue) {
	if (faceValue > 0 && faceValue <= MAX)
	    this.faceValue = faceValue;
    }

    //getter method
    public int getFaceValue() 
    {   return faceValue; }

    //return a String representation for the object
    public String toString()
    {   return Integer.toString(faceValue); }
}
