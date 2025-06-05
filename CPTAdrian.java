import arc.*;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Font;

public class CPTAdrian{
	// actual game
	public static void main(String[] args){
		Console con = new Console("Video Poker", 1280, 720);
		// test: con.println(intDeck[50][0]);
		// test: con.println(intDeck[50][1]);
		MainScreen(con);
	}

	// main screen
	public static void MainScreen(Console con){
		// drawing main screen	
		con.repaint();	
		BufferedImage imgMain = con.loadImage("Cover.png");
		con.drawImage(imgMain, 0, 0);
		con.repaint();
				
		// con.println("Video Poker");
		// con.println("Play (P)");
		// con.println("View Leaderboard (L)");
		// con.println("Help (H)");
		// con.println("Quit (Q)");
		char chrInputMain = con.getChar();
		
		// reacting to different keys being clicked
		while(chrInputMain != 'q' || chrInputMain != 'Q' || chrInputMain != 'p' || chrInputMain != 'P' ||chrInputMain != 's' || chrInputMain != 'S' ||chrInputMain != 'l' || chrInputMain != 'L' ||chrInputMain != 'h' || chrInputMain != 'H'){
			if(chrInputMain == 'q' || chrInputMain == 'Q'){
				BufferedImage imgQuit = con.loadImage("Quit.png");
				con.clear();
				con.drawImage(imgQuit, 0, 0);
				con.repaint();
				
				char chrInputQuit = con.getChar();
				while(chrInputQuit != '\n' || chrInputQuit != 'Y' || chrInputQuit != 27 || chrInputQuit != 'N'){
					if(chrInputQuit == '\n' || chrInputQuit == 'Y'){
						con.closeConsole();
					}else if(chrInputQuit == 27 || chrInputQuit == 'N'){
						con.drawImage(imgMain, 0, 0);
						con.repaint();
						MainScreen(con);
					}else{
						chrInputQuit = con.getChar();
					}
				}
			}else if(chrInputMain == 's' || chrInputMain == 'S'){
				con.clear();
				BufferedImage imgJoke = con.loadImage("Joke.png");
				con.drawImage(imgJoke, 0, 0);
				con.repaint();
				char chrInputSecret = con.getChar();
				while(chrInputSecret != '\n' || chrInputSecret != 'C'){
					if(chrInputSecret == '\n' || chrInputSecret == 'C'){
						con.drawImage(imgMain, 0, 0);
						con.repaint();
						MainScreen(con);
					}else{
						chrInputSecret = con.getChar();
					}
				}

			}else if(chrInputMain == 'p' || chrInputMain == 'P'){
				con.clear();
				BufferedImage imgName = con.loadImage("Name.png");
				BufferedImage imgDark = con.loadImage("Dark.png");
				con.drawImage(imgDark, 0, 0);
				con.drawImage(imgName, 0, 0);
				con.repaint();
				
				
				// ask for username
				String strUserName;
				char chrTyped;
				strUserName = "";
				
				// loop that adds character typed to a string
				Font fntFont = con.loadFont("FuturaLTProHeavy.otf", 26);
				con.setDrawFont(fntFont);
				con.setDrawColor(Color.WHITE);
				
				while(true){ // infinite loop
					chrTyped = con.getChar();
					if(chrTyped == '\n'){ // checking for enter
						if(!strUserName.equals("")){
							break;
						}else{
							con.drawImage(imgName, 0, 0);
							con.setDrawColor(Color.RED);
							con.drawString("Invalid Name!", 534, 340);
							con.repaint();
							con.setDrawColor(Color.WHITE);
							con.sleep(1200); 
							con.drawImage(imgName, 0, 0);
							con.repaint();
							strUserName = ""; 
							continue;
						}
					}
					if(chrTyped == 8 && strUserName.length() > 0){ // checking for backspace, >0 to prevent errors in the substring
						strUserName = strUserName.substring(0, strUserName.length()-1);
					}
					if(chrTyped == 27){
						con.drawImage(imgMain, 0, 0);
						con.repaint();
						MainScreen(con);
					}else if(chrTyped < 32 || chrTyped > 126){
						strUserName = strUserName;
					}else if(chrTyped != 8){
						strUserName = strUserName + chrTyped;
					}
					con.drawImage(imgMain, 0, 0);
					con.drawImage(imgDark, 0, 0);
					con.drawImage(imgName, 0, 0);
					con.drawString(strUserName, 534, 340);
					con.repaint();
				}
					
				// giving out money to player
				int intUserMoney = 0;
				if(strUserName.equalsIgnoreCase("statitan")){
					intUserMoney = 100000;
				}else{
					intUserMoney = 1000;
				}
				PlayScreen(con, intUserMoney, strUserName);
			}else if(chrInputMain == 'l' || chrInputMain == 'L'){
				con.clear();
				BufferedImage imgLBoard = con.loadImage("Leaderboard.png");
				con.drawImage(imgLBoard, 0, 0);
				con.repaint();
				LeaderboardScreen(con);
			}else if(chrInputMain == 'h' || chrInputMain == 'H'){
				con.clear();
				BufferedImage imgHelp1 = con.loadImage("Help 1.png");
				BufferedImage imgHelp2 = con.loadImage("Help 2.png");
				con.drawImage(imgHelp1, 0, 0);
				con.repaint();
				char chrInputHelp = con.getChar();
				while(chrInputHelp != '\n' || chrInputHelp != 'N'){
					if(chrInputHelp == '\n' || chrInputHelp == 'N'){
						con.clear();
						con.drawImage(imgHelp2, 0, 0);
						con.repaint();
						chrInputHelp = con.getChar();
						while(chrInputHelp != '\n' || chrInputHelp != 'C'){
							if(chrInputHelp == '\n' || chrInputHelp == 'C'){
								con.drawImage(imgMain, 0, 0);
								con.repaint();
								MainScreen(con);
							}else{
								chrInputHelp = con.getChar();
							}
						}
					}else{
						chrInputHelp = con.getChar();
					}
				}
			}else{
				chrInputMain = con.getChar();
			}
		}
	}
	
	// leaderboard screen
	public static void LeaderboardScreen(Console con){
		// adding input from the txt file
		TextInputFile LBoard = new TextInputFile("leaderboard.txt");
		String strLBoard[];
		int intLBoard[];
		String strName = "";
		String strScore = "";
		int intLBoardCount = 0;
		
		// checking the length of the file in order to determine the number of rows of the array
		while(LBoard.eof() == false){
			strName = LBoard.readLine();
			strScore = LBoard.readLine();
			intLBoardCount++;
			System.out.println(intLBoardCount);
		}
		
		LBoard.close();
		
		// setting up the array
		strLBoard = new String[intLBoardCount];
		intLBoard = new int[intLBoardCount];
		
		// reading actual data from input file
		TextInputFile LBoard2 = new TextInputFile("leaderboard.txt");
		int intCount;
		
		for(intCount = 0; intCount < intLBoardCount; intCount++){
			strLBoard[intCount] = LBoard2.readLine();
			intLBoard[intCount] = LBoard2.readInt();
			System.out.println(strLBoard[intCount]);
			System.out.println(intLBoard[intCount]);
		}
		
		// sorting data from highest score to lowest
		int intNext = 0;
		for (intCount = 0; intCount < intLBoardCount - 1; intCount++) {
			for (intNext = intCount + 1; intNext < intLBoardCount; intNext++) {
				if (intLBoard[intCount] < intLBoard[intNext]) {
					// swapping score (money)
					int intTempScore = intLBoard[intCount];
					intLBoard[intCount] = intLBoard[intNext];
					intLBoard[intNext] = intTempScore;

					// swapping names
					String strTempName = strLBoard[intCount];
					strLBoard[intCount] = strLBoard[intNext];
					strLBoard[intNext] = strTempName;
				}
			}
		}
		
		// printing the name and stuff to the leaderboard
		// for text printing
		Font fntFont = con.loadFont("FuturaLTProHeavy.otf", 26);
		con.setDrawFont(fntFont);
		con.setDrawColor(Color.WHITE);
		int intY = 115;
		con.drawString("", 0, 0);
		con.repaint();
		for(intCount = 0; intCount < intLBoardCount && intCount < 10; intCount++){
			con.drawString(Integer.toString(intCount+1), 448, intY);
			con.drawString(strLBoard[intCount], 506, intY);
			con.drawString("$"+Integer.toString(intLBoard[intCount]), 718, intY);
			intY = intY + 47;
			con.repaint();
		}
		
		// for closing screen
		char chrInputSecret = con.getChar();
		BufferedImage imgMain = con.loadImage("Cover.png");
		while(chrInputSecret != '\n' || chrInputSecret != 'R'){
			if(chrInputSecret == '\n' || chrInputSecret == 'R'){
				con.drawImage(imgMain, 0, 0);
				con.repaint();
				MainScreen(con);
			}else{
				chrInputSecret = con.getChar();
			}
		}
	}
	
	// play screen
	public static void PlayScreen(Console con, int intUserMoney, String strUserName){
		// printing image
		BufferedImage imgPlay = con.loadImage("Play.png");
		con.drawImage(imgPlay, 0, 0);
		
		// preparing the deck
		int intDeck[][];
		intDeck = loadDeck();
		
		// printing screen based on name input
		BufferedImage imgDark = con.loadImage("Dark.png");
		BufferedImage imgBet = con.loadImage("Bet.png");
		BufferedImage imgMoney = con.loadImage("Money.png");
		con.drawImage(imgPlay, 0, 0);
		con.drawImage(imgDark, 0, 0);
		con.drawImage(imgBet, 0, 0);
		con.drawImage(imgMoney, 0, 0);
		Font fntFont = con.loadFont("FuturaLTProHeavy.otf", 26);
		con.setDrawFont(fntFont);
		con.setDrawColor(Color.WHITE);
		con.drawString("You have: $"+intUserMoney, 50, 30);
		con.drawString("Your bet:", 960, 30);
		con.repaint();
	
		
		// load font
		fntFont = con.loadFont("FuturaLTProHeavy.otf", 26);
		con.setDrawFont(fntFont);
		con.setDrawColor(Color.WHITE);
		
		// asking for bet
		int intUserBet = 0;
		String strUserBet = "";
		char chrTyped;
		
		con.clear();
		while(true){ // infinite loop
			chrTyped = con.getChar();
			con.drawString("$"+strUserBet, 534, 340);
			if(chrTyped == '\n'){ // checking for enter
				// used to check if the entered bet is all numbers
				char chrDigit;
				int intCount;
				boolean blnNumberic = true;
				for(intCount = 0; intCount < strUserBet.length(); intCount++){
					chrDigit = strUserBet.charAt(intCount);
					if(chrDigit < 48 || chrDigit > 57){ // check if the char is beyond the 10 numbers
						blnNumberic = false;
					}
				}
				if(!strUserBet.equals("") && blnNumberic == true && Integer.parseInt(strUserBet) > 0 && Integer.parseInt(strUserBet) <= intUserMoney){
					break;
				}else{
					con.drawImage(imgBet, 0, 0);
					con.setDrawColor(Color.RED);
					con.drawString("Invalid Bet!", 534, 340);
					con.repaint();
					con.setDrawColor(Color.WHITE);
					con.sleep(1200); 
					con.drawImage(imgBet, 0, 0);
					con.repaint();
					strUserBet = ""; 
					continue;
				}
			}
			con.setDrawColor(Color.WHITE);
			if(chrTyped == 8 && strUserBet.length() > 0){ // checking for backspace, >0 to prevent errors in the substring
				strUserBet = strUserBet.substring(0, strUserBet.length()-1);
				con.clear();
				con.drawImage(imgBet, 0, 0);
				con.repaint();
				con.drawString("$"+strUserBet, 534, 340);
			}
			if(chrTyped != 8){
				strUserBet = strUserBet + chrTyped;
				con.clear();
				con.drawImage(imgBet, 0, 0);
				con.repaint();
				con.drawString("$"+strUserBet, 534, 340);
			}
		}
		intUserBet = Integer.parseInt(strUserBet);
		intUserMoney = intUserMoney - intUserBet;
		con.clear();
		con.drawImage(imgPlay, 0, 0);
		con.drawImage(imgMoney, 0, 0);
		con.drawString("You have: $"+intUserMoney, 50, 30);
		con.drawString("Your bet: $"+intUserBet, 960, 30);
		con.repaint();
		
		System.out.println("TEST BET VALUE: "+intUserBet);
		
		// show hand
		
		int intHand[][];
		intHand = new int[5][2];
		
		InitialHand(con, intHand, intDeck, intUserMoney, intUserBet);
		
		fntFont = con.loadFont("FuturaLTProHeavy.otf", 26);
		con.setDrawColor(Color.WHITE);
		con.setDrawFont(fntFont);		
		
		// ask to swap
		BufferedImage imgSwapping = con.loadImage("Swapping.png");
		BufferedImage imgBGUp = con.loadImage("BG Up.png");
		con.drawImage(imgBGUp, 0, 0);
		con.drawImage(imgMoney, 0, 0);
		con.drawString("You have: $"+intUserMoney, 50, 30);
		con.drawString("Your bet: $"+intUserBet, 960, 30);
		con.drawImage(imgSwapping, 0, 0);
		con.repaint();
		String strSwap = "";
		
		// printing swap scren
		con.clear();
		while(true){ // infinite loop
			chrTyped = con.getChar();
			con.drawString(strSwap, 534, 120);
			if(chrTyped != 'n'){
				if(chrTyped == '\n'){ // checking for enter
					// used to check if the entered bet is all numbers
					char chrDigit;
					int intCount;
					boolean blnNumberic = true;
					for(intCount = 0; intCount < strSwap.length(); intCount++){
						chrDigit = strSwap.charAt(intCount);
						if(chrDigit < 49 || chrDigit > 53){ // check if the char is beyond the 5 numbers
							blnNumberic = false;
						}
					}
					if(!strSwap.equals("") && blnNumberic == true){
						SwappedHand(con, intHand, intDeck, strSwap, intUserMoney, intUserBet);
						fntFont = con.loadFont("FuturaLTProHeavy.otf", 26);
						con.setDrawColor(Color.WHITE);
						con.setDrawFont(fntFont);	
						break;
					}else{
						con.clear();
						con.drawImage(imgBGUp, 0, 0);
						con.drawImage(imgMoney, 0, 0);
						con.drawString("You have: $"+intUserMoney, 50, 30);
						con.drawString("Your bet: $"+intUserBet, 960, 30);
						con.drawImage(imgSwapping, 0, 0);
						con.setDrawColor(Color.RED);
						con.drawString("Invalid Card(s)!", 534, 120);
						con.repaint();
						con.setDrawColor(Color.WHITE);
						con.sleep(1200); 
						con.clear();
						con.drawImage(imgBGUp, 0, 0);
						con.drawImage(imgMoney, 0, 0);
						con.drawString("You have: $"+intUserMoney, 50, 30);
						con.drawString("Your bet: $"+intUserBet, 960, 30);
						con.drawImage(imgSwapping, 0, 0);
						con.repaint();
						strSwap = ""; 
						continue;
					}
				}
				con.setDrawColor(Color.WHITE);
				if(chrTyped == 8 && strSwap.length() > 0){ // checking for backspace, >0 to prevent errors in the substring
					strSwap = strSwap.substring(0, strSwap.length()-1);
					con.clear();
					con.drawImage(imgBGUp, 0, 0);
					con.drawImage(imgMoney, 0, 0);
					con.drawString("You have: $"+intUserMoney, 50, 30);
					con.drawString("Your bet: $"+intUserBet, 960, 30);
					con.drawImage(imgSwapping, 0, 0);
					con.repaint();
					con.drawString(strSwap, 534, 120);
				}
				if(chrTyped != 8){
					con.clear();
					strSwap = strSwap + chrTyped;
					con.drawImage(imgBGUp, 0, 0);
					con.drawImage(imgMoney, 0, 0);
					con.drawString("You have: $"+intUserMoney, 50, 30);
					con.drawString("Your bet: $"+intUserBet, 960, 30);
					con.drawImage(imgSwapping, 0, 0);
					con.repaint();
					con.drawString(strSwap, 534, 120);
				}
				
				con.drawImage(imgSwapping, 0, 0);
				con.drawString(strSwap, 534, 120);
				con.repaint();
			}else{
				con.clear();
				con.drawImage(imgBGUp, 0, 0);
				con.drawImage(imgMoney, 0, 0);
				con.drawString("You have: $"+intUserMoney, 50, 30);
				con.drawString("Your bet: $"+intUserBet, 960, 30);
				con.repaint();
				break;
			}
		}
		
		// used to calculate results from hand
		int intUserMoneyResult;
		intUserMoneyResult = Result(con, intUserBet, intHand, intUserMoney);
		System.out.println(intUserMoneyResult);
		
		intUserMoney = intUserMoney + intUserMoneyResult;
		BufferedImage imgResult2 = con.loadImage("Result2.png");
		con.clear();
		if(intUserMoney != 0){
			con.drawImage(imgResult2, 0, 0);
		}
		con.drawImage(imgMoney, 0, 0);
		con.drawString("You have: $"+intUserMoney, 50, 30);
		con.drawString("Your bet: $"+intUserBet, 960, 30);
		con.repaint();
		
		// ask if continue
		fntFont = con.loadFont("FuturaLTProHeavy.otf", 29);
		con.setDrawColor(Color.WHITE);
		con.setDrawFont(fntFont);
		TextOutputFile LBoard = new TextOutputFile("leaderboard.txt", true);
		BufferedImage imgResult = con.loadImage("Result.png");
		if(intUserMoney != 0){
			char chrInputMain = con.getChar();
			while(chrInputMain != '\n' || chrInputMain != 27){
				if(chrInputMain == '\n'){
					con.clear();
					con.repaint();
					PlayScreen(con, intUserMoney, strUserName);
				}else if(chrInputMain == 27){
					LBoard.println(strUserName);
					LBoard.println(intUserMoney);
					con.clear();
					MainScreen(con);
				}else{
					chrInputMain = con.getChar();
				}
			}			
			LBoard.close();
		}else{
			con.sleep(2000);
			con.clear();
			con.drawImage(imgResult, 0, 0);
			con.drawString("Game Over...", 556, 260);
			con.drawString("Returning to Main Screen...", 470, 429);
			LBoard.println(strUserName);
			LBoard.println(intUserMoney);
			con.repaint();
			con.sleep(2000);
			MainScreen(con);
		}
	}
	
	// used to initialize deck
	public static int[][] loadDeck(){
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
	public static void InitialHand(Console con, int intHand[][], int intDeck[][], int intUserMoney, int intUserBet){
		// load font
		Font fntFont = con.loadFont("FuturaLTProHeavy.otf", 26);
		con.setDrawFont(fntFont);
		con.setDrawColor(Color.WHITE);
		
		// load images
		BufferedImage imgDark = con.loadImage("Dark.png");
		BufferedImage imgPlay = con.loadImage("Play.png");
		BufferedImage imgMoney = con.loadImage("Money.png");
		
		// back of cards 
		BufferedImage imgCardBack = con.loadImage("CardBack.png");
		int intY = 720;
		
		// animating cards
		while(intY != 320){
			con.clear();
			con.drawImage(imgPlay, 0, 0);
			con.drawImage(imgMoney, 0, 0);
			con.drawString("You have: $"+intUserMoney, 50, 30);
			con.drawString("Your bet: $"+intUserBet, 960, 30);
			
			con.drawImage(imgCardBack, 45, intY);
			con.drawImage(imgCardBack, 291, intY);
			con.drawImage(imgCardBack, 537, intY);
			con.drawImage(imgCardBack, 783, intY);
			con.drawImage(imgCardBack, 1029, intY);
			con.repaint();
			
			intY = intY - 10;
			
			con.sleep(18);
		}
		
		con.sleep(500);
		
		int intCountHand;
		
		// for grabbing cards from deck and load it to your hand
		for(intCountHand = 0; intCountHand < 5; intCountHand++){
			intHand[intCountHand][0] = intDeck[intCountHand][0];
			intHand[intCountHand][1] = intDeck[intCountHand][1];
		}
		
		// sorting hand
		intHand = sortHand(intHand);
			
		// printing images
		BufferedImage imgDiamonds = con.loadImage("Diamonds.png");
		BufferedImage imgClubs = con.loadImage("Clubs.png");
		BufferedImage imgHearts = con.loadImage("Hearts.png");
		BufferedImage imgSpades = con.loadImage("Spades.png");
		
		con.clear();
		con.drawImage(imgPlay, 0, 0);
		con.drawImage(imgMoney, 0, 0);
		fntFont = con.loadFont("FuturaLTProHeavy.otf", 26);
		con.drawString("You have: $"+intUserMoney, 50, 30);
		con.drawString("Your bet: $"+intUserBet, 960, 30);
		con.repaint();
		int intX = 45;
		
		for(intCountHand = 0; intCountHand < 5; intCountHand++){
			con.setDrawColor(Color.WHITE);
			fntFont = con.loadFont("FuturaLTProHeavy.otf", 40);
			con.setDrawFont(fntFont);
			con.fillRoundRect(intX, 330, 206, 290, 11, 11);
			
			// used to show card suit
			if(intHand[intCountHand][1] == 1){
				// con.print("diamonds");
				con.setDrawColor(Color.RED);
				con.drawImage(imgDiamonds, intX+61, 430);
			}else if(intHand[intCountHand][1] == 2){
				// con.print("clubs");
				con.setDrawColor(Color.BLACK);
				con.drawImage(imgClubs, intX+61, 430);
			}else if(intHand[intCountHand][1] == 3){
				// con.print("hearts");
				con.setDrawColor(Color.RED);
				con.drawImage(imgHearts, intX+61, 430);
			}else if(intHand[intCountHand][1] == 4){
				// con.print("spades");
				con.setDrawColor(Color.BLACK);
				con.drawImage(imgSpades, intX+61, 430);
			}
			
			// used to show card value
			// con.print(intCountHand+1+" - ");
			if(intHand[intCountHand][0] == 1){
				// con.print("A");
				con.drawString("A", intX+10, 338);
				con.drawString("A", intX+10+150, 548);
			}else if(intHand[intCountHand][0] == 11){
				// con.print("J");
				con.drawString("J", intX+10, 338);
				con.drawString("J", intX+10+150, 548);
			}else if(intHand[intCountHand][0] == 12){
				// con.print("Q");
				con.drawString("Q", intX+10, 338);
				con.drawString("Q", intX+10+150, 548);
			}else if(intHand[intCountHand][0] == 13){
				// con.print("K");
				con.drawString("K", intX+10, 338);
				con.drawString("K", intX+10+150, 548);
			}else{
				// con.print(intHand[intCountHand][0]);
				con.drawString(Integer.toString(intHand[intCountHand][0]), intX+10, 338);
				con.drawString(Integer.toString(intHand[intCountHand][0]), intX+10+150, 548);
			}
			
			// con.print(" of ");
			
			intX = intX + 246;
			con.repaint();
			// con.println("");
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
	public static void SwappedHand(Console con, int intHand[][], int intDeck[][], String strSwap, int intUserMoney, int intUserBet){
		// load font
		Font fntFont = con.loadFont("FuturaLTProHeavy.otf", 26);
		con.setDrawFont(fntFont);
		con.setDrawColor(Color.WHITE);
		
		// load images
		con.clear();
		BufferedImage imgDark = con.loadImage("Dark.png");
		BufferedImage imgPlay = con.loadImage("Play.png");
		BufferedImage imgBGUp = con.loadImage("BG Up.png");
		BufferedImage imgMoney = con.loadImage("Money.png");
		BufferedImage imgCardBack = con.loadImage("CardBack.png");
		con.drawImage(imgBGUp, 0, 0);
		con.drawImage(imgMoney, 0, 0);
		con.drawString("You have: $"+intUserMoney, 50, 30);
		con.drawString("Your bet: $"+intUserBet, 960, 30);
		con.drawImage(imgCardBack, 45, 330);
		con.drawImage(imgCardBack, 291, 330);
		con.drawImage(imgCardBack, 537, 330);
		con.drawImage(imgCardBack, 783, 330);
		con.drawImage(imgCardBack, 1029, 330);
		con.repaint();
		
		con.sleep(500); // wait before showing the animation
		
		int intY = 330;
		
		// animating cards
		while(intY != 750){
			con.clear();
			con.drawImage(imgPlay, 0, 0);
			con.drawImage(imgMoney, 0, 0);
			con.drawString("You have: $"+intUserMoney, 50, 30);
			con.drawString("Your bet: $"+intUserBet, 960, 30);
			
			con.drawImage(imgCardBack, 45, intY);
			con.drawImage(imgCardBack, 291, intY);
			con.drawImage(imgCardBack, 537, intY);
			con.drawImage(imgCardBack, 783, intY);
			con.drawImage(imgCardBack, 1029, intY);
			con.repaint();
			
			intY = intY + 10;
			
			con.sleep(18);
		}
		
		con.sleep(500);
		
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
		
		// moving cards back up
		intY = 720;
		
		// animating cards
		while(intY != 320){
			con.clear();
			con.drawImage(imgPlay, 0, 0);
			con.drawImage(imgMoney, 0, 0);
			con.drawString("You have: $"+intUserMoney, 50, 30);
			con.drawString("Your bet: $"+intUserBet, 960, 30);
			
			con.drawImage(imgCardBack, 45, intY);
			con.drawImage(imgCardBack, 291, intY);
			con.drawImage(imgCardBack, 537, intY);
			con.drawImage(imgCardBack, 783, intY);
			con.drawImage(imgCardBack, 1029, intY);
			con.repaint();
			
			intY = intY - 10;
			
			con.sleep(18);
		}
		
		con.sleep(500);
		
		// printing updated images
		BufferedImage imgDiamonds = con.loadImage("Diamonds.png");
		BufferedImage imgClubs = con.loadImage("Clubs.png");
		BufferedImage imgHearts = con.loadImage("Hearts.png");
		BufferedImage imgSpades = con.loadImage("Spades.png");
		
		con.clear();
		con.drawImage(imgPlay, 0, 0);
		con.drawImage(imgMoney, 0, 0);
		fntFont = con.loadFont("FuturaLTProHeavy.otf", 26);
		con.drawString("You have: $"+intUserMoney, 50, 30);
		con.drawString("Your bet: $"+intUserBet, 960, 30);
		con.repaint();
		int intX = 45;
		int intCountHand;
		
		for(intCountHand = 0; intCountHand < 5; intCountHand++){
			con.setDrawColor(Color.WHITE);
			fntFont = con.loadFont("FuturaLTProHeavy.otf", 40);
			con.setDrawFont(fntFont);
			con.fillRoundRect(intX, 330, 206, 290, 11, 11);
			
			// used to show card suit
			if(intHand[intCountHand][1] == 1){
				// con.print("diamonds");
				con.setDrawColor(Color.RED);
				con.drawImage(imgDiamonds, intX+61, 430);
			}else if(intHand[intCountHand][1] == 2){
				// con.print("clubs");
				con.setDrawColor(Color.BLACK);
				con.drawImage(imgClubs, intX+61, 430);
			}else if(intHand[intCountHand][1] == 3){
				// con.print("hearts");
				con.setDrawColor(Color.RED);
				con.drawImage(imgHearts, intX+61, 430);
			}else if(intHand[intCountHand][1] == 4){
				// con.print("spades");
				con.setDrawColor(Color.BLACK);
				con.drawImage(imgSpades, intX+61, 430);
			}
			
			// used to show card value
			// con.print(intCountHand+1+" - ");
			if(intHand[intCountHand][0] == 1){
				// con.print("A");
				con.drawString("A", intX+10, 338);
				con.drawString("A", intX+10+150, 548);
			}else if(intHand[intCountHand][0] == 11){
				// con.print("J");
				con.drawString("J", intX+10, 338);
				con.drawString("J", intX+10+150, 548);
			}else if(intHand[intCountHand][0] == 12){
				// con.print("Q");
				con.drawString("Q", intX+10, 338);
				con.drawString("Q", intX+10+150, 548);
			}else if(intHand[intCountHand][0] == 13){
				// con.print("K");
				con.drawString("K", intX+10, 338);
				con.drawString("K", intX+10+150, 548);
			}else{
				// con.print(intHand[intCountHand][0]);
				con.drawString(Integer.toString(intHand[intCountHand][0]), intX+10, 338);
				con.drawString(Integer.toString(intHand[intCountHand][0]), intX+10+150, 548);
			}
			
			// con.print(" of ");
			
			intX = intX + 246;
			con.repaint();
			// con.println("");
		}
	}

	// used to calculate result
	public static int Result(Console con, int intUserBet, int intHand[][], int intUserMoney){
		int intUserMoneyResult = 0;
		
		// used to extract values and suits from your hand
		int intCardValue[];
		intCardValue = new int[5];
		int intSuitValue[];
		intSuitValue = new int[5];
		int intCount;
		
		for(intCount = 0; intCount < 5; intCount++){
			intCardValue[intCount] = intHand[intCount][0]; // extract value from hand
			intSuitValue[intCount] = intHand[intCount][1]; // extract suit from hand
		}
		
		// used to count the frequencies of card value
		int intValueCount[];
		intValueCount = new int[13]; // for the 13 card values
		
		for(intCount = 0; intCount < 5; intCount++){
			intValueCount[intCardValue[intCount]-1]++; // adds one to the card value each time it appeared
		}
		
		// used to check for pairs, three of a kind, and four of a kind
		int intPairCount = 0;
		boolean blnThreeCount = false;
		boolean blnFourCount = false;
		
		
		for(intCount = 0; intCount < 13; intCount++){
			if(intValueCount[intCount] == 2){ // add one pair if a value appeared twice
				intPairCount++; // pair count cannot be boolean cuz there could be 2
			}else if(intValueCount[intCount] == 3){ // add one pair if a value appeared three times
				blnThreeCount = true;
			}else if(intValueCount[intCount] == 4){ // add one pair if a value appeared four times
				blnFourCount = true;
			}
		}
		
		// used to check if its jacks or better
		boolean blnJacks = false;
		
		if(intValueCount[0] == 2||intValueCount[10] == 2||intValueCount[11] == 2||intValueCount[12] == 2){ // jacks if A or J or Q or K appeared as a pair
			blnJacks = true;
		}
		
		System.out.println(intPairCount+""+blnThreeCount+""+blnFourCount);
		
		// used to sort card value for checking if its a straight or not
		int intTemp;
		int intCount01;
		int intCount02;

		for (intCount01 = 0; intCount01 < 5 - 1; intCount01++) {
			for (intCount02 = 0; intCount02 < 5 - 1; intCount02++) {
				if (intCardValue[intCount02] > intCardValue[intCount02 + 1]) {
					intTemp = intCardValue[intCount02];
					intCardValue[intCount02] = intCardValue[intCount02 + 1];
					intCardValue[intCount02 + 1] = intTemp;
				}
			}
		}
		
		// used to check if its a straight or not
		int intCount03;
		int intIsStraight = 0;
		boolean blnStraight = false;
		boolean blnRoyalStraight = false;
		
		for (intCount03 = 0; intCount03 < 4; intCount03++) {
			if (intCardValue[intCount03] + 1 == intCardValue[intCount03 + 1]) { // if previous sorted value + 1 is equal to the next one, then add 1 to the counter
				intIsStraight++;
				System.out.println(intIsStraight);
			}
		}
		
		if(intIsStraight == 4){ // if the counter maxs out, then its a straight
			blnStraight = true;
			System.out.println("STRAIGHT");
		}
		
		// used to check if its royal flush
		
		if (intCardValue[0] == 1 && intCardValue[1] == 10 && intCardValue[2] == 11 && intCardValue[3] == 12 && intCardValue[4] == 13) { 
			blnRoyalStraight = true;
			System.out.println("ROYAL STRAIGHT");
		}
	
		
		// used to check for flush
		int intFlushCount = 0;
		int intIsFlush = 0;
		boolean blnFlush = false;
		
		
		for (intFlushCount = 0; intFlushCount < 4; intFlushCount++) {
			if (intSuitValue[intFlushCount] == intSuitValue[intFlushCount + 1]) { // if previous sorted suit is equal to the next one, then add 1 to the counter
				intIsFlush++;
				System.out.println(intIsFlush);
			}
		}
		
		if(intIsFlush == 4){ // if the counter maxs out, then its a straight
			blnFlush = true;
			System.out.println("FLUSH");
		}
		
		// preparing images and stuff
		BufferedImage imgDark = con.loadImage("Dark.png");
		BufferedImage imgBet = con.loadImage("Bet.png");
		BufferedImage imgMoney = con.loadImage("Money.png");
		BufferedImage imgBGUp = con.loadImage("BG Up.png");
		BufferedImage imgResult = con.loadImage("Result.png");
		Font fntFont = con.loadFont("FuturaLTProHeavy.otf", 26);
		con.setDrawColor(Color.WHITE);
		con.setDrawFont(fntFont);	
		con.drawImage(imgBGUp, 0, 0);
		con.drawImage(imgMoney, 0, 0);
		fntFont = con.loadFont("FuturaLTProHeavy.otf", 26);
		con.setDrawFont(fntFont);	
		con.drawString("You have: $"+intUserMoney, 50, 30);
		con.drawString("Your bet: $"+intUserBet, 960, 30);	
		fntFont = con.loadFont("FuturaLTProHeavy.otf", 29);
		con.setDrawColor(Color.WHITE);
		con.setDrawFont(fntFont);
		con.sleep(1000);
		
		// used for payout 
		if(blnFlush == true && blnRoyalStraight == true){ // royal flush
			intUserMoneyResult = 800 * intUserBet;
			con.drawImage(imgBGUp, 0, 0);
			con.drawImage(imgDark, 0, 0);
			con.drawImage(imgResult, 0, 0);
			con.drawString("YOU WIN!", 574, 243);
			con.drawString("ROYAL FLUSH!", 550, 280);
			fntFont = con.loadFont("FuturaLTProHeavy.otf", 26);
			con.setDrawFont(fntFont);	
			con.drawString("Payout: $"+intUserMoneyResult, 534, 120);
			// con.print("ROYAL FLUSH! ");
		}else if(blnFlush == true && blnStraight == true){ // straight flush
			intUserMoneyResult = 50 * intUserBet;
			con.drawImage(imgBGUp, 0, 0);
			con.drawImage(imgDark, 0, 0);
			con.drawImage(imgResult, 0, 0);
			con.drawString("YOU WIN!", 574, 243);
			con.drawString("STRAIGHT FLUSH!", 529, 280);
			fntFont = con.loadFont("FuturaLTProHeavy.otf", 26);
			con.setDrawFont(fntFont);	
			con.drawString("Payout: $"+intUserMoneyResult, 534, 340);
		}else if(blnFourCount == true){ // four of a kind
			intUserMoneyResult = 25 * intUserBet;
			con.drawImage(imgBGUp, 0, 0);
			con.drawImage(imgDark, 0, 0);
			con.drawImage(imgResult, 0, 0);
			con.drawString("YOU WIN!", 574, 243);
			con.drawString("FOUR OF A KIND!", 526, 280);
			fntFont = con.loadFont("FuturaLTProHeavy.otf", 26);
			con.setDrawFont(fntFont);	
			con.drawString("Payout: $"+intUserMoneyResult, 534, 340);
		}else if(blnThreeCount == true && intPairCount == 1){ // full house
			intUserMoneyResult = 9 * intUserBet;
			con.drawImage(imgBGUp, 0, 0);
			con.drawImage(imgDark, 0, 0);
			con.drawImage(imgResult, 0, 0);
			con.drawString("YOU WIN!", 574, 243);
			con.drawString("FULL HOUSE!", 558, 280);
			fntFont = con.loadFont("FuturaLTProHeavy.otf", 26);
			con.setDrawFont(fntFont);	
			con.drawString("Payout: $"+intUserMoneyResult, 534, 340);
		}else if(blnFlush == true){ // flush
			intUserMoneyResult = 6 * intUserBet;
			con.drawImage(imgBGUp, 0, 0);
			con.drawImage(imgDark, 0, 0);
			con.drawImage(imgResult, 0, 0);
			con.drawString("YOU WIN!", 574, 243);
			con.drawString("FLUSH!", 596, 280);
			fntFont = con.loadFont("FuturaLTProHeavy.otf", 26);
			con.setDrawFont(fntFont);	
			con.drawString("Payout: $"+intUserMoneyResult, 534, 340);
		}else if(blnStraight == true || blnRoyalStraight == true){ // straight
			intUserMoneyResult = 4 * intUserBet;
			con.drawImage(imgBGUp, 0, 0);
			con.drawImage(imgDark, 0, 0);
			con.drawImage(imgResult, 0, 0);
			con.drawString("YOU WIN!", 574, 243);
			con.drawString("STRAIGHT!", 573, 280);
			fntFont = con.loadFont("FuturaLTProHeavy.otf", 26);
			con.setDrawFont(fntFont);	
			con.drawString("Payout: $"+intUserMoneyResult, 534, 340);
		}else if(blnThreeCount == true){ // three of a kind
			intUserMoneyResult = 3 * intUserBet;
			con.drawImage(imgBGUp, 0, 0);
			con.drawImage(imgDark, 0, 0);
			con.drawImage(imgResult, 0, 0);
			con.drawString("YOU WIN!", 574, 243);
			con.drawString("THREE OF A KIND!", 524, 280);
			fntFont = con.loadFont("FuturaLTProHeavy.otf", 26);
			con.setDrawFont(fntFont);	
			con.drawString("Payout: $"+intUserMoneyResult, 534, 340);
		}else if(intPairCount == 2){ // two pairs
			intUserMoneyResult = 2 * intUserBet;
			con.drawImage(imgBGUp, 0, 0);
			con.drawImage(imgDark, 0, 0);
			con.drawImage(imgResult, 0, 0);
			con.drawString("YOU WIN!", 574, 243);
			con.drawString("TWO PAIRS!", 564, 280);
			fntFont = con.loadFont("FuturaLTProHeavy.otf", 26);
			con.setDrawFont(fntFont);	
			con.drawString("Payout: $"+intUserMoneyResult, 534, 340);
		}else if(blnJacks  == true){ // jacks or better
			intUserMoneyResult = 1 * intUserBet;
			con.drawImage(imgBGUp, 0, 0);
			con.drawImage(imgDark, 0, 0);
			con.drawImage(imgResult, 0, 0);
			con.drawString("YOU WIN!", 574, 243);
			con.drawString("JACKS OR BETTER!", 529, 280);
			fntFont = con.loadFont("FuturaLTProHeavy.otf", 26);
			con.setDrawFont(fntFont);	
			con.drawString("Payout: $"+intUserMoneyResult, 534, 340);
		}else{
			intUserMoneyResult = 0 * intUserBet;
			con.drawImage(imgBGUp, 0, 0);
			con.drawImage(imgDark, 0, 0);
			con.drawImage(imgResult, 0, 0);
			con.drawString("You lose...", 579, 243);
			con.drawString("You got nothing...", 529, 280);
			fntFont = con.loadFont("FuturaLTProHeavy.otf", 26);
			con.setDrawFont(fntFont);	
			con.drawString("Payout: $"+intUserMoneyResult, 534, 340);
		}
		fntFont = con.loadFont("FuturaLTProHeavy.otf", 26);
		con.setDrawColor(Color.WHITE);
		con.setDrawFont(fntFont);
		con.repaint();
		
		return intUserMoneyResult;
	}
}
 
