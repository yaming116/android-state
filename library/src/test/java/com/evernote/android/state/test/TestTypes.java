package com.evernote.android.state.test;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;

import com.evernote.android.state.Extra;
import com.evernote.android.state.State;

import java.io.Serializable;
import java.util.ArrayList;

public class TestTypes {
    @State
    @Extra
    public boolean mBoolean;
    @State
    @Extra
    public boolean[] mBooleanArray;
    @State
    @Extra
    public Boolean mBooleanObj;
    @State
    @Extra
    public byte mByte;
    @State
    @Extra
    public byte[] mByteArray;
    @State
    @Extra
    public Byte mByteObj;
    @State
    @Extra
    public char mChar;
    @State
    @Extra
    public char[] mCharArray;
    @State
    @Extra
    public Character mCharObj;
    @State
    @Extra
    public double mDouble;
    @State
    @Extra
    public double[] mDoubleArray;
    @State
    @Extra
    public Double mDoubleObj;
    @State
    @Extra
    public float mFloat;
    @State
    @Extra
    public float[] mFloatArray;
    @State
    @Extra
    public Float mFloatObj;
    @State
    @Extra
    public int mInt;
    @State
    @Extra
    public int[] mIntArray;
    @State
    @Extra
    public Integer mIntegerObj;
    @State
    @Extra
    public long mLong;
    @State
    @Extra
    public long[] mLongArray;
    @State
    @Extra
    public Long mLongObj;
    @State
    @Extra
    public short mShort;
    @State
    @Extra
    public short[] mShortArray;
    @State
    @Extra
    public Short mShortObj;
    @State
    @Extra
    public CharSequence mCharSequence;
    @State
    @Extra
    public CharSequence[] mCharSequenceArray;
    @State
    @Extra
    public String mString;
    @State
    @Extra
    public String[] mStringArray;
    @State
    @Extra
    public ArrayList<CharSequence> mCharSequenceArrayList;
    @State
    @Extra
    public ArrayList<Integer> mIntegerArrayList;
    @State
    @Extra
    public ArrayList<String> mStringArrayList;
    @State
    @Extra
    public Bundle mBundle;
    @State
    @Extra
    public Parcelable[] mParcelableArray;
    @State
    @Extra
    public ParcelableImpl mParcelableImpl;
    @State
    @Extra
    public SerializableImpl mSerializableImpl;
    @State
    @Extra
    public ArrayList<ParcelableImpl> mParcelableArrayList;
    @State
    @Extra
    public SparseArray<ParcelableImpl> mParcelableSparseArray;

    public static class ParcelableImpl implements Parcelable {
        private int mInt;

        public ParcelableImpl(int anInt) {
            mInt = anInt;
        }

        protected ParcelableImpl(Parcel in) {
            mInt = in.readInt();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ParcelableImpl that = (ParcelableImpl) o;

            return mInt == that.mInt;
        }

        @Override
        public int hashCode() {
            return mInt;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(mInt);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<ParcelableImpl> CREATOR = new Creator<ParcelableImpl>() {
            @Override
            public ParcelableImpl createFromParcel(Parcel in) {
                return new ParcelableImpl(in);
            }

            @Override
            public ParcelableImpl[] newArray(int size) {
                return new ParcelableImpl[size];
            }
        };
    }

    public static class SerializableImpl implements Serializable {
    }
}
