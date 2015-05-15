package game.cards;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Giorgi on 5/15/2015.
 */
public class CardDeck {
    private final CardBase mCards[];
    int suits;
    int faces;
    int length;

    public CardDeck() {
        suits = Suit.values().length;
        faces = Face.values().length;
        length = (suits - 1) * faces + 2;
        mCards = new CardBase[length];//2 jokers
        for (int i = 0; i < suits - 1; i++) {
            Suit suit = Suit.valueOf(i);
            for (int k = 0; k < faces; k++) {
                Face face = Face.valueOf(k);
                mCards[k + i * faces] = new Card(suit, face);
            }
        }
        mCards[length - 2] = new JokerCard();
        mCards[length - 1] = new JokerCard();

    }

    public CardBase[] shuffle() {
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
