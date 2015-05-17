package game;

/**
 * Created by Giorgi on 5/12/2015.
 */
public interface Connector {
    /**
     * This should be called by GameMatch, on appropriate Connector object, to notify endpoints or UI about event.
     *
     * @param round
     */
    void updateMatch(Round round);

    void setConnectorCallback(ConnectorCallback callback);


    /**
     * Callback interface to notify GameMatch about events in match.
     */
    interface ConnectorCallback {
        void onMatchUpdated(Round round,Object extraParam);
        void onPlayerJoined(Player player);
    }



}
