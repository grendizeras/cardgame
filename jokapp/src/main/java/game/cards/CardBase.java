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

    @Override
    public boolean equals(Object o) {
        CardBase card=(CardBase)o;
        return card.getSuit()==this.getSuit();
    }

    @Override
    public String toString() {
        return mSuit.toString();
    }
}
