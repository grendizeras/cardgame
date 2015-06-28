package ge.cardgame.jokapp.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import game.Connector;
import game.GameMatch;
import game.Player;
import game.Round;
import game.RoundPlayer;
import game.UIConnectorPresenter;
import game.cards.CardBase;
import ge.cardgame.jokapp.R;
import ge.cardgame.jokapp.controls.CardButton;

public class MatchActivity extends ActionBarActivity implements UIConnectorPresenter, Connector.ConnectorCallback {
    GameMatch mMatch;
    LinearLayout mMyCardsLayout;
    HashMap<String, ArrayList<CardButton>> playerCards = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_match);
        Player[] players = new Player[4];
        players[0] = new Player("0001", "giorgi1");
        players[1] = new Player("0002", "giorgi2");
        players[2] = new Player("0003", "giorgi3");
        players[3] = new Player("0004", "giorgi4");

        if (mMatch == null) {
            mMatch = new GameMatch("0001", this, new Connector() {
                @Override
                public void updateMatch(Round round) {

                }

                @Override
                public void setConnectorCallback(ConnectorCallback callback) {

                }
            });
            mMatch.mPlayers = players;
        }
        mMyCardsLayout = ((LinearLayout) findViewById(R.id.ll_my_cards));
        mMatch.startMatch();


      /*  mMyCardsLayout.addView(new CardButton(this, new Card(Suit.CLUBS, Face.ACE)));
        mMyCardsLayout.addView(new CardButton(this, new Card(Suit.CLUBS, Face.KING)));
        mMyCardsLayout.addView(new CardButton(this, new Card(Suit.HEARTS, Face.KING)));
        mMyCardsLayout.addView(new CardButton(this, new Card(Suit.DIAMONDS, Face.SIX)));
        mMyCardsLayout.addView(new CardButton(this, new Card(Suit.SPADES, Face.TEN)));
        mMyCardsLayout.addView(new CardButton(this, new Card(Suit.DIAMONDS, Face.NINE)));
        mMyCardsLayout.addView(new CardButton(this, new JokerCard()));*/
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

    @Override
    public void onMatchUpdated(Round round, Object extraParam) {
        try {
            switch (round.getStatus()) {
                case ATUZVA:
                    break;
                case SAYING: {
                    RelativeLayout rl = (RelativeLayout) findViewById(R.id.asd);
                    if (round.getKozir() != null)
                        rl.addView(new CardButton(this, round.getKozir()));
                    for (RoundPlayer player : round.getRoundPlayers()) {
                        generatePlayerCardButtons(player);
                    }
                    populatePlayerData(round);
                    break;
                }

                case PLAYING: {
                    populatePlayerData(mMatch.getRound());

                }
                break;
            }
            Log.i("onMatchUpdated", round.getStatus().toString());
        } catch (Exception ex) {
            Log.e("onMatchUpdated", ex.toString());
        }
    }

    private void showSayingDialog(final int max) throws Exception {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);

        final EditText tv = new EditText(this);
        tv.setMinHeight(50);
        tv.setText("0");
        tv.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(tv);
        builder.setPositiveButton("ok", null
        );
        final RoundPlayer thisPlayer = mMatch.getRound().getCurrentPlayer();
        final AlertDialog ad = builder.create();
        ad.show();
        ad.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String text = tv.getText().toString().trim();
                    if (!TextUtils.isEmpty(text)) {
                        int said = Integer.valueOf(tv.getText().toString());
                        if (mMatch.makeMove(said, 0)) {
                            mMatch.commit();
                            logActivity(thisPlayer, true);

                            ad.dismiss();
                        } else {
                            ad.dismiss();
                            showSayingDialog(max);
                        }
                    }
                } catch (Exception ex) {
                    Log.e("ERROR", ex.toString());
                }
            }
        });
    }

    private void populatePlayerData(final Round round) throws Exception {
        mMyCardsLayout.removeAllViews();
        final RoundPlayer thisPlayer = round.getCurrentPlayer();
        for (CardButton btn : playerCards.get(thisPlayer.getPlayer().getId())) {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        CardButton btn = (CardButton) v;
                        btn.setOnClickListener(null);

                        CardBase cb = btn.getCard();
                        if (mMatch.makeMove(cb, 1)) {
                            mMatch.commit();
                            if (btn.getParent() != null)
                                ((ViewGroup) btn.getParent()).removeView(btn);
                            ((FrameLayout) findViewById(R.id.fl_cards_on_table)).addView(btn);
                            generatePlayerCardButtons(thisPlayer);
                            logActivity(thisPlayer, false);
                            // populatePlayerData(round);
                        }
                    } catch (Exception ex) {
                        Log.e("CardMove", ex.toString());
                    }
                }
            });
            mMyCardsLayout.addView(btn);
        }
        ((TextView) findViewById(R.id.tv_user_name)).setText(round.getCurrentPlayer().getPlayer().getName());
        if (round.getStatus() == Round.RoundStatus.SAYING)
            showSayingDialog(round.getCardCount());
    }

    private void generatePlayerCardButtons(RoundPlayer player) {
        ArrayList<CardButton> cards = new ArrayList<>();
        if (playerCards.get(player.getPlayer().getId()) != null)
            playerCards.get(player.getPlayer().getId()).clear();
        if (player.getCardsOnHand() != null) {
            for (CardBase card : player.getCardsOnHand()) {
                cards.add(new CardButton(this, card));
            }
            playerCards.put(player.getPlayer().getId(), cards);
        }
    }

    private void logActivity(RoundPlayer player, boolean saying) {
        try {
            TextView tv = (TextView) findViewById(R.id.log);
            StringBuilder builder = new StringBuilder();
            if (saying) {
                builder.append(player.getPlayer().getName()).append(" said ").append(player.getSaid());
            } else {
                builder.append(player.getPlayer().getName()).append(" took ").append(player.getTaken()).append("; said").append(player.getSaid());
            }
            builder.append("\n");
            tv.setText(tv.getText().toString() + builder.toString());
        } catch (Exception ex) {
            Log.e("logiing error", ex.toString(), ex);
        }
    }

    @Override
    public void onPlayerJoined(Player player) {

    }

    @Override
    public Connector.ConnectorCallback getUIConnector() {
        return this;
    }
}
