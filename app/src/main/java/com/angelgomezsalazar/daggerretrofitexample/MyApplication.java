package com.angelgomezsalazar.daggerretrofitexample;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.angelgomezsalazar.daggerretrofitexample.components.DaggerNetComponent;
import com.angelgomezsalazar.daggerretrofitexample.components.NetComponent;
import com.angelgomezsalazar.daggerretrofitexample.modules.AppModule;
import com.angelgomezsalazar.daggerretrofitexample.modules.NetworkModule;

/**
 * Created by angelgomez on 9/13/16.
 */
public class MyApplication extends Application{

    private NetComponent netComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        // No idea why the builder modules are being marked as deprecated but it's fine
        netComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule("https://api.themoviedb.org/3/"))
                .build();
    }

    public NetComponent getNetComponent() {
        return netComponent;
    }

}
