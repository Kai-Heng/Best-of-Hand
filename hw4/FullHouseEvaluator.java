package hw4;

import api.Card;
import api.Hand;

/**
 * Evaluator for a generalized full house.  The number of required
 * cards is equal to the hand size.  If the hand size is an odd number
 * n, then there must be (n / 2) + 1 cards of the matching rank and the
 * remaining (n / 2) cards must be of matching rank. In this case, when constructing
 * a hand, the larger group must be listed first even if of lower rank
 * than the smaller group</strong> (e.g. as [3 3 3 5 5] rather than [5 5 3 3 3]).
 * If the hand size is even, then half the cards must be of matching 
 * rank and the remaining half of matching rank.  Any group of cards,
 * all of which are the same rank, always satisfies this
 * evaluator.
 * 
 * The name of this evaluator is "Full House".
 * 
 * @author Kai Heng Gan
 */
//Note: You must edit this declaration to extend AbstractEvaluator
//or to extend some other class that extends AbstractEvaluator
public class FullHouseEvaluator extends AbstractEvaluator
{
  /**
   * Constructs the evaluator.
   * @param ranking
   *   ranking of this hand
   * @param handSize
   *   number of cards in a hand
   */
  public FullHouseEvaluator(int ranking, int handSize)
  {
    // TODO: call appropriate superclass constructor and 
    // perform other initialization
	  super(ranking, handSize, handSize);
  }

  	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Full House";
	}
	
	@Override
	public boolean canSatisfy(Card[] mainCards) {
		if(mainCards.length != cardsRequired()) {
			return false;
		}
		
		boolean isSatisfy = true;
		
		for(int i = 0; i<mainCards.length; i++) {
			if(mainCards[0].getRank() != mainCards[i].getRank()) {
				isSatisfy = false;
			}
		}
		
		
		int upper;
		int lower;
		if(handSize() % 2 == 0) {
			upper = handSize()/2;
			lower = upper;
			for(int i = 0; i<upper-1; i++) {
				if(mainCards[i].getRank() != mainCards[i+1].getRank()) {
					return false;
				}
			}
			for(int i = lower+1; i<mainCards.length-1; i++) {
				if(mainCards[i].getRank() != mainCards[i+1].getRank()) {
					return false;
				}
			}
			isSatisfy = true;
		}
		else{
			upper = (handSize()/2) + 1;
			lower = handSize()/2;
			boolean checkPossibleCase = true;
			
			for(int i = 0; i<upper-1; i++) {
				if(mainCards[i].getRank() != mainCards[i+1].getRank()) {
					checkPossibleCase = false;
				}
			}
			
			for(int i = lower+1; i<mainCards.length-1; i++) {
				if(mainCards[i].getRank() != mainCards[i+1].getRank()) {
					checkPossibleCase = false;
				}
			}
			
			if(!checkPossibleCase) {
				for(int i = 0; i<lower-1; i++) {
					if(mainCards[i].getRank() != mainCards[i+1].getRank()) {
						return false;
					}
				}
				
				for(int i = lower; i<mainCards.length-1; i++) {
					if(mainCards[i].getRank() != mainCards[i+1].getRank()) {
						return false;
					}
				}
			}
			isSatisfy = true;
		}
		
		return isSatisfy;
	}
	
	
	
	@Override
	public boolean canSubsetSatisfy(Card[] allCards) {
		if(allCards.length >= cardsRequired()) {
			int a = 0;
			for(int i = 0; i<allCards.length; i++) {
				int b = 1;
				for(int j = i+1; j<allCards.length; j++) {
					if(allCards[j].getRank() == allCards[i].getRank()) {
						b++;
					}
				}
				if(b!=1 && a+b >= cardsRequired()) {
					return true;
				}
				
				if(b>a && b!=1) {
					a = b;
				}
				
				i += b-1;
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
		int n = 0;
		for(int i = 0; i<subset.length; i++) {
			for(int j = 0; j<allCards.length; j++) {
				if(allCards[j].getRank() == subset[i] && n<mainCards.length) {
					mainCards[n] = allCards[j];
					n++;
				}
			}
		}
		
		
		return new Hand(mainCards, null, this);
	}
	
	@Override
	public Hand getBestHand(Card[] allCards) {
		// TODO Auto-generated method stub
		Card[] mainCards = new Card[cardsRequired()];
		boolean isSatisfy = false;
		
		for(int i = 0; i<allCards.length; i++) {
			int c = 0;
			for(int j = 0; j<allCards.length; j++) {
				if(allCards[i] !=null && allCards[j]!=null && allCards[i].getRank() == allCards[j].getRank()) {
					c++;
				}
			}
			if(c >= cardsRequired()) {
				int n = 0;
				for(int j = i; j<allCards.length; j++) {
					mainCards[n] = allCards[j];
					n++;
					if(n==mainCards.length) {
						break;
					}
				}
				isSatisfy = true;
				break;
			}
		}
		
		if(isSatisfy == false) {
			int upper;
			int lower;
			if(cardsRequired()%2 == 0) {
				upper = handSize()/2;
				lower = upper;
				int cardTaken = 0;
				for(int i = 0; i<allCards.length; i++) {
					int count = 0;
					for(int j = 0; j<allCards.length; j++) {
						if(allCards[j].getRank() == allCards[i].getRank()) {
							count++;
						}
					}
					if (count == upper) {
						int n = 0;
						cardTaken = i+upper;
						for(int k = i; k<i+upper; k++ ) {
							mainCards[n] = allCards[k];
							n++;
						}
						break;
					}
				}
				
				for(int i = cardTaken; i<allCards.length; i++) {
					int count = 0;
					for(int j = 0; j<allCards.length; j++) {
						if(allCards[j].getRank() == allCards[i].getRank()) {
							count++;
						}
					}
					
					if(count == lower) {
						int n = lower;
						for (int k = i; k<i+lower; k++) {
							mainCards[n] = allCards[k];
							n++;
						}
						break;
					}
				}
				
			}
			else {
				upper = (handSize()/2) + 1;
				lower = handSize()/2;
				for(int i = 0; i<allCards.length; i++) {
					int count = 0;
					for(int j = 0; j<allCards.length; j++) {
						if(allCards[j] != null && allCards[i] != null && allCards[j].getRank() == allCards[i].getRank()) {
							count++;
						}
					}
					if (count == upper) {
						int n = 0;
						for(int k = i; k<i+upper; k++ ) {
							mainCards[n] = allCards[k];
							n++;
						}
						break;
					}
				}
				
				for(int i = 0; i<allCards.length; i++) {
					int count = 0;
					for(int j = 0; j<allCards.length; j++) {
						if(mainCards[0] !=null && allCards[i].getRank() == mainCards[0].getRank()) {
							break;
						}
						
						if(allCards[j] != null && allCards[i] != null && allCards[j].getRank() == allCards[i].getRank()) {
							count++;
						}
					}
					
					if(count == lower) {
						int n = lower+1;
						for (int k = i; k<i+lower; k++) {
							mainCards[n] = allCards[k];
							n++;
						}
						break;
					}
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
