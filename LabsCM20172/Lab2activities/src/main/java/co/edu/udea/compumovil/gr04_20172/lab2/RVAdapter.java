package co.edu.udea.compumovil.gr04_20172.lab2;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dairo.garcia on 21/09/17.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ApartmentViewHolder>{

    @Override
    public ApartmentViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view, viewGroup, false);
        ApartmentViewHolder pvh = new ApartmentViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(ApartmentViewHolder holder, int position) {
        holder.apartmentPhoto.setImageResource(apartments.get(position).photoId);
        holder.apartmentType.setText(apartments.get(position).type);
        holder.apartmentValue.setText(apartments.get(position).value);
        holder.apartmentArea.setText(apartments.get(position).area);
        holder.apartmentDescriptionShort.setText(apartments.get(position).description);

    }


    @Override
    public int getItemCount() {
        return apartments.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    ArrayList<Apartment> apartments;

    public RVAdapter(ArrayList<Apartment> apartments){
        this.apartments = apartments;
    }


    public class ApartmentViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        ImageView apartmentPhoto;
        TextView apartmentType;
        TextView apartmentValue;
        TextView apartmentArea;
        TextView apartmentDescriptionShort;

        ApartmentViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            apartmentType = (TextView)itemView.findViewById(R.id.apartment_type);
            apartmentPhoto = (ImageView)itemView.findViewById(R.id.apartment_photo);
            apartmentValue = (TextView)itemView.findViewById(R.id.apartment_value);
            apartmentArea = (TextView)itemView.findViewById(R.id.apartment_area);
            apartmentDescriptionShort = (TextView)itemView.findViewById(R.id.apartment_description_short);

        }
    }
}
