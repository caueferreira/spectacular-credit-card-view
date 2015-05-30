package br.com.poke.spectacularcard;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

/**
 * Created by Cauê Ferreira on 5/17/15.
 */
public class ExpirationDateFormatWatcher implements TextWatcher {

    // Change this to what you want... ' ', '-' etc..
    private static final char slash = '/';

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() == 3 && !s.toString().substring(s.length()-1, s.length()).equals("/")){
            s.insert(s.length() - 1, String.valueOf(slash));
        } else if(s.length() == 3 && s.toString().substring(s.length()-1, s.length()).equals("/")){
            s.delete(s.length()-1, s.length());
        }
    }
}
