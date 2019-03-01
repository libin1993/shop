package cn.cloudworkshop.shop.mvp.customerlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.cloudworkshop.shop.R;
import cn.cloudworkshop.shop.base.BaseMvpActivity;
import cn.cloudworkshop.shop.bean.CustomerListBean;
import cn.cloudworkshop.shop.mvp.altercutomer.AlterCustomerActivity;
import cn.cloudworkshop.shop.mvp.guestrecord.GuestRecordActivity;
import cn.cloudworkshop.shop.utils.GlideApp;
import cn.cloudworkshop.shop.utils.ToastUtils;
import cn.cloudworkshop.shop.view.LoadingView;

/**
 * Author：Libin on 2018/11/28 18:08
 * Email：1993911441@qq.com
 * Describe：访客列表
 */
public class CustomerListActivity extends BaseMvpActivity<CustomerListContract.Presenter> implements CustomerListContract.View {
    @BindView(R.id.rv_customer_list)
    RecyclerView rvCustomer;
    @BindView(R.id.sfr_customer)
    SmartRefreshLayout sfrCustomer;
    @BindView(R.id.loading_view)
    LoadingView loadingView;
    @BindView(R.id.iv_header_back)
    ImageView ivHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.view_header_line)
    View viewHeaderLine;
    private int shopId;
    private int page = 1;
    //0:init, 1: refresh, 2:load
    private int type;

    private List<CustomerListBean.DataBean> dataList = new ArrayList<>();
    private CommonAdapter<CustomerListBean.DataBean> adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        getData();
        initView();
    }

    private void getData() {
        shopId = getIntent().getIntExtra("shop_id", 0);
    }

    private void initView() {
        tvHeaderTitle.setText(R.string.shop_customer);
        viewHeaderLine.setVisibility(View.VISIBLE);
        loadingView.setState(LoadingView.State.LOADING);
        mPresenter.initData(shopId, page, type);
        rvCustomer.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CommonAdapter<CustomerListBean.DataBean>(this, R.layout.rv_customer_list_item, dataList) {
            @Override
            protected void convert(ViewHolder holder, CustomerListBean.DataBean dataBean, final int position) {
                ImageView ivAvatar = holder.getView(R.id.iv_customer_avatar);
                GlideApp.with(CustomerListActivity.this)
                        .load(dataBean.getRecent_visit_imgurl())
                        .into(ivAvatar);


                holder.setText(R.id.tv_name_customer, dataBean.getGuest_name());
                int age = dataBean.getGuest_age();
                String customerAge = "";
                if (age > 0) {
                    customerAge = String.valueOf(age);
                }
                holder.setText(R.id.tv_age_customer, customerAge);

                String sex;
                switch (dataBean.getGuest_gender()) {
                    case 1:
                        sex = "男";
                        break;
                    case 2:
                        sex = "女";
                        break;
                    default:
                        sex = "";
                        break;
                }
                holder.setText(R.id.tv_sex_customer, sex);

                String type = "";
                switch (dataBean.getGuest_type()) {
                    case 1:
                        type = "员工";
                        break;
                    case 2:
                        type = "非顾客";
                        break;
                    case 3:
                        type = "会员";
                        break;
                    case 4:
                        type = "老客";
                        break;
                    case 5:
                        type = "新客";
                        break;
                }
                holder.setText(R.id.tv_type_customer, type);
                holder.setText(R.id.tv_phone_customer, dataBean.getGuest_mobile());


                TextView tvRecord = holder.getView(R.id.tv_customer_record);
                TextView tvAlter = holder.getView(R.id.tv_alter_customer);

                tvRecord.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(CustomerListActivity.this, GuestRecordActivity.class);
                        intent.putExtra("guest_id", dataList.get(position).getGuest_id());
                        startActivity(intent);
                    }
                });


                tvAlter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(CustomerListActivity.this, AlterCustomerActivity.class);
                        intent.putExtra("guest", dataList.get(position));
                        startActivity(intent);


                    }
                });
            }
        };
        rvCustomer.setAdapter(adapter);


        sfrCustomer.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                page++;
                type = 2;
                mPresenter.initData(shopId, page, type);
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                page = 1;
                type = 1;
                mPresenter.initData(shopId, page, type);
            }
        });

        loadingView.setOnRetryListener(new LoadingView.OnRetryListener() {
            @Override
            public void onRetry() {
                loadingView.setState(LoadingView.State.LOADING);
                mPresenter.initData(shopId, page, type);
            }
        });
    }

    @Override
    protected CustomerListContract.Presenter initPresenter() {
        return new CustomerListPresenter();
    }

    @Override
    public void hideLoading() {
        loadingView.setState(LoadingView.State.LOAD_DONE);
    }

    @Override
    public void finishRefresh() {
        sfrCustomer.finishRefresh();
    }

    @Override
    public void finishLoad() {
        sfrCustomer.finishLoadMore();
    }

    @Override
    public void loadError() {
        loadingView.setState(LoadingView.State.LOAD_ERROR);
    }

    @Override
    public void loadSuccess(List<CustomerListBean.DataBean> customerList) {
        if (type != 2) {
            dataList.clear();
        }
        dataList.addAll(customerList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void loadFail(String msg) {
        ToastUtils.showToast(CustomerListActivity.this, msg);
    }

    @OnClick(R.id.iv_header_back)
    public void onViewClicked() {
        finish();
    }

    @Subscribe
    public void alterSuccess(String msg) {
        if ("alter_success".equals(msg)) {
            page = 1;
            type = 1;
            mPresenter.initData(shopId, page, type);
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
