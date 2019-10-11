package cn.lattice.cabinet.entity;

/**
 * Created by zhengpeng on 2019/7/7.
 */
public class LoginEntity {

    /**
     * status : 1
     * msg : Success.
     * data : {"id":2,"phoneNumber":"17673139387","passWord":"e10adc3949ba59abbe56e057f20f883e","creatTime":1569677806000,"cash":0,"balance":0,"status":0,"isDelete":0}
     */

    private int status;
    private String msg;
    private DataBean data;

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

    public static class DataBean {
        /**
         * id : 2
         * phoneNumber : 17673139387
         * passWord : e10adc3949ba59abbe56e057f20f883e
         * creatTime : 1569677806000
         * cash : 0
         * balance : 0.0
         * status : 0
         * isDelete : 0
         */

        private int id;
        private String phoneNumber;
        private String passWord;
        private long creatTime;
        private int cash;
        private double balance;
        private int status;
        private int isDelete;

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

        public long getCreatTime() {
            return creatTime;
        }

        public void setCreatTime(long creatTime) {
            this.creatTime = creatTime;
        }

        public int getCash() {
            return cash;
        }

        public void setCash(int cash) {
            this.cash = cash;
        }

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(int isDelete) {
            this.isDelete = isDelete;
        }
    }
}
