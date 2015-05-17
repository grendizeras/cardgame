package game.cards;

/**
 * Created by Giorgi on 5/12/2015.
 */
public class Card extends CardBase {
    private Face mFace;

    public Card(Suit suit, Face face) {
        super(suit);
        mFace = face;
    }

    public Face getFace() {
        return mFace;
    }

    @Override
    public boolean equals(Object o) {
        CardBase card=(CardBase)o;
        if(card.getSuit()!=Suit.JOKER){
            return super.equals(o)&&((Card)card).getFace()==this.getFace();
        }
        return false;

    }
}
