package cn.lattice.cabinet.entity;

/**
 * Created by zhengpeng on 2019/7/7.
 */
public class ForgetEntity {

    /**
     * status : 0
     * msg : 修改成功
     * data :
     * trxCode : /app/forgetPwd
     */

    private int status;
    private String msg;
    private String data;
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTrxCode() {
        return trxCode;
    }

    public void setTrxCode(String trxCode) {
        this.trxCode = trxCode;
    }
}
