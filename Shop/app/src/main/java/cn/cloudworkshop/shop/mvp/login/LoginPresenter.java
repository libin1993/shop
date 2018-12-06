package cn.cloudworkshop.shop.mvp.login;

import cn.cloudworkshop.shop.application.MyApp;
import cn.cloudworkshop.shop.base.BaseBean;
import cn.cloudworkshop.shop.base.BasePresenterImpl;
import cn.cloudworkshop.shop.base.RetrofitUtils;
import cn.cloudworkshop.shop.base.RxObserver;
import cn.cloudworkshop.shop.utils.LogUtils;
import cn.cloudworkshop.shop.utils.SPUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Author：Libin on 2018/11/28 14:14
 * Email：1993911441@qq.com
 * Describe：
 */
public class LoginPresenter extends BasePresenterImpl<LoginContract.View> implements LoginContract.Presenter {
    @Override
    public void login(String username, String pwd) {
        if (!isViewAttached())
            return;
        RetrofitUtils.getInstance()
                .request()
                .login(username, pwd,SPUtils.getStr(MyApp.getContext(),"client_id"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxObserver<>(new RxObserver.Callback<BaseBean>() {
                    @Override
                    public void onSuccess(BaseBean baseBean) {
                        getView().loginSuccess(baseBean.getToken());
                        getView().hideLoading();
                    }

                    @Override
                    public void onFail(String msg) {
                        getView().loginFail(msg);
                        getView().hideLoading();
                    }

                    @Override
                    public void onError() {
                        getView().loadError();
                    }
                }));
    }
}
