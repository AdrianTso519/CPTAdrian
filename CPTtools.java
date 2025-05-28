import arc.*;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Font;

public class CPTtools{
	// main screen
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
		}else if(chrInputMain == 'p' || chrInputMain == 'P'){
			PlayScreen(con);
		}
	}
	
	// play screen
	public static void PlayScreen(Console con){
		con.println("TEST");
		con.clear();
		con.setDrawColor(Color.BLACK);
		con.fillRect(0,0,1280,720);
		
		// ask for username
		String strUserName;
		con.println("What is your name?");
		strUserName = con.readLine();
		
		// giving out money to player
		int intUserMoney = 0;
		if(strUserName.equalsIgnoreCase("statitan")){
			intUserMoney = 100000;
		}else{
			intUserMoney = 1000;
		}
		
		System.out.println("TEST MONEY VALUE: "+intUserMoney);
		
		// preparing the deck
		int intDeck[][];
		intDeck = loadDeck();
		
		// ask for bet
		int intUserBet = 0;
		con.println("What is your bet?");
		intUserBet = con.readInt();
		
		System.out.println("TEST BET VALUE: "+intUserBet);
		
		// TEMPORARY RETURN
		con.sleep(5000);
		MainScreen(con);
	}
	
	// used to initialize deck
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
		
		System.out.println(intDeck[51][2]);
		
		int intShuffledDeck[][];
		intShuffledDeck = sortDeck(intDeck);
		System.out.println(intShuffledDeck[51][2]);
		
		return intShuffledDeck;
	}

	// used to shuffle deck
	public static int[][] sortDeck(int intDeck [][]) {

        int intTemp[] = new int[3];
        int intCount;
        int intCount2;
        int intCount3;

        for (intCount2 = 0; intCount2 < 52-1; intCount2++) {
            for (intCount = 0; intCount < 52-1; intCount++) {
                if (intDeck [intCount][2] > intDeck [intCount+1][2]) {
                    for (intCount3 = 0; intCount3 < 3; intCount3++) {
                        intTemp [intCount3] = intDeck [intCount][intCount3];
                        intDeck [intCount][intCount3] = intDeck [intCount+1][intCount3];
                        intDeck [intCount+1][intCount3] = intTemp [intCount3];
                    }
                }
            }
        }
        return intDeck;
    }
}
