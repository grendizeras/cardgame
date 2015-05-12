package game.cards;

/**
 * Created by Giorgi on 5/12/2015.
 */
public class Card extends CardBase {
    Face mFace;
   public Card(Suit suit,Face face){
       super(suit);
       mFace=face;
   }
}
