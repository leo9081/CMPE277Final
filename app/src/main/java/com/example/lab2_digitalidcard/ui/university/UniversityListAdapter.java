package com.example.lab2_digitalidcard.ui.university;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab2_digitalidcard.R;
import com.example.lab2_digitalidcard.data.model.University;

import java.util.ArrayList;

public class UniversityListAdapter extends RecyclerView.Adapter<UniversityListAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    ArrayList<University> dataModelArrayList;

    public UniversityListAdapter(Context ctx, ArrayList<University> dataModelArrayList){

        inflater = LayoutInflater.from(ctx);
        this.dataModelArrayList = dataModelArrayList;
    }

    @NonNull
    @Override
    public UniversityListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.university_item,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UniversityListAdapter.MyViewHolder holder, int position) {
        //Picasso.get().load(dataModelArrayList.get(position).getImgURL()).into(holder.iv);
        holder.name.setText("University Name: " + dataModelArrayList.get(position).getName());
        holder.location.setText("University Location: " +dataModelArrayList.get(position).getLocation());
        holder.male_female_ratio.setText("University Male Female ratio: " +dataModelArrayList.get(position).getMale());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Bundle bundle = new Bundle();
                bundle.putSerializable("CLIKEDUV",dataModelArrayList.get(position));

                AppCompatActivity appCompatActivity = (AppCompatActivity) v.getContext();
                UniversityDetailFragment myFragment = new UniversityDetailFragment();
                myFragment.setArguments(bundle);
                appCompatActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.search_child,myFragment).commit();*/
                AppCompatActivity appCompatActivity = (AppCompatActivity) v.getContext();
                Intent i = new Intent(appCompatActivity, UniversityDetailActivity.class);
                i.putExtra("CLIKEDUV",dataModelArrayList.get(position));

                appCompatActivity.startActivity(i);
            }
        });
    }


    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView location, name, male_female_ratio;
        ImageView iv;

        public MyViewHolder(View itemView) {
            super(itemView);

            male_female_ratio = (TextView) itemView.findViewById(R.id.male_female_ratio);
            name = (TextView) itemView.findViewById(R.id.name);
            location = (TextView) itemView.findViewById(R.id.location);
            //iv = (ImageView) itemView.findViewById(R.id.iv);
        }
    }



    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }
}
