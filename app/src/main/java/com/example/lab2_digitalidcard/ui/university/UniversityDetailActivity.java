package com.example.lab2_digitalidcard.ui.university;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lab2_digitalidcard.R;
import com.example.lab2_digitalidcard.data.model.University;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UniversityDetailActivity extends AppCompatActivity {
    @BindView(R.id.name)
    TextView name;

    @BindView(R.id.state)
    TextView state;

    @BindView(R.id.control)
    TextView control;

    @BindView(R.id.location)
    TextView location;

    @BindView(R.id.percent_admittance)
    TextView percent_admittance;

    @BindView(R.id.percent_enrolled)
    TextView percent_enrolled;

    @BindView(R.id.no_applicants)
    TextView no_applicants;
    @BindView(R.id.sat_verbal)
    TextView sat_verbal;

    @BindView(R.id.sat_math)
    TextView sat_math;

    @BindView(R.id.expenses)
    TextView expenses;

    @BindView(R.id.percent_financial_aid)
    TextView percent_financial_aid;

    @BindView(R.id.male_female_ratio)
    TextView male_female_ratio;

    @BindView(R.id.academics_scale)
    TextView academics_scale;

    @BindView(R.id.social_scale)
    TextView social_scale;

    @BindView(R.id.quality_of_life_scale)
    TextView quality_of_life_scale;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_university_detail);

        getSupportActionBar().setTitle("Universities Detail");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        University selectedUV = (University) getIntent().getSerializableExtra("CLIKEDUV");

        name.setText("University Name: " + selectedUV.getName());
        state.setText("University State: " + selectedUV.getState());
        control.setText("University Control: " + selectedUV.getControl());
        location.setText("University Location: " + selectedUV.getLocation());
        percent_admittance.setText("University admittance: " + selectedUV.getPercent_admittance());
        percent_enrolled.setText("University enrolled: " + selectedUV.getPercent_enrolled());
        no_applicants.setText("University applicants: " + selectedUV.getNo_applicants_thous());
        sat_verbal.setText("University SAT Verbal: " + selectedUV.getSat_verbal());
        sat_math.setText("University SAT Math: " + selectedUV.getSat_math());
        expenses.setText("University Expenses: " + selectedUV.getExpenses_thous());
        percent_financial_aid.setText("University aid: " + selectedUV.getPercent_financial_aid());
        male_female_ratio.setText("University ratio: " + selectedUV.getMale());
        academics_scale.setText("University scale: " + selectedUV.getAcademics_scale());
        social_scale.setText("University social: " + selectedUV.getSocial_scale());
        quality_of_life_scale.setText("University life: " + selectedUV.getQuality_of_life_scale());

    }
}
