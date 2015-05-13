package game.cards;

/**
 * Created by Giorgi on 5/12/2015.
 */
public abstract class CardBase {
    private Suit mSuit;

    public CardBase(Suit suit) {
        mSuit = suit;
    }


    public Suit getSuit(){
        return mSuit;
    }
}