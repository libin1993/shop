package cn.cloudworkshop.shop.mvp.shoplist;

import java.util.List;

import cn.cloudworkshop.shop.base.BaseBean;
import cn.cloudworkshop.shop.base.BasePresenterImpl;
import cn.cloudworkshop.shop.base.RetrofitUtils;
import cn.cloudworkshop.shop.base.RxObserver;
import cn.cloudworkshop.shop.bean.ShopListBean;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Author：Libin on 2018/11/28 16:42
 * Email：1993911441@qq.com
 * Describe：
 */
public class ShopListPresenter extends BasePresenterImpl<ShopListContract.View> implements ShopListContract.Presenter {
    @Override
    public void initData(String token) {
        if (!isViewAttached())
            return;
        RetrofitUtils.getInstance()
                .request()
                .shopList(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxObserver<>(new RxObserver.Callback<ShopListBean>() {
                    @Override
                    public void onSuccess(ShopListBean shopListBean) {
                        getView().hideLoading();
                        getView().loadSuccess(shopListBean.getData());
                    }

                    @Override
                    public void onFail(String msg) {
                        getView().loadFail(msg);
                    }

                    @Override
                    public void onError() {
                        getView().loadError();
                    }

                }));
    }
}
