package game;

import java.util.ArrayList;
import java.util.Arrays;

import game.cards.Card;
import game.cards.CardBase;
import game.cards.CardDeck;
import game.cards.Face;
import game.cards.Suit;

/**
 * Shared object class, that represents current round.
 */
public class Round implements Cloneable {

    private int mRoundNumber;//from 0 to 22
    //0 means needs atuzva
    private ArrayList<RoundPlayer> mRoundPlayers;
    private RoundPlayer mCurrentPlayer;
    private RoundStatus mStatus = RoundStatus.ATUZVA;

    public Round() {
    }
    //Serialization constructor

    public Round(Player[] players) {
        mRoundPlayers = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            mRoundPlayers.add(new RoundPlayer(players[i]));
        }
        initRound();
    }


    //atuzva
    public void initRound() {
        if (GameMatch.isMyTurn()) {
            CardBase[] cards = CardDeck.shuffle();
            setPlayerCards(cards);
            detectFirstToPlayAfterAtuzva();
        }
    }

    public void detectFirstToPlayAfterAtuzva() {
        endLoop:
        for (int i = 0; i < 9; i++) {
            for (RoundPlayer player : mRoundPlayers) {
                CardBase cardBase = player.getCardsOnHand()[i];
                if (cardBase.getSuit() != Suit.JOKER) {
                    Card card = (Card) cardBase;
                    if (card.getFace() == Face.ACE) {
                        ArrayList<RoundPlayer> players = new ArrayList<>();
                        int index = mRoundPlayers.indexOf(player);
                        for (int k = 0; k < 4; k++) {
                            players.add(mRoundPlayers.get((k + index) % 4));
                        }
                        mRoundPlayers = players;

                        break endLoop;

                    }
                }
            }
        }
    }

    public void startRound() {
        if (GameMatch.isMyTurn()) {
            mStatus = RoundStatus.SAYING;
            mRoundNumber++;
            CardBase[] cards = CardDeck.shuffle();
            setPlayerCards(cards);

        }
    }

    public int getCardCount() {
        int count = 0;
        if (mRoundNumber > 8) {
            if ( mRoundNumber <= 12 ||(mRoundNumber>20&&mRoundNumber<=24))
                count = 9;
            else count = 21 - mRoundNumber;
        } else {
            count = mRoundNumber;
        }

        count = (count == 0 && mRoundNumber != 22 ? 9 : count);
        return count;
    }

    public void setPlayerCards(CardBase[] cards) {
        int count = getCardCount();
        for (int i = 0; i < 4; i++) {
            mRoundPlayers.get(i).setCardsOnHand(Arrays.copyOfRange(cards, count * i, count * i + count));
        }
    }

    private Round(Round source) throws CloneNotSupportedException {
        mRoundNumber = source.mRoundNumber;
        mRoundPlayers = new ArrayList<>(source.mRoundPlayers);
        for (int i = 0; i < mRoundPlayers.size(); i++) {
            mRoundPlayers.set(i, source.mRoundPlayers.get(i).clone());
        }

    }

    public boolean say(int amount) {
        if (mStatus != RoundStatus.SAYING && !GameMatch.isMyTurn()) {
            return false;
        }
        //
        mCurrentPlayer.setSaid(amount);
        return GameLogic.processSay(this);
    }

    public boolean play(CardBase card) {
        if (mStatus != RoundStatus.PLAYING && !GameMatch.isMyTurn()) {
            return false;
        }
        //
        mCurrentPlayer.setPlayedCard(card);
        return GameLogic.processPlay(this);
    }

    public boolean isPlayerFirst(String id) {
        try {
            if (mRoundPlayers != null) {
                return mRoundPlayers.get(0).getPlayer().getId() == id;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public RoundPlayer findPlayer(String id) {

        try {
            if (mRoundPlayers != null) {
                for (int i = 0; i < mRoundPlayers.size(); i++) {
                    if (mRoundPlayers.get(i).getPlayer().getId() == id)
                        return mRoundPlayers.get(i).clone();
                }

            }
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public RoundPlayer findPreviousPlayer(String currPlayerId) {
        try {
            if (mRoundPlayers != null) {
                RoundPlayer prevPlayer = null;
                for (int i = 0; i < mRoundPlayers.size(); i++) {

                    if (mRoundPlayers.get(i).getPlayer().getId() == currPlayerId) {
                        if (prevPlayer == null)//i am the first
                            return null;
                        else
                            return prevPlayer.clone();
                    } else
                        prevPlayer = mRoundPlayers.get(i);
                }

            }
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setRoundNumber(int num) {
        mRoundNumber = num;
    }

    public RoundPlayer getCurrentPlayer() throws CloneNotSupportedException {
        return mCurrentPlayer.clone();
    }

    public int getRoundNumber() {
        return mRoundNumber;
    }

    public ArrayList<RoundPlayer> getRoundPlayers() {
        return mRoundPlayers;
    }

    public RoundStatus getStatus() {
        return mStatus;
    }

    void setStatus(RoundStatus status) {//from containing  package
        mStatus = status;
    }

    @Override
    protected Round clone() throws CloneNotSupportedException {
        return new Round(this);
    }


    public enum RoundStatus {
        ATUZVA, SAYING, PLAYING, FINISHED
    }
}
