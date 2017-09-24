package co.edu.udea.compumovil.gr04_20172.lab2;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class ApartmentDetailFragment extends Fragment {
    private CollapsingToolbarLayout toolbar;
    SQLiteDatabase db;
    Cursor cursor,cursor2;
    TextView type, rooms, price,area,ubication,description;
    ImageView image;
    Button map;
/*
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        int id = bundle.getInt("id");
        Log.d("Alerta",String.valueOf(id));
        String consulta="select * from "+ ApartmentsDB.entityApartment + " where " + ApartmentsDB.ColumnApartment.ubicationApartment +"="+ id;
        DbHelper dbHelper=new DbHelper(getActivity());
        db=dbHelper.getWritableDatabase();
        cursor= db.rawQuery(consulta,null);
    }

    public ApartmentDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_apartment_detail, container, false);

        type=v.findViewById(R.id.);
        rooms=v.findViewById(R.id.room_apartment_detail);
        price=v.findViewById(R.id.cost_apartment_detail);
        area=v.findViewById(R.id.area_apartment_detail);
        ubication=v.findViewById(R.id.ubication_apartment_detail);
        description=v.findViewById(R.id.description_aparment_detail);
        map = v.findViewById(R.id.map_button);
        map.setOnClickListener(this););
    }
*/
}
