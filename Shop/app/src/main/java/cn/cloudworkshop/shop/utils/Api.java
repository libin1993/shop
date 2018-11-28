package cn.cloudworkshop.shop.utils;


import cn.cloudworkshop.shop.base.BaseBean;
import cn.cloudworkshop.shop.bean.CustomerListBean;
import cn.cloudworkshop.shop.bean.ShopListBean;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Author：Libin on 2018/10/11 09:34
 * Email：1993911441@qq.com
 * Describe：
 */
public interface Api {
    String HOST = "http://192.168.1.97/";

    @FormUrlEncoded
    @POST("index.php/admin/login/app_login_on")
    Observable<BaseBean> login(@Field("account_name") String username, @Field("account_password") String pwd);

    @FormUrlEncoded
    @POST("index.php/h5/login/isvalid_token")
    Observable<BaseBean> isLogin(@Field("token") String token);

    @FormUrlEncoded
    @POST("index.php/admin/shop/index")
    Observable<ShopListBean> shopList(@Field("token") String token);

    @FormUrlEncoded
    @POST("index.php/admin/mrj/get_visit_users")
    Observable<CustomerListBean> customerList(@Field("token") String token, @Field("company_id") int shopId, @Field("page") int page);
}



