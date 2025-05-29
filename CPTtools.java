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
			PlayScreen(con);
		}
	}
	
	// play screen
	public static void PlayScreen(Console con){
		// preparing the deck
		int intDeck[][];
		intDeck = loadDeck();
		
		// ask for bet
		int intUserBet = 0;
		con.println("What is your bet?");
		intUserBet = con.readInt();
		
		System.out.println("TEST BET VALUE: "+intUserBet);
		
		// show hand
		
		int intHand[][];
		intHand = new int[5][2];
		
		InitialHand(con, intHand, intDeck);
		
		// ask to swap
		String strSwap;
		con.println("Which cards do you want to swap? (Ex. 124) (n for no)");
		strSwap = con.readLine();
		if(!strSwap.equalsIgnoreCase("n")){
			SwappedHand(con, intHand, intDeck, strSwap);
		}
		
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

	// used to load your hand
	public static void InitialHand(Console con, int intHand[][], int intDeck[][]){
		int intCountHand;
		
		for(intCountHand = 0; intCountHand < 5; intCountHand++){
			intHand[intCountHand][0] = intDeck[intCountHand][0];
			intHand[intCountHand][1] = intDeck[intCountHand][1];
		}
			intHand = sortHand(intHand);
		for(intCountHand = 0; intCountHand < 5; intCountHand++){
			// for showing card value
			con.print(intCountHand+1+" - ");
			if(intHand[intCountHand][0] == 1){
				con.print("A");
			}else if(intHand[intCountHand][0] == 11){
				con.print("J");
			}else if(intHand[intCountHand][0] == 12){
				con.print("Q");
			}else if(intHand[intCountHand][0] == 13){
				con.print("K");
			}else{
				con.print(intHand[intCountHand][0]);
			}
			
			con.print(" of ");
			
			// for showing card suit
			if(intHand[intCountHand][1] == 1){
				con.print("diamonds");
			}else if(intHand[intCountHand][1] == 2){
				con.print("clubs");
			}else if(intHand[intCountHand][1] == 3){
				con.print("hearts");
			}else if(intHand[intCountHand][1] == 4){
				con.print("spades");
			}
			
			con.println("");
		}
	}

	// used to sort hand
	public static int[][] sortHand(int intHand[][]) {
		int intTemp[] = new int[2];
		int intCount;
		int intCount2;
		int intCount3;

		for (intCount2 = 0; intCount2 < 5 - 1; intCount2++) {
			for (intCount = 0; intCount < 5 - 1; intCount++) {
				if (intHand[intCount][0] > intHand[intCount + 1][0]) {
					for (intCount3 = 0; intCount3 < 2; intCount3++) {
						intTemp[intCount3] = intHand[intCount][intCount3];
						intHand[intCount][intCount3] = intHand[intCount + 1][intCount3];
						intHand[intCount + 1][intCount3] = intTemp[intCount3];
					}
				}
			}
		}
		return intHand;
	}

	// used to swap hand
	public static void SwappedHand(Console con, int intHand[][], int intDeck[][], String strSwap){
		int intLength = strSwap.length();
		String strSwapCardNumber;
		int intSwapCardNumber[];
		intSwapCardNumber = new int[intLength];

		// used to get individual digits of the string
		int intCount1;
		for(intCount1 = 0; intCount1 < intLength; intCount1++){
			strSwapCardNumber = strSwap.substring(intCount1, intCount1+1);
			intSwapCardNumber[intCount1] = Integer.parseInt(strSwapCardNumber) - 1;
		}

		// used to swap selected cards
		for(intCount1 = 0; intCount1 < intLength; intCount1++){
			intHand[intSwapCardNumber[intCount1]][0] = intDeck[5 + intCount1][0];
			intHand[intSwapCardNumber[intCount1]][1] = intDeck[5 + intCount1][1];
		}
		intHand = sortHand(intHand);
		// print entire updated hand
		int intCountHand;
		for(intCountHand = 0; intCountHand < 5; intCountHand++){
			con.print(intCountHand+1+" - ");
			
			// show card value
			if(intHand[intCountHand][0] == 1){
				con.print("A");
			}else if(intHand[intCountHand][0] == 11){
				con.print("J");
			}else if(intHand[intCountHand][0] == 12){
				con.print("Q");
			}else if(intHand[intCountHand][0] == 13){
				con.print("K");
			}else{
				con.print(intHand[intCountHand][0]);
			}

			con.print(" of ");

			// show card suit
			if(intHand[intCountHand][1] == 1){
				con.print("diamonds");
			}else if(intHand[intCountHand][1] == 2){
				con.print("clubs");
			}else if(intHand[intCountHand][1] == 3){
				con.print("hearts");
			}else if(intHand[intCountHand][1] == 4){
				con.print("spades");
			}
			
			con.println("");
		}
	}
}
