package br.com.poke.spectacularcard;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by Cauê Ferreira on 4/28/15.
 */
public class SpectacularCreditCardView extends RelativeLayout {

    private EditText ccEditText;
    private EditText cvvEditText;
    private EditText dateEditText;
    private ImageView brandImageView;

    private String creditCard;

    public SpectacularCreditCardView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.SpectacularCreditCardView,
                0, 0);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.spectacular_card_view, this, true);

        ccEditText = (EditText) findViewById(R.id.ccEditText);
        cvvEditText = (EditText) findViewById(R.id.cvvEditText);
        dateEditText = (EditText) findViewById(R.id.dateEditText);
        brandImageView = (ImageView) findViewById(R.id.brandImageView);

        cvvEditText.setVisibility(View.INVISIBLE);
        dateEditText.setVisibility(View.INVISIBLE);
        brandImageView.setVisibility(View.VISIBLE);

        ccEditText.addTextChangedListener(new FourDigitCardFormatWatcher());
        dateEditText.addTextChangedListener(new ExpirationDateFormatWatcher());

        ccEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(ccEditText.getText() != null && cvvEditText.getVisibility()!= View.VISIBLE )
                    if(quicklyBrand(ccEditText.getText().toString())!= -1){
                        brandImageView.setVisibility(View.VISIBLE);
                        brandImageView.setImageResource(quicklyBrand(ccEditText.getText().toString()));
                    }

                if (ccEditText.getText().toString().length() == 20) {
                    creditCard = ccEditText.getText().toString();
                    ccEditText.setText(creditCard.substring(0, 4));

                    LayoutParams layoutParams = new LayoutParams(pixelFromDensity(80), ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.addRule(RelativeLayout.ALIGN_TOP);
                    ccEditText.setLayoutParams(layoutParams);
                    cvvEditText.setVisibility(View.VISIBLE);

//                    ccEditText.setEnabled(false);
                    ccEditText.clearFocus();

                    cvvEditText.requestFocus();
                } else {
                    LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.addRule(RelativeLayout.ALIGN_TOP);
                    ccEditText.setLayoutParams(layoutParams);
                    cvvEditText.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        cvvEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (cvvEditText.getText().toString().length() == 3) {

                    LayoutParams layoutParams = new LayoutParams(pixelFromDensity(80), ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.addRule(RelativeLayout.ALIGN_TOP);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, ccEditText.getId());
                    layoutParams.addRule(RelativeLayout.END_OF, ccEditText.getId());

                    cvvEditText.setLayoutParams(layoutParams);
                    dateEditText.setVisibility(View.VISIBLE);

//                    cvvEditText.setEnabled(false);
                    cvvEditText.clearFocus();

                    dateEditText.requestFocus();
                } else {
                    dateEditText.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


//        cvvEditText.setOnKeyListener(new OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
//
//                if (keyCode == KeyEvent.KEYCODE_DEL) {
//                    //this is for backspace
//                    if (cvvEditText.getText().toString().length() == 0) {
//                        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                        layoutParams.addRule(RelativeLayout.ALIGN_TOP);
//                        ccEditText.setLayoutParams(layoutParams);
//                        cvvEditText.setVisibility(View.INVISIBLE);
//
//                        ccEditText.setEnabled(true);
//                        ccEditText.requestFocus();
//                        ccEditText.setText(creditCard);
//                    }
//                }
//                return false;
//            }
//        });
//
//        dateEditText.setOnKeyListener(new OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
//
//                if(keyCode == KeyEvent.KEYCODE_DEL){
//                    //this is for backspace
//                    if(dateEditText.getText().toString().length() == 0){
//                        dateEditText.setVisibility(View.INVISIBLE);
//                        cvvEditText.setEnabled(true);
//                        cvvEditText.requestFocus();
//                    }
//                }
//                return false;
//            }
//        });
    }

    private int quicklyBrand(final String ccNumber){
        if(ccNumber.length() < 2)
            return R.drawable.ic_no_brand;

        Integer range = Integer.valueOf(ccNumber.substring(0,2));

        if (range >= 40 && range <= 49) {
            return R.drawable.ic_visa;
        } else if (range >= 50 && range <= 59) {
            return R.drawable.ic_mastercard;
        } else if (range == 34 || range == 37) {
            return R.drawable.ic_amex;
        } else {
            return R.drawable.ic_no_brand;
        }
    }

    private int pixelFromDensity(final int density){
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (density * scale + 0.5f);
    }
}