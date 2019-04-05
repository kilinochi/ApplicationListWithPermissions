package com.kilinochi.applicationlistwithpermissions.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kilinochi.applicationlistwithpermissions.R;

public class ApplicationPermissionAdapter extends RecyclerView.Adapter<ApplicationPermissionAdapter.AppPermissionViewHolder> {

    private final String [] requestedPermissions;

    public ApplicationPermissionAdapter(String[] requestedPermissions) {
        this.requestedPermissions = requestedPermissions;
    }


    @NonNull
    @Override
    public AppPermissionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.application_permission__item_permission, viewGroup, false);
        return new AppPermissionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppPermissionViewHolder appPermissionViewHolder, int index) {
        String appPermission = requestedPermissions[index];
        appPermissionViewHolder.bind(appPermission);
        appPermissionViewHolder.itemView.setTag(appPermission);
    }

    @Override
    public int getItemCount() {
        return requestedPermissions.length;
    }

    final class AppPermissionViewHolder extends RecyclerView.ViewHolder {

        private TextView permissionName;

        private AppPermissionViewHolder(@NonNull View itemView) {
            super(itemView);
            permissionName = itemView.findViewById(R.id.application_permission_item__desc);
        }

        private void bind(String permission) {
            //TODO - String replace 
           // permission = permission.replaceAll("","");
            permissionName.setText(permission);
        }
    }
}
