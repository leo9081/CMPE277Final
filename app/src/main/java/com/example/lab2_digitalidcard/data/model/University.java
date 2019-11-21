package com.example.lab2_digitalidcard.data.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

import java.io.Serializable;

public class University implements Serializable {


    public int getUniversityId() {
        return universityId;
    }

    public String getState() {
        return state;
    }

    public String getLocation() {
        return location;
    }

    public String getControl() {
        return control;
    }

    public String getNo_of_students() {
        return no_of_students;
    }

    public String getMale() {
        return male;
    }

    public String getStudent() {
        return student;
    }

    public String getSat_verbal() {
        return sat_verbal;
    }

    public String getSat_math() {
        return sat_math;
    }

    public String getExpenses_thous() {
        return expenses_thous;
    }

    public String getPercent_financial_aid() {
        return percent_financial_aid;
    }

    public String getNo_applicants_thous() {
        return no_applicants_thous;
    }

    public String getPercent_admittance() {
        return percent_admittance;
    }

    public String getPercent_enrolled() {
        return percent_enrolled;
    }

    public String getAcademics_scale() {
        return academics_scale;
    }

    public String getSocial_scale() {
        return social_scale;
    }

    public String getQuality_of_life_scale() {
        return quality_of_life_scale;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUniversityId(int universityId) {
        this.universityId = universityId;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setControl(String control) {
        this.control = control;
    }

    public void setNo_of_students(String no_of_students) {
        this.no_of_students = no_of_students;
    }

    public void setMale(String male) {
        this.male = male;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public void setSat_verbal(String sat_verbal) {
        this.sat_verbal = sat_verbal;
    }

    public void setSat_math(String sat_math) {
        this.sat_math = sat_math;
    }

    public void setExpenses_thous(String expenses_thous) {
        this.expenses_thous = expenses_thous;
    }

    public void setPercent_financial_aid(String percent_financial_aid) {
        this.percent_financial_aid = percent_financial_aid;
    }

    public void setNo_applicants_thous(String no_applicants_thous) {
        this.no_applicants_thous = no_applicants_thous;
    }

    public void setPercent_admittance(String percent_admittance) {
        this.percent_admittance = percent_admittance;
    }

    public void setPercent_enrolled(String percent_enrolled) {
        this.percent_enrolled = percent_enrolled;
    }

    public void setAcademics_scale(String academics_scale) {
        this.academics_scale = academics_scale;
    }

    public void setSocial_scale(String social_scale) {
        this.social_scale = social_scale;
    }

    public void setQuality_of_life_scale(String quality_of_life_scale) {
        this.quality_of_life_scale = quality_of_life_scale;
    }

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "universityId")
    public int universityId;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "state")
    public String state;

    @ColumnInfo(name = "control")
    public String control;

    @ColumnInfo(name = "location")
    public String location;

    @ColumnInfo(name = "percent_admittance")
    public String percent_admittance;

    @ColumnInfo(name = "percent_enrolled")
    public String percent_enrolled;

    @ColumnInfo(name = "no_applicants_thous")
    public String no_applicants_thous;

    @ColumnInfo(name = "no_of_students")
    public String no_of_students;

    @ColumnInfo(name = "male_female_ratio")
    public String male;

    @ColumnInfo(name = "student")
    public String student;

    @ColumnInfo(name = "sat_verbal")
    public String sat_verbal;

    @ColumnInfo(name = "sat_math")
    public String sat_math;

    @ColumnInfo(name = "expenses_thous")
    public String expenses_thous;

    @ColumnInfo(name = "percent_financial_aid")
    public String percent_financial_aid;

    @ColumnInfo(name = "academics_scale")
    public String academics_scale;

    @ColumnInfo(name = "social_scale")
    public String social_scale;

    @ColumnInfo(name = "quality_of_life_scale")
    public String quality_of_life_scale;


}
