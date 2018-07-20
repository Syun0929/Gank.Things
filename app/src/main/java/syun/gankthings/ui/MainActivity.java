package syun.gankthings.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.DraweeTransition;

import syun.gankthings.R;
import syun.gankthings.fuli.FuliFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentLayout,new FuliFragment()).commit();
    }
}
