package com.kilinochi.applicationlistwithpermissions.callbacks;

import android.content.pm.ApplicationInfo;

import java.util.List;


/*
* Колбэк, цель которого - логически и максимально абстрактно связать между собой активити, куда будет передаваться
* информация о всех установленных приложениях, и AsyncTask, где мы будем на фоновом потоке загружать необходимые данные*/
public interface LoadAppCallbacks {
    void onPostExecute(List<ApplicationInfo> appList);
}
