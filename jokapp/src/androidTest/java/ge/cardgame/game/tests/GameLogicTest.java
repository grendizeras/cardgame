package ge.cardgame.game.tests;

import android.test.suitebuilder.annotation.MediumTest;
import android.test.suitebuilder.annotation.SmallTest;

import junit.framework.TestCase;

import game.GameLogic;
import game.Player;
import game.Round;
import game.RoundPlayer;

/**
 * Created by Giorgi on 5/16/2015.
 */
public class GameLogicTest extends TestCase {
    GameLogic mLogic;
    RoundPlayer[] mPlayers;
    Round mRound;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mPlayers=new RoundPlayer[4];
        for(int i=0;i<4;i++){
            final String id="0000"+i;
            RoundPlayer rPlayer=new RoundPlayer(new Player(id,"asd"));
            mPlayers[i]=rPlayer;
        }
      //  mRound=new Round(mPlayers);
        mLogic = new GameLogic();

    }

    @MediumTest
    public void testSaying(){
        mRound.setRoundNumber(1);
        mPlayers[0].setSaid(0);
        mPlayers[1].setSaid(1);
        mPlayers[2].setSaid(1);
        mPlayers[3].setSaid(1);
       // boolean result=mLogic.processMove(mRound,"00003");
      //  assertEquals(false,result);
    }


    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
