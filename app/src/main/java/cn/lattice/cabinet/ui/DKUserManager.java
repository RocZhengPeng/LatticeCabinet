package cn.lattice.cabinet.ui;

import android.content.Context;
import android.content.SharedPreferences;

import cn.lattice.cabinet.entity.LoginEntity;
import cn.lattice.cabinet.util.PreferenceUtil;


/**
 * @author vane
 */
public class DKUserManager {

    private static Context mContext = RocApplication.getContext();
    private static SharedPreferences mSpUserInfo;
    private static SharedPreferences mSpToken;
    private static final String PreferenceUserInfo = "userinfo";
    private static LoginEntity userInfo;

    static {
        mSpUserInfo = mContext.getSharedPreferences(PreferenceUserInfo, Context.MODE_PRIVATE);
    }


    /**
     * 最初mUserInfo各属性内容为空，
     * 如登录后可更新当前的UserInfo和本地的缓存
     */
    public static void saveUserInfo(LoginEntity loginEntity) {
        if (userInfo != null) {
            PreferenceUtil.updateBean(mSpUserInfo, getUserInfo(), userInfo);
        }
    }

    public static void updateUserInfo(LoginEntity loginEntity) {
        if (userInfo != null) {
            PreferenceUtil.updateBean(mSpUserInfo, getUserInfo(), userInfo);
        }
    }

    /**
     * 如果从本地缓存中获取对象为空则实例化一个空对象
     * 判断是否登录全程通过user_id是不是为0来判断而不是通过
     * mUserInfo是否等于null，防止UserManager.getUserInfo出现空指针
     */
    private static void readUserInfo() {
        userInfo = (LoginEntity) PreferenceUtil.getBeanValue(mSpUserInfo, LoginEntity.class);
        if (userInfo == null) {
            userInfo = new LoginEntity();
        }
    }


    /**
     * 清空缓存时调用
     */
    public static void resetUserInfo() {
        SharedPreferences.Editor editor = mSpUserInfo.edit();
        editor.clear();
        editor.apply();
        userInfo = new LoginEntity();
    }

    /**
     * 判断已经登录
     */
    public static boolean isLogined() {
        LoginEntity.DataBean dataBean = getUserInfo().getData();
        if (dataBean != null) {
            return (dataBean.getId()) != -1 ? true : false;
        }
        return false;
    }


    public static LoginEntity getUserInfo() {
        if (userInfo == null) {
            readUserInfo();
        }
        return userInfo;
    }
}
