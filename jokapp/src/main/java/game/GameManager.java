package game;

import game.messages.MatchMessage;

/**
 * Created by Giorgi on 5/12/2015.
 */
public class GameManager implements Connector.ConnectorCallback {
    private static GameManager mManager;
    private Connector mConnector;
    private GameMatch mMacth;

    public GameManager(Connector connector) {
        mConnector = connector;
        mConnector.setConnectorCallback(this);
        mManager = this;
    }

    public Connector getConnector() {
        return mConnector;
    }

    public static GameManager getCurrent() {
        return mManager;
    }

    @Override
    public void onMatchStarted(MatchMessage message) {

    }

    @Override
    public void onMatchUpdated(MatchMessage message) {
        mMacth.propagadeMessage(message);
    }

    @Override
    public void onMatchFinished(MatchMessage message) {

    }
}
