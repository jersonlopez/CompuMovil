package co.edu.udea.compumovil.gr04_20172.lab3;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignOffFragment extends Fragment {
    Button buttonSignOut;

    public SignOffFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_sign_off, container, false );
        buttonSignOut = v.findViewById(R.id.buttonSignOut);

    return null;
    }


}
