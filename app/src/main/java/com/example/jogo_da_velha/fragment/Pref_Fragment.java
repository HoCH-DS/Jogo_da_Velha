package com.example.jogo_da_velha.fragment;

import android.os.Bundle;
import androidx.preference.PreferenceFragmentCompat;
import com.example.jogo_da_velha.R;



public class Pref_Fragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences);
    }
}