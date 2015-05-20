package game;

import game.cards.CardBase;

/**
 * Created by Giorgi on 5/12/2015.
 */
public class GameMatch implements Connector.ConnectorCallback {
    private Player[] mPlayers;
    private Round mCurrentRound;
    private Round mTempRound;
    private static boolean mIsMyTurn = true;
    private static String mMyId="00001";

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


    public boolean makeMove(Object object, int type) {
        mTempRound = mCurrentRound;
        if (type == 0) {//say
            return mTempRound.say((int) object);
        } else {
            return mTempRound.play((CardBase) object);
        }
    }

    public void commit() {
        mCurrentRound = mTempRound;
        mTempRound = null;
        mOutput.updateMatch(mCurrentRound);
    }


    public boolean startMatch() {
        return false;
    }

    public boolean joinPlayer(Player player) {
        return false;
    }


}
