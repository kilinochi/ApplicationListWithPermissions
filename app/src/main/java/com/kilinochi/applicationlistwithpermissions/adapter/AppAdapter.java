package com.kilinochi.applicationlistwithpermissions.adapter;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kilinochi.applicationlistwithpermissions.R;
import com.kilinochi.applicationlistwithpermissions.app_config.App;

import java.util.List;

public class AppAdapter extends RecyclerView.Adapter <AppAdapter.AppViewHolder>  {

    /**
     * Цель работы Адаптера заключается в отрисовке списка в RecyclerView
     * на вход подается коллекция ApplicationInfo,
     * каждый из объект которого содержит в себе информацию о конкретном приложении
     * */
    private final List <ApplicationInfo> appList;
    private final Listener onAppListener;

    public AppAdapter(List<ApplicationInfo> appList, Listener onAppListener) {
        this.appList = appList;
        this.onAppListener = onAppListener;
    }

    @NonNull
    @Override
    public AppViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.application_item, viewGroup, false);
        view.setOnClickListener(v -> onAppListener.onAppClick((ApplicationInfo) v.getTag()));
        return new AppViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppViewHolder appViewHolder, int i) {
        ApplicationInfo applicationInfo = appList.get(i);
        appViewHolder.bind(appList.get(i));
        appViewHolder.itemView.setTag(applicationInfo);
    }

    @Override
    public int getItemCount() {
        return appList.size();
    }

    final class AppViewHolder extends RecyclerView.ViewHolder {

        private ImageView appIcon;
        private TextView appName;

        private AppViewHolder(@NonNull View itemView) {
            super(itemView);
            appIcon = itemView.findViewById(R.id.application_item__icon);
            appName = itemView.findViewById(R.id.application_item__desc);
        }

        private void bind(@NonNull ApplicationInfo applicationInfo) {
            PackageManager packageManager = App.getInstance().getPackManager();
            appName.setText(applicationInfo.loadLabel(packageManager));
            appIcon.setImageDrawable(applicationInfo.loadIcon(packageManager));
        }
    }

    public interface Listener {
       void onAppClick(ApplicationInfo applicationInfo);
    }
}