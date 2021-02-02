package com.example.p0681_parcel;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class MyObject implements Parcelable {
   
  final static String LOG_TAG = "myLogs";
 
  public String s;
  public int i;
 
  // обычный конструктор
  public MyObject(String _s, int _i) {
    Log.d(LOG_TAG, "MyObject(String _s, int _i)");
    s = _s;
    i = _i;
  }
 
  public int describeContents() {
    return 0;
  }



  // упаковываем объект в Parcel
  public void writeToParcel(Parcel parcel, int flags) {
    Log.d(LOG_TAG, "writeToParcel");
    parcel.writeString(s);
    parcel.writeInt(i);
  }
 
  public static final Parcelable.Creator<MyObject> CREATOR = new Parcelable.Creator<MyObject>() {
    // распаковываем объект из Parcel
    public MyObject createFromParcel(Parcel in) {
      Log.d(LOG_TAG, "createFromParcel");
      return new MyObject(in);
    }
 
    public MyObject[] newArray(int size) {
      return new MyObject[size];
    }
  };
 
  // конструктор, считывающий данные из Parcel
  private MyObject(Parcel parcel) {
    Log.d(LOG_TAG, "MyObject(Parcel parcel)");
    s = parcel.readString();
    i = parcel.readInt();
  }
 
}