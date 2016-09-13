package com.angelgomezsalazar.daggerretrofitexample.components;

import com.angelgomezsalazar.daggerretrofitexample.activities.MainActivity;
import com.angelgomezsalazar.daggerretrofitexample.modules.AppModule;
import com.angelgomezsalazar.daggerretrofitexample.modules.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by angelgomez on 9/13/16.
 */
@Singleton
@Component(modules = {AppModule.class, NetworkModule.class })
public interface NetComponent {

    void inject(MainActivity mainActivity);

}
