package game;

import game.cards.CardBase;

/**
 * Created by Giorgi on 5/12/2015.
 */
public class RoundPlayer implements Cloneable {
    private final Player mPlayer;
    private int mSaid;
    private int mTaken;
    private CardBase mPlayedCard;
    private  CardBase[] mCardsOnHand;

    public RoundPlayer(Player player) {
        mPlayer = player;

    }

    private RoundPlayer(RoundPlayer source) {
        mPlayer = source.mPlayer;
        mSaid = source.mSaid;
        mTaken = source.mTaken;
        mPlayedCard = source.mPlayedCard;
        mCardsOnHand = source.mCardsOnHand;

    }

    public CardBase getPlayedCard() {
        return mPlayedCard;
    }

    public void setPlayedCard(CardBase mPlayedCard) {
        this.mPlayedCard = mPlayedCard;
    }

    public int getTaken() {
        return mTaken;
    }

    public void setTaken(int mTaken) {
        this.mTaken = mTaken;
    }

    public int getSaid() {
        return mSaid;
    }

    public void setSaid(int mSaid) {
        this.mSaid = mSaid;
    }

    public CardBase[] getCardsOnHand() {
        return mCardsOnHand;
    }

    public void setCardsOnHand(CardBase[] cards) {
     this.mCardsOnHand=cards;
    }

    public Player getPlayer() {
        return mPlayer;
    }


    @Override
    protected RoundPlayer clone() throws CloneNotSupportedException {
        return new RoundPlayer(this);
    }

    @Override
    public boolean equals(Object o) {
        return this.mPlayer.getId()==((RoundPlayer)o).mPlayer.getId();
    }
}
