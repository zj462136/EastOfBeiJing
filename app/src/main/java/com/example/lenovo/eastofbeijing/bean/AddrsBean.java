package com.example.lenovo.eastofbeijing.bean;

import java.util.List;

public class AddrsBean {

    /**
     * msg : 地址列表请求成功
     * code : 0
     * data : [{"addr":"天津市天津市和平区放大法","addrid":1857,"mobile":123131231132,"name":"打发","status":0,"uid":1235},
     * {"addr":"天津市天津市河东区4324热熔法法","addrid":1862,"mobile":1231244124,"name":"发斯蒂芬大","status":0,"uid":1235}]
     */

    private String msg;
    private String code;
    private List<DataBean> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * addr : 天津市天津市和平区放大法
         * addrid : 1857
         * mobile : 123131231132
         * name : 打发
         * status : 0
         * uid : 1235
         */

        private String addr;
        private int addrid;
        private long mobile;
        private String name;
        private int status;
        private int uid;

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public int getAddrid() {
            return addrid;
        }

        public void setAddrid(int addrid) {
            this.addrid = addrid;
        }

        public long getMobile() {
            return mobile;
        }

        public void setMobile(long mobile) {
            this.mobile = mobile;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }
    }
}
