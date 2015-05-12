package game.cards;

/**
 * Created by Giorgi on 5/12/2015.
 */
public enum Suit {
    SPADES,HEARTS,DIAMONDS,CLUBS,JOKER;

    public static Suit valueOf(Integer num){
        return values()[num];
    }
}
