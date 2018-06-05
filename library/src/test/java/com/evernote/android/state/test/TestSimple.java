package com.evernote.android.state.test;

import com.evernote.android.state.Extra;
import com.evernote.android.state.State;

public class TestSimple {
    @State
    @Extra
    int field;

    @State
    @Extra("id")
    int mId;
}
