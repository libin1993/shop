package cn.cloudworkshop.shop.base;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.igexin.sdk.PushManager;

import cn.cloudworkshop.shop.service.GeTuiIntentService;
import cn.cloudworkshop.shop.service.GeTuiService;

/**
 * Author：Libin on 2018/10/25 15:16
 * Email：1993911441@qq.com
 * Describe：
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //个推
        PushManager.getInstance().initialize(this.getApplicationContext(), GeTuiService.class);
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), GeTuiIntentService.class);
    }

    /**
     * @return 字体大小固定
     */
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }
}
