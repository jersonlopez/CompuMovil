package co.edu.udea.compumovil.gr04_20172.lab4;



import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class Profile_Fragment extends Fragment implements View.OnClickListener {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference ref;
    Customer user;

    Context context;
    private int type;
    private Button btnUpdate;
    String email;
    Cursor cursor;
    TextView nameProfile=null, lastnameProfile=null, emailProfile=null, genderProfile=null, phoneProfile=null;
    ImageView photo;



    public Profile_Fragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        email = getActivity().getIntent().getStringExtra("email");
        type = getActivity().getIntent().getIntExtra("type", 5);
        firebaseDatabase = FirebaseDatabase.getInstance();
        ref = firebaseDatabase.getReference("Customer");
        //Toast.makeText(getActivity(), email, Toast.LENGTH_SHORT).show();

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile_, container,false);
        nameProfile = v.findViewById(R.id.textViewNameProfile);
        lastnameProfile = v.findViewById(R.id.textViewLastnameProfile);
        emailProfile = v.findViewById(R.id.textViewEmailProfile);
        genderProfile = v.findViewById(R.id.textViewGender);
        phoneProfile = v.findViewById(R.id.textViewNumberphoneProfile);
        photo = v.findViewById(R.id.imageViewProfile);
        btnUpdate = v.findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(this);

        ref.orderByChild("id").equalTo(email).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("Aviso","¿Entró?");
                user = dataSnapshot.getValue(Customer.class);
                nameProfile.setText(user.getUsername());
                phoneProfile.setText(String.valueOf(user.getNumberphone()));
                lastnameProfile.setText(user.getUserlastname());
                emailProfile.setText(user.getId());
                genderProfile.setText(user.getGender());
                Picasso.with(getContext()).load(user.getPhoto()).into(photo);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //Toast.makeText(getActivity(), "acabe en el perfil", Toast.LENGTH_SHORT).show();
        return v;
    }

    @Override
    public void onClick(View view) {
        String name, lastname, uri;
        name = getActivity().getIntent().getStringExtra("name");
        lastname = getActivity().getIntent().getStringExtra("lastname");
        uri = getActivity().getIntent().getStringExtra("photo");
        Intent intentToEdit = new Intent(getActivity(), EditProfile.class);
        intentToEdit.putExtra("email", email);
        intentToEdit.putExtra("name", name);
        intentToEdit.putExtra("lastname", lastname);
        intentToEdit.putExtra("photo", uri);
        intentToEdit.putExtra("type", type);
        getActivity().startActivity(intentToEdit);
    }
}
