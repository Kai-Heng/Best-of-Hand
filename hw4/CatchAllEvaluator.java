package hw4;

import api.Card;
import api.Hand;
import api.Suit;

/**
 * Evaluator satisfied by any set of cards.  The number of
 * required cards is equal to the hand size.
 * 
 * The name of this evaluator is "Catch All".
 * 
 * @author Kai Heng Gan
 */
//Note: You must edit this declaration to extend AbstractEvaluator
//or to extend some other class that extends AbstractEvaluator
public class CatchAllEvaluator extends AbstractEvaluator
{
  /**
   * Constructs the evaluator.
   * @param ranking
   *   ranking of this hand
   * @param handSize
   *   number of cards in a hand
   */
  public CatchAllEvaluator(int ranking, int handSize)
  {
    // TODO: call appropriate superclass constructor and 
    // perform other initialization
	  super(ranking, handSize, handSize);
	  
  }

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Catch All";
	}
	
	@Override
	public boolean canSatisfy(Card[] mainCards) {
		// TODO Auto-generated method stub
		if(mainCards.length != cardsRequired()) {
			return false;
		}
		return true;
	}
	
	@Override
	public boolean canSubsetSatisfy(Card[] allCards) {
		if(allCards.length >= cardsRequired()) {
			return true;
		}
		return false;
	}
	
	@Override
	public Hand createHand(Card[] allCards, int[] subset){
		if(allCards.length < handSize()) {
			return null;
		}
		Card mainCards[] = new Card[cardsRequired()];
		int n = 0;
		for(int i = 0; i<subset.length; i++) {
			for(int j = 0; j<allCards.length; j++) {
				if((allCards[j].getRank() == subset[i]) && (n<mainCards.length)) {
					mainCards[n] = allCards[j];
					n++;
				}
			}
		}
		
		return new Hand(mainCards, null, this);
	}
	
	@Override
	public Hand getBestHand(Card[] allCards) {
		if(allCards.length < handSize()) {
			return null;
		}
		
		Card mainCards[] = new Card[cardsRequired()];
		
		int j = 0;
		for(int i = 0; i<allCards.length; i++) {
			if(allCards[i] != null) {
				mainCards[j] = allCards[i];
				j++;
				
				if(j == cardsRequired()) {
					break;
				}
			}
		}
		
		for(int i = 0; i<mainCards.length; i++) {
			if(mainCards[i] == null) {
				return null;
			}
		}
		
		return new Hand(mainCards, null, this);
	}
  

}
