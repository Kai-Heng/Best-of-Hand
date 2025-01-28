package hw4;

import api.Card;
import api.Hand;
import api.IEvaluator;
import api.Suit;
import util.SubsetFinder;

/**
 * The class AbstractEvaluator includes common code for all evaluator types.
 * 
 * TODO: Expand this comment with an explanation of how your class hierarchy
 * is organized.
 * 
 * @author Kai Heng Gan
 */
public abstract class AbstractEvaluator implements IEvaluator{
	/**
	 * Evaluator name
	 */
	private String name;
	
	/**
	 * Card rank
	 */
	private int rank;
	
	/**
	 * Max card rank
	 */
	private int maxRank;
	
	/**
	 * Amount of cards required for the evaluator
	 */
	private int cardsRequired;
	
	/**
	 * Number of card in hand
	 */
	private int handSize;
	
	 /**
	  * Returns a hand whose main cards consist of the indicated subset
	  * of the given cards.  If the indicated subset does
	  * not satisfy the criteria for this evaluator, this
	  * method returns null. The subset is described as
	  * an ordered array of indices to be selected from the given
	  * Card array.  The number of main cards in the hand
	  * will be the value of <code>getRequiredCards()</code>
	  * and the total number of cards in the hand will
	  * be the value of <code>handSize()</code>.  If the length
	  * of the given array of cards is less than <code>handSize()</code>,
	  * this method returns null.   The
	  * given array must be sorted with highest-ranked card first
	  * according to <code>Card.compareTo()</code>.  The array
	  * is not modified by this operation.
	  * 
	  * @param allCards
	  *   list of cards from which to select the main cards for the hand
	  * @param subset
	  *   list of indices of cards to be selected, in ascending order
	  * @return
	  *   hand whose main cards consist of the indicated subset, or null
	  *   if the indicated subset does not satisfy this evaluator
	  */
	public abstract Hand createHand(Card[] allCards, int[] subset);
	
	/**
	   * Returns the best possible hand satisfying this evaluator's 
	   * criteria that can be formed from the given list of cards.
	   * "Best" is defined to be first according to the compareTo() method of 
	   * Hand.  Returns null if there is no such hand.  The number of main cards 
	   * in the hand will be the value of <code>getRequiredCards()</code>
	   * and the total number of cards in the hand will
	   * be the value of <code>handSize()</code>.  If the length
	   * of the given array of cards is less than <code>totalCards()</code>,
	   * this method returns null.  The
	   * given array must be sorted with highest-ranked card first
	   * according to <code>Card.compareTo()</code>.  The array
	   * is not modified by this operation.
	   *  
	   * @param allCards
	   *   list of cards from which to create the hand
	   * @return
	   *   best possible hand satisfying this evaluator that can be formed
	   *   from the given cards
	   */
	public abstract Hand getBestHand(Card[] allCards);
	
	/**
	 * Constructor for two parameters evaluator
	 * @param ranking
	 * @param handSize
	 * @param cardsRequired
	 */
	protected AbstractEvaluator(int ranking, int handSize, int cardsRequired) {
		this.rank = ranking;
		this.handSize = handSize;
		this.cardsRequired = cardsRequired;
	}
	
	/**
	 * Constructor for three parameters evaluator
	 * @param ranking
	 * @param handSize
	 * @param maxCardRank
	 * @param cardsRequired
	 */
	protected AbstractEvaluator(int ranking, int handSize, int maxCardRank, int cardsRequired) {
		this.rank = ranking;
		this.handSize = handSize;
		this.maxRank = maxCardRank;
		this.cardsRequired = cardsRequired;
	}
	
	/**
	 * Returns a name for this evaluator.
	 * @return name of this evaluator
	 */
	protected String getname() {
	  return name;
	}
	  /**
	   * Returns the ranking for this evaluator, where a lower number
	   * is considered "better".
	   * @return
	   *   ranking for this evaluator
	   */
	public int getRanking() {
		return rank;
	}
	
	  /**
	   * Returns the minimum number of cards needed for a hand
	   * produced by this evaluator (main cards only).
	   * @return
	   */
	public int cardsRequired() {
		return cardsRequired;
	}
	
	  /**
	   * Returns the number of cards in a hand.  This value is generally
	   * defined by a game, and is not necessarily the same as
	   * the value returned by <code>cardsRequired</code>
	   * (main cards plus side cards).
	   * @return
	   *   number of cards in a hand
	   */
	public int handSize() {
		return handSize;
	}
	
	  
	  /**
	   * Determines whether there exists a subset of the given cards
	   * that satisfies the criteria for this evaluator.  The length of
	   * the given array must be greater than or equal to the value
	   * returned by <code>cardsRequired()</code>. The
	   * given array must be sorted with highest-ranked card first
	   * according to <code>Card.compareTo()</code>.  The array
	   * is not modified by this operation.
	   * @param allCards
	   *   list of cards to evaluate
	   * @return
	   *   true if some subset of the given cards satisfy this evaluator
	   */
	public boolean canSubsetSatisfy(Card[] allCards){
		if(allCards.length >= cardsRequired) {
			return true;
		}
		return false;
	}
	
	  /**
	   * Determines whether the given group of cards satisfies the
	   * criteria this evaluator.  The length
	   * of the given array must exactly match the value 
	   * returned by <code>cardsRequired()</code>.  The
	   * given array must be sorted with highest-ranked card first
	   * according to <code>Card.compareTo()</code>.  The array
	   * is not modified by this operation.
	   * @param mainCards
	   *   array of cards
	   * @return
	   *   true if the given cards satisfy this evaluator
	   */
	public boolean canSatisfy(Card[] mainCards){
		if (mainCards.length != cardsRequired()) return false;
		
		Card[] temp = mainCards.clone();
		int last = temp[0].getRank();
		if (temp[0].getRank() == 1 && temp[1].getRank() == maxRank) last = maxRank + 1;
		else if(temp[0].getRank() == 1 && temp[mainCards.length - 1].getRank() == 2){
			for (int i = 1; i < temp.length; i++) {
				Card cur = temp[i];
				temp[i] = temp[i - 1];
				temp[i - 1] = cur;
			}
			last = temp[0].getRank();
		}
		
		for (int i = 1; i < temp.length; i++) {
			if (temp[i].getRank() != last - 1) return false;
			else last = temp[i].getRank();
		}
		return true;
	}
	
}
