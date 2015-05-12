package game.messages;

import game.cards.CardBase;

/**
 * Created by Giorgi on 5/12/2015.
 */
public class DarigebaMessage {

    private CardPlayerPair[] mPlayerCards;
    private CardBase mKozir;

    public CardBase getKoziri() {
        return mKozir;
    }

    public CardPlayerPair[] getPlayerCards() {
        return mPlayerCards;
    }
}
