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
    private final CardBase[] mCardsOnHand;

    public RoundPlayer(Player player,CardBase[] cards){
        mPlayer=player;
        mCardsOnHand=cards;
    }

    private RoundPlayer(RoundPlayer source){
        mPlayer=source.mPlayer;
        mSaid=source.mSaid;
        mTaken=source.mTaken;
        mPlayedCard=source.mPlayedCard;
        mCardsOnHand=source.mCardsOnHand;

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

    public CardBase[] getCardsOnHand(){
        return mCardsOnHand;
    }


    public Player getPlayer() {
        return mPlayer;
    }



    @Override
    protected RoundPlayer clone() throws CloneNotSupportedException {
        return new RoundPlayer(this);
    }
}
