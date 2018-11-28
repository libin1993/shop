package cn.cloudworkshop.shop.mvp.customerlist;

import android.util.Log;

import cn.cloudworkshop.shop.application.MyApp;
import cn.cloudworkshop.shop.base.BasePresenterImpl;
import cn.cloudworkshop.shop.base.RetrofitUtils;
import cn.cloudworkshop.shop.base.RxObserver;
import cn.cloudworkshop.shop.bean.CustomerListBean;
import cn.cloudworkshop.shop.utils.ObjectUtils;
import cn.cloudworkshop.shop.utils.SPUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Author：Libin on 2018/11/28 18:08
 * Email：1993911441@qq.com
 * Describe：
 */
public class CustomerListPresenter extends BasePresenterImpl<CustomerListContract.View> implements CustomerListContract.Presenter {
    private int totalPage = 1;

    @Override
    public void initData(int shopId, final int page, final int type) {
        if (page <= totalPage) {
            RetrofitUtils.getInstance()
                    .request()
                    .customerList(SPUtils.getStr(MyApp.getContext(), "token"), shopId, page)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new RxObserver<>(new RxObserver.Callback<CustomerListBean>() {

                        @Override
                        public void onSuccess(CustomerListBean customerListBean) {
                            if (customerListBean.getPages() != null) {
                                totalPage = customerListBean.getPages().getTotalpage();
                            }

                            if (ObjectUtils.isNotNull(customerListBean.getData())) {
                                getView().loadSuccess(customerListBean.getData());
                            }

                            switch (type) {
                                case 0:
                                    getView().hideLoading();
                                    break;
                                case 1:
                                    getView().finishRefresh();
                                    break;
                                case 2:
                                    getView().finishLoad();
                                    break;
                            }
                        }

                        @Override
                        public void onError(String msg) {
                            switch (type) {
                                case 1:
                                    getView().finishRefresh();
                                    break;
                                case 2:
                                    getView().finishLoad();
                                    break;
                            }
                            getView().loadError();
                        }


                    }));
        } else {
            getView().finishLoad();
        }

    }
}
