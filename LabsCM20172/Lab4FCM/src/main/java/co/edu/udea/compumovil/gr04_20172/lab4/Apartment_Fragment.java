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

import java.util.ArrayList;
import java.util.List;

import static co.edu.udea.compumovil.gr04_20172.lab4.ApartmentsDB.ColumnUser.email;


/**
 * A simple {@link Fragment} subclass.
 */
public class Apartment_Fragment extends Fragment implements View.OnClickListener {
    //private List<Apartment> apartments;
    //private RecyclerView.LayoutManager llm;
    RecyclerView rv;
    RecyclerView.LayoutManager llm;
    ArrayList<Apartment> apartments;
    FloatingActionButton fab;
    private RVAdapter adapter;
    DbHelper dbHelper;
    SQLiteDatabase db;
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
        dbHelper = new DbHelper(getActivity());
        db = dbHelper.getWritableDatabase();
        email = getActivity().getIntent().getStringExtra("email");
        //Toast.makeText(getActivity(), "apart F " + email, Toast.LENGTH_SHORT).show();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_apartment, container, false);
        apartments = new ArrayList<>();

        String consulta = "select * from " + ApartmentsDB.entityApartment;
        Cursor cursor = db.rawQuery(consulta, null);

        while (cursor.moveToNext()) {
            //Toast.makeText(getActivity(), cursor.getString(cursor.getColumnIndex(ApartmentsDB.ColumnApartment.areaApartment)), Toast.LENGTH_LONG).show();
            String textType =cursor.getString(cursor.getColumnIndex(ApartmentsDB.ColumnApartment.typeApartment));
            String textPrice =cursor.getString(cursor.getColumnIndex(ApartmentsDB.ColumnApartment.priceApartment));
            String textArea = cursor.getString(cursor.getColumnIndex(ApartmentsDB.ColumnApartment.areaApartment));
            String textShort = cursor.getString(cursor.getColumnIndex(ApartmentsDB.ColumnApartment.ShortDescriptionApartment));
            String textubication = cursor.getString(cursor.getColumnIndex(ApartmentsDB.ColumnApartment.ubicationApartment));

            String consulta1 = "select * from " + ApartmentsDB.entityResource+" where "+ApartmentsDB.ColumnResource.ubicationApartment + "="+ "\"" +
                    textubication.toString()+ "\"" ;
            Cursor cursor1 = db.rawQuery(consulta1, null);
            if(cursor1.moveToNext())
            {
                blob = cursor1.getBlob(cursor1.getColumnIndex(ApartmentsDB.ColumnResource.photo));
                id = cursor1.getInt(cursor1.getColumnIndex(ApartmentsDB.ColumnResource.id));
                bitmap = BitmapFactory.decodeByteArray(blob,0,blob.length);
                apartments.add(new Apartment(bitmap,textType, textPrice, textArea, textShort, textubication, id));

                //Toast.makeText(getActivity(), String.valueOf(id), Toast.LENGTH_SHORT).show();
                //apartments.add(new Apartment("Finca", "900.000.000", "160 m2", "Es una finca grande, estilo colonial", "villa hermosa"));
                //photo.setImageBitmap(bitmap);
                //Toast.makeText(getActivity(),"la imagen si guardo", Toast.LENGTH_SHORT).show();
                //Toast.makeText(getActivity(),String.valueOf(id), Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getActivity(), "no hay imagen", Toast.LENGTH_SHORT).show();
            }

        }

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
                int id = adapter.getItem(rv.getChildAdapterPosition(view)).getId();
                //Toast.makeText(getActivity(), String.valueOf(id), Toast.LENGTH_SHORT).show();
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
        void onFragmentClickButton(int id);
    }

}
