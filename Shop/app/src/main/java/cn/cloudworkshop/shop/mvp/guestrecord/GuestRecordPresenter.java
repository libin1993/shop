package cn.cloudworkshop.shop.mvp.guestrecord;

import cn.cloudworkshop.shop.application.MyApp;
import cn.cloudworkshop.shop.base.BasePresenterImpl;
import cn.cloudworkshop.shop.base.RetrofitUtils;
import cn.cloudworkshop.shop.base.RxObserver;
import cn.cloudworkshop.shop.bean.CustomerListBean;
import cn.cloudworkshop.shop.bean.GuestRecordBean;
import cn.cloudworkshop.shop.utils.ObjectUtils;
import cn.cloudworkshop.shop.utils.SPUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Author：Libin on 2018/11/29 16:03
 * Email：1993911441@qq.com
 * Describe：
 */
public class GuestRecordPresenter extends BasePresenterImpl<GuestRecordContract.View> implements GuestRecordContract.Presenter {
    private int totalPage = 1;

    @Override
    public void initData(int guestId, int page, final int type) {
        if (!isViewAttached())
            return;
        if (page <= totalPage) {
            RetrofitUtils.getInstance()
                    .request()
                    .guestRecord(SPUtils.getStr(MyApp.getContext(), "token"), guestId, page)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new RxObserver<>(new RxObserver.Callback<GuestRecordBean>() {


                        @Override
                        public void onSuccess(GuestRecordBean guestRecordBean) {
                            if (guestRecordBean.getPages() != null) {
                                totalPage = guestRecordBean.getPages().getTotalpage();
                            }

                            if (ObjectUtils.isNotNull(guestRecordBean.getData())) {
                                getView().loadSuccess(guestRecordBean.getData());
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
                        public void onFail(String msg) {
                            switch (type) {
                                case 1:
                                    getView().finishRefresh();
                                    break;
                                case 2:
                                    getView().finishLoad();
                                    break;
                            }
                            getView().loadFail(msg);
                        }

                        @Override
                        public void onError() {
                            getView().loadError();
                        }

                    }));
        } else {
            getView().finishLoad();
        }
    }
}
