package game;

import android.util.Log;

import game.cards.CardBase;
import game.messages.RoundHistory;

/**
 * Created by Giorgi on 5/12/2015.
 */
public class GameMatch implements Connector.ConnectorCallback {
    public Player[] mPlayers;
    private Round mCurrentRound;
    private Round mTempRound;
    private static boolean mIsMyTurn = true;
    public static String mMyId = "00001";
    public RoundHistory mHistory;
    //GameHistory

    UIConnectorPresenter mUIpresenter;
    Connector mOutput;

    /**
     * @param uiPresenter - the one that represents events to/from UI
     * @param output      - represents out world connection
     */
    public GameMatch(String myId, UIConnectorPresenter uiPresenter, Connector output) {
        mUIpresenter = uiPresenter;
        mOutput = output;
        mOutput.setConnectorCallback((Connector.ConnectorCallback) this);
        mMyId = myId;
    }

    public static String getMyId() {
        return mMyId;
    }

    public Round getRound() throws CloneNotSupportedException {
        return mCurrentRound.clone();
    }

    public static boolean isMyTurn() {
        return mIsMyTurn;
    }

    @Override
    public void onMatchUpdated(Round round, Object extraParam) {
        mIsMyTurn = (boolean) extraParam;
        mCurrentRound = round;
        mUIpresenter.getUIConnector().onMatchUpdated(round, null);//Update ui
    }

    @Override
    public void onPlayerJoined(Player player) {
        mUIpresenter.getUIConnector().onPlayerJoined(player);
    }


    public boolean makeMove(Object object, int type)throws Exception {//maybe better to use round status instead of type here. if say then say if play then play

        mTempRound = mCurrentRound;
        mMyId=mCurrentRound.getCurrentPlayer().getPlayer().getId();
        boolean result = false;

        if (type == 0) {//say
            result = mTempRound.say((int) object);
        } else {
            result = mTempRound.play((CardBase) object);
        }


        return result;

    }

    public void commit() {
        try {
            mCurrentRound = mTempRound;
            mTempRound = null;
            mOutput.updateMatch(mCurrentRound);
            mMyId = mCurrentRound.getCurrentPlayer().getPlayer().getId();
            mUIpresenter.getUIConnector().onMatchUpdated(mCurrentRound, 0);
        } catch (Exception ex) {
            Log.e("MatchMakeMove", ex.toString());
        }
    }


    public boolean startMatch() {
        mCurrentRound = new Round(mPlayers);
        mCurrentRound.startRound();
        mUIpresenter.getUIConnector().onMatchUpdated(mCurrentRound, null);
        return true;
    }

    public boolean joinPlayer(Player player) {
        return false;
    }


}
