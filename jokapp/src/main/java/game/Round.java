package game;

/**
 * Shared object class, that represents current round.
 */
public class Round implements Cloneable {

    private int mRoundNumber;//from 0 to 20
    //0 means needs atuzva
    private final  RoundPlayer[] mRoundPlayers;

    public Round(RoundPlayer[] players){
        mRoundPlayers=players;
    }
    private Round(Round source) throws CloneNotSupportedException{
        mRoundNumber=source.mRoundNumber;
        mRoundPlayers=new RoundPlayer[source.mRoundPlayers.length];
        for(int i=0;i<mRoundPlayers.length;i++){
            mRoundPlayers[i]=source.mRoundPlayers[i].clone();
        }

    }

    public int getRoundNumber() {
        return mRoundNumber;
    }

    public RoundPlayer[] getRoundPlayers() {
        return mRoundPlayers;
    }

    @Override
    protected Round clone() throws CloneNotSupportedException {
        return new Round(this);
    }
}
