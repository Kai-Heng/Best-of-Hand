package hw4;

import java.util.ArrayList;

import api.Card;
import api.Hand;
import api.Suit;
import util.SubsetFinder;

/**
 * Evaluator for a hand consisting of a "straight" in which the
 * card ranks are consecutive numbers AND the cards all
 * have the same suit.  The number of required 
 * cards is equal to the hand size.  An ace (card of rank 1) 
 * may be treated as the highest possible card or as the lowest
 * (not both) To evaluate a straight containing an ace it is
 * necessary to know what the highest card rank will be in a
 * given game; therefore, this value must be specified when the
 * evaluator is constructed.  In a hand created by this
 * evaluator the cards are listed in descending order with high 
 * card first, e.g. [10 9 8 7 6] or [A K Q J 10], with
 * one exception: In case of an ace-low straight, the ace
 * must appear last, as in [5 4 3 2 A]
 * 
 * The name of this evaluator is "Straight Flush".
 * 
 * @author Kai Heng Gan
 */
//Note: You must edit this declaration to extend AbstractEvaluator
//or to extend some other class that extends AbstractEvaluator
public class StraightFlushEvaluator extends AbstractEvaluator
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
  public StraightFlushEvaluator(int ranking, int handSize, int maxCardRank)
  {
    // TODO: call appropriate superclass constructor and 
    // perform other initialization
	  super(ranking, handSize, maxCardRank, handSize);
  }

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Straight Flush";
	}
	
	@Override
	public boolean canSatisfy(Card[] mainCards){
		if(!super.canSatisfy(mainCards)) return false;
		//checks if the hand is of the same suit
		for (int i = 1; i < mainCards.length; i++) if(mainCards[i].getSuit() != mainCards[i - 1].getSuit()) return false;
		return true;
	}
	
	@Override
	public boolean canSubsetSatisfy(Card[] allCards) {
		return (allCards.length >= cardsRequired() && getBestHand(allCards) != null);
	}
	
	@Override
	public Hand createHand(Card[] allCards, int[] subset) {
		Card[] mainCards = new Card[subset.length];
		Card[] sideCards = new Card[handSize() - subset.length];
		//adding the cards into main cards
		for (int i = 0; i < mainCards.length; i++) mainCards[i] = allCards[subset[i]];
		//adding the cards that are not in the main cards into side cards
		for (int i = 0, j = 0; i < sideCards.length && j < allCards.length; i++) {
			  for (int k = 0; k < mainCards.length; k++) {
				  if (mainCards[k].equals(allCards[j])) j++;
			  }
			  sideCards[i] = allCards[j++];
		}
		Hand newHand = new Hand(mainCards, sideCards, this);
		if (canSatisfy(newHand.getMainCards())) return newHand;
		else return null;
	}
	
	@Override
	public Hand getBestHand(Card[] allCards) {
		if (allCards.length < cardsRequired()) return null;
		ArrayList<int[]> subs = SubsetFinder.findSubsets(allCards.length, cardsRequired());
		for (int[] i : subs) if (createHand(allCards, i) != null) return createHand(allCards, i);
		return null;
	}
}
