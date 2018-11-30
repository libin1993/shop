package cn.cloudworkshop.shop.mvp.altercutomer;

import java.util.List;
import java.util.Map;

import cn.cloudworkshop.shop.base.BasePresenter;
import cn.cloudworkshop.shop.base.BaseView;
import cn.cloudworkshop.shop.bean.ShopListBean;
import cn.cloudworkshop.shop.mvp.shoplist.ShopListContract;

/**
 * Author：Libin on 2018/11/30 15:59
 * Email：1993911441@qq.com
 * Describe：
 */
public class AlterCustomerContract {
    interface View extends BaseView {

        void loadSuccess(String msg);

        void loadFail(String msg);
    }

    interface Presenter extends BasePresenter<AlterCustomerContract.View> {
        void submitData(Map<String, Object> map);
    }
}
