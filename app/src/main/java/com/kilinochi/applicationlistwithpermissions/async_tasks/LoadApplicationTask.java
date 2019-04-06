package com.kilinochi.applicationlistwithpermissions.async_tasks;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;

import com.kilinochi.applicationlistwithpermissions.app_config.App;
import com.kilinochi.applicationlistwithpermissions.callbacks.LoadAppCallbacks;

import java.util.ArrayList;
import java.util.List;

/**
 * Так как работа с извлеченим списка приложенй достаточно трудоемкая, она не должна нагружать собой UI - поток
 * для этого мы используем AsyncTask, который мы предварительно запустили из Фрагмента- см MainActivityFragment
 * На вход мы ничего не передаем, но на выходе мы должны получить коллекцию, содержащую в себе объекты класса
 * ApplicationInfo
 * */

public class LoadApplicationTask extends AsyncTask <Void, Integer, List <ApplicationInfo>> {

    private PackageManager packageManager;
    private LoadAppCallbacks loadAppCallbacks;


    /**Текущий контекст фрагмента, который ранее раширил собой интерфейс LoadAppCallbacks
     * */
    public LoadApplicationTask(LoadAppCallbacks loadAppCallbacks) {
        this.loadAppCallbacks = loadAppCallbacks;
    }


    /*Логика поиска всех установленных приложений на телефоне при помощи PackageManager*/
    private List<ApplicationInfo> checkForLaunchIntent(List<ApplicationInfo> list) {
        ArrayList<ApplicationInfo> applist = new ArrayList<ApplicationInfo>();
        for (ApplicationInfo info : list) {
            try {
                if (null != packageManager.getLaunchIntentForPackage(info.packageName)) {
                    applist.add(info);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return applist;
    }

    /**
     * См. описание выше
     * */
    @Override
    protected List<ApplicationInfo> doInBackground(Void...voids) {
        packageManager = App.getInstance().getPackManager();
        return checkForLaunchIntent(packageManager.getInstalledApplications(PackageManager.GET_META_DATA));
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    /**
     * Данный метод работает уже в UI потоке. Так как мы переопределили интерфейс LoadAppCallbacks в
     * классах MainActivityFragment и MainActivity, мы вызываем уже у них переопределенные методы и делегируем
     * туда результат выполнения работы фонового потока
     * */

    @Override
    protected void onPostExecute(List<ApplicationInfo> appList) {
        super.onPostExecute(appList);
        loadAppCallbacks.onPostExecute(appList);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }
}
