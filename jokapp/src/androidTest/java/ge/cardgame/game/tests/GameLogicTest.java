package ge.cardgame.game.tests;

import android.test.suitebuilder.annotation.MediumTest;
import android.util.Log;

import junit.framework.TestCase;

import game.GameMatch;
import game.Player;
import game.Round;
import game.RoundPlayer;
import game.cards.Card;
import game.cards.CardBase;
import game.cards.Face;
import game.cards.JokerCard;
import game.cards.Suit;

/**
 * Created by Giorgi on 5/16/2015.
 */
public class GameLogicTest extends TestCase {

    Player[] players;
    Round round;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        players = new Player[4];
        Player player = new Player("00000", null);
        players[0] = player;
        player = new Player("00001", null);
        players[1] = player;
        player = new Player("00002", null);
        players[2] = player;
        player = new Player("00003", null);
        players[3] = player;

        round = new Round(players);
        round.startRound();

        round.getRoundPlayers().get(0).setCardsOnHand(new CardBase[]{new Card(Suit.CLUBS, Face.ACE)});
        round.getRoundPlayers().get(1).setCardsOnHand(new CardBase[]{new Card(Suit.DIAMONDS, Face.QUEEN)});
        round.getRoundPlayers().get(2).setCardsOnHand(new CardBase[]{new Card(Suit.CLUBS, Face.KING)});
        round.getRoundPlayers().get(3).setCardsOnHand(new CardBase[]{new JokerCard(){{setJoker(true);}}});
        round.setKozir(new JokerCard());

    }


    @MediumTest
    public void testSaying2() throws Exception {
        Round temp = round.clone();
        temp.setRoundNumber(22);
        RoundPlayer player = round.getCurrentPlayer();
        GameMatch.mMyId = player.getPlayer().getId();

        boolean result = temp.say(4);
        assertEquals(true, result);
        GameMatch.mMyId = temp.getCurrentPlayer().getPlayer().getId();
        result = temp.say(5);
        assertEquals(true, result);


        GameMatch.mMyId = temp.getCurrentPlayer().getPlayer().getId();
        result = temp.say(0);

        assertEquals(true, result);


        GameMatch.mMyId = temp.getCurrentPlayer().getPlayer().getId();
        result = temp.say(0);

        assertEquals(false, result);


        GameMatch.mMyId = temp.getCurrentPlayer().getPlayer().getId();
        result = temp.say(1);

        assertEquals(true, result);

    }

    @MediumTest
    public void testPlay() throws Exception {
        boolean result;
        Round temp = round.clone();
        temp.setKozir(new Card(Suit.CLUBS,Face.TEN));
        GameMatch.mMyId = round.getCurrentPlayer().getPlayer().getId();
        Log.i("CARD_PLAYER",GameMatch.mMyId);
        CardBase card=temp.getCurrentPlayer().getCardsOnHand()[0];
        Log.i("CARD_Card",card.toString());
        result= temp.play(card);
        assertEquals(true,result);

        GameMatch.mMyId = temp.getCurrentPlayer().getPlayer().getId();
        Log.i("CARD_PLAYER",GameMatch.mMyId);
        card=temp.getCurrentPlayer().getCardsOnHand()[0];
        Log.i("CARD_Card",card.toString());
        temp.play(card);
        assertEquals(true, result);

        GameMatch.mMyId = temp.getCurrentPlayer().getPlayer().getId();
        Log.i("CARD_PLAYER",GameMatch.mMyId);
        card=temp.getCurrentPlayer().getCardsOnHand()[0];
        Log.i("CARD_Card",card.toString());
        temp.play(card);
        assertEquals(true, result);

        GameMatch.mMyId = temp.getCurrentPlayer().getPlayer().getId();
        Log.i("CARD_PLAYER",GameMatch.mMyId);
        card=temp.getCurrentPlayer().getCardsOnHand()[0];
        Log.i("CARD_Card",card.toString());
        temp.play(card);
        assertEquals(true, result);

        assertEquals(temp.getStartingPlayer(),temp.findPlayer("00003"));

    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();

    }


}
