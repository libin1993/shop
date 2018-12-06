package cn.cloudworkshop.shop.mvp.timesstatistics;

import java.util.List;
import java.util.Map;

import cn.cloudworkshop.shop.base.BasePresenter;
import cn.cloudworkshop.shop.base.BaseView;
import cn.cloudworkshop.shop.bean.GuestCountBean;

/**
 * Author：Libin on 2018/12/5 17:13
 * Email：1993911441@qq.com
 * Describe：
 */
public class TimesStatisticsContact {
    interface View extends BaseView {

        void loadSuccess(List<GuestCountBean.DataBean.GuestArrivalTimesStatisticsBean> timesList);

        void loadFail(String msg);
    }

    interface Presenter extends BasePresenter<TimesStatisticsContact.View> {
        void initData(Map<String, Object> map);
    }
}
