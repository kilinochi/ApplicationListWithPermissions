package com.kilinochi.applicationlistwithpermissions.async_tasks;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;

import com.kilinochi.applicationlistwithpermissions.app_config.App;
import com.kilinochi.applicationlistwithpermissions.callbacks.LoadAppCallbacks;

import java.util.ArrayList;
import java.util.List;

public class LoadApplicationTask extends AsyncTask <Void, Integer, List <ApplicationInfo>> {

    private PackageManager packageManager;
    private LoadAppCallbacks loadAppCallbacks;

    public LoadApplicationTask(LoadAppCallbacks loadAppCallbacks) {
        this.loadAppCallbacks = loadAppCallbacks;
    }


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
