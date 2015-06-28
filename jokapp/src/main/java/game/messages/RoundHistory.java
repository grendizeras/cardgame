package game.messages;

import java.util.ArrayList;

import game.Player;
import game.Round;

/**
 * Created by Giorgi on 6/28/2015.
 */
public class RoundHistory {


    ArrayList<RoundHistoryItem[]> mHistory=new ArrayList<>();

    public RoundHistory(){
//make default sequence of players.
    }


    public void addItem(Round round) {
//when adding items, add according to sequence.
    }

    public  class RoundHistoryItem {

        private Player mPlayer;
        private int mSaid;
        private int mTaken;


        public Player getPlayer() {
            return mPlayer;
        }

        public int getSaid() {
            return mSaid;
        }

        public int getTaken() {
            return mTaken;
        }
    }
}
