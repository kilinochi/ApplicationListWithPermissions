package com.kilinochi.applicationlistwithpermissions.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PermissionStringFilter {

    public static List<String> filter(String []strings) {

        List<String> permissions = new ArrayList<>();
        String regex = "android.permission.";
        Pattern pattern = Pattern.compile("^"+regex);
        Matcher matcher;
        for(int i = 0; i < strings.length; i++) {
            matcher = pattern.matcher(strings[i]);
            if(matcher.find()){
                strings[i] = strings[i].replaceAll(regex, "");
                String res = strings[i].replaceAll("_", " ");
                permissions.add(res);
            }
        }
        return permissions;
    }
    private PermissionStringFilter() {

    }
}
