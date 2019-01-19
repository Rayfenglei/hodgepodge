package com.example.ray.myapplication.Function.News;

import java.io.Serializable;
import java.util.Arrays;

public class YwnUID implements Serializable {
    private static final long serialVersionUID = 5947827332427115190L;
    private String code;
    private String error_msg;
    private String uid;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public YwnUID() {
    }

    public YwnUID(String uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return Arrays.asList(uid, code, error_msg).toString();
    }


}
