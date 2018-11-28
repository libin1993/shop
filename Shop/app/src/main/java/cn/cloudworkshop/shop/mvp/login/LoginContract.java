package cn.cloudworkshop.shop.mvp.login;

import java.util.List;

import cn.cloudworkshop.shop.base.BasePresenter;
import cn.cloudworkshop.shop.base.BaseView;

/**
 * Author：Libin on 2018/11/28 14:15
 * Email：1993911441@qq.com
 * Describe：
 */
public class LoginContract {
    interface View extends BaseView {
        void loginSuccess(String token);

        void loginFail(String msg);
    }

    interface Presenter extends BasePresenter<View> {
        void login(String username, String pwd);
    }
}
