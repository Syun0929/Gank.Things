package syun.gankthings.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
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

    String title;
    String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meizi_details);
        ButterKnife.bind(this);
        initData();
        tvTitle.setText(title);
        ViewCompat.setTransitionName(ivMeiziDetails,PIC);
        Uri uri = Uri.parse(url);
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setProgressiveRenderingEnabled(true)
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(ivMeiziDetails.getController())
                .build();
        ivMeiziDetails.setController(controller);
    }

    private void initData() {
        Intent intent = getIntent();
        title = intent.getStringExtra(PIC_TITLE);
        url = intent.getStringExtra(PIC_URL);
    }
}
