package game;

/**
 * Shared object class, that represents current round.
 */
public class Round {

    private int mRoundNumber;//from 0 to 20
    //0 means needs atuzva
    private RoundPlayer[] mRoundPlayers;

    public int getRoundNumber() {
        return mRoundNumber;
    }

    public RoundPlayer[] getRoundPlayers() {
        return mRoundPlayers;
    }


}
