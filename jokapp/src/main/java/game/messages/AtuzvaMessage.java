package game.messages;

import game.cards.CardBase;

/**
 * Created by Giorgi on 5/12/2015.
 */
public class AtuzvaMessage  {
    CardBase[] mCards;
    String[] mOrderedPlayers;

    public CardBase[] getCards() {
        return mCards;
    }

    public String[] getOrderedPlayers() {
        return mOrderedPlayers;
    }
}
