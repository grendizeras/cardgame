package ge.cardgame.jokapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.games.Games;
import com.google.android.gms.games.Player;
import com.google.example.games.basegameutils.GameHelper;

import java.util.Calendar;

import ge.cardgame.jokapp.R;
import ge.cardgame.jokapp.common.MatchHelper;


/**
 * Created by Giorgi on 5/10/2015.
 */

public class BaseActivity extends AppCompatActivity implements GameHelper.GameHelperListener {


    private final int RESULT_RESOLVE_CONN_FAILURE = 1000;

    private static BaseActivity mCurrentActivity;
    protected Toolbar mToolbar;
    protected Player mPlayer;
    protected GameHelper mHelper;
    protected MatchHelper mMatchHelper;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            mCurrentActivity = this;
            if (mHelper == null)
                getHelper().setup(this);
            mMatchHelper=new MatchHelper(getHelper());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onStart() {
        Log.i("onStart", Calendar.getInstance().toString());
        super.onStart();
        mCurrentActivity = this;
        getHelper().onStart(this);
    }

    @Override
    protected void onStop() {
        Log.i("onStop", Calendar.getInstance().toString());
        super.onStop();
        getHelper().onStop();
    }

    public GameHelper getHelper() {
        if (mHelper == null) {
            mHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
        }
        return mHelper;
    }

    public static BaseActivity current() {
        return mCurrentActivity;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getHelper().onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onSignInFailed() {

    }

    @Override
    public void onSignInSucceeded() {
        mPlayer = Games.Players.getCurrentPlayer(getHelper().getApiClient());
        ((TextView)findViewById(R.id.tv_me)).setText(mPlayer.getDisplayName());
    }
}
