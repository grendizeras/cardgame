package ge.cardgame.jokapp.common;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.multiplayer.Invitation;
import com.google.android.gms.games.multiplayer.OnInvitationReceivedListener;
import com.google.android.gms.games.multiplayer.ParticipantUtils;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig;
import com.google.android.gms.games.multiplayer.turnbased.OnTurnBasedMatchUpdateReceivedListener;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatchConfig;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMultiplayer;
import com.google.example.games.basegameutils.GameHelper;

/**
 * Created by Giorgi on 5/10/2015.
 */
public class MatchHelper implements OnTurnBasedMatchUpdateReceivedListener, OnInvitationReceivedListener,
        ResultCallback<TurnBasedMultiplayer.InitiateMatchResult> ,TurnBasedMultiplayer.UpdateMatchResult{
    public static final int PLAYERS_COUNT = 2;
    TurnBasedMatch mMatch;
    GameHelper mHelper;


    public MatchHelper(GameHelper helper) {
        mHelper = helper;
    }

    public void createMatch() {
        Log.i("MATCH_CREATED", "CREATED");
        Bundle criteria = RoomConfig.createAutoMatchCriteria(PLAYERS_COUNT, PLAYERS_COUNT, 0);
        TurnBasedMatchConfig config = TurnBasedMatchConfig.builder().setAutoMatchCriteria(criteria).build();
        Games.TurnBasedMultiplayer.createMatch(mHelper.getApiClient(), config).setResultCallback(this);

    }

    @Override
    public void onInvitationReceived(Invitation invitation) {

    }

    @Override
    public void onInvitationRemoved(String s) {

    }

    @Override
    public void onTurnBasedMatchReceived(TurnBasedMatch turnBasedMatch) {
        Log.i("TurnBasedMatchReceived", turnBasedMatch.toString());
    }

    @Override
    public void onTurnBasedMatchRemoved(String s) {

    }


    @Override
    public void onResult(TurnBasedMultiplayer.InitiateMatchResult initiateMatchResult) {
        Log.i("onResult", initiateMatchResult.toString());
        mMatch = initiateMatchResult.getMatch();
        byte[] data = mMatch.getData();
        Log.i("TURN STATUS", mMatch.getTurnStatus() + "");
        Log.i("MATCH_STATUS", mMatch.getStatus() + "");

        if (data == null) {//just started
            Log.i("MATCH_DATA", "null");
            Games.TurnBasedMultiplayer.takeTurn(mHelper.getApiClient(), mMatch.getMatchId(), "somthing".getBytes(), null);
        }
        else{
            Log.i("MATCH_DATA", new String(data));
        }

    }

    @Override
    public TurnBasedMatch getMatch() {
        return null;
    }

    @Override
    public Status getStatus() {
        return null;
    }
}
