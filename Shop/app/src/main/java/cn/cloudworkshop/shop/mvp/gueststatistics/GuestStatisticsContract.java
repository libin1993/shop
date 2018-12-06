package cn.cloudworkshop.shop.mvp.gueststatistics;

import java.util.List;
import java.util.Map;

import cn.cloudworkshop.shop.base.BasePresenter;
import cn.cloudworkshop.shop.base.BaseView;
import cn.cloudworkshop.shop.bean.GuestCountBean;

/**
 * Author：Libin on 2018/12/5 10:04
 * Email：1993911441@qq.com
 * Describe：
 */
public class GuestStatisticsContract {
    interface View extends BaseView {

        void loadSuccess(List<GuestCountBean.DataBean.GuestFlowTrendBean> guestList);

        void loadFail(String msg);
    }

    interface Presenter extends BasePresenter<GuestStatisticsContract.View> {
        void initData(Map<String, Object> map);
    }
}
