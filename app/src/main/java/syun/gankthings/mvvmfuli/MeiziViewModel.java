package syun.gankthings.mvvmfuli;

import android.content.Context;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import androidx.databinding.BaseObservable;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import syun.gankthings.api.GankApi;
import syun.gankthings.base.BaseObserver;
import syun.gankthings.bean.Meizi;
import syun.gankthings.http.HttpManager;

public class MeiziViewModel extends BaseObservable {

    public final ObservableList<Meizi> items = new ObservableArrayList<>();
    public final ObservableBoolean refresh = new ObservableBoolean(false);
    public final ObservableBoolean loadmore = new ObservableBoolean(false);
    public final ObservableBoolean isShowToast = new ObservableBoolean();
    public final ObservableField<String> errorMessage = new ObservableField<>();

    private Context context;
    private GankApi gankApi;
    private int page = 1;

    public MeiziViewModel(Context context) {
        this.context = context.getApplicationContext();
        gankApi = HttpManager.getInstance().create(GankApi.class);
    }


    public void start(){
        refresh.set(true);
        page = 1;
        getMeizi(page);
    }

    public void loadMore(){
        loadmore.set(true);
        getMeizi(++page);
    }

    private void getMeizi(final int page){
        gankApi.getMeizi(page).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new BaseObserver<List<Meizi>>() {
            @Override
            protected void onSuccess(List<Meizi> meizis) throws Exception {
                if(page==1) {
                    items.clear();
                    items.addAll(meizis);

                    refresh.set(false);
                }else{
                    items.addAll(meizis);
                    loadmore.set(false);
                }
            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                if(isNetWorkError){
                    errorMessage.set("网络异常");
                }else{
                    errorMessage.set("请求失败");
                }
                refresh.set(false);
                loadmore.set(false);
            }
        });
    }

}
