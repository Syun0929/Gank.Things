package syun.gankthings.mvvmfuli;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;

import java.lang.ref.WeakReference;
import java.util.List;

import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.app.SharedElementCallback;
import androidx.databinding.BaseObservable;
import syun.gankthings.bean.Meizi;
import syun.gankthings.ui.MeiziDetailsActivity;

public class MeiziItemViewModel extends BaseObservable {

    private Context context;
    private Activity activity;
    private  SimpleDraweeView view;

    private Meizi meizi;

    public MeiziItemViewModel(Context context) {
        this.context = context.getApplicationContext();
    }

    public void meiziClicked(){
        if(meizi == null){return;}
        Log.d("MeiziItemViewModel","meiziClicked");

        startDetailActivity(meizi,view,activity);
    }

    private void startDetailActivity(Meizi meizi, View transitView , Activity activity){
        Intent intent = new Intent(activity, MeiziDetailsActivity.class);
        intent.putExtra(MeiziDetailsActivity.PIC_TITLE,meizi.getDesc());
        intent.putExtra(MeiziDetailsActivity.PIC_URL,meizi.getUrl());
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity, transitView, MeiziDetailsActivity.PIC);
        //解决Android 7.0以上返回跳转前页面完成过场动画之后SimpleDraweeView不显示图片
        ActivityCompat.setExitSharedElementCallback(activity, new SharedElementCallback() {
            @Override
            public void onSharedElementEnd(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
                super.onSharedElementEnd(sharedElementNames, sharedElements, sharedElementSnapshots);
                for (View view : sharedElements) {
                    if (view instanceof SimpleDraweeView) {
                        view.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        ActivityCompat.startActivity(activity,intent,optionsCompat.toBundle());
    }

    public void setMeizi(Meizi meizi) {
        this.meizi = meizi;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setView(SimpleDraweeView view) {
        this.view = view;
    }


}
