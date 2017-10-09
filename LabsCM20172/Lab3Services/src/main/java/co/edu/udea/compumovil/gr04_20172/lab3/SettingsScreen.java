package co.edu.udea.compumovil.gr04_20172.lab3;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v14.preference.SwitchPreference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.widget.Toast;

/**
 * Created by jerson.lopez on 22/09/17.
 */

public class SettingsScreen extends PreferenceFragmentCompat {

    SwitchPreference sw;
    String email, state;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
        sw = (SwitchPreference)findPreference("swicthSession");
        state = sw.getSwitchTextOn().toString();

    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

        email = getActivity().getIntent().getStringExtra("email");
        Toast.makeText(getActivity(), state, Toast.LENGTH_SHORT).show();

    }
}
