package co.edu.udea.compumovil.gr04_20172.lab2;


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

import java.io.ByteArrayInputStream;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDetail extends Fragment implements View.OnClickListener {
    private CollapsingToolbarLayout toolbar;
    SQLiteDatabase db;
    DbHelper dbHelper;
    String textubication1;
    Bitmap bitmap;
    Cursor cursor,cursor2,cursor3;
    TextView name, type, room, cost,area,ubication,description;
    ImageView image;
    Button map;


    public FragmentDetail() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        int id = bundle.getInt("id");
        String id1 = String.valueOf(id);
        //Toast.makeText(getActivity(), String.valueOf(id), Toast.LENGTH_SHORT).show();
        String consulta="select * from "+ ApartmentsDB.entityResource + " where Resource." + ApartmentsDB.ColumnResource.id +"="+id;
        dbHelper=new DbHelper(getActivity());
        db=dbHelper.getWritableDatabase();
        cursor= db.rawQuery(consulta,null);

        if(cursor.moveToNext()) {
            cursor.moveToFirst();
            textubication1 = cursor.getString(cursor.getColumnIndex(ApartmentsDB.ColumnResource.ubicationApartment));
            byte[] blob = cursor.getBlob(cursor.getColumnIndex(ApartmentsDB.ColumnResource.photo));
            bitmap = BitmapFactory.decodeByteArray(blob,0,blob.length);
            //Toast.makeText(getActivity(),textubication1, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getActivity(),"no traje nada", Toast.LENGTH_SHORT).show();
        }
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



        String consulta3 = "select * from " + ApartmentsDB.entityApartment+" where "+ApartmentsDB.ColumnApartment.ubicationApartment + "="+ "\"" +
                textubication1.toString() + "\"";
        cursor3= db.rawQuery(consulta3,null);
        if (cursor3.moveToNext()){
            cursor3.moveToFirst();

            String textType =cursor3.getString(cursor3.getColumnIndex(ApartmentsDB.ColumnApartment.typeApartment));
            String textRooms =cursor3.getString(cursor3.getColumnIndex(ApartmentsDB.ColumnApartment.roomsApartment));
            String textPrice =cursor3.getString(cursor3.getColumnIndex(ApartmentsDB.ColumnApartment.priceApartment));
            String textArea = cursor3.getString(cursor3.getColumnIndex(ApartmentsDB.ColumnApartment.areaApartment));
            String textLarge = cursor3.getString(cursor3.getColumnIndex(ApartmentsDB.ColumnApartment.LargeDescriptionApartment));

            toolbar.setTitle(textType);
            type.setText(textType);
            room.setText(textRooms);
            cost.setText(textPrice);
            area.setText(textArea);
            ubication.setText(textubication1);
            description.setText(textLarge);
            image.setImageBitmap(bitmap);
        }
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
