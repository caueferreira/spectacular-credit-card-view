package br.com.poke.spectacularcard;

/**
 * Created by Cauê Ferreira on 5/10/15.
 */

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

/**
 * Formats the watched EditText to a credit card number
 */
//http://stackoverflow.com/questions/11790102/format-credit-card-in-edit-text-in-android
public class FourDigitCardFormatWatcher implements TextWatcher {

    // Change this to what you want... ' ', '-' etc..
    private static final char space = ' ';

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        // Remove spacing char
        if (s.length() > 0 && (s.length() % 5) == 0) {
            final char c = s.charAt(s.length() - 1);
            if (space == c) {
                s.delete(s.length() - 1, s.length());
            }
        }
        // Insert char where needed.
        if (s.length() > 0 && (s.length() % 5) == 0) {
            char c = s.charAt(s.length() - 1);
            // Only if its a digit where there should be a space we insert a space
            if (Character.isDigit(c) && TextUtils.split(s.toString(), String.valueOf(space)).length <= 3) {
                s.insert(s.length() - 1, String.valueOf(space));
            }
        }
    }
}