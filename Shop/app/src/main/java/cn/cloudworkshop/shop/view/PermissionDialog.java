package cn.cloudworkshop.shop.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

import cn.cloudworkshop.shop.R;

/**
 * Author：Libin on 2018/11/30 09:38
 * Email：1993911441@qq.com
 * Describe：运行时权限提示框
 */
public class PermissionDialog  {
    /**
     * 提示对话框
     */
    public static void showPermissionDialog(final Context context, String msg) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context,
                R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        dialog.setTitle("帮助");
        dialog.setCancelable(false);
        dialog.setMessage("当前应用缺少" + msg + "权限，请点击\"设置\" - \"权限管理\"，打开所需权限。");
        //为“确定”按钮注册监听事件
        dialog.setPositiveButton("设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 启动应用的设置
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + context.getPackageName()));
                context.startActivity(intent);
            }
        });
        dialog.create();
        dialog.show();
    }
}
