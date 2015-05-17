package game.cards;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Giorgi on 5/15/2015.
 */
public class CardDeck {
    private static CardBase mCards[];
    private static int suits;
    private static int faces;
    private static int length;

    public static CardBase[] initDeck() {

        suits = Suit.values().length;
        faces = Face.values().length;
        length = (suits - 1) * faces + 2;
        CardBase cards[]=new CardBase[length];
        for (int i = 0; i < suits - 1; i++) {
            Suit suit = Suit.valueOf(i);
            for (int k = 0; k < faces; k++) {
                Face face = Face.valueOf(k);
                cards[k + i * faces] = new Card(suit, face);
            }
        }
        cards[length - 2] = new JokerCard();
        cards[length - 1] = new JokerCard();
        return cards;
    }

    static {
        mCards=initDeck();
    }

    public static CardBase[] shuffle() {
        ArrayList<Integer> shuffledPos = new ArrayList<>();
        CardBase[] shuffledCards = new CardBase[length];
        Random random = new Random();
        while (shuffledPos.size() != length) {
            int rand = random.nextInt(length);
            if (!shuffledPos.contains(rand)) {
                shuffledPos.add(rand);
            }
        }
        for (int i = 0; i < length; i++) {
            shuffledCards[i] = mCards[shuffledPos.get(i)];
        }
        return shuffledCards;

    }


}
