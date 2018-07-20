package syun.gankthings.fuli;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import syun.gankthings.api.GankApi;
import syun.gankthings.base.BaseObserver;
import syun.gankthings.bean.BaseEntity;
import syun.gankthings.bean.Meizi;
import syun.gankthings.http.HttpManager;

public class FuliPresenter {

    private GankApi gankApi;

    public FuliPresenter() {
        gankApi = HttpManager.getInstance().create(GankApi.class);
    }

    public void getMeizi(final int page){
        gankApi.getMeizi(page).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new BaseObserver<List<Meizi>>() {
            @Override
            protected void onSuccess(List<Meizi> meizis) throws Exception {
                Log.d("FuliPresenter","onSuccess:"+meizis.size());
                if(page==1) {
                    EventBus.getDefault().post(new FuliEvent.LoadMeiziDataEvent(meizis));
                }else{
                    EventBus.getDefault().post(new FuliEvent.LoadMoreMeiziDataEvent(meizis));
                }
            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                if(isNetWorkError){
                    EventBus.getDefault().post(new FuliEvent.LoadDataErrorEvent("网络异常"));
                }else{
                    EventBus.getDefault().post(new FuliEvent.LoadDataErrorEvent("请求失败"));
                }
            }
        });
    }
}
