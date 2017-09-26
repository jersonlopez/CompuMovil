package co.edu.udea.compumovil.gr04_20172.lab2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dairo.garcia on 21/09/17.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ApartmentViewHolder> implements View.OnClickListener{

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
        holder.apartmentType.setText(apartments.get(position).getType());
        holder.apartmentValue.setText(apartments.get(position).getPrice());
        holder.apartmentArea.setText(apartments.get(position).getArea());
        holder.apartmentDescriptionShort.setText(apartments.get(position).getDescription());
        byte[] blob = apartments.get(position).getPhoto();
        Bitmap bitmap = BitmapFactory.decodeByteArray(blob,0,blob.length);
        holder.photov.setImageBitmap(bitmap);
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
        if (view != null){
            listener.onClick(view);
        }

    }


    public class ApartmentViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        TextView apartmentType;
        TextView apartmentValue;
        TextView apartmentArea;
        TextView apartmentDescriptionShort;
        ImageView photov;

        ApartmentViewHolder(View itemView) {
            super(itemView);
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
