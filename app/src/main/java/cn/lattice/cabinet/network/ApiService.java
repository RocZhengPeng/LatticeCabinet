package cn.lattice.cabinet.network;


import cn.lattice.cabinet.entity.AllNetEntity;
import cn.lattice.cabinet.entity.CodeEntity;
import cn.lattice.cabinet.entity.ForgetEntity;
import cn.lattice.cabinet.entity.LoginEntity;
import cn.lattice.cabinet.entity.RegisterEntity;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;;
import retrofit2.http.POST;

/**
 * @author create by wjh
 * @Date 2018/9/30
 */
public interface ApiService {

    //String BASE_URL = "http://api.giantsnakehr.com/luzhiserver/"; //生产服务器
    String BASE_URL = "http://47.106.166.23:8080/obd/"; //开发服务器

    /**
     * 登录
     *
     * @return
     */
    @POST("app/Login")
    @FormUrlEncoded
    Observable<ResponseBody> login(@Field("phoneNo") String phone, @Field("passWord") String password);

    /**
     * 发送验证码
     */
    @POST("app/sendYzm")
    @FormUrlEncoded
    Observable<CodeEntity> sendCode(@Field("phoneNo") String phone);

    /**
     * 注册接口
     */
    @POST("app/register")
    @FormUrlEncoded
    Observable<ResponseBody> register(@Field("phoneNumber") String phoneNumber, @Field("passWord") String passWord, @Field("username") String username, @Field("mailBox") String mailBox, @Field("messagecode") String messagecode);

    /**
     * 忘记密码
     */
    @POST("app/sendYzm")
    @FormUrlEncoded
    Observable<ResponseBody> forgetPwd(@Field("phoneNo") String phone, @Field("password") String password, @Field("messagecode") String messagecode);

    /**
     * 查询所有门店
     */
    @POST("app/selectAllNet")
    @FormUrlEncoded
    Observable<AllNetEntity> selectAllNet(@Field("address_lat") String address_lat,@Field("address_lon")String address_lon,@Field("shop_name")String shop_name);


}
