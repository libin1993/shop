package cn.cloudworkshop.shop.base;

import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * Author：Libin on 2018/10/25 15:17
 * Email：1993911441@qq.com
 * Describe：
 */
public class BaseFragment extends Fragment {
    private BaseActivity mActivity;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (BaseActivity) context;
    }
}



