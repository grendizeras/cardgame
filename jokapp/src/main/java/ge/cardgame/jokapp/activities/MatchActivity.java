package ge.cardgame.jokapp.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import game.cards.Card;
import game.cards.Face;
import game.cards.JokerCard;
import game.cards.Suit;
import ge.cardgame.jokapp.R;
import ge.cardgame.jokapp.controls.CardButton;

public class MatchActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        LinearLayout ll_my_cards=((LinearLayout) findViewById(R.id.ll_my_cards));

        ll_my_cards.addView(new CardButton(this,new Card(Suit.CLUBS, Face.ACE)));
        ll_my_cards.addView(new CardButton(this, new Card(Suit.CLUBS, Face.KING)));
        ll_my_cards.addView(new CardButton(this, new Card(Suit.HEARTS, Face.KING)));
        ll_my_cards.addView(new CardButton(this,new Card(Suit.DIAMONDS, Face.SIX)));
        ll_my_cards.addView(new CardButton(this,new Card(Suit.SPADES, Face.TEN)));
        ll_my_cards.addView(new CardButton(this,new Card(Suit.DIAMONDS, Face.NINE)));
        ll_my_cards.addView(new CardButton(this,new JokerCard()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_match, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
