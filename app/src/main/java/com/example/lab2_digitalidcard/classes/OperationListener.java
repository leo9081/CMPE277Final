package com.example.lab2_digitalidcard.classes;

import com.example.lab2_digitalidcard.data.Result;

import java.util.ArrayList;

public interface OperationListener {

    public void onSuccess(@SuppressWarnings("rawtypes") Object returnObj);

    public void onError(Result result);

    public void onPreExecution();

    public void onPostExecution();

    public void onOperationProgressUpdate(String... updateParams);
}
