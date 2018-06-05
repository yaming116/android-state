package com.evernote.android.state.test.lint;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.evernote.android.state.Extra;
import com.evernote.android.state.State;
import com.evernote.android.state.StateSaver;

import java.util.ArrayList;

/**
 * @author rwondratschek
 */
public class LintFailingActivityJava extends Activity {

    @Extra("mId")
    @State
    int id;

    @Extra
    ArrayList<Boolean> a;

    @SuppressLint("NonMatchingStateSaverCalls")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StateSaver.restoreInstanceState(this, savedInstanceState);
    }
}
