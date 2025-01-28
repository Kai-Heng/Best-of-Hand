package hw4;

import java.util.ArrayList;
import java.util.Arrays;

import api.Card;
import api.Hand;
import util.SubsetFinder;

/**
 * Evaluator for a hand consisting of a "straight" in which the
 * card ranks are consecutive numbers.  The number of required 
 * cards is equal to the hand size.  An ace (card of rank 1) 
 * may be treated as the highest possible card or as the lowest
 * (not both).  To evaluate a straight containing an ace it is
 * necessary to know what the highest card rank will be in a
 * given game; therefore, this value must be specified when the
 * evaluator is constructed.  In a hand created by this
 * evaluator the cards are listed in descending order with high 
 * card first, e.g. [10 9 8 7 6] or [A K Q J 10], with
 * one exception: In case of an ace-low straight, the ace
 * must appear last, as in [5 4 3 2 A]
 * 
 * The name of this evaluator is "Straight".
 * 
 * @author Kai Heng Gan
 */
//Note: You must edit this declaration to extend AbstractEvaluator
//or to extend some other class that extends AbstractEvaluator
public class StraightEvaluator extends AbstractEvaluator
{  
  /**
   * Constructs the evaluator. Note that the maximum rank of
   * the cards to be used must be specified in order to 
   * correctly evaluate a straight with ace high.
   * @param ranking
   *   ranking of this hand
   * @param handSize
   *   number of cards in a hand
   * @param maxCardRank
   *   largest rank of any card to be used
   */
  public StraightEvaluator(int ranking, int handSize, int maxCardRank)
  {
    // TODO: call appropriate superclass constructor and 
    // perform other initialization
	  super(ranking, handSize, maxCardRank, handSize);
  }

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Straight";
	}

	@Override
	public boolean canSatisfy(Card[] mainCards) {
		// TODO Auto-generated method stub
		if(mainCards.length != cardsRequired()) {
			return false;
		}
		
		if(mainCards[0].getRank() == 1) {
			int j = 1;
			int count = 1;
			for(int i = 1; i<mainCards.length; i++) {
				if(mainCards[i].getRank() + j == 14) {
					count++;
					j++;
				}
			}
			
			if(count == cardsRequired()) {
				return true;
			}
		}
		if(mainCards[0].getRank() == 1 && mainCards[mainCards.length-1].getRank() == 2) {
			int cardRank = mainCards[1].getRank() + 1;
			int count = 1;
			for(int i = 1; i<mainCards.length; i++) {
				if(mainCards[i].getRank() + i == cardRank) {
					count++;
				}
			}
			
			if(count == cardsRequired()) {
				return true;
			}
		}
		else {
			int count = 1;
			for(int i = 0; i<mainCards.length-1; i++) {
				if(mainCards[i].getRank() != 1) {
					if((mainCards[i].getRank() == mainCards[i+1].getRank()+1)) {
						count++;
					}
					else if((mainCards[i].getRank() > mainCards[i+1].getRank()+1)) {
						count = 1;
					}
				}
			}
			if(count == cardsRequired()) {
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public boolean canSubsetSatisfy(Card[] allCards) {
		ArrayList<int[]> sub = SubsetFinder.findSubsets(allCards.length, cardsRequired());
		for(int i = 0; i<sub.size(); i++) {
			Card cards[] = new Card[sub.get(i).length];
			
			for(int j = 0; j<cards.length; j++) {
				cards[j] = allCards[sub.get(i)[j]];
			}
			
			if(canSatisfy(cards) == true) {
				return true;
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
				if(allCards[j].getRank() == subset[i] && n<cardsRequired()) {
					if(n==0) {
						mainCards[n] = allCards[j];
						n++;
					}
					else if(allCards[j] != mainCards[n-1]) {
						mainCards[n] = allCards[j];
						n++;
					}
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
	
	@Override
	public Hand getBestHand(Card[] allCards) {
		if(allCards.length < cardsRequired()) {
			return null;
		}
		Card mainCards[] = new Card[cardsRequired()];
		

		int n = 1;
		Card newCardList[] = new Card[allCards.length];
		newCardList[0] = allCards[0];
		for(int i = 0; i<allCards.length; i++) {
			if(newCardList[n-1] != null && allCards[i] != null && newCardList[n-1].getRank() != allCards[i].getRank()) {
				newCardList[n] = allCards[i];
				n++;
			}
		}
		ArrayList <Card> cardArr = new ArrayList<>();
		for(int i = 0; i<allCards.length; i++) {
			if(newCardList[i] != null) {
				cardArr.add(newCardList[i]);
			}
		}
		
		Card monoCardList[] = new Card[cardArr.size()];
		boolean checkCase = true;
		
		for(int i = 0; i<monoCardList.length; i++) {
			monoCardList[i] = newCardList[i];
		}
		
		if(monoCardList.length > 0 && monoCardList[0].getRank() == 1) {
			mainCards[0] = monoCardList[0];
			int j = 1;
			int count = 1;
			for(int i = 1; i<monoCardList.length; i++) {
				if(monoCardList[i].getRank() + j == 14) {
					mainCards[j] = monoCardList[i];
					count++;
					j++;
				}
				if(count == cardsRequired()) {
					checkCase = false;
					break;
				}
			}
		}
		

		if(checkCase) {
			if(monoCardList.length > 0 && monoCardList[0].getRank() == 1) {
				mainCards[0] = monoCardList[0];
				Card straight[] = new Card[monoCardList.length];
				int j = 0;
				for(int i = 1; i<monoCardList.length-1; i++) {
					if((monoCardList[i].getRank() == monoCardList[i+1].getRank()+1)) {
						straight[j] = monoCardList[i];
						j++;
						if((i+1 == monoCardList.length-1) && (monoCardList[i+1].getRank()+1 == monoCardList[i].getRank())) {
							straight[j] = monoCardList[i+1];
						}
						
					}
					else if((monoCardList[i].getRank() > monoCardList[i+1].getRank()+1)) {
						j = 0;
					}
				}

				int main = 1;
				for(int i = 0; i<mainCards.length; i++) {
					if(i<straight.length && straight[i] != null) {
						mainCards[main] = straight[i];
						main++;
					}
				}
				Card temp[] = new Card[mainCards.length];
				
				int m = 0;
				for(int i = 1; i<temp.length; i++) {
					temp[m] = mainCards[i];
					m++;
				}
				temp[temp.length-1] = mainCards[0];
				
				for(int i = 0; i<mainCards.length; i++) {
					mainCards[i] = temp[i];
				}
				
			}
			else {
				Card straight[] = new Card[monoCardList.length];
				int j = 0;
				if(monoCardList.length > 0 && monoCardList[0].getRank() != 1) {
					for(int i = 0; i<monoCardList.length-1; i++) {
						if((monoCardList[i].getRank() == monoCardList[i+1].getRank()+1)) {
							straight[j] = monoCardList[i];
							j++;
							if((i+1 == monoCardList.length-1) && (monoCardList[i+1].getRank()+1 == monoCardList[i].getRank())) {
								straight[j] = monoCardList[i+1];
							}
							
						}
						else if((monoCardList[i].getRank() > monoCardList[i+1].getRank()+1)) {
							j = 0;
						}
					}

					int main = 0;
					for(int i = 0; i<mainCards.length; i++) {
						if(straight[i] != null) {
							mainCards[main] = straight[i];
							main++;
						}
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
