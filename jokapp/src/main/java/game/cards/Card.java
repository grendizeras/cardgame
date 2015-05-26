package game.cards;

import java.io.Serializable;

/**
 * Created by Giorgi on 5/12/2015.
 */
public class Card extends CardBase implements Serializable {
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


    @Override
    public String toString() {
        return super.toString()+"_"+mFace.toString();
    }
}
