package cn.lattice.cabinet.network;


import java.util.Map;

import cn.lattice.cabinet.entity.AllNetEntity;
import cn.lattice.cabinet.entity.AllStoreEntity;
import cn.lattice.cabinet.entity.CodeEntity;
import cn.lattice.cabinet.entity.ForgetEntity;
import cn.lattice.cabinet.entity.LoginEntity;
import cn.lattice.cabinet.entity.RegisterEntity;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * @author create by wjh
 * @Date 2018/9/30
 */
public interface ApiService {

    //String BASE_URL = "http://api.giantsnakehr.com/luzhiserver/"; //生产服务器
    String BASE_URL = "http://47.107.227.121:8019/"; //开发服务器

    /**
     * 登录
     *
     * @return
     */
    @POST("user/userLogin")
    @FormUrlEncoded
    Observable<ResponseBody> login(@Field("phoneNumber") String phone, @Field("passWord") String password);

    /**
     * 发送验证码
     */
    @POST("user/sendCode")
    @FormUrlEncoded
    Observable<CodeEntity> sendCode(@Field("phoneNumber") String phone);

    /**
     * 注册接口
     */
    @POST("user/userRegister")
    @FormUrlEncoded
    Observable<ResponseBody> register(@Field("phoneNumber") String phoneNumber, @Field("passWord") String passWord, @Field("vCode") String vCode);

    /**
     * 忘记密码
     */
    @POST("app/sendYzm")
    @FormUrlEncoded
    Observable<ResponseBody> forgetPwd(@Field("phoneNo") String phone, @Field("password") String password, @Field("messagecode") String messagecode);

    /**
     * 查询附近所有门店
     */
    @POST("shop/selectNearbyNet")
    @FormUrlEncoded
    Observable<AllNetEntity> selectAllNet(@Field("lat") String lat, @Field("lng") String lng);

    /**
     * 查询附近所有门店
     */
    @POST("shop/selectAllNet")
    Observable<AllStoreEntity> queryAllStore();

    /**
     * 上传故障信息
     */

    @POST("device/addFault")
    @FormUrlEncoded
    @Multipart
    Observable<ResponseBody> addFault(@Field("userId") String userID,
                                      @Field("deviceCode") String deviceCode,
                                      @Field("cause") int cause,
                                      @Field("otherCause") String otherCause,
                                      @Field("content") String content,
                                      @Part MultipartBody.Part MultipartFile);

    @POST("device/addFault")
    Observable<ResponseBody> addFault(@Body Map<String, Object> map);

    @POST("device/open")
    @FormUrlEncoded
    Observable<ResponseBody> deviceOpen(@Field("sn") String sn, @Field("user_id") int userId);


}
