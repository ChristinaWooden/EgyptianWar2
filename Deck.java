import java.util.ArrayList;
import java.util.Collections;

public class Deck {
  public static final int NUM_FACES = 13;
  public static final int NUM_SUITS = 4;
  public static final int NUM_CARDS = 52;

  public static final String SUITS[] = { "CLUBS", "SPADES", "DIAMONDS", "HEARTS" };

  private int topCardIndex;
  private ArrayList<Card> stackOfCards;

  public Deck () {
    stackOfCards = new ArrayList<Card>();
    topCardIndex = NUM_CARDS - 1;
                
    for (int s = 0; s < NUM_SUITS; s++){
    	for (int f = 0; f < NUM_FACES; f++){
    		Card card = new Card(f, SUITS[s]);
    		stackOfCards.add(card);
    	}
    }
  }

  public void shuffle () {
    Collections.shuffle(stackOfCards);
    topCardIndex = getSize() - 1;
  }

  public int getSize() {
    return stackOfCards.size();
  }

  public int numRemainingCards() {
    return topCardIndex + 1;
  }

  public Card getNextCard() {
  	if (topCardIndex < 0){
  		return null;
  	}
    return stackOfCards.get(topCardIndex--);
  }

  public Card removeFromDeck(int i){
    if (i < getSize()) {
      return stackOfCards.remove(i);
    } else {
      return null;
    }
  }

  public String toString() {
    return stackOfCards + "  topCardIndex = " + topCardIndex;
  } 
}