import arc.*;

public class CPTAdrian{
	public static void main(String[] args){
		Console con = new Console("CPTAdrian - Video Poker", 1280, 720);
		int intDeck[][];
		intDeck = loadDeck();
		// test: con.println(intDeck[50][0]);
		// test: con.println(intDeck[50][1]);
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
		
		// test: con.println(intDeck[51][1]);
		
		return intDeck;
	}
}
