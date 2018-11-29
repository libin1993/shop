package cn.cloudworkshop.shop.mvp.shoplist;

import java.util.List;

import cn.cloudworkshop.shop.base.BasePresenter;
import cn.cloudworkshop.shop.base.BaseView;
import cn.cloudworkshop.shop.bean.ShopListBean;
import cn.cloudworkshop.shop.mvp.login.LoginContract;

/**
 * Author：Libin on 2018/11/28 16:41
 * Email：1993911441@qq.com
 * Describe：
 */
public class ShopListContract {
    interface View extends BaseView {

        void loadSuccess(List<ShopListBean.DataBean> shopList);

        void loadFail(String msg);
    }

    interface Presenter extends BasePresenter<ShopListContract.View> {
        void initData(String token);
    }
}
