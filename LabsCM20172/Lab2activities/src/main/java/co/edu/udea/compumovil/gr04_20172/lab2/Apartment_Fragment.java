package co.edu.udea.compumovil.gr04_20172.lab2;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Apartment_Fragment extends Fragment {
    //private List<Apartment> apartments;
    //private RecyclerView.LayoutManager llm;
    RecyclerView rv;
    RecyclerView.LayoutManager llm;
    DbHelper dbHelper;
    SQLiteDatabase db;


    public Apartment_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_apartment, container, false);
        ArrayList<Apartment> apartments;

        rv = (RecyclerView) v.findViewById(R.id.rv);
        dbHelper = new DbHelper(getActivity()); //Instancia de DbHelper
        db = dbHelper.getWritableDatabase(); //Obtenemos la instancia de la BD

        llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        //LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        //RecyclerView.LayoutManager llm = new LinearLayoutManager(getActivity());
        //rv.setLayoutManager(llm);

        apartments = new ArrayList<>();
        apartments.add(new Apartment(R.drawable.in1, "Finca", "90.000.000", "70 m2", "Es una casa bonita, estilo colonial"));
        apartments.add(new Apartment(R.drawable.ic_menu_profile, "Segundo piso", "100.000.000", "90 m2", "Es una casa grande, estilo colonial"));
        apartments.add(new Apartment(R.drawable.ic_menu_signoff, "Finca", "900.000.000", "160 m2", "Es una finca grande, estilo colonial"));

        String consulta = "select * from " + ApartmentsDB.entityApartment;
        Cursor cursor = db.rawQuery(consulta, null);

        while (cursor.moveToNext()) {
            //Toast.makeText(getActivity(), cursor.getString(cursor.getColumnIndex(ApartmentsDB.ColumnApartment.areaApartment)), Toast.LENGTH_LONG).show();
            String textType =cursor.getString(cursor.getColumnIndex(ApartmentsDB.ColumnApartment.typeApartment));
            String textPrice =cursor.getString(cursor.getColumnIndex(ApartmentsDB.ColumnApartment.priceApartment));
            String textArea = cursor.getString(cursor.getColumnIndex(ApartmentsDB.ColumnApartment.areaApartment));
            String textShort = cursor.getString(cursor.getColumnIndex(ApartmentsDB.ColumnApartment.ShortDescriptionApartment));
            apartments.add(new Apartment(R.drawable.ic_menu_signoff, textType, textPrice, textArea, textShort));
        }

        RVAdapter adapter = new RVAdapter(apartments);
        rv.setAdapter(adapter);
        return v;

    }


    public interface OnFragmentInteractionListener {
        void onFragmentClickButton(int id);
    }

    private class TAG {
    }
}
