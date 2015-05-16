package game;

/**
 * Created by Giorgi on 5/12/2015.
 */
public class GameLogic {


    public boolean processMove(Round round, String playerId) {
        if (round.getStatus() == Round.RoundStatus.SAYING) {
            return processSaying(round, playerId);
        } else  {
            return processPlay(round, playerId);
        }
    }


    private boolean processSaying(Round round, String playerId) {

        return false;
    }

    private boolean processPlay(Round round, String playerId) {
        return false;
    }
}
