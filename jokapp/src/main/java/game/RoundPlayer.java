package game;

import game.cards.Card;
import game.cards.CardBase;
import game.cards.Suit;

/**
 * Created by Giorgi on 5/12/2015.
 */
public class RoundPlayer implements Cloneable {
    private final Player mPlayer;
    private int mSaid;
    private int mTaken;
    private CardBase mPlayedCard;
    private CardBase[] mCardsOnHand;

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

    public boolean hasSuit(Suit suit) {
        if (mCardsOnHand != null) {
            for (CardBase card : mCardsOnHand) {
                if (card.getSuit() == suit)
                    return true;
            }
        }
        return false;
    }

    public boolean isCardBiggestOfSuit(CardBase card) {
        if (card.getSuit() == Suit.JOKER) {
            return true;
        }
        Card mCard = (Card) card;
        for (CardBase kard : mCardsOnHand) {
            if (kard.getSuit() == Suit.JOKER)
                continue;
            Card cCard = (Card) kard;
            if (cCard.getSuit() == mCard.getSuit()) {
                if (cCard.getFace().getRank() > mCard.getFace().getRank())
                    return false;
            }
        }
        return true;
    }

    public CardBase getPlayedCard() {
        return mPlayedCard;
    }

    public void setPlayedCard(CardBase playedCard) {
        this.mPlayedCard = playedCard;
        if (playedCard != null)
            removeCard(playedCard);
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
        this.mCardsOnHand = cards;
    }

    public Player getPlayer() {
        return mPlayer;
    }


    private void removeCard(CardBase card) {

        CardBase[] cards = new CardBase[mCardsOnHand.length - 1];
        int k = 0;
        for (int i = 0; i < mCardsOnHand.length; i++) {

            if (mCardsOnHand[i].equals(card))
                continue;

            cards[k] = mCardsOnHand[i];
            k++;
        }
        mCardsOnHand = cards;

    }

    @Override
    protected RoundPlayer clone() throws CloneNotSupportedException {
        return new RoundPlayer(this);
    }

    @Override
    public boolean equals(Object o) {
        return this.mPlayer.getId() == ((RoundPlayer) o).mPlayer.getId();
    }
}
