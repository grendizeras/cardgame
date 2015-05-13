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
}
