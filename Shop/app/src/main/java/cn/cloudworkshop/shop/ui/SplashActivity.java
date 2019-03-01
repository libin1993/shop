package cn.cloudworkshop.shop.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import cn.cloudworkshop.shop.R;
import cn.cloudworkshop.shop.base.BaseActivity;
import cn.cloudworkshop.shop.base.BaseBean;
import cn.cloudworkshop.shop.base.RetrofitUtils;
import cn.cloudworkshop.shop.base.RxObserver;
import cn.cloudworkshop.shop.mvp.altercutomer.AlterCustomerActivity;
import cn.cloudworkshop.shop.mvp.shoplist.ShopListActivity;
import cn.cloudworkshop.shop.mvp.login.LoginActivity;

import cn.cloudworkshop.shop.utils.SPUtils;
import cn.cloudworkshop.shop.utils.TitleBarUtils;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Author：Libin on 2018/11/28 15:22
 * Email：1993911441@qq.com
 * Describe：
 */
public class SplashActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TitleBarUtils.setNoTitleBar(this);
        setContentView(R.layout.activity_splash);
        initView();
    }

    private void initView() {
        String token = SPUtils.getStr(SplashActivity.this, "token");
        if (!TextUtils.isEmpty(token)) {
            isLogin(token);
        } else {
            timing(2500, false);
        }
    }

    /**
     * 检测token是否失效
     *
     * @param token
     */
    private void isLogin(String token) {
        final long currentTime = System.currentTimeMillis();
        RetrofitUtils.getInstance()
                .request()
                .isLogin(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxObserver<>(new RxObserver.Callback<BaseBean>() {
                    @Override
                    public void onSuccess(BaseBean baseBean) {
                        long millisecond = System.currentTimeMillis() - currentTime;
                        if (millisecond < 2500) {
                            timing(2500 - millisecond, true);
                        } else {
                            startActivity(new Intent(SplashActivity.this, ShopListActivity.class));
                            finish();
                        }
                    }



                    @Override
                    public void onFail(String msg) {
                        long millisecond = System.currentTimeMillis() - currentTime;
                        if (millisecond < 2500) {
                            timing(2500 - millisecond, false);
                        } else {
                            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                            finish();
                        }
                    }

                    @Override
                    public void onError() {

                    }
                }));

    }

    /**
     * @param totalTime
     * @param isLogin   倒计时
     */
    private void timing(long totalTime, final boolean isLogin) {
        CountDownTimer countDownTimer = new CountDownTimer(totalTime, 1000) {
            @Override
            public void onTick(long l) {

            }


            @Override
            public void onFinish() {
                Intent intent;
                if (isLogin) {
                    intent = new Intent(SplashActivity.this, ShopListActivity.class);
                } else {
                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                }
                startActivity(intent);
                finish();
            }
        };
        countDownTimer.start();
    }
}


