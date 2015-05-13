package game;

/**
 * Created by Giorgi on 5/12/2015.
 */
public class GameMatch implements Connector.ConnectorCallback {
    Player[] mPlayers;
    GameLogic mLogic;

    Connector mInput;
    Connector mOutput;

    /**
     * @param input  - the one that represents events to/from UI
     * @param output - represents out world connection
     */
    public GameMatch(Connector input, Connector output) {
        mInput = input;
        mOutput = output;
        mInput.setConnectorCallback(this);
        mOutput.setConnectorCallback(this);
    }

    @Override
    public void onMatchUpdated(Round round, boolean isInput) {
        processMessage(round, isInput);
    }


    /**
     * Main entry for processing round object
     *
     * @param round
     * @param isInput
     */
    private void processMessage(Round round, boolean isInput) {

        if (isInput) {
            //filter by gameLogic
            mOutput.updateMatch(round);//update central round object
        } else {
            mInput.updateMatch(round);//Update ui
        }

    }
    //HistoryTable

}
