package cn.cloudworkshop.shop.mvp.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.cloudworkshop.shop.R;
import cn.cloudworkshop.shop.base.BaseMvpActivity;
import cn.cloudworkshop.shop.mvp.shoplist.ShopListActivity;
import cn.cloudworkshop.shop.utils.LogUtils;
import cn.cloudworkshop.shop.utils.SPUtils;
import cn.cloudworkshop.shop.utils.ToastUtils;
import cn.cloudworkshop.shop.view.LoadingView;

/**
 * Author：Libin on 2018/11/28 13:56
 * Email：1993911441@qq.com
 * Describe：登录
 */
public class LoginActivity extends BaseMvpActivity<LoginContract.Presenter> implements LoginContract.View {
    @BindView(R.id.et_user_name)
    EditText etUserName;
    @BindView(R.id.et_user_password)
    EditText etPwd;
    @BindView(R.id.iv_login)
    ImageView ivLogin;
    @BindView(R.id.loading_view)
    LoadingView loadingView;

    //是否输入账号
    private boolean isNo;
    //是否输入密码
    private boolean isPwd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
    }

    /**
     * 监听输入账号密码
     */
    private void initView() {
        ivLogin.setEnabled(false);
        etUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                isNo = !TextUtils.isEmpty(editable.toString());

                inInput();
            }

        });

        etPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                isPwd = !TextUtils.isEmpty(editable.toString());
                inInput();
            }
        });
    }

    /**
     * 是否输入账号密码
     */
    private void inInput() {
        if (isNo && isPwd) {
            ivLogin.setEnabled(true);
        } else {
            ivLogin.setEnabled(false);
        }
    }

    @Override
    protected LoginContract.Presenter initPresenter() {
        return new LoginPresenter();
    }

    @Override
    public void loginSuccess(String token) {
        SPUtils.saveStr(LoginActivity.this, "token", token);
        startActivity(new Intent(LoginActivity.this, ShopListActivity.class));
        finish();
    }

    @Override
    public void loginFail(String msg) {
        ToastUtils.showToast(this, msg);
    }

    @OnClick(R.id.iv_login)
    public void onViewClicked() {
        loadingView.setState(LoadingView.State.LOADING);
        mPresenter.login(etUserName.getText().toString(), etPwd.getText().toString());
    }

    @Override
    public void hideLoading() {
        loadingView.setState(LoadingView.State.LOAD_DONE);
    }

    @Override
    public void loadError() {
        loadingView.setState(LoadingView.State.LOAD_ERROR);
    }
}
