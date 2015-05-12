package game;

import game.cards.CardBase;

/**
 * Created by Giorgi on 5/12/2015.
 */
public class RoundPlayer {
    private Player mPlayer;
    private int mSaid;
    private int mTaken;
    private CardBase mPlayedCard;

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



    public Player getPlayer() {
        return mPlayer;
    }

    public void setPlayer(Player mPlayer) {
        this.mPlayer = mPlayer;
    }




}
