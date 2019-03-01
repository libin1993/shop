package cn.cloudworkshop.shop.base;

import cn.cloudworkshop.shop.application.MyApp;
import cn.cloudworkshop.shop.utils.LogUtils;
import cn.cloudworkshop.shop.utils.SPUtils;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Author：Libin on 2018/10/25 15:24
 * Email：1993911441@qq.com
 * Describe：
 */
public class RxObserver<T extends BaseBean> implements Observer<T> {

    private Callback<T> mCallback;

    public RxObserver(Callback<T> callback) {
        mCallback = callback;

    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onNext(T t) {
        switch (t.getCode()) {
            case 10000:
                mCallback.onSuccess(t);
                break;
            case 10001:
                SPUtils.deleteStr(MyApp.getContext(), "token");
                mCallback.onFail(t.getMsg());
                break;
            default:
                mCallback.onFail(t.getMsg());
                break;
        }
    }

    @Override
    public void onError(@NonNull Throwable e) {
        e.printStackTrace();
        mCallback.onError();
    }

    @Override
    public void onComplete() {

    }

    public interface Callback<T> {
        void onSuccess(T t);

        void onFail(String msg);

        void onError();
    }
}