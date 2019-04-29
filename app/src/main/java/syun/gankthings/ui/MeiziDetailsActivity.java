package syun.gankthings.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.appbar.AppBarLayout;
import androidx.core.view.ViewCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeTransition;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import syun.gankthings.R;

public class MeiziDetailsActivity extends AppCompatActivity {

    public static final String PIC = "pic";
    public static final String PIC_URL = "pic_url";
    public static final String PIC_TITLE = "pic_title";

    @BindView(R.id.title)
    TextView tvTitle;
    @BindView(R.id.iv_meizi_details)
    SimpleDraweeView ivMeiziDetails;
    @BindView(R.id.appbar)
    AppBarLayout appBar;

    String title;
    String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meizi_details);
        ButterKnife.bind(this);

        //解决页面跳转的时候SimpleDraweeView不显示图片
        getWindow().setSharedElementEnterTransition(DraweeTransition.createTransitionSet(ScalingUtils.ScaleType.CENTER_CROP, ScalingUtils.ScaleType.FIT_CENTER)); // 进入
        getWindow().setSharedElementReturnTransition(DraweeTransition.createTransitionSet(ScalingUtils.ScaleType.FIT_CENTER, ScalingUtils.ScaleType.CENTER_CROP)); // 返回
        //共享动画
        ViewCompat.setTransitionName(ivMeiziDetails,PIC);
        appBar.setAlpha(0.7f);
        initData();
        tvTitle.setText(title);
        Uri uri = Uri.parse(url);
//        ivMeiziDetails.setImageURI(uri);
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setProgressiveRenderingEnabled(true)
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(ivMeiziDetails.getController())
                .build();
        ivMeiziDetails.setController(controller);
//        Glide.with(this).load(uri).into(ivMeiziDetails);
    }

    private void initData() {
        Intent intent = getIntent();
        title = intent.getStringExtra(PIC_TITLE);
        url = intent.getStringExtra(PIC_URL);
    }
}
