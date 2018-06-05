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

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Helper interface to save any object inside a {@link Bundle}.
 *
 * @param <T> The object class.
 */
public interface ExtraBundler<T> {

    /**
     * Restore the value from the bundle.
     *
     * @param key    The key for this value.
     * @param bundle The bundle in which the value is stored.
     * @return The object restored from the bundle.
     */
    @Nullable
    T get(@NonNull String key, @NonNull Bundle bundle);
}
