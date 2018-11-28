package cn.cloudworkshop.shop.base;

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
        //mIResult.onStart();
    }

    @Override
    public void onNext(T t) {
        if (t.getCode() == 10000) {
            mCallback.onSuccess(t);
        } else {
            mCallback.onError(t.getMsg());
        }
    }


    @Override
    public void onError(@NonNull Throwable e) {
        e.printStackTrace();
        mCallback.onError(e.getMessage());
    }

    @Override
    public void onComplete() {


    }

    public interface Callback<T> {
        void onSuccess(T t);

        void onError(String msg);
    }
}