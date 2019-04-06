package com.kilinochi.applicationlistwithpermissions;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.kilinochi.applicationlistwithpermissions.async_tasks.LoadApplicationTask;
import com.kilinochi.applicationlistwithpermissions.callbacks.LoadAppCallbacks;

import java.util.List;

/*Фрагмент, который создается один раз во время жизни всего приложения.
* Необходим для максимально абстрактной связи между AsyncTask, где мы загружаем данные с PacketManager в фоновом потоке,
* Активити, где мы эти данные отлавливаем при помощи Колбэков.
* Фрагмент также, как и сам Активити, расширяет собой интерфейс LoadAppCallbacks для делегирования методов Колбэка в Активити
* */

public class MainActivityFragment extends Fragment implements LoadAppCallbacks {

    /*
    * В переменную записываем текущий контекст Активити (т.к. он расширяет собой интерфейс AppLoadCallbacks
    * */
    private LoadAppCallbacks callbacks;

    /*Метод, который необходим нам для того, чтобы в активити положить данный фрагмент во фрагмент менеджер*/
    @NonNull
    public static Fragment newInstance() {
        return new MainActivityFragment();
    }
    /*Данный метод срадабывает один раз во время жизни всего фрагмента (т.к. мы поставили фраг setRetainInstance
    * необходим нам для сохранения данных между переворотами экрана, и, соответственно, уничтожением старго активити
    * и созданием нового.
    * Здесь же мы и запускаем Task для поиска всех установленных приложений на телефоне
    * */

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        LoadApplicationTask downloadApplications = new LoadApplicationTask(this);
        downloadApplications.execute();
    }


    /*Метод, необходимый нам для прикрепления к новой активити между переворотами экрана*/
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof LoadAppCallbacks) {
            callbacks = (LoadAppCallbacks) context;
        }
    }

    /*Метод, срабатывающий при уничтожений фрагмента, и, соответственно, активити*/
    @Override
    public void onDetach() {
        super.onDetach();
        callbacks = null;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /*Метод интерфейса AppLoadCallbacks, дерегирующий работу по отрисовке в активити*/

    @Override
    public void onPostExecute(List<ApplicationInfo> appList) {
        callbacks.onPostExecute(appList);
    }
}