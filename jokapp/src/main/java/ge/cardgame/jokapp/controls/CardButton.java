package ge.cardgame.jokapp.controls;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.Button;

import game.cards.CardBase;
import ge.cardgame.jokapp.R;

/**
 * Created by Giorgi on 5/26/2015.
 */
public class CardButton extends Button{

    private CardBase mCard;

    public CardButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs,  R.attr.cardButtonStyle);
    }

    public CardButton(Context context, AttributeSet attrs) {
        super(context, attrs, R.attr.cardButtonStyle);
    }

    public CardButton(Context context,CardBase card) {
        super(context,null, R.attr.cardButtonStyle);
        mCard=card;
        int identifier=getResources().getIdentifier(card.toString().toLowerCase(),"drawable",getContext().getPackageName());
        setBackground(ContextCompat.getDrawable(getContext(),identifier));
    }


    @Override
    public Parcelable onSaveInstanceState() {
        return super.onSaveInstanceState();
    }
}
