package cn.cloudworkshop.shop.utils;


import cn.cloudworkshop.shop.base.BaseBean;
import cn.cloudworkshop.shop.bean.CustomerListBean;
import cn.cloudworkshop.shop.bean.GuestRecordBean;
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
//    String HOST = "http://192.168.1.97/";
    String HOST = "http://api.cloudworkshop.cn/";

    @FormUrlEncoded
    @POST("admin/login/app_login_on")
    Observable<BaseBean> login(@Field("account_name") String username, @Field("account_password") String pwd);

    @FormUrlEncoded
    @POST("h5/login/isvalid_token")
    Observable<BaseBean> isLogin(@Field("token") String token);

    @FormUrlEncoded
    @POST("admin/shop/index")
    Observable<ShopListBean> shopList(@Field("token") String token);

    @FormUrlEncoded
    @POST("admin/mrj/get_visit_users")
    Observable<CustomerListBean> customerList(@Field("token") String token, @Field("company_id") int shopId, @Field("page") int page);

    @FormUrlEncoded
    @POST("admin/mrj/get_user_history")
    Observable<GuestRecordBean> guestRecord(@Field("token") String token, @Field("guest_id") int guestId, @Field("page") int page);
}



