import arc.*;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Font;

public class CPTtools{
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
		while(chrInputMain != 'q' || chrInputMain != 'Q' || chrInputMain != 'p' || chrInputMain != 'P' ||chrInputMain != 's' || chrInputMain != 'S' ||chrInputMain != 'l' || chrInputMain != 'L' ||chrInputMain != 'h' || chrInputMain != 'H' ){
			if(chrInputMain == 'q' || chrInputMain == 'Q'){
				BufferedImage imgQuit = con.loadImage("Quit.png");
				con.clear();
				con.drawImage(imgQuit, 0, 0);
				con.repaint();
				
				char chrInputQuit = con.getChar();
				while(chrInputQuit != 'y' || chrInputQuit != 'Y' || chrInputQuit != 'n' || chrInputQuit != 'N'){
					if(chrInputQuit == 'y' || chrInputQuit == 'Y'){
						con.closeConsole();
					}else if(chrInputQuit == 'n' || chrInputQuit == 'N'){
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
				while(chrInputSecret != 'c' || chrInputSecret != 'C'){
					if(chrInputSecret == 'c' || chrInputSecret == 'C'){
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
						break;
					}
					if(chrTyped == 8 && strUserName.length() > 0){ // checking for backspace, >0 to prevent errors in the substring
						strUserName = strUserName.substring(0, strUserName.length()-1);
					}else if(chrTyped != 8){
						strUserName = strUserName + chrTyped;
					}
					con.clear();
					con.drawImage(imgName, 0, 0);
					con.drawString(strUserName, 534, 317);
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
				while(chrInputHelp != 'n' || chrInputHelp != 'N'){
					if(chrInputHelp == 'n' || chrInputHelp == 'N'){
						con.clear();
						con.drawImage(imgHelp2, 0, 0);
						con.repaint();
						chrInputHelp = con.getChar();
						while(chrInputHelp != 'c' || chrInputHelp != 'C'){
							if(chrInputHelp == 'c' || chrInputHelp == 'C'){
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
		while(chrInputSecret != 'r' || chrInputSecret != 'R'){
			if(chrInputSecret == 'r' || chrInputSecret == 'R'){
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
		// load font
		Font fntFont = con.loadFont("FuturaLTProHeavy.otf", 26);
		con.setDrawFont(fntFont);
		con.setDrawColor(Color.WHITE);
		
		// printing image
		con.clear();
		BufferedImage imgPlay = con.loadImage("Play.png");
		con.drawImage(imgPlay, 0, 0);
		con.repaint();
		
		// printing amount of money
		con.println("You have: $"+intUserMoney);
		
		// preparing the deck
		int intDeck[][];
		intDeck = loadDeck();
		
		// ask for bet
		int intUserBet = 0;
		con.println("What is your bet?");
		intUserBet = con.readInt();
		
		// bet cannot exceed users money
		if(intUserBet > intUserMoney){
			intUserBet = intUserMoney;
		}
		
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
		
		// used to calculate results from hand
		int intUserMoneyResult;
		intUserMoneyResult = Result(con, intUserBet, intHand);
		System.out.println(intUserMoneyResult);
		
		intUserMoney = intUserMoney + intUserMoneyResult;
		
		// show result either won or lost
		if(intUserMoneyResult < 0){
			con.println("You lose! You currently have $"+intUserMoney);
		}else{
			con.println("You win! You currently have $"+intUserMoney);
		}
		
		// ask if continue
		TextOutputFile LBoard = new TextOutputFile("leaderboard.txt", true);
		if(intUserMoney == 0){
			con.println("Game Over!");
			LBoard.println(strUserName);
			LBoard.println(intUserMoney);
			con.sleep(5000);
			MainScreen(con);
		}else{
			con.println("Continue? (Y or N)");
			char chrContinue = con.getChar();
			if(chrContinue == 'y' || chrContinue == 'Y'){
				PlayScreen(con, intUserMoney, strUserName);
			}else{
				LBoard.println(strUserName);
				LBoard.println(intUserMoney);
				con.clear();
				MainScreen(con);
			}
		}
		
		LBoard.close();
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
	public static void InitialHand(Console con, int intHand[][], int intDeck[][]){
		int intCountHand;
		
		for(intCountHand = 0; intCountHand < 5; intCountHand++){
			intHand[intCountHand][0] = intDeck[intCountHand][0];
			intHand[intCountHand][1] = intDeck[intCountHand][1];
		}
			intHand = sortHand(intHand);
		for(intCountHand = 0; intCountHand < 5; intCountHand++){
			// used to show card value
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
			
			// used to show card suit
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

	// used to calculate result
	public static int Result(Console con, int intUserBet, int intHand[][]){
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
		
		if (intCardValue[0] == 1 && intCardValue[0] == 10 && intCardValue[0] == 11 && intCardValue[0] == 12 && intCardValue[0] == 13) { 
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
		
		// used for payout 
		if(blnFlush == true && blnRoyalStraight == true){ // royal flush
			intUserMoneyResult = 800 * intUserBet;
			con.print("ROYAL FLUSH! ");
			con.print(intUserMoneyResult);
			con.println("");
		}else if(blnFlush == true && blnStraight == true){ // straight flush
			intUserMoneyResult = 50 * intUserBet;
			con.print("STRAIGHT FLUSH! ");
			con.print(intUserMoneyResult);
			con.println("");
		}else if(blnFourCount == true){ // four of a kind
			intUserMoneyResult = 25 * intUserBet;
			con.print("FOUR OF A KIND! ");
			con.print(intUserMoneyResult);
			con.println("");
		}else if(blnThreeCount == true && intPairCount == 1){ // full house
			intUserMoneyResult = 9 * intUserBet;
			con.print("FULL HOUSE! ");
			con.print(intUserMoneyResult);
			con.println("");
		}else if(blnFlush == true){ // flush
			intUserMoneyResult = 6 * intUserBet;
			con.print("FLUSH! ");
			con.print(intUserMoneyResult);
			con.println("");
		}else if(blnStraight == true || blnRoyalStraight == true){ // straight
			intUserMoneyResult = 4 * intUserBet;
			con.print("STRAIGHT! ");
			con.print(intUserMoneyResult);
			con.println("");
		}else if(blnThreeCount == true){ // three of a kind
			intUserMoneyResult = 3 * intUserBet;
			con.print("THREE OF A KIND! ");
			con.print(intUserMoneyResult);
			con.println("");
		}else if(intPairCount == 2){ // two pairs
			intUserMoneyResult = 2 * intUserBet;
			con.print("TWO PAIRS! ");
			con.print(intUserMoneyResult);
			con.println("");
		}else if(blnJacks  == true){ // jacks or better
			intUserMoneyResult = 1 * intUserBet;
			con.print("JACKS OR BETTER! ");
			con.print(intUserMoneyResult);
			con.println("");
		}else{
			intUserMoneyResult = -1 * intUserBet;
		}
		
		
		return intUserMoneyResult;
	}
}
