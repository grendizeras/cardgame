package game.cards;

import java.util.HashMap;

/**
 * Created by Giorgi on 5/12/2015.
 */
public enum Face {
    SIX(6),SEVEN(7),EIGHT(8),NINE(9),TEN(10),JACK(11),QUEEN(12),KING(13),ACE(14);
    int mRank;
    static HashMap<Integer,Face> map=new HashMap<>();
    static {
        for(int i=0;i<values().length;i++){
            Face face=values()[i];
            map.put(face.getRank(),face);
        }
    }
    Face(int rank){
        mRank=rank;
    }
    public int getRank(){
        return mRank;
    }

    public static Face valueOf(Integer rank){
        return map.get(rank);
    }
}
