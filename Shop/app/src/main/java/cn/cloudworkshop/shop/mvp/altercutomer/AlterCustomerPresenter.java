package cn.cloudworkshop.shop.mvp.altercutomer;

import java.util.Map;

import cn.cloudworkshop.shop.base.BaseBean;
import cn.cloudworkshop.shop.base.BasePresenterImpl;
import cn.cloudworkshop.shop.base.RetrofitUtils;
import cn.cloudworkshop.shop.base.RxObserver;
import cn.cloudworkshop.shop.bean.ShopListBean;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Author：Libin on 2018/11/30 16:01
 * Email：1993911441@qq.com
 * Describe：
 */
public class AlterCustomerPresenter extends BasePresenterImpl<AlterCustomerContract.View> implements AlterCustomerContract.Presenter {

    @Override
    public void submitData(Map<String, Object> map) {
        if (!isViewAttached())
            return;
        RetrofitUtils.getInstance()
                .request()
                .alterCustomer(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxObserver<>(new RxObserver.Callback<BaseBean>() {
                    @Override
                    public void onSuccess(BaseBean baseBean) {
                        getView().hideLoading();
                        getView().loadSuccess(baseBean.getMsg());
                    }

                    @Override
                    public void onFail(String msg) {
                        getView().hideLoading();
                        getView().loadFail(msg);
                    }

                    @Override
                    public void onError() {
                        getView().loadError();
                    }
                }));
    }
}
