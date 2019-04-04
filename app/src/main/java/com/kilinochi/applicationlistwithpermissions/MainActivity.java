package com.kilinochi.applicationlistwithpermissions;

import android.content.pm.ApplicationInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kilinochi.applicationlistwithpermissions.adapter.AppAdapter;
import com.kilinochi.applicationlistwithpermissions.callbacks.LoadAppCallbacks;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoadAppCallbacks {


    private static final String TAG_TASK_FRAGMENT = "MAIN_ACTIVITY_FRAGMENT";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(getSupportFragmentManager().findFragmentByTag(TAG_TASK_FRAGMENT) == null) {
                    getSupportFragmentManager().beginTransaction()
                    .add(MainActivityFragment.newInstance(), TAG_TASK_FRAGMENT)
                    .commit();
        }
    }


    private void onAppClick (ApplicationInfo applicationInfo){
        //TODO - Логика выбора определенного приложения
    }

    @Override
    public void onPostExecute(List<ApplicationInfo> appList) {
        RecyclerView recyclerView = findViewById(R.id.activity_main__recycler_view);
        AppAdapter appAdapter = new AppAdapter(appList, this::onAppClick);
        recyclerView.setAdapter(appAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }
}