/* *****************************************************************************
 * Copyright (c) 2017 Frankie Sardo, and Evernote Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Frankie Sardo - initial API and implementation
 *    Ralf Wondratschek - documentation and feature enhancement
 *******************************************************************************/
package com.evernote.android.state;

import android.app.Application;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import java.util.Map;

/*package*/ @SuppressWarnings({"unused", "WeakerAccess"})
final class StateSaverImpl {

    private final Map<Class<?>, Injector> mInjectors;

    /*package*/ StateSaverImpl(Map<Class<?>, Injector> injectors) {
        mInjectors = injectors;
    }

    private Injector getInjector(Class<?> cls) {
        Injector injector = mInjectors.get(cls);
        if (injector != null || mInjectors.containsKey(cls)) {
            return injector;
        }
        String clsName = cls.getName();
        if (clsName.startsWith(StateSaver.ANDROID_PREFIX) || clsName.startsWith(StateSaver.JAVA_PREFIX)) {
            return null;
        }
        try {
            Class<?> injectorClass = Class.forName(clsName + StateSaver.SUFFIX);
            injector = (Injector) injectorClass.newInstance();
        } catch (Exception e) {
            injector = getInjector(cls.getSuperclass());
        }
        mInjectors.put(cls, injector);
        return injector;
    }

    @SuppressWarnings("unchecked")
    private <T extends Injector> T safeGet(Object target, Injector nop) {
        try {
            Class<?> targetClass = target.getClass();
            Injector injector = getInjector(targetClass);
            if (injector == null) {
                injector = nop;
            }
            return (T) injector;
        } catch (Exception e) {
            throw new RuntimeException("Unable to inject state for " + target, e);
        }
    }

    /**
     * Save the given {@code target} in the passed in {@link Bundle}.
     *
     * @param target The object containing fields annotated with {@link State}.
     * @param state  The object is saved into this bundle.
     */
    /*package*/ <T> void saveInstanceState(@NonNull T target, @NonNull Bundle state) {
        Injector.Object<T> injector = safeGet(target, Injector.Object.DEFAULT);
        injector.save(target, state);
    }

    /**
     * Restore the given {@code target} from the passed in {@link Bundle}.
     *
     * @param target The object containing fields annotated with {@link State}.
     * @param state  The object is being restored from this bundle. Nothing is restored if the argument is {@code null}.
     */
    /*package*/ <T> void restoreInstanceState(@NonNull T target, @Nullable Bundle state) {
        if (state != null) {
            Injector.Object<T> injector = safeGet(target, Injector.Object.DEFAULT);
            injector.restore(target, state);
        }
    }

    /**
     * Restore the given {@code target} from the passed in intent {@link Bundle}.
     *
     * @param target The object containing fields annotated with {@link State}.
     * @param state  The object is being restored from this bundle. Nothing is restored if the argument is {@code null}.
     */
    /*package*/ <T> void restoreIntentState(@NonNull T target, @Nullable Bundle state) {
        if (state != null) {
            Injector.ObjectExtra<T> injector = safeGet(target, Injector.Object.DEFAULT);
            injector.restoreIntent(target, state);
        }
    }

    /**
     * Save the state of the given view and the other state inside of the returned {@link Parcelable}.
     *
     * @param target The view containing fields annotated with {@link State}.
     * @param state  The super state of the parent class of the view. Usually it isn't {@code null}.
     * @return A parcelable containing the view's state and its super state.
     */
    @NonNull
    /*package*/ <T extends View> Parcelable saveInstanceState(@NonNull T target, @Nullable Parcelable state) {
        Injector.View<T> injector = safeGet(target, Injector.View.DEFAULT);
        return injector.save(target, state);
    }

    /**
     * Restore the sate of the given view and return the super state of the parent class.
     *
     * @param target The view containing fields annotated with {@link State}.
     * @param state  The parcelable containing the view's state and its super sate.
     * @return The super state of teh parent class of the view. Usually it isn't {@code null}.
     */
    @Nullable
    /*package*/ <T extends View> Parcelable restoreInstanceState(@NonNull T target, @Nullable Parcelable state) {
        if (state != null) {
            Injector.View<T> injector = safeGet(target, Injector.View.DEFAULT);
            return injector.restore(target, state);
        } else {
            return null;
        }
    }

    /**
     * Turns automatic instance saving on or off for all activities and fragments from the support library. This avoids
     * manual calls of {@link #saveInstanceState(Object, Bundle)} and {@link #restoreInstanceState(Object, Bundle)}, instead
     * the library is doing this automatically for you. It's still necessary to annotate fields with {@link State}, though.
     * <br>
     * <br>
     * The best place to turn on this feature is in your {@link Application#onCreate()} method.
     *
     * @param application Your application instance.
     * @param enabled Whether this feature should be enabled.
     */
    /*package*/ void setEnabledForAllActivitiesAndSupportFragments(@NonNull Application application, boolean enabled) {
        if (AndroidLifecycleCallbacks.INSTANCE.mEnabled != enabled) {
            if (enabled) {
                application.registerActivityLifecycleCallbacks(AndroidLifecycleCallbacks.INSTANCE);
            } else {
                application.unregisterActivityLifecycleCallbacks(AndroidLifecycleCallbacks.INSTANCE);
            }
            AndroidLifecycleCallbacks.INSTANCE.mEnabled = enabled;
        }
    }
}
