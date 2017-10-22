package co.edu.udea.compumovil.gr04_20172.lab4;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static co.edu.udea.compumovil.gr04_20172.lab4.ApartmentsDB.ColumnUser.email;


/**
 * A simple {@link Fragment} subclass.
 */
public class Apartment_Fragment extends Fragment implements View.OnClickListener {
    //private List<Apartment> apartments;
    //private RecyclerView.LayoutManager llm;
    private FirebaseDatabase database;
    private DatabaseReference ref;
    private Apartment apartment;

    FirebaseUser user;
    RecyclerView rv;
    RecyclerView.LayoutManager llm;
    ArrayList<Apartment> apartments;
    FloatingActionButton fab;
    private RVAdapter adapter;
    byte[] blob;
    int id;
    String email;
    Bitmap bitmap;
    private OnFragmentButtonListener mListener;


    public Apartment_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Apartment");
        user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null)
        {
            email = user.getEmail();
        }
        else
        {
            getActivity().finish();
            Intent toLogin= new Intent(getContext(), LoginActivity.class);
            startActivity(toLogin);
        }

        apartments = new ArrayList<>();

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                apartment = dataSnapshot.getValue(Apartment.class);
                //Toast.makeText(getActivity(), apartment.getUbication(), Toast.LENGTH_SHORT).show();

                String textType = apartment.getType();
                String textPrice = apartment.getPrice();
                String textArea = apartment.getArea();
                String textShort = apartment.getShortDescription();
                String textubication = apartment.getUbication();
                String texPhoto = apartment.getPhoto();
                String textLarge = apartment.getLargeDescription();

                //apartments.add(new Apartment("soy la foto", textType, textPrice, textArea, textShort, textubication, "hola a todos"));
                apartments.add(apartment);
                adapter.notifyDataSetChanged();

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

        //Toast.makeText(getActivity(), "apart F " + email, Toast.LENGTH_SHORT).show();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_apartment, container, false);

        fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Agregar apartamento", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intentToAdd = new Intent(getActivity(), Add_Apartment_Activity.class);
                intentToAdd.putExtra("email",email);
                getActivity().startActivity(intentToAdd);
            }
        });

        rv = (RecyclerView) v.findViewById(R.id.rv);
        llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        adapter = new RVAdapter(apartments);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = adapter.getItem(rv.getChildAdapterPosition(view)).getUbication();
                //Toast.makeText(getActivity(), id, Toast.LENGTH_SHORT).show();
                if(mListener!=null)
                {
                    mListener.onFragmentClickButton(id);
                }
            }
        });
        rv.setAdapter(adapter);
        setHasOptionsMenu(true);
        return v;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OnFragmentButtonListener)
        {
            mListener = (OnFragmentButtonListener) context;
        }
        else{
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentButtonListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {

    }

    public interface OnFragmentButtonListener{
        void onFragmentClickButton(String id);
    }

}
