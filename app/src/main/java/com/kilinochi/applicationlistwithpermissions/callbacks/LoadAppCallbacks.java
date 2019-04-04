package com.kilinochi.applicationlistwithpermissions.callbacks;

import android.content.pm.ApplicationInfo;

import java.util.List;

public interface LoadAppCallbacks {
    void onPostExecute(List<ApplicationInfo> appList);
}
