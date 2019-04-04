package com.kilinochi.applicationlistwithpermissions;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.kilinochi.applicationlistwithpermissions.async_tasks.LoadApplicationTask;
import com.kilinochi.applicationlistwithpermissions.callbacks.LoadAppCallbacks;

import java.util.List;

public class MainActivityFragment extends Fragment implements LoadAppCallbacks {

    private LoadAppCallbacks callbacks;

    @NonNull
    public static Fragment newInstance() {
        return new MainActivityFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        LoadApplicationTask downloadApplications = new LoadApplicationTask(this);
        downloadApplications.execute();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof LoadAppCallbacks) {
            callbacks = (LoadAppCallbacks) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callbacks = null;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onPostExecute(List<ApplicationInfo> appList) {
        callbacks.onPostExecute(appList);
    }
}