package com.example.wecar.utilities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wecar.D_FireBase.CarItem;
import com.example.wecar.D_FireBase.FirebaseServices;
import com.example.wecar.D_FireBase.User;
import com.example.wecar.MainActivity;
import com.example.wecar.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CarListAdapter2 extends RecyclerView.Adapter<CarListAdapter2.MyViewHolder> {
    Context context;
    ArrayList<CarItem> carsList;
    private CarListAdapter2.OnItemClickListener itemClickListener;
    private FirebaseServices fbs;

    public CarListAdapter2(Context context, ArrayList<CarItem> carsList) {
        this.context = context;
        this.carsList = carsList;
        this.fbs = FirebaseServices.getInstance();
    }

    @NonNull
    @Override
    public CarListAdapter2.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v= LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return  new CarListAdapter2.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CarListAdapter2.MyViewHolder holder, int position){
        CarItem car= carsList.get(position);
        User u = fbs.getCurrentUser();
        if (u != null)
        {
            if (u.getFavorites().contains(car.getId()))
                Picasso.get().load(R.drawable.favcheck).into(holder.ivFavourite);
            else
                Picasso.get().load(R.drawable.ic_fav).into(holder.ivFavourite);
        }
        holder.carName.setText(car.getNameCar());
        holder.Price.setText(car.getPrice() + " ₪");
        holder.Year.setText(car.getYear());
        holder.location.setText(car.getHorse_power() + " Hp");
        holder.GearShift.setText(car.getGear_shifting_model());
        holder.kilometre.setText(car.getKilometre() + " Km");
        holder.carName.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(position);
            }
        });
/*
        holder.carName.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.setOnItemClick(position);
            }
        }); */
        if (car.getPhoto() == null || car.getPhoto().isEmpty())
        {
            Picasso.get().load(R.drawable.ic_fav).into(holder.ivCar);
        }
        else {
            Picasso.get().load(car.getPhoto()).into(holder.ivCar);
        }
        holder.ivFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFavorite(car) == true)
                {
                    removeStar(car, holder);
                }
                else
                {
                    addStar(car, holder);
                }
                fbs.setUserChangeFlag(true);
                //setFavourite(holder, car);
            }
        });
    }

    private void removeStar(CarItem car, CarListAdapter2.MyViewHolder holder) {
        User u = fbs.getCurrentUser();
        if (u != null) {
            if (u.getFavorites().contains(car.getId())) {
                u.getFavorites().remove(car.getId());
                holder.ivFavourite.setImageResource(android.R.color.transparent);
                Picasso.get().load(R.drawable.ic_fav).into(holder.ivFavourite);
            }
        }
    }

    private void addStar(CarItem car, CarListAdapter2.MyViewHolder holder) {
        User u = fbs.getCurrentUser();
        if (u != null) {
                u.getFavorites().add(car.getId());
            holder.ivFavourite.setImageResource(android.R.color.transparent);
            Picasso.get().load(R.drawable.favcheck).into(holder.ivFavourite);
        }
    }

    private boolean isFavorite(CarItem car) {
        User u = fbs.getCurrentUser();
        if (u != null)
        {
            if (u.getFavorites().contains(car.getId()))
                return true;
        }
        return false;
    }

    @Override
    public int getItemCount(){
        return carsList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView carName,Price,Year,location,GearShift,kilometre;
        ImageView ivCar, ivFavourite;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            carName=itemView.findViewById(R.id.tvNameCar_carListFragment);
            Price=itemView.findViewById(R.id.tvPrice_carListFragment);
            Year=itemView.findViewById(R.id.tvYear_carListFragment);
            location=itemView.findViewById(R.id.tvlocation_carListFragment);
            GearShift=itemView.findViewById(R.id.tvGearShift_carListFragment);
            kilometre=itemView.findViewById(R.id.tvKelometer_carListFragment);
            ivCar = itemView.findViewById(R.id.ivCarPhotoItem);
            ivFavourite = itemView.findViewById(R.id.ivFavoriteIcon);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(CarListAdapter2.OnItemClickListener listener) {
        this.itemClickListener = listener;
    }
}
