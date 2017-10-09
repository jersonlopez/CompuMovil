package co.edu.udea.compumovil.gr04_20172.lab3;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


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
    Bitmap bitmap = null, photo;
    ImageView imageView;
    String ubi, foto, url, email;
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
        //getApartment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_apartment, container, false);
        apartments = new ArrayList<>();
        View v1 = inflater.inflate(R.layout.card_view, container, false);
        imageView = v1.findViewById(R.id.apartment_photo);
        apartments.clear();

        String consulta = "select * from " + ApartmentsDB.entityApartment;
        Cursor cursor = db.rawQuery(consulta, null);

        while (cursor.moveToNext()) {
            //Toast.makeText(getActivity(), cursor.getString(cursor.getColumnIndex(ApartmentsDB.ColumnApartment.areaApartment)), Toast.LENGTH_LONG).show();
            String textType =cursor.getString(cursor.getColumnIndex(ApartmentsDB.ColumnApartment.typeApartment));
            String textPrice =cursor.getString(cursor.getColumnIndex(ApartmentsDB.ColumnApartment.priceApartment));
            String textArea = cursor.getString(cursor.getColumnIndex(ApartmentsDB.ColumnApartment.areaApartment));
            String textShort = cursor.getString(cursor.getColumnIndex(ApartmentsDB.ColumnApartment.ShortDescriptionApartment));
            String textubication = cursor.getString(cursor.getColumnIndex(ApartmentsDB.ColumnApartment.ubicationApartment));
            //Toast.makeText(getActivity(),"@@@" +textubication, Toast.LENGTH_SHORT).show();
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
                intentToAdd.putExtra("email", email);
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

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL_APARTMENTS, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response){
                        Apartment[] apartment = new Gson().fromJson(response.toString(), Apartment[].class);

                        if (apartment != null){
                            for (int i = 0; i < apartment.length ; i++){
                                String textLarge = apartment[i].getLargedescriptionapartment();
                                String textType = apartment[i].getTypeapartment();
                                String textPrice = apartment[i].getPriceapartment();
                                String textArea = apartment[i].getAreaapartment();
                                String textShort = apartment[i].getShortdescriptionapartment();
                                String textUbication = apartment[i].getId();
                                String textRooms = apartment[i].getRoomsapartment();

                                Log.d("@@@", textType+"_"+textPrice+"-"+textArea+"_"+textUbication);
                                //Toast.makeText(getActivity(), textubication, Toast.LENGTH_SHORT).show();

                                ubi = textUbication.replace(" ","");
                                ubi =ubi.replace("#","");
                                ubi =ubi.replace("-","");
                                foto = apartment[i].getPhotoapartment();
                                url = URL_CONTAINER_DOWN.concat(ubi).concat(foto);

                                new Thread(new Runnable() {
                                    @Nullable
                                    private Bitmap loadImageFromNetwork(String url) {
                                        try {
                                            Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(url).getContent());
                                            return bitmap;
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        return null;
                                    }

                                    public void run() {
                                        final Bitmap bitmap = loadImageFromNetwork(url);
                                        imageView.post(new Runnable() {
                                            public void run() {
                                                imageView.setImageBitmap(bitmap);
                                            }
                                        });
                                    }
                                }).start();

                                bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                                blob = baos.toByteArray();

                                apartments.add(new Apartment(bitmap, textType, textPrice, textArea, textShort, textUbication, id));

                                /*ContentValues values1= new ContentValues();
                                ContentValues values2= new ContentValues();
                                values2.put(ApartmentsDB.ColumnApartment.ubicationApartment, textUbication);
                                values2.put(ApartmentsDB.ColumnApartment.typeApartment, textType);
                                values2.put(ApartmentsDB.ColumnApartment.priceApartment, textPrice);
                                values2.put(ApartmentsDB.ColumnApartment.areaApartment, textArea);
                                values2.put(ApartmentsDB.ColumnApartment.roomsApartment, textRooms);
                                values2.put(ApartmentsDB.ColumnApartment.ShortDescriptionApartment, textShort);
                                values2.put(ApartmentsDB.ColumnApartment.LargeDescriptionApartment, textLarge);
                                db.insertWithOnConflict(ApartmentsDB.entityApartment, null, values2, SQLiteDatabase.CONFLICT_IGNORE);

                                String consulta="select "+ ApartmentsDB.ColumnApartment.ubicationApartment +" from " +ApartmentsDB.entityApartment+ " where " + ApartmentsDB.ColumnApartment.typeApartment+"="+ "\"" + textType + "\"" +
                                        " and "+ApartmentsDB.ColumnApartment.priceApartment+"=" + "\"" + textPrice + "\"" + " and "+ApartmentsDB.ColumnApartment.roomsApartment+"=" + "\"" + textRooms + "\"" +
                                        " and "+ApartmentsDB.ColumnApartment.areaApartment+"=" + "\"" + textArea + "\"" + " and "+ApartmentsDB.ColumnApartment.ubicationApartment+"=" + "\"" +
                                        textUbication + "\"";
                                Cursor cursor3=db.rawQuery(consulta,null);

                                cursor3.moveToFirst();
                                String ubication = cursor3.getString(cursor3.getColumnIndex(ApartmentsDB.ColumnApartment.ubicationApartment));

                                Toast.makeText(getActivity(), "ubi "+ ubication, Toast.LENGTH_SHORT).show();
                                /*if(cursor3.moveToNext()){
                                    values1.put(ApartmentsDB.ColumnResource.photo, blob);
                                    values1.put(ApartmentsDB.ColumnResource.ubicationApartment, textUbication);
                                    db.insertWithOnConflict(ApartmentsDB.entityResource,null,values1,SQLiteDatabase.CONFLICT_IGNORE);
                                }*/


                            }
                            llm = new LinearLayoutManager(getActivity());
                            rv.setLayoutManager(llm);
                            adapter = new RVAdapter(apartments);
                            rv.setAdapter(adapter);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Error consultando información", Toast.LENGTH_SHORT).show();
                        Log.d("nada2",error.getMessage());
                    }
                }
        );
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(jsonObjectRequest);
    }

}