package com.kilinochi.applicationlistwithpermissions;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kilinochi.applicationlistwithpermissions.adapter.AppAdapter;
import com.kilinochi.applicationlistwithpermissions.callbacks.LoadAppCallbacks;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoadAppCallbacks {

    /*
    * Константы необходимы для поиска во FragmentManager нужного фрагмента по тегу
    * */
    private static final String TAG_TASK_FRAGMENT = "MAIN_ACTIVITY_FRAGMENT";

    /*
    * Данная константа нужна для того, чтобы во втором активити мы отловили нужные данные (в нашем случае -
    * объект класса ApplicationInfo
    * */
    private static final String APPLICATION_INFO_TAG = "APP_TAG";


    /*
    * Метод onCreate срабатывает каждый раз, когда активити пересоздается (в том числе если мы перевернули экран.
    * Чтобы избежать потерю данных, мы ищем во фрагмент менеджере нужный фрагмент по тегу, и, если находим, он просто прикрепляется
    * к новой активити, сохраняя в себе данные. Логика поиска представлена ниже.
    * Если мы не обнаружили фрагмент, мы его создаем и кладем в менеджер.
    * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(getSupportFragmentManager().findFragmentByTag(TAG_TASK_FRAGMENT) == null) {
                    getSupportFragmentManager().beginTransaction()
                    .add(MainActivityFragment.newInstance(), TAG_TASK_FRAGMENT)
                    .commit();
        }
    }

    /*
    * Колбэк, используемый для перехода в новую активити, где будет выводится информация о разрешениях конкретного приложения.
    * Информация о разрешениях хранится в объекте класса ApplicationInfo.
    * Т.к. класс ApplicationInfo расширяет собой интерфейс Parcelable, мы кладем его (объект класса) в интент по тегу, в другой
    * активити мы отлавливаем необходимый нам экземпляр этого класса
    * */


    private void onAppClick (ApplicationInfo applicationInfo){
        Intent intent = new Intent(this, ApplicationPermissionsActivity.class);
        intent.putExtra(APPLICATION_INFO_TAG,applicationInfo);
        startActivity(intent);
    }

    /*
    * Так как Активити расширяет собой интерфейс LoadAppCallback (см. описание интерфейса), мы переопределим этот метод здесь, с целью
    * его дальнейшей вызова в AsyncTask.
    * Здесь мы отрисовываем необходимые данные на RecyclerView при помощи AppAdapter(см. описание класса)
    * */

    @Override
    public void onPostExecute(List<ApplicationInfo> appList) {
        RecyclerView recyclerView = findViewById(R.id.activity_main__recycler_view);
        AppAdapter appAdapter = new AppAdapter(appList, this::onAppClick);
        recyclerView.setAdapter(appAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }
}