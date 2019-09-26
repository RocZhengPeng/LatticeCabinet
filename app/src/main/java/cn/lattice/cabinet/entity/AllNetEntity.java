package cn.lattice.cabinet.entity;

import java.util.List;

/**
 * Created by zhengpeng on 2019/7/7.
 */
public class AllNetEntity {

    /**
     * status : 0
     * msg : 查询成功
     * data : [{"update_time":1562002792000,"address":"广东省深圳市宝安区前进二路宝田工业区128号天勤安盛大厦","create_time":1562002792000,"admin_id":28,"address_lat":"113.870873","id":548,"shop_name":"杨凯勇","address_lon":"22.602808","is_delete":0,"netCount":0}]
     * trxCode : null
     */

    private int status;
    private String msg;
    private Object trxCode;
    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getTrxCode() {
        return trxCode;
    }

    public void setTrxCode(Object trxCode) {
        this.trxCode = trxCode;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * update_time : 1562002792000
         * address : 广东省深圳市宝安区前进二路宝田工业区128号天勤安盛大厦
         * create_time : 1562002792000
         * admin_id : 28
         * address_lat : 113.870873
         * id : 548
         * shop_name : 杨凯勇
         * address_lon : 22.602808
         * is_delete : 0
         * netCount : 0
         */

        private long update_time;
        private String address;
        private long create_time;
        private int admin_id;
        private String address_lat;
        private int id;
        private String shop_name;
        private String address_lon;
        private int is_delete;
        private int netCount;

        public long getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(long update_time) {
            this.update_time = update_time;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public long getCreate_time() {
            return create_time;
        }

        public void setCreate_time(long create_time) {
            this.create_time = create_time;
        }

        public int getAdmin_id() {
            return admin_id;
        }

        public void setAdmin_id(int admin_id) {
            this.admin_id = admin_id;
        }

        public String getAddress_lat() {
            return address_lat;
        }

        public void setAddress_lat(String address_lat) {
            this.address_lat = address_lat;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public String getAddress_lon() {
            return address_lon;
        }

        public void setAddress_lon(String address_lon) {
            this.address_lon = address_lon;
        }

        public int getIs_delete() {
            return is_delete;
        }

        public void setIs_delete(int is_delete) {
            this.is_delete = is_delete;
        }

        public int getNetCount() {
            return netCount;
        }

        public void setNetCount(int netCount) {
            this.netCount = netCount;
        }
    }
}
