package cn.cloudworkshop.shop.mvp.customerlist;

import java.util.List;

import cn.cloudworkshop.shop.base.BasePresenter;
import cn.cloudworkshop.shop.base.BaseView;
import cn.cloudworkshop.shop.bean.CustomerListBean;

/**
 * Author：Libin on 2018/11/28 18:08
 * Email：1993911441@qq.com
 * Describe：
 */
public class CustomerListContract {
    interface View extends BaseView {

        void finishRefresh();

        void finishLoad();

        void loadSuccess(List<CustomerListBean.DataBean> customerList);

        void loadFail(String msg);
    }

    interface Presenter extends BasePresenter<View> {
        void initData(int shopId, int page, int type);
    }
}
