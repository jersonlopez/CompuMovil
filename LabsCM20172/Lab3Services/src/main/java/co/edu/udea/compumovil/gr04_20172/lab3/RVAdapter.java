package co.edu.udea.compumovil.gr04_20172.lab3;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by jerson.lopez on 21/09/17.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ApartmentViewHolder> implements View.OnClickListener{

    // LINK DEL SERIVDOR
    private final String HOST_CODE = "https://ensuenoservices-jersonlopez.c9users.io";

    // COMPLEMENTOS
    private final String URL_CONTAINER_DOWN_COMPLEMENT = ":8080/api/Containers/all/download/";

    private final String URL_CONTAINER_DOWN = HOST_CODE.concat(URL_CONTAINER_DOWN_COMPLEMENT);

    private View.OnClickListener listener;
    private ArrayList<Apartment> apartments;

    @Override
    public ApartmentViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view, viewGroup, false);
        v.setOnClickListener(this);
        ApartmentViewHolder pvh = new ApartmentViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(ApartmentViewHolder holder, int position) {
        //holder.apartmentPhoto.setImageResource(apartments.get(position).photoId);
        /*holder.apartmentType.setText("casa linda");
        holder.apartmentValue.setText("1'200.000");
        holder.apartmentArea.setText("150 m2");*
        holder.apartmentDescriptionShort.setText("casa grande, bien ubicada...");*/
        //holder.photov.setImageResource(R.drawable.ic_menu_signoff);
        holder.apartmentType.setText(apartments.get(position).getType());
        holder.apartmentValue.setText(apartments.get(position).getPrice());
        holder.apartmentArea.setText(apartments.get(position).getArea());
        holder.apartmentDescriptionShort.setText(apartments.get(position).getDescription());
        holder.photov.setImageBitmap(apartments.get(position).getPhoto());
        /*byte[] blob = apartments.get(position).getPhoto();
        Bitmap bitmap = BitmapFactory.decodeByteArray(blob,0,blob.length);
        */
        /*String ubi = apartments.get(position).getUbitacion().replace(" ","");
        ubi =ubi.replace("#","");
        ubi =ubi.replace("-","");
        Glide.with(Apartment_Fragment.class)
                .load(URL_CONTAINER_DOWN.concat(ubi).concat(apartments.get(position).getPhotoapartment())).asBitmap()
                .into(photov);*/
    }



    @Override
    public int getItemCount() {
        return apartments.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    public void setOnClickListener(View.OnClickListener listener)
    {
        this.listener = listener;
    }

    public RVAdapter(ArrayList<Apartment> apartments){
        this.apartments = apartments;
    }

    @Override
    public void onClick(View view) {
        if (listener != null){
            listener.onClick(view);
        }

    }

    public Apartment getItem(int position)
    {
        return apartments.get(position);

    }



    public class ApartmentViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        TextView apartmentType;
        TextView apartmentValue;
        TextView apartmentArea;
        TextView apartmentDescriptionShort;
        ImageView photov;
        LinearLayout ly;

        public ApartmentViewHolder(View itemView) {
            super(itemView);
            /*ly = (LinearLayout) itemView.findViewById(R.id.layout);
            ly.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment fragment = new FragmentDetail();
                    Intent detail = new Intent(cv.getContext(), FragmentDetail.class);
                    startActivity(cv.getContext(),detail,null);


                }
            });*/
            cv = (CardView)itemView.findViewById(R.id.cv);
            apartmentType = (TextView)itemView.findViewById(R.id.apartment_type);
            apartmentValue = (TextView)itemView.findViewById(R.id.apartment_value);
            apartmentArea = (TextView)itemView.findViewById(R.id.apartment_area);
            apartmentDescriptionShort = (TextView)itemView.findViewById(R.id.apartment_description_short);
            photov = (ImageView)itemView.findViewById(R.id.apartment_photo);

        }


    }


    public void addApartment(Apartment apartment)
    {
        apartments.add(apartment);
        this.notifyDataSetChanged();
    }




}
