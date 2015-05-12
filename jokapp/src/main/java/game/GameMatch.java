package game;

import game.messages.MatchMessage;

/**
 * Created by Giorgi on 5/12/2015.
 */
public class GameMatch {
    Player[] mPlayers;
    GameLogic mLogic;
    //HistoryTable

    public void propagadeMessage(MatchMessage message) {
        mLogic.processMatchMessage(message);
    }
}
