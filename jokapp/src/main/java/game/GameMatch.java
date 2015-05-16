package game;

/**
 * Created by Giorgi on 5/12/2015.
 */
public class GameMatch implements Connector.ConnectorCallback, Connector.UIConnectorCallback {
    // Player[] mPlayers;
    String mMyId;
    GameLogic mLogic;
    Round mCurrentRound;
    //GameHistory

    Connector mInput;
    Connector mOutput;

    /**
     * @param input  - the one that represents events to/from UI
     * @param output - represents out world connection
     */
    public GameMatch(Connector input, Connector output) {
        mInput = input;
        mOutput = output;
        mInput.setConnectorCallback((Connector.ConnectorCallback) this);
        mOutput.setConnectorCallback((Connector.UIConnectorCallback) this);
        mLogic = new GameLogic();
    }

    public Round getRound() throws CloneNotSupportedException {
        return mCurrentRound.clone();
    }

    @Override
    public void onMatchUpdated(Round round) {
        mCurrentRound = round;
        mInput.updateMatch(round);//Update ui
    }

    @Override
    public boolean onMove(Round round) {
        if (mLogic.processMove(round, mMyId)) {
            mOutput.updateMatch(round);//update central round object
            mCurrentRound=round;
            if(round.getStatus()== Round.RoundStatus.FINISHED){
                //add to game history
            }

            return true;
        }
        return false;//return result of logic. this should be used to notify ui that something is wring or everything is ok.
    }
}
