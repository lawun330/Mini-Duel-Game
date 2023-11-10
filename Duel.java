//importing packages and modules
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Scanner;
import java.lang.Math;

public class Duel {
	
	// function to display the HP and ENERGY
	public static void displayStatus(Player player, Player _player, int number) {
		System.out.println("---------------------");
		System.out.println("Round " + number + " :");
		System.out.println("Player A's Health: " + player.HP + "\tPlayer B's Health: " + + _player.HP);
		System.out.println("Player A's ENERGY: " + player.ENERGY + "\tPlayer B's ENERGY: " + _player.ENERGY);
		System.out.println("---------------------");
	}
	
	// function to ask user input for the choice
	public static char askOption(String name) {
		System.out.println("What would " + name + " do?");
		System.out.println("A. Deal normal damage");
		System.out.println("B. Channel your energy");
		System.out.println("C. Use your ultimate power");
		System.out.println("D. Show a white flag");
		System.out.println(name + " >>>");
		char chosen_option =  new Scanner(System.in).next().toUpperCase().charAt(0);	// take the case-insensitive first letter of the input string
		return chosen_option;
	}
	
	// function to do processing based on the chosen option
	public static ArrayList<Object> processOption(String player, char option, ArrayList<Character> record, double HP, double ENERGY) {
		// initiate local variables to ensure the return value
		boolean B_status = false;
		boolean valid_choice = false;
		boolean surrender_status = false;
		double new_HP = HP;
		double new_ENERGY = ENERGY;
		ArrayList<Object> new_info = new ArrayList<>();

		// when the player chooses the valid option, take record
		if (option=='A' || option=='B' || option=='C' || option=='D') {
			valid_choice = true;
			record.add(option);
		}
		
		// the player chooses to cause damage 
		if (option=='A') {
			new_HP = HP - 1.5;
			System.out.println(player + " inflicts damage on the opponent.");
		}
		
		// the player chooses to channel energy
		else if (option=='B') {
			B_status = true;	// boolean value for comparison
			System.out.println(player + " tries to channel energy.");
		}
		
		// the player uses the ultimate power to cause max damage
		else if (option=='C') {
			if (ENERGY >= 6)	// enough energy
			{
				new_HP = HP - 6;
				new_ENERGY = ENERGY - 6;	// energy is consumed in the process
				System.out.println(player + " causes heavy damage!");
			}
			else	// not enough energy
				System.out.println(player + " does not have enough energy. Ultimate power cannot be used.");
		}
		
		// the player chooses to surrender
		else if (option=='D') {
			System.out.println(player + " calls surrender!");
			surrender_status = true;
		}
		
		// there can be no negative HP
		if (new_HP<0)
			new_HP = 0;
		
		// add new stats to the ArrayList to return
		new_info.add(new_HP);
		new_info.add(new_ENERGY);
		new_info.add(B_status);
		new_info.add(valid_choice);
		new_info.add(surrender_status);
		
		return new_info;
	}
	
	// function to choose an option
	public static ArrayList<Object> playGame(String name, Player player, Player _player, boolean computer) {
		char chosen_option = ' ';
		if (computer==true) {	// randomly selecting an option
			int index = (int) (3 * Math.random());	// select a number between 0, 1, 2 to avoid surrendering in computer turns
			chosen_option = Player.available_options[index];	// choose appropriate option
		}
		else if (computer==false) {	// the user types an option
			chosen_option = askOption(name);
		}
		return processOption(name, chosen_option, player.record, _player.HP, player.ENERGY);	// get the new stats
	}
	
	// main() or the point of entry
	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);
		
		// create two objects for two players
		Player playerA = new Player(10, 0, false, false, false);
		Player playerB = new Player(10, 0, false, false, false);
		
		// create variables to track the rounds as well as the option for computer player
		boolean computer_opponent = false;
		boolean computer_player = false;
		int round_number = 1;
		int rounds_won = 0;
		int rounds_lost = 0;

		// keep asking the same question
		while(true) {
			System.out.println("Welcome to the game. Are you ready to battle?");
			System.out.println("A. Play with bot");
			System.out.println("B. Play with another user");
			System.out.println("C. Watch two bots play");
			System.out.println("Enter 'A' or 'B' or 'C':");
			char chosen_opponent = input.next().toUpperCase().charAt(0);		
			if (chosen_opponent=='A') {
				computer_opponent = true;
				break;	// only stop asking if the answer is valid
			}
			else if (chosen_opponent=='B')
				break;	// only stop asking if the answer is valid
			else if (chosen_opponent=='C') {
				computer_opponent = true;
				computer_player = true;
				break;	// only stop asking if the answer is valid
			}		
		}
		
		System.out.println("The game has started...");
		
		// play 5 rounds but each round will take indefinite turns
		while (round_number < 6) {
			// show HP and ENERGY before every choice to help making better decision
			displayStatus(playerA, playerB, round_number);
			// one of the players is dead
			if (playerA.HP<=0 || playerB.HP<=0) {
				round_number++;	// move on to the next round
				if (playerA.HP>0) {	// player A is alive and player B is dead
					System.out.println("Player A has won the round!!!");
					rounds_won++;	// note the player A's total won round
					for (Character element: playerA.record)	// copy player A's chosen-option-sequence that leads to a win
						Player.winning_record.add(element);
					Player.winning_record.add(' ');	// there can be multiple winning sequences // add a blank character to separate
				}
				else if (playerB.HP>0) {	// player B is alive and player A is dead
					System.out.println("Player B has won the round!!!");
					rounds_lost++;	// note the player A's total lost round
					for (Character element: playerB.record) // copy player's B chosen-option-sequence that leads to a win
						Player.winning_record.add(element);
					Player.winning_record.add(' ');	// there can be multiple winning sequences // add a blank character to separate
				}
				else	// both players are dead
					System.out.println("What a tie!!!");
				// reset HP, ENERGY, and record of options for next round
				playerA.HP = 10;
				playerA.ENERGY = 0;
				playerB.HP = 10;
				playerB.ENERGY = 0;
				playerA.record = new ArrayList<Character>();
				playerB.record = new ArrayList<Character>();
				continue;
			}
			// no player is dead
			else {
				// keep asking the same question
				while(!playerA.valid_choice) {
					ArrayList<Object> new_data = playGame("Player A", playerA, playerB, computer_player);
					// update stats
					playerB.HP = (double) new_data.get(0);
					playerA.ENERGY = (double) new_data.get(1);
					playerA.B_status = (boolean) new_data.get(2);
					playerA.valid_choice = (boolean) new_data.get(3);
					playerA.surrender = (boolean) new_data.get(4);
				}
				// keep asking the same question
				while(!playerB.valid_choice) {				
					ArrayList<Object> new_data = playGame("Player B", playerB, playerA, computer_opponent);
					// update stats
					playerA.HP = (double) new_data.get(0);
					playerB.ENERGY = (double) new_data.get(1);
					playerB.B_status = (boolean) new_data.get(2);
					playerB.valid_choice = (boolean) new_data.get(3);
					playerB.surrender = (boolean) new_data.get(4);
				}
				
				// if both players have chosen a valid option
				if (playerA.valid_choice==true && playerB.valid_choice==true) {
					// check who is surrendering
					if (playerA.surrender) {
						playerA.HP=0;
						playerA.ENERGY=0;
					}
					else if (playerB.surrender) {
						playerB.HP=0;
						playerB.ENERGY=0;
					}
					// compare the B_status of each player // reduce HP according to the game rule
					if (playerA.B_status==true && playerB.B_status==true) {
						playerA.HP = playerA.HP - 2.5;
						playerB.HP = playerB.HP - 2.5;
						// there can be no negative HP 
						if (playerA.HP<0)
							playerA.HP = 0;
						if (playerB.HP<0)
							playerB.HP = 0;
					}
					// increase ENERGY according to the game rule
					else if (playerA.B_status==true && playerB.B_status==false)
						playerA.ENERGY = playerA.ENERGY + 2;
					else if (playerA.B_status==false && playerB.B_status==true)
						playerB.ENERGY = playerB.ENERGY + 2;
				}
				// re-adjust variables for next turn's evaluation
				playerA.valid_choice = false;
				playerB.valid_choice = false;
				playerA.surrender = false;
				playerB.surrender = false;
				playerA.B_status = false;
				playerB.B_status = false;
			}
		}
		
		System.out.println("*********************");
		// check the game winner
		if (rounds_won>=4) 
			System.out.println("CONGRATULATION! Player A has won the game.");
		else if (rounds_lost>=4)
			System.out.println("CONGRATULATION! Player B has won the game.");
		else if (rounds_won<4 && rounds_lost<4)
			System.out.println("The game is a tie. You may win next time!");
		
		// ask for a log
		System.out.println("Do you want to check the winning options? Y or N:");
		if (input.next().toUpperCase().charAt(0)=='Y') {
			System.out.println("Enter the file name:");	// customize a file name
			File log = new File(input.next());
			try {	// either create or append a file
				// add those sequences of chosen-option that ultimately leads to a win
				Files.writeString(log.toPath(), Player.winning_record.toString(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
			}
			catch(IOException ex) {	// catching errors
				System.out.println("Error: " + ex.getMessage());
			}
		}	
		
		// final output of the program
		System.out.println("Successfully done.");
		System.out.println("Thank you for playing ^_^");
	}
}
