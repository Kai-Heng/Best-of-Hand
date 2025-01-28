package hw4;

import api.Card;
import api.Hand;

/**
 * Evaluator for a hand in which the rank of each card is a prime number.
 * The number of cards required is equal to the hand size. 
 * 
 * The name of this evaluator is "All Primes".
 * 
 * @author Kai Heng Gan
 */
//Note: You must edit this declaration to extend AbstractEvaluator
//or to extend some other class that extends AbstractEvaluator
public class AllPrimesEvaluator extends AbstractEvaluator
{
  /**
   * Constructs the evaluator.
   * @param ranking
   *   ranking of this hand
   * @param handSize
   *   number of cards in a hand
   */
  public AllPrimesEvaluator(int ranking, int handSize)
  {
    // TODO: call appropriate superclass constructor and 
    // perform other initialization
	  super(ranking, handSize, handSize);
  }
  
  	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "All Primes";
	}
  
	@Override
	public boolean canSatisfy(Card[] mainCards) {
		// TODO Auto-generated method stub
		if(mainCards.length != cardsRequired()) {
			return false;
		}
		for(int i = 0; i < mainCards.length; i++) {
			for(int j = 2; j <= (mainCards[i].getRank() / 2); j++) {
				if(mainCards[i].getRank() % j == 0) {
					return false;
				}
			}
		}
		return true;
	}
	
	@Override
	public boolean canSubsetSatisfy(Card[] allCards) {
		if(allCards.length < cardsRequired()) {
			return false;
		}
		int trackNotPrime = 0;
		for(int i = 0; i < allCards.length; i++) {
			for(int j = 2; j <= (allCards[i].getRank() / 2); j++) {
				if(allCards[i].getRank() == 1 || allCards[i].getRank() % j == 0) {
					trackNotPrime++;
					break;
				}
			}
		}
		
		if((allCards.length - trackNotPrime) < cardsRequired()) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public Hand createHand(Card[] allCards, int[] subset){
		if(allCards.length < handSize()) {
			return null;
		}
		Card mainCards[] = new Card[cardsRequired()];
		
		for(int i = 0; i < subset.length; i++) {
			for(int j = 2; j <= (subset[i] / 2); j++) {
				if(subset[i] % j == 0) {
					subset[i] = 0;
				}
			}
		}
		
		int n = 0;
		for(int i = 0; i<subset.length; i++) {
			for(int j = 0; j<allCards.length; j++) {
				if((allCards[j].getRank() == subset[i]) && (n<cardsRequired())) {
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
		Card newCardList[] = new Card[allCards.length];
		
		for(int i = 0; i<allCards.length; i++) {
			if(allCards[i] != null) {
				for(int j = 2; j <= (allCards[i].getRank() / 2); j++) {
					if(allCards[i].getRank() == 1 || allCards[i].getRank() % j == 0) {
						allCards[i] = null;
						break;
					}
				}
			}
			
		}
		
		int n = 0;
		for(int i = 0; i<allCards.length; i++) {
			if(allCards[i] != null) {
				newCardList[n] = allCards[i];
				n++;
			}
		}
		for(int i = 0; i<mainCards.length; i++) {
			mainCards[i] = newCardList[i];
			if(mainCards[i] == null) {
				return null;
			}
		}
		
		return new Hand(mainCards, null, this);
	}
}
