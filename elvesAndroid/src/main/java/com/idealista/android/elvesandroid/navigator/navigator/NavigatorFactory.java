package com.idealista.android.elvesandroid.navigator.navigator;

import android.app.Activity;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class NavigatorFactory {

    public Navigator getNavigator(Activity activity, Class clazz) {
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
        return new NullNavigator();
    }

}
