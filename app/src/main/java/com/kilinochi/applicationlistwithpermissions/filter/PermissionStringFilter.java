package com.kilinochi.applicationlistwithpermissions.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PermissionStringFilter {

    /**Так как нам необходимы не все данные, которые пришли из нам списка всех разрешений для конкретного приложения,
     * мы их "отфильтруем" при помощи статического метода filter*/

    public static List<String> filter(String []strings) {

        List<String> permissions = new ArrayList<>();
        String regex = "android.permission.";
        Pattern pattern = Pattern.compile("^"+regex); // для начала мы отбросим все значения, которые начинаются не с android.permission
        Matcher matcher;
        for(int i = 0; i < strings.length; i++) {
            matcher = pattern.matcher(strings[i]); //пройдемся по списку, и, если мы нашли необходимые нам значения
            if(matcher.find()){
                strings[i] = strings[i].replaceAll(regex, ""); // заменяем их на пустую строку
                String res = strings[i].replaceAll("_", " "); // убираем нижние пробелы
                permissions.add(res); // добавляем экзепляр списка в коллекцию и возвращаем ее для передачи в адаптер
            }
        }
        return permissions;
    }

    /**
     *нам не нужно создавать экземпляр класса PermissionStringFilter, а только его метод filter, так что мы
     * оставим здесь приватный конструктор */
    private PermissionStringFilter() {

    }
}
