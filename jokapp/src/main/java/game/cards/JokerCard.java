package game.cards;

/**
 * Created by Giorgi on 5/12/2015.
 */
public class JokerCard extends CardBase {

    private boolean mJoker;
    private Suit mSuit;
    private boolean mMustTakeOrGive;

    public JokerCard() {
        super(Suit.JOKER);
    }


    public boolean isJoker() {
        return mJoker;
    }

    public void setJoker(boolean mJoker) {
        this.mJoker = mJoker;
    }

    public Suit getSaidSuit() {
        return mSuit;
    }

    public void setSaidSuit(Suit mSuit) {
        this.mSuit = mSuit;
    }

    public boolean isMustTakeOrGive() {
        return mMustTakeOrGive;
    }

    public void setMustTakeOrGive(boolean mMustTakeOrGive) {
        this.mMustTakeOrGive = mMustTakeOrGive;
    }




}
