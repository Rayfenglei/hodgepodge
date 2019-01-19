package com.example.ray.myapplication.Function.News;

import java.io.Serializable;

public class YwnBaseBean implements Serializable {
    private static final long serialVersionUID = 5947827332427115190L;

    private String code;
    private String error_msg;
    private String count;
    private String server_time;
    private String t_expire_c;
    private String reset;
    private String upack;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getServer_time() {
        return server_time;
    }

    public void setServer_time(String server_time) {
        this.server_time = server_time;
    }

    public String getT_expire_c() {
        return t_expire_c;
    }

    public void setT_expire_c(String t_expire_c) {
        this.t_expire_c = t_expire_c;
    }

    public String getReset() {
        return reset;
    }

    public void setReset(String reset) {
        this.reset = reset;
    }

    public String getUpack() {
        return upack;
    }

    public void setUpack(String upack) {
        this.upack = upack;
    }
}

