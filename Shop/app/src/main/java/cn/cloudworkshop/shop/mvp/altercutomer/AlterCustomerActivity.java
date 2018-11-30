package cn.cloudworkshop.shop.mvp.altercutomer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.cloudworkshop.shop.R;
import cn.cloudworkshop.shop.base.BaseMvpActivity;
import cn.cloudworkshop.shop.bean.CustomerListBean;
import cn.cloudworkshop.shop.utils.DisplayUtils;
import cn.qqtheme.framework.picker.NumberPicker;
import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.widget.WheelView;

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
    EditText etguestPhone;
    @BindView(R.id.tv_guest_type)
    TextView tvGuestType;
    @BindView(R.id.tv_confirm_alter)
    TextView tvConfirmAlter;

    private String name;
    private int sex;
    private int age;
    private String phone;
    private int type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_customer);
        ButterKnife.bind(this);
//        getData();
        initView();
    }

    private void initView() {
        tvHeaderTitle.setText(R.string.guset_info);
        viewHeaderLine.setVisibility(View.VISIBLE);
    }

    private void getData() {
        CustomerListBean.DataBean guestBean = (CustomerListBean.DataBean) getIntent().getSerializableExtra("guest");
        name = guestBean.getGuest_name();
        sex = guestBean.getGuest_gender();
        age = guestBean.getGuest_age();
        phone = guestBean.getGuest_mobile();
        type = guestBean.getGuest_type();
    }

    @Override
    protected AlterCustomerContract.Presenter initPresenter() {
        return new AlterCustomerPresenter();
    }

    @Override
    public void loadSuccess(String msg) {

    }

    @Override
    public void loadFail(String msg) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void loadError() {

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
                break;
        }
    }

    /**
     * 选择性别
     */
    private void selectSex() {
        OptionPicker picker = new OptionPicker(this, new String[]{"男", "女"});
        picker.setCanceledOnTouchOutside(true);
        picker.setShadowColor(Color.RED, 40);
        picker.setSelectedIndex(0);
        picker.setCycleDisable(true);
        picker.setTextSize(13);
        picker.setTopLineColor(ContextCompat.getColor(this, R.color.gray_15));
        picker.setCancelTextColor(ContextCompat.getColor(this, R.color.gray_22));
        picker.setSubmitTextColor(ContextCompat.getColor(this, R.color.gray_22));
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
        picker.setDividerRatio(WheelView.DividerConfig.FILL);
        picker.setShadowColor(Color.RED, 40);
        picker.setSelectedIndex(1);
        picker.setCycleDisable(true);
        picker.setTextSize(11);
        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {
                type = index + 1;
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
        picker.setTextSize(14);
        picker.setSelectedItem(25);
        picker.setOnNumberPickListener(new NumberPicker.OnNumberPickListener() {
            @Override
            public void onNumberPicked(int index, Number item) {
                age = item.intValue();
            }
        });

        picker.show();
    }

}
