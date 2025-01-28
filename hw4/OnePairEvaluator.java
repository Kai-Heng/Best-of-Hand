package hw4;

import api.Card;
import api.Hand;
import api.IEvaluator;
import api.Suit;

/**
 * Evaluator for a hand containing (at least) two cards of the same rank.
 * The number of cards required is two.
 * 
 * The name of this evaluator is "One Pair".
 * 
 * @author Kai Heng Gan
 */
//Note: You must edit this declaration to extend AbstractEvaluator
//or to extend some other class that extends AbstractEvaluator
public class OnePairEvaluator extends AbstractEvaluator
{
  /**
   * Constructs the evaluator.
   * @param ranking
   *   ranking of this hand
   * @param handSize
   *   number of cards in a hand
   */
  public OnePairEvaluator(int ranking, int handSize)
  {
    // TODO: call appropriate superclass constructor and 
    // perform other initialization
	  super(ranking, handSize, 2);
  }
	
  @Override
	public String getName() {
		// TODO Auto-generated method stub
		return "One Pair";
	}
	@Override
	public boolean canSatisfy(Card[] mainCards) {
		if(mainCards.length != cardsRequired()) {
			return false;
		}
		
		for(int i = 0; i<mainCards.length-1; i++) {
			if(mainCards[i].getRank() == mainCards[i+1].getRank()) {
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public boolean canSubsetSatisfy(Card[] allCards) {
		if(allCards.length >= cardsRequired()) {
			for(int i = 0; i<allCards.length-1; i++) {
				if(allCards[i].getRank() == allCards[i+1].getRank()) {
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
		Card[] temp = allCards.clone();
		Card[] newCardList = new Card[allCards.length];
		
		Card[] mainCard = new Card[cardsRequired()];
		Card[] sideCard = new Card[handSize() - mainCard.length];
		
		boolean satisfy = false;
		for(int i = 0; i<subset.length; i++) {
			for(int j = 0; j<allCards.length-2; j++) {
				if(subset[i] == allCards[j].getRank() && subset[i] == allCards[j+1].getRank()) {
					int n = j;
					for(int k = 0; k<mainCard.length; k++) {
						mainCard[k] = new Card(subset[i], allCards[n].getSuit());
						n++;
					}
					satisfy = true;
				}
				if(subset[i] == allCards[j].getRank() && subset[i] == allCards[j+1].getRank() && subset[i] == allCards[j+2].getRank()) {
					int n = j;
					if(allCards[j].getSuit().equals(Suit.SPADES) && allCards[j+2].getSuit().equals(Suit.CLUBS)) {
						for(int k = 0; k<mainCard.length; k++) {
							mainCard[k] = new Card(subset[i], allCards[n].getSuit());
							n += 2;
						}
					}
					else if(allCards[j].getSuit().equals(Suit.HEARTS) && allCards[j+1].getSuit().equals(Suit.DIAMONDS)) {
						for(int k = 0; k<mainCard.length; k++) {
							mainCard[k] = new Card(subset[i], allCards[n].getSuit());
							n++;
						}
					}
					
					satisfy = true;
				}
			}
		}
		
		if(satisfy) {
			for(int i = 0; i<mainCard.length; i++) {
				for(int j = 0; j<temp.length; j++) {
					if(mainCard[i].equals(temp[j])) {
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
			
			for(int i = 0; i<sideCard.length; i++) {
				sideCard[i] = newCardList[i];
			}
		}
		else {
			return null;
		}
		
		return new Hand(mainCard, sideCard, this);
	}
	
	@Override
	public Hand getBestHand(Card[] allCards) {
		Card[] mainCards = new Card[cardsRequired()];
		Card[] sideCards = new Card[handSize() - mainCards.length];
		Card[] temp = allCards.clone();
		Card[] newCardList = new Card[allCards.length];
		boolean haveMainCards = false;
		for(int i = 0; i<allCards.length-1; i++) {
			if(allCards[i] != null && allCards[i+1] != null && allCards[i].getRank() == allCards[i+1].getRank()) {
				mainCards[0] = allCards[i];
				mainCards[1] = allCards[i+1];
				haveMainCards = true;
				break;
			}
		}
		if(haveMainCards == false) {
			return null;
		}
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
		
		
		return new Hand(mainCards, sideCards, this);
	}
	
}
