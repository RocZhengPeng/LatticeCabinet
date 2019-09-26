package cn.lattice.cabinet.entity;

/**
 * Created by zhengpeng on 2019/7/7.
 */
public class LoginEntity {

    /**
     * status : 0
     * msg : 登录成功
     * data : {"id":1,"phoneNumber":"17673132955","passWord":"123456","wxOpenid":"ok1dK5AhBrBJQPsznql6iX4nMSZc","creatTime":1550924231000,"cash":"0","username":"郭佳明","headImg":"https://wx.qlogo.cn/mmhead/y64anJx5pMRZW1jicb2vypI5FN4DlfRjNJR4FngibveLk/132","status":0,"isDelete":null}
     * trxCode : /app/Login
     */

    private int status;
    private String msg;
    private DataBean data;
    private String trxCode;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getTrxCode() {
        return trxCode;
    }

    public void setTrxCode(String trxCode) {
        this.trxCode = trxCode;
    }

    public static class DataBean {
        /**
         * id : 1
         * phoneNumber : 17673132955
         * passWord : 123456
         * wxOpenid : ok1dK5AhBrBJQPsznql6iX4nMSZc
         * creatTime : 1550924231000
         * cash : 0
         * username : 郭佳明
         * headImg : https://wx.qlogo.cn/mmhead/y64anJx5pMRZW1jicb2vypI5FN4DlfRjNJR4FngibveLk/132
         * status : 0
         * isDelete : null
         */

        private int id;
        private String phoneNumber;
        private String passWord;
        private String wxOpenid;
        private long creatTime;
        private String cash;
        private String username;
        private String headImg;
        private int status;
        private Object isDelete;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getPassWord() {
            return passWord;
        }

        public void setPassWord(String passWord) {
            this.passWord = passWord;
        }

        public String getWxOpenid() {
            return wxOpenid;
        }

        public void setWxOpenid(String wxOpenid) {
            this.wxOpenid = wxOpenid;
        }

        public long getCreatTime() {
            return creatTime;
        }

        public void setCreatTime(long creatTime) {
            this.creatTime = creatTime;
        }

        public String getCash() {
            return cash;
        }

        public void setCash(String cash) {
            this.cash = cash;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public Object getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(Object isDelete) {
            this.isDelete = isDelete;
        }
    }
}
