package com.example.lab2_digitalidcard.ui.university;

import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab2_digitalidcard.R;
import com.example.lab2_digitalidcard.classes.OperationListener;
import com.example.lab2_digitalidcard.data.DBfunctions.UniversityFunctions;
import com.example.lab2_digitalidcard.data.Result;
import com.example.lab2_digitalidcard.data.model.University;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Response;

public class UniversityResultActivity extends AppCompatActivity {

    private UniversityListAdapter universityListAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_university);

        getSupportActionBar().setTitle("Universities List");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recycler);

        String expenses = getIntent().getStringExtra("EXPENSES");
        String sat_math = getIntent().getStringExtra("SATMATH");
        String sat_verbal = getIntent().getStringExtra("SATVERBAL");
        String control = getIntent().getStringExtra("CONTROL");
        String state = getIntent().getStringExtra("STATE");

        fetchJSON(expenses,sat_math,sat_verbal,control,state);

    }

    private void fetchJSON(String expenses,String sat_math,String sat_verbal,String control,String state){
        new UniversityFunctions().setListerner(fetchUniversitieseListener)
                .getUniversityFromRequirement(expenses,sat_math,sat_verbal,control,state);

    }

    OperationListener fetchUniversitieseListener = new OperationListener() {
        @Override
        public void onSuccess(Object returnObj) {
            Result<University> result = null;

            writeRecycler(returnObj);
        }

        @Override
        public void onError(Result result) {

        }

        @Override
        public void onPreExecution() {

        }

        @Override
        public void onPostExecution() {

        }

        @Override
        public void onOperationProgressUpdate(String... updateParams) {

        }
    };

    private void writeRecycler(Object response){

        try {
            //getting the whole json object from the response
            Response rp = (Response) response;
            List<Object> resultList = (List)rp.body();

            ArrayList<University> modelRecyclerArrayList = new ArrayList<>();

            for(int i = 0; i< resultList.size(); i++){
                Map<String,String> m = (Map)resultList.get(i);

                University university = new University();

                //modelRecycler.setImgURL(dataobj.getString("imgURL"));
                university.setName(m.get("name"));
                university.setAcademics_scale(m.get("academics_scale"));
                university.setControl(m.get("control"));
                university.setExpenses_thous(m.get("expenses"));
                university.setLocation(m.get("location"));
                university.setMale(m.get("male_female_ratio"));
                university.setNo_applicants_thous(m.get("no_applicants"));
                //university.setNo_of_students(m.get());
                university.setPercent_admittance(m.get("percent_admittance"));
                university.setPercent_enrolled(m.get("percent_enrolled"));
                university.setPercent_financial_aid(m.get("percent_financial_aid"));
                university.setQuality_of_life_scale(m.get("quality_of_life_scale"));
                university.setSat_math(m.get("sat_math"));
                university.setSat_verbal(m.get("sat_verbal"));
                university.setSocial_scale(m.get("social_scale"));
                university.setState(m.get("state"));
                //university.setStudent(m.get("name"));


                modelRecyclerArrayList.add(university);

                universityListAdapter = new UniversityListAdapter(this, modelRecyclerArrayList);
                recyclerView.setAdapter(universityListAdapter);

                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
