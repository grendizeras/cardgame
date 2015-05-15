package game;

/**
 * Created by Giorgi on 5/12/2015.
 */
public class GameLogic {
    private boolean mRoundFinished;

    public boolean processMove(Round round, String playerId, int type) {
        if (type == 0)
            return processPlay(round, playerId);
        else {
            return processSaying(round, playerId);
        }
    }

    public boolean isRoundFinished() {
        return mRoundFinished;
    }

    private boolean processSaying(Round round, String playerId) {

        return false;
    }

    private boolean processPlay(Round round, String playerId) {
        return false;
    }
}
