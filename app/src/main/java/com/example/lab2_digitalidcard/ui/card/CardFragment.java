package com.example.lab2_digitalidcard.ui.card;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lab2_digitalidcard.R;
import com.example.lab2_digitalidcard.data.model.UserProfile;

import java.util.zip.Inflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CardFragment extends Fragment {

    @BindView(R.id.person_photo)
    ImageView person_photo;

    @BindView(R.id.person_name)
    TextView person_name;

    @BindView(R.id.person_age)
    TextView person_age;

    @BindView(R.id.person_school)
    TextView person_school;

    @BindView(R.id.person_id)
    TextView person_id;

    private UserProfile userProfile;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_card,container,false);

        ButterKnife.bind(this, v);

        Intent i = getActivity().getIntent();

        userProfile = (UserProfile) i.getSerializableExtra("UserProfile");

        setCardData();

        return v;
    }

    public void setCardData(){
        person_name.setText(userProfile.getUserName());
        person_age.setText(userProfile.getAge());
        person_school.setText(userProfile.getSchoolName());
        person_id.setText(userProfile.getCurrentId());
    }
}
