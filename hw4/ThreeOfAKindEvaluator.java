package hw4;

import api.Card;
import api.Hand;

/**
 * Evaluator for a hand containing (at least) three cards of the same rank.
 * The number of cards required is three.
 * 
 * The name of this evaluator is "Three of a Kind".
 * 
 * @author Kai Heng Gan
 */
//Note: You must edit this declaration to extend AbstractEvaluator
//or to extend some other class that extends AbstractEvaluator
public class ThreeOfAKindEvaluator extends AbstractEvaluator
{
  /**
   * Constructs the evaluator.
   * @param ranking
   *   ranking of this hand
   * @param handSize
   *   number of cards in a hand
   */
  public ThreeOfAKindEvaluator(int ranking, int handSize)
  {
    // TODO: call appropriate superclass constructor and 
    // perform other initialization
	  super(ranking, handSize, 3);
  }

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Three of a Kind";
	}
	
	@Override
	public boolean canSatisfy(Card[] mainCards) {
		// TODO Auto-generated method stub
		if(mainCards.length != cardsRequired()) {
			return false;
		}
		for (int i = 0; i<mainCards.length-2; i++) {
			if(mainCards[i].getRank() == mainCards[i+1].getRank() && mainCards[i+1].getRank() == mainCards[i+2].getRank()) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean canSubsetSatisfy(Card[] allCards) {
		if(allCards.length >= cardsRequired()) {
			for (int i = 0; i<allCards.length-2; i++) {
				if(allCards[i].getRank() == allCards[i+1].getRank() && allCards[i+1].getRank() == allCards[i+2].getRank()) {
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public Hand createHand(Card[] allCards, int[] subset){
		if(allCards.length < handSize()) {
			return null;
		}
		Card mainCards[] = new Card[cardsRequired()];
		Card sideCards[] = new Card[handSize() - mainCards.length];
		
		boolean isSatisfy = false;
		for(int i = 0; i<subset.length; i++) {
			for(int j = 0; j<allCards.length-2; j++) {
				if(allCards[j].getRank() == subset[i] && allCards[j+1].getRank() == subset[i] && allCards[j+2].getRank() == subset[i]) {
					mainCards[0] = allCards[j];
					mainCards[1] = allCards[j+1];
					mainCards[2] = allCards[j+2];
					
					isSatisfy = true;
					break;
				}
			}
		}
		
		if(isSatisfy) {
			Card[] temp = allCards.clone();
			Card[] newCardList = new Card[allCards.length];
			
			for(int i = 0; i<mainCards.length; i++) {
				for(int j = 0; j<temp.length; j++) {
					if(mainCards[i].equals(temp[j])) {
						temp[j] = null;
					}
				}
			}
			
			int list = 0;
			for(int i = 0; i<temp.length; i++) {
				if(temp[i] != null) {
					newCardList[list] = temp[i];
					list++;
				}
			}
			
			for(int i = 0; i<sideCards.length; i++) {
				sideCards[i] = newCardList[i];
			}
		}
		else {
			return null;
		}
		
		return new Hand(mainCards, sideCards, this);
	}
	
	@Override
	public Hand getBestHand(Card[] allCards) {
		if(allCards.length < handSize()) {
			return null;
		}
		Card mainCards[] = new Card[cardsRequired()];
		Card sideCards[] = new Card[handSize() - mainCards.length];
		
		boolean isSatisfy = false;
		
		for(int i = 0; i<allCards.length-3; i++) {
			if(allCards[i] != null && allCards[i+1] != null && allCards[i+2] != null && allCards[i+3] != null && allCards[i].getRank() == allCards[i+1].getRank() && allCards[i+1].getRank() == allCards[i+2].getRank() ) {
				mainCards[0] = allCards[i];
				mainCards[1] = allCards[i+1];
				mainCards[2] = allCards[i+2];
				
				isSatisfy = true;
				break;
			}
		}
		
		if(isSatisfy) {
			Card[] temp = allCards.clone();
			Card[] newCardList = new Card[allCards.length];
			
			for(int i = 0; i<mainCards.length; i++) {
				for(int j = 0; j<temp.length; j++) {
					if(mainCards[i].equals(temp[j])) {
						temp[j] = null;
					}
				}
			}
			
			int list = 0;
			for(int i = 0; i<temp.length; i++) {
				if(temp[i] != null) {
					newCardList[list] = temp[i];
					list++;
				}
			}
			
			for(int i = 0; i<sideCards.length; i++) {
				sideCards[i] = newCardList[i];
			}
		}
		else {
			return null;
		}
		
		for(int i = 0; i<mainCards.length; i++) {
			if(mainCards[i] == null) {
				return null;
			}
		}
		
		return new Hand(mainCards, sideCards, this);
	}

}
