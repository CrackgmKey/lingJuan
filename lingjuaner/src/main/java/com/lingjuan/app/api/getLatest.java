package com.lingjuan.app.api;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by sks on 2017/8/19.
 */

public interface getLatest {

    //查询单个接口
    //	http://api.tkjidi.com/getGoodInfo?appkey=7bc56a1a59cd14b7257b1ca3ded90eda&id=xx
    @GET("http://api.tkjidi.com/getGoodInfo?appkey=7bc56a1a59cd14b7257b1ca3ded90eda&id=2")
    Observable<String> getDanGe(@Query("id") String id);
    //推举接口
    //http://api.tkjidi.com/getGoodsLink?appkey=7bc56a1a59cd14b7257b1ca3ded90eda&type=www_lingquan&page=2
    @GET("getGoodsLink?appkey=7bc56a1a59cd14b7257b1ca3ded90eda&type=www_lingquan")
    Observable<String> getLates(@Query("page") int page);
    //分类接口
    //http://api.tkjidi.com/getGoodsLink?appkey=您的appkey&type=classify&cid=1
    @GET("getGoodsLink?appkey=7bc56a1a59cd14b7257b1ca3ded90eda&type=classify")
    Observable<String> getFen(@Query("cid") int cid,@Query("page") int page);
    //分类接口
    //http://api.tkjidi.com/getGoodsLink?appkey=7bc56a1a59cd14b7257b1ca3ded90eda&type=www_lingquan&page=1
    @GET("getGoodsLink?appkey=7bc56a1a59cd14b7257b1ca3ded90eda&type=top100")
    Observable<String> getTeMAi(@Query("page") int page);
    //9.9
    //http://openapi.qingtaoke.com/qingsoulist?sort=1&page=1&app_key=LdMnLUGl&v=1.0&cat=2&min_price=1&max_price=100&new=0&is_ju=1&is_tqg=0
    @GET("http://openapi.qingtaoke.com/qingsoulist?sort=2&page=1&app_key=LdMnLUGl&v=1.0&cat=0&min_price=1&max_price=100&new=0&is_ju=0&is_tqg=0")
    Observable<String> getYouPin(@Query("page") int page,@Query("min_price") int min_price,@Query("max_price") int max_price);


    //9.9新街口
    //http://openapi.qingtaoke.com/qingsoulist?sort=1&page=1&app_key=LdMnLUGl&v=1.0&cat=2&min_price=1&max_price=100&new=0&is_ju=1&is_tqg=0
    @GET("http://openapi.qingtaoke.com/qingsoulist?sort=2&page=1&app_key=LdMnLUGl&v=1.0&cat=0&min_price=1&max_price=100&new=0&is_ju=0&is_tqg=0")
    Observable<String> getYouPin(@Query("page") int page,@Query("min_price") int min_price,@Query("max_price") int max_price,@Query("sort") int sort,@Query("cat") int cat);

    //优品
    //http://openapi.qingtaoke.com/qingsoulist?sort=1&page=1&app_key=LdMnLUGl&v=1.0&cat=2&min_price=1&max_price=100&new=0&is_ju=1&is_tqg=0
    @GET("http://openapi.qingtaoke.com/qingsoulist?sort=2&app_key=LdMnLUGl&v=1.0&cat=0&min_price=1&max_price=9999&new=0&is_ju=0&is_tqg=0")
    Observable<String> getJiuJiu(@Query("page") int page,@Query("cat") int cat,@Query("sort") int sort);


    //优品
    //http://openapi.qingtaoke.com/qingsoulist?sort=1&page=1&app_key=LdMnLUGl&v=1.0&cat=2&min_price=1&max_price=100&new=0&is_ju=1&is_tqg=0
    @GET("http://openapi.qingtaoke.com/qingsoulist?sort=2&app_key=LdMnLUGl&v=1.0&cat=0&min_price=1&max_price=9999&new=0&is_ju=0&is_tqg=0")
    Observable<String> getJiuJiu(@Query("page") int page,@Query("cat") int cat);

    //爆款 特价 前100
    //http://openapi.qingtaoke.com/qingsoulist?sort=1&page=1&app_key=LdMnLUGl&v=1.0&cat=2&min_price=1&max_price=100&new=0&is_ju=1&is_tqg=0
    @GET("http://openapi.qingtaoke.com/qingsoulist?sort=5&app_key=LdMnLUGl&v=1.0&cat=0&min_price=1&max_price=1000&new=0&is_ju=0&is_tqg=0")
    Observable<String> getSanHeYi(@Query("page") int page,@Query("sort") int sort);

    //爆款 特价 前100
    //http://openapi.qingtaoke.com/qingsoulist?sort=1&page=1&app_key=LdMnLUGl&v=1.0&cat=2&min_price=1&max_price=100&new=0&is_ju=1&is_tqg=0
    @GET("http://openapi.qingtaoke.com/qingsoulist?sort=5&app_key=LdMnLUGl&v=1.0&cat=0&min_price=1&max_price=1000&new=0&is_ju=0&is_tqg=0")
    Observable<String> getSanHeYi(@Query("page") int page,@Query("sort") int sort,@Query("cat") int cat);


    //特价搜索
    //http://openapi.qingtaoke.com/qingsoulist?sort=1&page=1&app_key=LdMnLUGl&v=1.0&cat=2&min_price=1&max_price=100&new=0&is_ju=1&is_tqg=0
    @GET("http://openapi.qingtaoke.com/search?s_type=2&key_word=你好&app_key=LdMnLUGl&page=1&v=1.0&cat=0&min_price=1&max_price=9999&sort=1&is_ju=0&is_tqg=0")
    Observable<String> getSuoSou(@Query("page") int page,@Query("key_word") String neirng,@Query("cat") int car);


    //热门搜索
    //http://openapi.qingtaoke.com/qingsoulist?sort=1&page=1&app_key=LdMnLUGl&v=1.0&cat=2&min_price=1&max_price=100&new=0&is_ju=1&is_tqg=0
    @GET("http://openapi.qingtaoke.com/hot?app_key=LdMnLUGl&v=1.0&t=1")
    Observable<String> getReMen();

    //热门搜索
    //http://openapi.qingtaoke.com/qingsoulist?sort=1&page=1&app_key=LdMnLUGl&v=1.0&cat=2&min_price=1&max_price=100&new=0&is_ju=1&is_tqg=0
    @GET("http://www.aiboyy.pw/gx/dazhong.txt")
    Observable<String> getUpade();

    //热门搜索
    //http://openapi.qingtaoke.com/qingsoulist?sort=1&page=1&app_key=LdMnLUGl&v=1.0&cat=2&min_price=1&max_price=100&new=0&is_ju=1&is_tqg=0
    @GET("http://hws.m.taobao.com/cache/mtop.wdetail.getItemDescx/4.1/?data={item_num_id:550060277274}")
    Observable<String> getScXiangPing(@Query("data") String data);

    //导航页和轮播
    //http://openapi.qingtaoke.com/qingsoulist?sort=1&page=1&app_key=LdMnLUGl&v=1.0&cat=2&min_price=1&max_price=100&new=0&is_ju=1&is_tqg=0
    @GET("http://www.aiboyy.pw/gx/weipic.txt")
    Observable<String> getLunBpo();


    //导航页和轮播
    //http://openapi.qingtaoke.com/qingsoulist?sort=1&page=1&app_key=LdMnLUGl&v=1.0&cat=2&min_price=1&max_price=100&new=0&is_ju=1&is_tqg=0
    @GET("http://www.aiboyy.pw/lingjuan/yewu/serlno.php?name=CPdVuO")
    Observable<String> getYaoQingma(@Query("name") String data);
}
