package cn.cloudworkshop.shop.mvp.altercutomer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.cloudworkshop.shop.R;
import cn.cloudworkshop.shop.base.BaseMvpActivity;
import cn.cloudworkshop.shop.bean.CustomerListBean;
import cn.cloudworkshop.shop.utils.PhoneNumberUtils;
import cn.cloudworkshop.shop.utils.SPUtils;
import cn.cloudworkshop.shop.utils.ToastUtils;
import cn.cloudworkshop.shop.view.LoadingView;
import cn.qqtheme.framework.picker.NumberPicker;
import cn.qqtheme.framework.picker.OptionPicker;

/**
 * Author：Libin on 2018/11/30 16:05
 * Email：1993911441@qq.com
 * Describe：
 */
public class AlterCustomerActivity extends BaseMvpActivity<AlterCustomerContract.Presenter> implements AlterCustomerContract.View {
    @BindView(R.id.iv_header_back)
    ImageView ivHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.view_header_line)
    View viewHeaderLine;
    @BindView(R.id.et_customer_name)
    EditText etGuestName;
    @BindView(R.id.tv_guest_sex)
    TextView tvGuestSex;
    @BindView(R.id.tv_guest_age)
    TextView tvGuestAge;
    @BindView(R.id.et_customer_phone)
    EditText etGuestPhone;
    @BindView(R.id.tv_guest_type)
    TextView tvGuestType;
    @BindView(R.id.tv_confirm_alter)
    TextView tvConfirmAlter;
    @BindView(R.id.loading_view)
    LoadingView loadingView;

    private String name;
    private int sex;
    private int age;
    private String phone;
    private int type;
    private String guestId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_customer);
        ButterKnife.bind(this);
        getData();
        initView();
    }

    private void initView() {
        tvHeaderTitle.setText(R.string.guset_info);
        viewHeaderLine.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(name)) {
            etGuestName.setText(name);
        }

        String guestSex = "";
        switch (sex) {
            case 1:
                guestSex = "男";
                break;
            case 2:
                guestSex = "女";
                break;
        }
        tvGuestSex.setText(guestSex);


        if (age != 0) {
            tvGuestAge.setText(String.valueOf(age));
        }

        if (!TextUtils.isEmpty(phone)) {
            etGuestPhone.setText(phone);
        }


        String guestType = "";
        switch (type) {
            case 1:
                guestType = "员工";
                break;
            case 2:
                guestType = "非顾客";
                break;
            case 3:
                guestType = "会员";
                break;
            case 4:
                guestType = "老客";
                break;
            case 5:
                guestType = "新客";
                break;
        }

        tvGuestType.setText(guestType);

    }

    private void getData() {
        CustomerListBean.DataBean guestBean = (CustomerListBean.DataBean) getIntent().getSerializableExtra("guest");
        name = guestBean.getGuest_name();
        sex = guestBean.getGuest_gender();
        age = guestBean.getGuest_age();
        phone = guestBean.getGuest_mobile();
        type = guestBean.getGuest_type();
        guestId = guestBean.getGuest_id();
    }

    @Override
    protected AlterCustomerContract.Presenter initPresenter() {
        return new AlterCustomerPresenter();
    }

    @Override
    public void loadSuccess(String msg) {
        ToastUtils.showToast(this, msg);
        EventBus.getDefault().post("alter_success");
    }

    @Override
    public void loadFail(String msg) {
        ToastUtils.showToast(this, msg);
    }

    @Override
    public void hideLoading() {
        loadingView.setState(LoadingView.State.LOAD_DONE);
    }

    @Override
    public void loadError() {
        loadingView.setState(LoadingView.State.LOAD_ERROR);
    }

    @OnClick({R.id.iv_header_back, R.id.tv_guest_sex, R.id.tv_guest_age, R.id.tv_guest_type, R.id.tv_confirm_alter})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_header_back:
                finish();
                break;
            case R.id.tv_guest_sex:
                selectSex();
                break;
            case R.id.tv_guest_age:
                selectAge();
                break;
            case R.id.tv_guest_type:
                selectType();
                break;
            case R.id.tv_confirm_alter:
                confirmAlter();
                break;
        }
    }

    private void confirmAlter() {
        Map<String, Object> map = new HashMap<>();
        map.put("token", SPUtils.getStr(this, "token"));
        map.put("guest_id", guestId);

        name = etGuestName.getText().toString().trim();
        if (!TextUtils.isEmpty(name)) {
            map.put("guest_name", name);
        }

        if (sex != 0) {
            map.put("guest_gender", sex);
        }

        if (age != 0) {
            map.put("guest_age", age);
        }

        phone = etGuestPhone.getText().toString().trim();
        if (!TextUtils.isEmpty(phone)) {
            if (PhoneNumberUtils.judgePhoneNumber(phone)) {
                map.put("guest_mobile", phone);
            } else {
                ToastUtils.showToast(this, "请输入正确的手机号码");
            }
        }

        if (type != 0) {
            map.put("guest_type", type);
        }

        if (map.size() > 2) {
            mPresenter.submitData(map);
        } else {
            ToastUtils.showToast(this, "请输入相关信息");
        }

    }

    /**
     * 选择性别
     */
    private void selectSex() {
        OptionPicker picker = new OptionPicker(this, new String[]{"男", "女"});
        picker.setCanceledOnTouchOutside(true);

        picker.setSelectedIndex(0);
        picker.setCycleDisable(true);
        picker.setTextSize(14);
        picker.setTopLineColor(ContextCompat.getColor(this, R.color.gray_15));
        picker.setCancelTextColor(ContextCompat.getColor(this, R.color.gray_22));
        picker.setSubmitTextColor(ContextCompat.getColor(this, R.color.gray_22));

        picker.setDividerColor(ContextCompat.getColor(this, R.color.gray_ed));
        picker.setTextColor(ContextCompat.getColor(this, R.color.gray_22), ContextCompat.getColor(this, R.color.gray_6a));
        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {
                sex = index + 1;
                switch (sex) {
                    case 1:
                        tvGuestSex.setText(R.string.man);
                        break;
                    case 2:
                        tvGuestSex.setText(R.string.woman);
                        break;
                }
            }
        });
        picker.show();
    }

    /**
     * 选择类型
     */
    private void selectType() {
        OptionPicker picker = new OptionPicker(this, new String[]{"员工", "非顾客", "会员", "老客", "新客"});
        picker.setCanceledOnTouchOutside(true);
        picker.setSelectedIndex(0);
        picker.setCycleDisable(true);
        picker.setTextSize(14);
        picker.setTopLineColor(ContextCompat.getColor(this, R.color.gray_15));
        picker.setCancelTextColor(ContextCompat.getColor(this, R.color.gray_22));
        picker.setSubmitTextColor(ContextCompat.getColor(this, R.color.gray_22));
        picker.setDividerColor(ContextCompat.getColor(this, R.color.gray_ed));
        picker.setTextColor(ContextCompat.getColor(this, R.color.gray_22), ContextCompat.getColor(this, R.color.gray_6a));
        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {
                type = index + 1;

                String typeName;
                switch (type) {
                    case 1:
                        typeName = "员工";
                        break;
                    case 2:
                        typeName = "非顾客";
                        break;
                    case 3:
                        typeName = "会员";
                        break;
                    case 4:
                        typeName = "老客";
                        break;
                    case 5:
                        typeName = "新客";
                        break;
                    default:
                        typeName = "保密";
                        break;
                }
                tvGuestType.setText(typeName);
            }
        });
        picker.show();
    }


    /**
     * 选择年龄
     */
    private void selectAge() {
        NumberPicker picker = new NumberPicker(this);
        picker.setOffset(2);//偏移量
        picker.setRange(1, 100);//数字范围
        picker.setSelectedItem(25);
        picker.setCanceledOnTouchOutside(true);
        picker.setCycleDisable(true);
        picker.setTextSize(14);
        picker.setTopLineColor(ContextCompat.getColor(this, R.color.gray_15));
        picker.setCancelTextColor(ContextCompat.getColor(this, R.color.gray_22));
        picker.setSubmitTextColor(ContextCompat.getColor(this, R.color.gray_22));
        picker.setDividerColor(ContextCompat.getColor(this, R.color.gray_ed));
        picker.setTextColor(ContextCompat.getColor(this, R.color.gray_22), ContextCompat.getColor(this, R.color.gray_6a));
        picker.setOnNumberPickListener(new NumberPicker.OnNumberPickListener() {
            @Override
            public void onNumberPicked(int index, Number item) {
                age = item.intValue();
                tvGuestAge.setText(String.valueOf(age));
            }
        });

        picker.show();
    }

}
