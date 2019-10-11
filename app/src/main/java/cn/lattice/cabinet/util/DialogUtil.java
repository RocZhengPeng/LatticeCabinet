package cn.lattice.cabinet.util;

import android.content.Context;
import android.text.TextUtils;
import cn.lattice.cabinet.R;
import cn.lattice.cabinet.ui.RocApplication;

/**
 * Created by zhengpeng on 2019/8/6.
 */
public class DialogUtil {

    private CommonDialog commonDialog;

    public CommonDialog.Builder builder;

    private DialogUtil() {
    }

    public static DialogUtil getInstance() {
        return DialogInstance.dialogUtil;
    }

    private static final class DialogInstance {
        private static final DialogUtil dialogUtil = new DialogUtil();
    }

    public void showDialog(Context context, String msg) {
        showDialog(context, msg, null, false);
    }

    public void showDialog(Context context, String msg, DKCallBackBoolean listener, boolean isCancelButton) {
        showDialog(context, msg, null, null, listener, isCancelButton);
    }

    public void showDialog(Context context, String msg, String ok, String cancel, DKCallBackBoolean listener, boolean isCancelButton) {
        builder = new CommonDialog.Builder(context);
        builder.setTitle(msg);

        if (isCancelButton) {
            builder.setNegativeButton(TextUtils.isEmpty(cancel) ? "取消" : cancel, RocApplication.getContext().getResources().getColor(R.color.color_313131), new DKCallBackBoolean() {
                @Override
                public void action(int type) {
                    commonDialog.dismiss();
                }
            });
        }
        builder.setPositiveButton(TextUtils.isEmpty(ok) ? "确定" : ok, RocApplication.getContext().getResources().getColor(R.color.color_313131), listener != null ? listener : new DKCallBackBoolean() {
            @Override
            public void action(int type) {
                commonDialog.dismiss();
            }
        });
        commonDialog = builder.create();
        commonDialog.show();
    }
}
