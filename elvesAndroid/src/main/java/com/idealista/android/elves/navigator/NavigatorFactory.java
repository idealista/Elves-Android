package com.idealista.android.elves.navigator;

import android.app.Activity;
import android.support.annotation.NonNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class NavigatorFactory<ViewModel> {

    public Navigator<ViewModel> getNavigator(@NonNull final Activity activity,
                                             @NonNull final Class clazz) {
        try {
            Constructor<?> constructor = clazz.getConstructor(Activity.class);
            return (Navigator) constructor.newInstance(activity);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return new NullNavigator(activity);
    }

}
