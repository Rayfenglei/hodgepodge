package com.example.ray.myapplication.Bean;

public class MessageEvent {
    private String mMassage;
    public MessageEvent(String massage){
        this.mMassage = massage;
    }
    public void setmMassage(String massage)
    {
        mMassage = massage;
    }
    public String getmMassage(){
        return mMassage;
    }
}
