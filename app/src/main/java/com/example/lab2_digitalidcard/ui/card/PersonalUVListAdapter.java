package com.example.lab2_digitalidcard.ui.card;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lab2_digitalidcard.R;
import com.example.lab2_digitalidcard.data.model.University;

import java.util.ArrayList;

public class PersonalUVListAdapter extends BaseAdapter {

    LayoutInflater inflater;
    ArrayList<University> dataModelArrayList;

    public PersonalUVListAdapter(Context mContext, ArrayList<University> dataModelArrayList){
        inflater = LayoutInflater.from(mContext);
        this.dataModelArrayList = dataModelArrayList;
    }


    @Override
    public int getCount() {
        return dataModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null) {
            convertView = inflater.inflate(R.layout.listview_puv, null);
        }
        TextView un = convertView.findViewById(R.id.name);
        TextView index = convertView.findViewById(R.id.index);

        un.setText(dataModelArrayList.get(position).getName());
        index.setText("1");
        return convertView;
    }
}
