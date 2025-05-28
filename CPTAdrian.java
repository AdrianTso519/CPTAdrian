import arc.*;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Font;

public class CPTAdrian{
	public static void main(String[] args){
		Console con = new Console("CPTAdrian - Video Poker", 1280, 720);
		int intDeck[][];
		intDeck = loadDeck();
		// test: con.println(intDeck[50][0]);
		// test: con.println(intDeck[50][1]);
		MainScreen(con);
	}
	
	public static void MainScreen(Console con){
		BufferedImage imgMain = con.loadImage("Cover.png");
		// TEMPORARY COVER IMAGE
		con.drawImage(imgMain, 0, 0);
		con.println("");
		con.clear();
		// con.println("Video Poker");
		// con.println("Play (P)");
		// con.println("View Leaderboard (L)");
		// con.println("Help (H)");
		// con.println("Quit (Q)");
		char chrInputMain = con.getChar();
		
		// reacting to different keys being clicked
		if(chrInputMain == 'q' || chrInputMain == 'Q'){
			con.closeConsole();
		}else if(chrInputMain == 's' || chrInputMain == 'S'){
			con.clear();
			con.setDrawColor(Color.BLACK);
			con.fillRect(0,0,1280,720);
			con.println("What's the difference between a large pizza and a poker player?");
			con.println("A large pizza can feed a family of four, a poker player can't.");
			con.sleep(5000);
			MainScreen(con);
		}
	}
	public static int[][] loadDeck(){
		// initialize deck
		int intDeck[][];
		intDeck = new int[52][3];
		
		int intCardValue = 1;
		int intCardSuit = 1;
		int intCardRow;
		
		for(intCardRow = 0; intCardRow < 52; intCardRow++){
			// loads 1-13 4 times
			intDeck[intCardRow][0] = intCardValue;
			// loads 1-4 13 times
			intDeck[intCardRow][1] = intCardSuit;
			// random integer
			intDeck[intCardRow][2] = (int)(Math.random()*100);
			// value increases until 14
			// suit increases by one when value resets
			intCardValue++;
			if(intCardValue == 14){
				intCardSuit++;
				intCardValue = 1;
			}
		}
		
		System.out.println(intDeck[51][1]);
		
		return intDeck;
	}
}
