package com.kilinochi.applicationlistwithpermissions;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.kilinochi.applicationlistwithpermissions.adapter.ApplicationPermissionAdapter;
import com.kilinochi.applicationlistwithpermissions.app_config.App;
import com.kilinochi.applicationlistwithpermissions.filter.PermissionStringFilter;

import java.util.List;


public class ApplicationPermissionsActivity extends AppCompatActivity {

    private ApplicationInfo applicationInfo;
    private static final String APPLICATION_PARCELABLE_TAG = "APP_TAG";
    private List <String> reqPermissions;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.application_permissions_activity);
        ImageView applicationIcon = findViewById(R.id.application_permission__app_icon);
        TextView applicationName = findViewById(R.id.application_permission__app_name);
        if(getIntent().getExtras() == null) {
            finish();
        }
        applicationInfo = getIntent().getExtras().getParcelable(APPLICATION_PARCELABLE_TAG);
        PackageManager packageManager = App.getInstance().getPackageManager();
        applicationIcon.setImageDrawable(applicationInfo.loadIcon(packageManager));
        applicationName.setText(applicationInfo.loadLabel(packageManager));
        setApplicationPermissions(packageManager);
        setupRecyclerView();
    }

    private void setApplicationPermissions(PackageManager packageManager) {
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(applicationInfo.packageName, PackageManager.GET_PERMISSIONS);
            reqPermissions = PermissionStringFilter.filter(packageInfo.requestedPermissions);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.application_permission__recycler_view);
        ApplicationPermissionAdapter applicationPermissionAdapter = new ApplicationPermissionAdapter(reqPermissions);
        recyclerView.setAdapter(applicationPermissionAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }
}
