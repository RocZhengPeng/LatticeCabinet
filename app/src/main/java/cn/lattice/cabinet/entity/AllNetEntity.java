package cn.lattice.cabinet.entity;

import java.util.List;

/**
 * Created by zhengpeng on 2019/7/7.
 */
public class AllNetEntity {
    /**
     * status : 1
     * msg : 查询成功
     * data : [{"address":"广东省深圳市宝安区联昇百货(花园店)","addressLat":"113.849753","createTime":1568470434000,"name":"威望","adminId":28,"logo":"https://juxunkeji.oss-cn-beijing.aliyuncs.com/%E9%A3%8E%E6%89%87%281%29.png","addressLon":"22.59705","updateTime":1568470434000,"id":547,"m":"62.2"},{"address":"广东省深圳市宝安区凤塘大道535号华丰科技园(凤塘大道)","addressLat":"113.791961","createTime":1568470571000,"name":"佳佳","adminId":28,"logo":"https://juxunkeji.oss-cn-beijing.aliyuncs.com/%E9%A3%8E%E6%89%87%281%29.png","addressLon":"22.699551","updateTime":1568470571000,"id":548,"m":"766.1"}]
     */

    private int status;
    private String msg;
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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * address : 广东省深圳市宝安区联昇百货(花园店)
         * addressLat : 113.849753
         * createTime : 1568470434000
         * name : 威望
         * adminId : 28
         * logo : https://juxunkeji.oss-cn-beijing.aliyuncs.com/%E9%A3%8E%E6%89%87%281%29.png
         * addressLon : 22.59705
         * updateTime : 1568470434000
         * id : 547
         * m : 62.2
         */

        private String address;
        private String addressLat;
        private long createTime;
        private String name;
        private int adminId;
        private String logo;
        private String addressLon;
        private long updateTime;
        private int id;
        private String m;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAddressLat() {
            return addressLat;
        }

        public void setAddressLat(String addressLat) {
            this.addressLat = addressLat;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAdminId() {
            return adminId;
        }

        public void setAdminId(int adminId) {
            this.adminId = adminId;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getAddressLon() {
            return addressLon;
        }

        public void setAddressLon(String addressLon) {
            this.addressLon = addressLon;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getM() {
            return m;
        }

        public void setM(String m) {
            this.m = m;
        }
    }
}
