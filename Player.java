import java.util.ArrayList;

public class Player {
	// common instance variables to be accessed from another class
	public double HP;
	public double ENERGY;
	public boolean B_status;
	public boolean valid_choice;
	public boolean surrender;
	public ArrayList<Character> record;	// keep tracker of a player's chosen-option-sequence
	
	// class variables
	static public char[] available_options = {'A', 'B', 'C'};	// for randomly selecting an option
	static public ArrayList<Character> winning_record = new ArrayList<>();	// keep track of the winning chosen-option-sequence
	
	// parameterized constructor
	public Player (double HP, double ENERGY, boolean B_status, boolean valid_choice, boolean surrender) {
		this.HP = HP;
		this.ENERGY = ENERGY;
		this.B_status = B_status;
		this.valid_choice = valid_choice;
		this.surrender = surrender;
		this.record = new ArrayList<>();
	}
}
