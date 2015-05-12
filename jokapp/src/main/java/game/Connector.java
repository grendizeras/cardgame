package game;

import game.messages.MatchMessage;

/**
 * Created by Giorgi on 5/12/2015.
 */
public interface Connector {
    void startMatch();

    void updateMatch();

    void finishMatch();

    void setConnectorCallback(ConnectorCallback callback);

    interface ConnectorCallback {
        void onMatchStarted(MatchMessage message);

        void onMatchUpdated(MatchMessage message);

        void onMatchFinished(MatchMessage message);
    }

}
