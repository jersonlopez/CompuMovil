package co.edu.udea.compumovil.gr04_20172.lab2;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class Apartment_Fragment extends Fragment implements View.OnClickListener {
    //private List<Apartment> apartments;
    //private RecyclerView.LayoutManager llm;
    RecyclerView rv;
    RecyclerView.LayoutManager llm;
    DbHelper dbHelper;
    SQLiteDatabase db;
    byte[] blob;
    Bitmap bitmap;
    ImageView photo;


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
        //apartments.add(new Apartment(R.drawable.in1, "Finca", "90.000.000", "70 m2", "Es una casa bonita, estilo colonial"));
        //apartments.add(new Apartment(R.drawable.ic_menu_profile, "Segundo piso", "100.000.000", "90 m2", "Es una casa grande, estilo colonial"));

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
                bitmap = BitmapFactory.decodeByteArray(blob,0,blob.length);
                apartments.add(new Apartment("Finca", "900.000.000", "160 m2", "Es una finca grande, estilo colonial", "villa hermosa"));
                //photo.setImageBitmap(bitmap);

                Toast.makeText(getActivity(),"la imagen si guardo", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getActivity(), "no hay imagen", Toast.LENGTH_SHORT).show();
            }
            /*String consulta1 = "select " + ApartmentsDB.ColumnResource.photo + " from " + ApartmentsDB.entityResource + " where " + ApartmentsDB.ColumnResource.ubicationApartment+ "="+ "\"" + textubication + "\"" ;
            Cursor cursor1 = db.rawQuery(consulta1, null);
            byte[] blob = cursor1.getBlob(cursor1.getColumnIndex(ApartmentsDB.ColumnResource.photo));
            //Bitmap bitmap = BitmapFactory.decodeByteArray(blob,0,blob.length);
            //photo.setImageBitmap(bitmap)*/
            apartments.add(new Apartment(textType, textPrice, textArea, textShort, textubication));

        }
        RVAdapter adapter = new RVAdapter(apartments);
        rv.setAdapter(adapter);
        return v;

    }

    @Override
    public void onClick(View view) {

    }


    public interface OnFragmentInteractionListener {
        void onFragmentClickButton(int id);
    }

    private class TAG {
    }
}
