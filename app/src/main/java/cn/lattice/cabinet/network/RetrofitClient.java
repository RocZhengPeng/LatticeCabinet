package cn.lattice.cabinet.network;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;


import java.util.Map;
import java.util.concurrent.TimeUnit;


import cn.lattice.cabinet.entity.AllNetEntity;
import cn.lattice.cabinet.entity.AllStoreEntity;
import cn.lattice.cabinet.entity.CodeEntity;
import cn.lattice.cabinet.entity.ForgetEntity;
import cn.lattice.cabinet.entity.LoginEntity;
import cn.lattice.cabinet.entity.RegisterEntity;
import io.reactivex.Observer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.android.schedulers.AndroidSchedulers;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.Part;

/**
 * @author create by wjh
 * @Date 2018/9/30
 */
public class RetrofitClient {

    private static final int DEFAULT_TIMEOUT = 30;
    private ApiService apiService;
    private OkHttpClient okHttpClient;
    public static String baseUrl = ApiService.BASE_URL;
    private static Context mContext;
    private static RetrofitClient sNewInstance;

    private static class SingletonHolder {
        private static RetrofitClient INSTANCE = new RetrofitClient(mContext);
    }

    public static RetrofitClient getInstance(Context context) {
        if (context != null) {
            mContext = context.getApplicationContext();
        }
        return SingletonHolder.INSTANCE;
    }

    private RetrofitClient(Context context) {
        this(context, null);
    }

    private RetrofitClient(Context context, String url) {
        if (TextUtils.isEmpty(url)) {
            url = baseUrl;
        }
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                //打印retrofit日志
                Log.e("RetrofitLog", "retrofitBack = " + message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClient = new OkHttpClient.Builder()
                //.addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
                //.cookieJar(new NovateCookieManger(context))
                //.addInterceptor(new BaseInterceptor(mContext))
                .addInterceptor(loggingInterceptor)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(url).build();
        apiService = retrofit.create(ApiService.class);
    }


    public void login(String phone, String password, Observer<ResponseBody> subscriber) {
        apiService.login(phone, password)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void sendCode(String phone, Observer<CodeEntity> subscriber) {
        apiService.sendCode(phone)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }

    public void register(String phoneNumber, String passWord, String vCode, Observer<ResponseBody> subscriber) {
        apiService.register(phoneNumber, passWord, vCode)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }

    public void forgetPwd(String phoneNo, String passWord, String messagecode, Observer<ResponseBody> subscriber) {
        apiService.forgetPwd(phoneNo, passWord, messagecode)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }

    public void selectAllNet(String lat, String lng, Observer<AllNetEntity> subscriber) {
        apiService.selectAllNet(lat, lng)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }

    public void queryAllStore(Observer<AllStoreEntity> subscriber) {
        apiService.queryAllStore()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }

    public void addFault(String userID,
                         String deviceCode,
                         int cause,
                         String otherCause,
                         String content,
                         MultipartBody.Part MultipartFile,
                         Observer<ResponseBody> subscriber) {
        apiService.addFault(userID, deviceCode, cause, otherCause, content, MultipartFile)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);


    }

    public void addFault(Map<String, Object> map, Observer<ResponseBody> subscriber) {
        apiService.addFault(map)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void deviceOpen(String sn, int userId, Observer<ResponseBody> subscriber) {
        apiService.deviceOpen(sn, userId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

}
