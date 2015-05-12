package game.messages;

/**
 * Created by Giorgi on 5/12/2015.
 */
public class MatchMessage {

    private Type mType;
    private Object mData;
    private String mPLayersTurn;


    public Type getType() {
        return mType;
    }

    public Object getDate() {
        return mData;
    }

    public String getWhosTurn() {
        return mPLayersTurn;
    }

    public enum Type {
        GAME_STARTED, ATUZVA, CXADEBA, DARIGEBA, PLAY
    }
}
