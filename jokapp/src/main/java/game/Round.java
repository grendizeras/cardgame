package game;

import java.util.ArrayList;
import java.util.Arrays;

import game.cards.Card;
import game.cards.CardBase;
import game.cards.CardDeck;
import game.cards.Face;
import game.cards.JokerCard;
import game.cards.Suit;

/**
 * Shared object class, that represents current round.
 */
public class Round implements Cloneable {

    private int mRoundNumber;//from 0 to 24
    //0 means needs atuzva
    private CardBase mKozir;
    private ArrayList<RoundPlayer> mRoundPlayers;
    private RoundPlayer mCurrentPlayer;//vin tamashobs exla
    private RoundStatus mStatus = RoundStatus.ATUZVA;
    private RoundPlayer mStartingPlayer;//the one who start in the round (ex: someone took cards and mast start)

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

    public RoundPlayer getMe() {
        if (mRoundPlayers != null)
            return findPlayer(GameMatch.getMyId());
        throw new IllegalStateException("My id is not initialized");
    }

    //atuzva
    public void initRound() {
        if (GameMatch.isMyTurn()) {
            mCurrentPlayer = getMe();//init is always done by one player- initiator
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

    public void startRound() {//kartebis darigeba da gagzavna
        if (GameMatch.isMyTurn()) {
            mStatus = RoundStatus.SAYING;
            mRoundNumber++;
            CardBase[] cards = CardDeck.shuffle();
            setPlayerCards(cards);
            mCurrentPlayer = mRoundPlayers.get(0);//pirveli motamashidan dacyeba.
            mStartingPlayer = mCurrentPlayer;
            //anu svla gadava pirvel motamasheze, romelmac unda acxados
        }
    }

    public int getCardCount() {
        int count = 0;
        if (mRoundNumber > 8) {
            if (mRoundNumber <= 12 || (mRoundNumber > 20 && mRoundNumber <= 24))
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
        if (count * 4 < 36) {
            mKozir = cards[count * 4];
        }
    }

    private Round(Round source) throws CloneNotSupportedException {
        mRoundNumber = source.mRoundNumber;
        mRoundPlayers = new ArrayList<>(source.mRoundPlayers);
        mCurrentPlayer = source.mCurrentPlayer;
        mStartingPlayer = source.mStartingPlayer;
        mStatus = source.mStatus;
        for (int i = 0; i < mRoundPlayers.size(); i++) {
            mRoundPlayers.set(i, source.mRoundPlayers.get(i).clone());
        }

    }

    public boolean say(int amount) {
        if (mStatus != RoundStatus.SAYING && !GameMatch.isMyTurn()) {
            return false;
        }
        //

        if (amount > getCardCount())
            return false;
        //if is last player then check sum of all previous
        int index = getPlayerIndex(getMe());
        if (index == mRoundPlayers.size() - 1) {
            int sum = 0;
            for (int i = 0; i < 3; i++) {
                sum += mRoundPlayers.get(i).getSaid();
            }
// if sum of all is rouund number, than is unable to say it
            if (sum + amount == getCardCount()) {
                return false;
            }
        }
        getMe().setSaid(amount);
        mCurrentPlayer = getNextPlayer();
        return true;


    }

    public void shiftPlayers() {
        ArrayList<RoundPlayer> shifted = new ArrayList<>();
        for (int i = 0; i < mRoundPlayers.size(); i++) {
            shifted.add(mRoundPlayers.get((i + 1) % 4));
        }
        mRoundPlayers = shifted;
    }

    public int getPlayerIndex(RoundPlayer player) {
        return mRoundPlayers.indexOf(player);
    }

    public RoundPlayer getNextPlayer() {
        return getNextPlayer(getMe());
    }

    public RoundPlayer getNextPlayer(RoundPlayer player) {
        int index = mRoundPlayers.indexOf(player);
        int nextIndex = (index + 1) % 4;
        return mRoundPlayers.get(nextIndex);
    }

    public boolean play(CardBase card) {
        boolean success = false;
        if (mStatus != RoundStatus.PLAYING && !GameMatch.isMyTurn()) {
            return false;
        }
        //
        if (getPlayerIndex(getMe()) == 0) {// first player, no validation needed
            getMe().setPlayedCard(card);
            success= true;
        } else {

            success = validateCard(card);






        }
        if(success){
            mCurrentPlayer.setPlayedCard(card);
            mCurrentPlayer=getNextPlayer();
            if (getPlayerIndex(getMe()) == 3) {
                RoundPlayer player = findWinner();
                player.setTaken(player.getTaken() + 1);
                mStartingPlayer = player;
            }
        }
        return success;
    }

    public RoundPlayer findWinner() {
        RoundPlayer winner = null;
        RoundPlayer currentPlayer = null;
        winner = currentPlayer = mStartingPlayer;

        for (int i = 0; i < 3; i++) {
            currentPlayer = getNextPlayer(currentPlayer);
            if (processCards(winner, currentPlayer)) {
                winner = currentPlayer;
            }
        }
        return winner;

    }


    public Round nextRound() {
        reset();
        mRoundNumber++;
        shiftPlayers();
        mStatus = RoundStatus.SAYING;
        mCurrentPlayer = mStartingPlayer = mRoundPlayers.get(0);
        return this;
    }

    public boolean processCards(RoundPlayer winner, RoundPlayer current) {
        CardBase winnerCard = winner.getPlayedCard();
        CardBase currentCard = current.getPlayedCard();


        if (currentCard.getSuit() == Suit.JOKER) {//mashin tu shemdegi jokeria,
            JokerCard jCard = (JokerCard) currentCard;
            if (jCard.isJoker()) {// tu mojokra, mashin  igebs
                return true;
            }
            return false;//tu arada, nijea
        }

        if (winnerCard.getSuit() == Suit.JOKER) {//tu momgebiani karti aris jokeri,
            JokerCard jWinnerCard = (JokerCard) winnerCard;
            //tu shemdegi karti jokeri ar aris
            if (jWinnerCard.isMustTakeOrGive()) {// tu unda caigos iman, vinc jokeri itamasha, mashin, tu vishe acxada
//isev is iqneba mogebuli,
                return false;
            } else {// tu caigos acxada jokerma
                if (jWinnerCard.getSaidSuit() == currentCard.getSuit()) {// tu cvetia, mashin caigebs
                    return true;
                } else {//tu arada mashin vera
                    return false;
                }

            }
        } else {// tu mogebuli jokeri ar aris, mashin
            Card wCard = (Card) winnerCard;
            Card cCard = (Card) currentCard;
            if (winnerCard.getSuit() == currentCard.getSuit()) {
                return cCard.getFace().getRank() > wCard.getFace().getRank();
            } else {
                return currentCard.getSuit() == mKozir.getSuit();
            }

        }

    }


    public boolean validateCard(CardBase card) {
        CardBase firstCard = mStartingPlayer.getPlayedCard();
        if (firstCard.getSuit() == Suit.JOKER) {//if first card is joker,
            JokerCard jCard = (JokerCard) firstCard;

            if (card.getSuit() == Suit.JOKER) {// if currently played card is joker, than it can be played
                return true;
            } else {//if not joker then if joker says take, possible cards are kozir suit and said suit

                if (getMe().hasSuit(mKozir.getSuit()) || getMe().hasSuit(jCard.getSaidSuit())) {//tu me maqvs es cvetebi
                    if (card.getSuit() == mKozir.getSuit() || card.getSuit() == jCard.getSaidSuit()) {
                        if (jCard.isMustTakeOrGive()) {//true=vishe
                            if (getMe().isCardBiggestOfSuit(card)) {
//anu jokerma acxada vishe, me vamocmeb rom udidesi am cvetis vitamasho
                                return true;
                            }
                            return false;
                        } else {
                            return true;//tu caigos acxada jokerma, mashin am cvetebshi nebismieri
                        }
                    } else {
                        return false;//tu am cvetis kartebi maqvs , magram sxva ceti vitamashe
                    }
                } else {//tu ar maqvs mashin nebismieris tamashi shemidzlia
                    return true;
                }
            }
        } else {//if card is ordinary,then joker always can be played, and otherwise it must be same suit as starting card or kozir
            if (card.getSuit() == Suit.JOKER)
                return true;
            else {
                if (getMe().hasSuit(mKozir.getSuit()) || getMe().hasSuit(firstCard.getSuit())) {//tu me maqvs es cvetebi
                    if (card.getSuit() == firstCard.getSuit() || card.getSuit() == mKozir.getSuit()) {
                        return true;
                    }
                    return false;
                } else {
                    return true;
                }
            }
        }
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

      /*  try {*/
        if (mRoundPlayers != null) {
            for (int i = 0; i < mRoundPlayers.size(); i++) {
                if (mRoundPlayers.get(i).getPlayer().getId() == id)
                    return mRoundPlayers.get(i)/*.clone()*/;
            }

        }
       /* } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }*/
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
        return mCurrentPlayer/*.clone()*/;
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

    public CardBase getKozir() {
        return mKozir;
    }

    public void setKozir(CardBase base) {
        mKozir = base;
    }

    void setStatus(RoundStatus status) {//from containing  package
        mStatus = status;
    }

    public RoundPlayer getStartingPlayer() {
        return mStartingPlayer;
    }

    public void reset() {
        for (RoundPlayer player : mRoundPlayers) {
            player.setSaid(0);
            player.setTaken(0);
            player.setCardsOnHand(null);
            player.setPlayedCard(null);
        }
        mKozir = null;
    }

    @Override
    public Round clone() throws CloneNotSupportedException {
        return new Round(this);
    }


    public enum RoundStatus {
        ATUZVA, SAYING, PLAYING, FINISHED
    }
}
