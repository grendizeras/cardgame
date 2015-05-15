package game;

/**
 * Shared object class, that represents current round.
 */
public class Round implements Cloneable {

    private int mRoundNumber;//from 0 to 20
    //0 means needs atuzva
    private final RoundPlayer[] mRoundPlayers;

    public Round(RoundPlayer[] players) {
        mRoundPlayers = players;
    }

    private Round(Round source) throws CloneNotSupportedException {
        mRoundNumber = source.mRoundNumber;
        mRoundPlayers = new RoundPlayer[source.mRoundPlayers.length];
        for (int i = 0; i < mRoundPlayers.length; i++) {
            mRoundPlayers[i] = source.mRoundPlayers[i].clone();
        }

    }

    public RoundPlayer findPlayer(String id) {

        try {
            if (mRoundPlayers != null) {
                for (int i = 0; i < mRoundPlayers.length; i++) {
                    if (mRoundPlayers[i].getPlayer().getId() == id)
                        return mRoundPlayers[i].clone();
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
                for (int i = 0; i < mRoundPlayers.length; i++) {

                    if (mRoundPlayers[i].getPlayer().getId() == currPlayerId) {
                        if (prevPlayer == null)//i am the first
                            return null;
                        else
                            return prevPlayer.clone();
                    } else
                        prevPlayer = mRoundPlayers[i];
                }

            }
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }


    public int getRoundNumber() {
        return mRoundNumber;
    }

    public RoundPlayer[] getRoundPlayers() {
        return mRoundPlayers;
    }

    @Override
    protected Round clone() throws CloneNotSupportedException {
        return new Round(this);
    }
}
