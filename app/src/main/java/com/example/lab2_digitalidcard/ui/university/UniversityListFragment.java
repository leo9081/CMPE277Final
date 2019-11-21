package com.example.lab2_digitalidcard.ui.university;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lab2_digitalidcard.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UniversityListFragment extends Fragment {

    @BindView(R.id.expenses)
    TextView expenses;

    @BindView(R.id.sat_math)
    TextView sat_math;

    @BindView(R.id.sat_verbal)
    TextView sat_verbal;

    @BindView(R.id.control)
    TextView control;

    @BindView(R.id.state)
    TextView state;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_university_search, container,false);

        ButterKnife.bind(this, v);

        return v;
    }

    @OnClick(R.id.search)
    public void searchForUniversities(){
        //check empty

        Intent intent = new Intent(getActivity(),UniversityResultActivity.class);
        intent.putExtra("EXPENSES", expenses.getText().toString());
        intent.putExtra("SATMATH",sat_math.getText().toString());
        intent.putExtra("SATVERBAL",sat_verbal.getText().toString());
        intent.putExtra("CONTROL",control.getText().toString());
        intent.putExtra("STATE",state.getText().toString());
        startActivity(intent);

    }


}
