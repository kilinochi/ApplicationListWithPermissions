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



/*Второе активити, где отрисовывается визуальная составляющая конкретного приложения и его разрешения
* На входе принимается экземпляр класса ApplicationInfo, содержащий в себе информацию о приложении*/
public class ApplicationPermissionsActivity extends AppCompatActivity {

    private ApplicationInfo applicationInfo;

    /*Константа для поиска экземпляра класса ApplicationInfo*/
    private static final String APPLICATION_PARCELABLE_TAG = "APP_TAG";
    /*Коллекция, содержащая в себе разрешения для данного приложения*/
    private List <String> reqPermissions;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.application_permissions_activity);
        ImageView applicationIcon = findViewById(R.id.application_permission__app_icon);
        TextView applicationName = findViewById(R.id.application_permission__app_name);
        if(getIntent().getExtras() == null) { // если по каким-то причинам мы на входе получаем null, активити уничтожается
            finish();
        }
        // достаем экземпляр класса,
        // переданного нам в главной активити
        applicationInfo = getIntent().getExtras().getParcelable(APPLICATION_PARCELABLE_TAG);
        PackageManager packageManager = App.getInstance().getPackageManager();
        applicationIcon.setImageDrawable(applicationInfo.loadIcon(packageManager));
        applicationName.setText(applicationInfo.loadLabel(packageManager));
        setApplicationPermissions(packageManager);
        setupRecyclerView();
    }

    /*Здесь мы должны наполнить нашу коллецию ликвидными разрешениям и
    * Для этого мы пропускаем их через специальный "метод-фильтр" - 58 строчка кода
    * - подробнее см. в описании
    * класса - PermissionStringFilter*/

    private void setApplicationPermissions(PackageManager packageManager) {
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(applicationInfo.packageName, PackageManager.GET_PERMISSIONS);
            reqPermissions = PermissionStringFilter.filter(packageInfo.requestedPermissions);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /*установка RecyclerView и отрисовка при помощи ApplicationPermissionsAdapter*/
    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.application_permission__recycler_view);
        ApplicationPermissionAdapter applicationPermissionAdapter = new ApplicationPermissionAdapter(reqPermissions);
        recyclerView.setAdapter(applicationPermissionAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }
}
