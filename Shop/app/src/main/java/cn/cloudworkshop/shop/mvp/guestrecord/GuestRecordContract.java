package cn.cloudworkshop.shop.mvp.guestrecord;

import java.util.List;

import cn.cloudworkshop.shop.base.BasePresenter;
import cn.cloudworkshop.shop.base.BaseView;
import cn.cloudworkshop.shop.bean.CustomerListBean;
import cn.cloudworkshop.shop.bean.GuestRecordBean;
import cn.cloudworkshop.shop.mvp.customerlist.CustomerListContract;

/**
 * Author：Libin on 2018/11/29 16:01
 * Email：1993911441@qq.com
 * Describe：
 */
public class GuestRecordContract {
    interface View extends BaseView {

        void finishRefresh();

        void finishLoad();

        void loadSuccess(List<GuestRecordBean.DataBean> recordList);

        void loadFail(String msg);
    }

    interface Presenter extends BasePresenter<GuestRecordContract.View> {
        void initData(int guestId, int page, int type);
    }
}
