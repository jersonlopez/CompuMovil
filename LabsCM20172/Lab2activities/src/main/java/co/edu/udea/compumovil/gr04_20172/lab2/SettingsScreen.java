package co.edu.udea.compumovil.gr04_20172.lab2;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v14.preference.SwitchPreference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;
import android.widget.Toast;

/**
 * Created by jerson.lopez on 22/09/17.
 */

public class SettingsScreen extends PreferenceFragmentCompat {

private SwitchPreference estate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
        PreferenceManager preferenceManager = getPreferenceManager();

        //estate = (SwitchPreference) findPreference();
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

    }
}
