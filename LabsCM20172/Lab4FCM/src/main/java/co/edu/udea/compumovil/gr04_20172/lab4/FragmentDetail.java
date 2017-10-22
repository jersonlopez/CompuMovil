package co.edu.udea.compumovil.gr04_20172.lab4;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;

import static android.R.attr.value;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDetail extends Fragment implements View.OnClickListener {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference ref;
    Apartment value;


    private CollapsingToolbarLayout toolbar;
    String id;
    TextView type, room, cost,area,ubication,description;
    ImageView image;
    Button map;


    public FragmentDetail() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        id = bundle.getString("id");
        firebaseDatabase = FirebaseDatabase.getInstance();
        ref = firebaseDatabase.getReference("Apartment");
        //Toast.makeText(getActivity(), id, Toast.LENGTH_SHORT).show();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_detail, container, false);
        toolbar = (CollapsingToolbarLayout)v.findViewById(R.id.collapsing_toolbar);
        image=v.findViewById(R.id.placeImage);
        type=v.findViewById(R.id.type_apartment_detail);
        room=v.findViewById(R.id.room_apartment_detail);
        cost=v.findViewById(R.id.cost_apartment_detail);
        area=v.findViewById(R.id.area_apartment_detail);
        ubication=v.findViewById(R.id.ubication_aparment_detail);
        description=v.findViewById(R.id.description_aparment_detail);
        map = v.findViewById(R.id.map_button);
        map.setOnClickListener(this);

        ref.orderByChild("ubication").equalTo(id).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("Aviso","¿Entró?");
                value = dataSnapshot.getValue(Apartment.class);
                toolbar.setTitle(value.getType());
                type.setText(value.getType());
                room.setText(String.valueOf(value.getRooms()));
                cost.setText(value.getPrice());
                area.setText(value.getArea());
                ubication.setText(value.getUbication());
                description.setText(value.getLargeDescription());
                Picasso.with(getContext()).load(value.getPhoto()).into(image);
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
        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.map_button:
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:6.266953,-75.569111?z=30"));
                if (intent!=null)
                {
                    startActivity(intent);
                }
                break;
        }
    }

}
