package cn.cloudworkshop.shop.mvp.gueststatistics;

import java.util.Map;

import cn.cloudworkshop.shop.base.BasePresenterImpl;
import cn.cloudworkshop.shop.base.RetrofitUtils;
import cn.cloudworkshop.shop.base.RxObserver;
import cn.cloudworkshop.shop.bean.GuestCountBean;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Author：Libin on 2018/12/5 10:29
 * Email：1993911441@qq.com
 * Describe：
 */
public class GuestStatisticsPresenter extends BasePresenterImpl<GuestStatisticsContract.View> implements GuestStatisticsContract.Presenter {
    @Override
    public void initData(Map<String, Object> map) {
        if (!isViewAttached())
            return;
        RetrofitUtils.getInstance()
                .request()
                .guestCount(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxObserver<>(new RxObserver.Callback<GuestCountBean>() {

                    @Override
                    public void onSuccess(GuestCountBean guestCountBean) {
                        getView().hideLoading();
                        getView().loadSuccess(guestCountBean.getData().getGuest_flow_trend());
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
