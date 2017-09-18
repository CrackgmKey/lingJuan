package com.lingjuan.app.uitls;

import com.lingjuan.app.api.getLatest;
import com.lingjuan.app.view.ToStringConverterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by sks on 2017/8/19.
 */

public class HttpMethods {

    public static String BASE_URL = "http://api.tkjidi.com/";

    private static final int DEFAULT_TIMEOUT = 10;

    private Retrofit retrofit;
    private getLatest movieService;

    //构造方法私有
    private HttpMethods() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(new ToStringConverterFactory())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        movieService = retrofit.create(getLatest.class);
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder{
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    //获取单例
    public static HttpMethods getInstance(){
        return SingletonHolder.INSTANCE;
    }

    /**
     * 用于获取推举的数据
     * @param subscriber 由调用者传过来的观察者对象
     * @param count 获取长度
     */
    public void getTopMovie(Subscriber<String> subscriber,int count){
        movieService.getFen(count,6)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 用于搜索单个数据
     * @param subscriber 由调用者传过来的观察者对象
     * @param count 获取长度
     */
    public void getDanGe(Subscriber<String> subscriber,String count){
        movieService.getDanGe(count)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 用于分类的数据
     * @param subscriber 由调用者传过来的观察者对象
     * @param count 获取长度
     */
    public void getFewnLei(Subscriber<String> subscriber,int count,int page){
        movieService.getFen(count,page)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 用于特卖的数据
     * @param subscriber 由调用者传过来的观察者对象
     */
    public void getSanHeYi(Subscriber<String> subscriber,int page,int fangshi){
        movieService.getSanHeYi(page,fangshi)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 用于特卖的数据
     * @param subscriber 由调用者传过来的观察者对象
     */
    public void getSanHeYi(Subscriber<String> subscriber,int page,int fangshi,int cat){
        movieService.getSanHeYi(page,fangshi,cat)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 用于特卖的数据
     * @param subscriber 由调用者传过来的观察者对象
     */
    public void getYouHuii(Subscriber<String> subscriber,int page,int qishi,int jieshu) {
        movieService.getYouPin(page,qishi,jieshu)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 用于特卖的数据
     * @param subscriber 由调用者传过来的观察者对象
     */
    public void getYouHuii(Subscriber<String> subscriber,int page,int qishi,int jieshu,int sort,int cat) {
        movieService.getYouPin(page,qishi,jieshu,sort,cat)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 用于分类的数据
     * @param subscriber 由调用者传过来的观察者对象
     */
    public void getJiuJIud(Subscriber<String> subscriber,int page,int cat,int sort) {
        movieService.getJiuJiu(page,cat,sort)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 用于9.9的数据
     * @param subscriber 由调用者传过来的观察者对象
     */
    public void getJiuJIud(Subscriber<String> subscriber,int page,int cat) {
        movieService.getJiuJiu(page,cat)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 用于搜索页面的数据
     * @param subscriber 由调用者传过来的观察者对象
     */
    public void getSOuSuo(Subscriber<String> subscriber,int  path,String neirong,int fenlei) {
        movieService.getSuoSou(path,neirong,fenlei)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 用于搜索页面的热门搜索数据
     * @param subscriber 由调用者传过来的观察者对象
     */
    public void getReSouSuo(Subscriber<String> subscriber) {
        movieService.getReMen()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 用于搜索页面的热门搜索数据
     * @param subscriber 由调用者传过来的观察者对象
     */
    public void getGengXin(Subscriber<String> subscriber) {
        movieService.getUpade()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 用于搜索页面的热门搜索数据
     * @param subscriber 由调用者传过来的观察者对象
     */
    public void getLunBo(Subscriber<String> subscriber) {
        movieService.getLunBpo()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 用于Pid的获取的热门搜索数据
     * @param subscriber 由调用者传过来的观察者对象
     */
    public void getPid(Subscriber<String> subscriber,String name) {
        movieService.getYaoQingma(name)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 用于搜索页面的热门搜索数据
     * @param subscriber 由调用者传过来的观察者对象
     */
    public void getWEbView(Subscriber<String> subscriber,String str) {
        movieService.getScXiangPing(str)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

}
