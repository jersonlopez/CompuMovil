package co.edu.udea.compumovil.gr04_20172.lab3;


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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Apartment_Fragment extends Fragment implements View.OnClickListener {

    // LINK DEL SERIVDOR
    private final String HOST_CODE = "https://ensuenoservices-jersonlopez.c9users.io";

    // COMPLEMENTOS
    private final String URL_APARTMENTS_COMPLEMENT = ":8080/api/Apartments/";
    private final String URL_CONTAINER_DOWN_COMPLEMENT = ":8080/api/Containers/all/download/";
    private final String URL_CONTAINER_UP_COMPLEMENT = ":8080/api/Containers/all/upload";

    private final String URL_APARTMENTS = HOST_CODE.concat(URL_APARTMENTS_COMPLEMENT);
    private final String URL_CONTAINER_DOWN = HOST_CODE.concat(URL_CONTAINER_DOWN_COMPLEMENT);
    private final String URL_CONTAINER_UP = HOST_CODE.concat(URL_CONTAINER_UP_COMPLEMENT);

    RecyclerView rv;
    RecyclerView.LayoutManager llm;
    ArrayList<Apartment> apartments;
    FloatingActionButton fab;
    private RVAdapter adapter;
    DbHelper dbHelper;
    SQLiteDatabase db;
    byte[] blob;
    int id = 0;
    Bitmap bitmap;
    ImageView imageView;
    private OnFragmentButtonListener mListener;


    public Apartment_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DbHelper(getActivity());
        db = dbHelper.getWritableDatabase();
        getApartment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_apartment, container, false);
        apartments = new ArrayList<>();


        /*String consulta = "select * from " + ApartmentsDB.entityApartment;
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

        }*/

        fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Agregar apartamento", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intentToAdd = new Intent(getActivity(), Add_Apartment_Activity.class);
                intentToAdd.putExtra("email", ApartmentsDB.ColumnUser.email);
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
                int id = adapter.getItem(rv.getChildAdapterPosition(view)).getId1();
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

    private void getApartment() {
        /*String id_Apartment = eUbication.getText().toString();
        if ("".equals(id_Apartment)){
            Toast.makeText(this, "Ingrese una Ubicación", Toast.LENGTH_SHORT).show();
            return;
        }*/

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL_APARTMENTS, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response){
                        Apartment apartment = new Gson().fromJson(response.toString(), Apartment.class);

                        String textType = apartment.getType();
                        String textPrice = apartment.getPrice();
                        String textArea = apartment.getArea();
                        String textShort = apartment.getShortdescriptionapartment();
                        String textubication = apartment.getId();

                        Glide.with(Apartment_Fragment.this)
                                .load(URL_CONTAINER_DOWN.concat(String.valueOf(apartment.getId())).concat(apartment.getPhotoapartment()))
                                .into(imageView);

                        imageView.buildDrawingCache();
                        bitmap = imageView.getDrawingCache();

                        apartments.add(new Apartment(bitmap,textType, textPrice, textArea, textShort, textubication, id));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Error consultando información", Toast.LENGTH_SHORT).show();
                        //Log.d("nada2",error.getMessage());
                    }
                }
        );
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(jsonObjectRequest);
    }


}
