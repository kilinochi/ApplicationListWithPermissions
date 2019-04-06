package com.kilinochi.applicationlistwithpermissions.app_config;

import android.app.Application;
import android.content.pm.PackageManager;

public class App extends Application {

    /**
     * Класс конфигурации (см. документацию)
     * необходим для доступа к объекту класса packageManager из всего приложения
     * */
    private static App instance;
    private PackageManager packageManager;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        packageManager = getPackageManager();
    }

    public PackageManager getPackManager(){
        return packageManager;
    }

    public static App getInstance() {
        return instance;
    }

    public App() {

    }
}
