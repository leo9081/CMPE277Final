package com.example.lab2_digitalidcard.ui.card;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lab2_digitalidcard.R;
import com.example.lab2_digitalidcard.classes.OperationListener;
import com.example.lab2_digitalidcard.data.DBfunctions.UniversityFunctions;
import com.example.lab2_digitalidcard.data.Result;
import com.example.lab2_digitalidcard.data.model.University;
import com.example.lab2_digitalidcard.data.model.UserProfile;
import com.example.lab2_digitalidcard.ui.university.UniversityListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;

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

    @BindView(R.id.sum_text)
    TextView sum_text;

    @BindView(R.id.fit_uv)
    ListView fit_uv;

    private UserProfile userProfile;
    private PersonalUVListAdapter personalUVListAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = getActivity().getIntent();

        userProfile = (UserProfile) i.getSerializableExtra("UserProfile");

        fetchJSON("7000","400","500","private","ny");
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

                personalUVListAdapter = new PersonalUVListAdapter(getActivity().getApplicationContext(), modelRecyclerArrayList);
                fit_uv.setAdapter(personalUVListAdapter);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_card,container,false);

        ButterKnife.bind(this, v);

        sum_text.setText(R.string.eva);

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
