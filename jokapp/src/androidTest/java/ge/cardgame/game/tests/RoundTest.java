package ge.cardgame.game.tests;

import android.test.suitebuilder.annotation.MediumTest;

import junit.framework.TestCase;

import game.Player;
import game.Round;
import game.RoundPlayer;
import game.cards.CardBase;
import game.cards.CardDeck;

/**
 * Created by Giorgi on 5/17/2015.
 */
public class RoundTest extends TestCase {
    Round round;
    Player[] players;

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
    }

    @MediumTest
    public void testInitialRound() {
        for (int i = 0; i < 4; i++) {
            RoundPlayer p = round.getRoundPlayers().get(i);
            assertEquals(9, p.getCardsOnHand().length);
        }
        assertEquals(0, round.getRoundNumber());
        assertEquals(round.getStatus(), Round.RoundStatus.ATUZVA);
    }

    @MediumTest
    public void testStartRound() throws Exception {
        round.startRound();
        for (int i = 0; i < 4; i++) {
            RoundPlayer p = round.getRoundPlayers().get(i);
            assertEquals(1, p.getCardsOnHand().length);
        }
        assertEquals(1, round.getRoundNumber());
        assertEquals(round.getStatus(), Round.RoundStatus.SAYING);
        assertNotNull(round.getCurrentPlayer());
        assertEquals(round.getRoundPlayers().get(0), round.getCurrentPlayer());


    }

    @MediumTest
    public void testRoundSay() {
        round.setRoundNumber(6);
        round.say(4);
        assertEquals(4, round.getMe().getSaid());
    }


    @MediumTest
    public void testSetPlayerCards() {
        CardBase[] cards = CardDeck.initDeck();
        round.setRoundNumber(5);
        round.setPlayerCards(cards);
        assertEquals(5, round.getRoundPlayers().get(0).getCardsOnHand().length);
        assertEquals(cards[4*round.getCardCount()],round.getKozir());
    }

    @MediumTest
    public void testGetCardCount() {
        round.setRoundNumber(0);
        int count = round.getCardCount();
        assertEquals(9, count);


        round.setRoundNumber(3);
        count = round.getCardCount();
        assertEquals(3, count);

        round.setRoundNumber(9);
        count = round.getCardCount();
        assertEquals(9, count);

        round.setRoundNumber(12);
        count = round.getCardCount();
        assertEquals(9, count);

        round.setRoundNumber(13);
        count = round.getCardCount();
        assertEquals(8, count);

        round.setRoundNumber(19);
        count = round.getCardCount();
        assertEquals(2, count);

        round.setRoundNumber(20);
        count = round.getCardCount();
        assertEquals(1, count);

        round.setRoundNumber(24);
        count = round.getCardCount();
        assertEquals(9, count);

    }


    public void reInitRound(){
        round=new Round(players);
        round.reset();
    }
}

