package syun.gankthings.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import syun.gankthings.R;
import syun.gankthings.fuli.FuliFragment;
import syun.gankthings.mvvmfuli.MeiziFragment;
import syun.gankthings.mvvmfuli.MeiziViewModel;
import syun.gankthings.util.ActivityUtils;
import syun.gankthings.util.ViewModelHolder;

public class MainActivity extends AppCompatActivity {
    public static final String MEIZI_VIEWMODEL_TAG = "MEIZI_VIEWMODEL_TAG";
    MeiziViewModel meiziViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentLayout,new FuliFragment()).commit();

//        MeiziFragment meiziFragment = findOrCreateViewFragment();
//        meiziViewModel = findOrCreateViewModel();
//        meiziFragment.setMeiziViewModel(meiziViewModel);

    }

    @NonNull
    private MeiziFragment findOrCreateViewFragment() {
        MeiziFragment meiziFragment =
                (MeiziFragment) getSupportFragmentManager().findFragmentById(R.id.contentLayout);
        if (meiziFragment == null) {
            // Create the fragment
            meiziFragment = MeiziFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), meiziFragment, R.id.contentLayout);
        }
        return meiziFragment;
    }

    private MeiziViewModel findOrCreateViewModel() {
        // In a configuration change we might have a ViewModel present. It's retained using the
        // Fragment Manager.
        @SuppressWarnings("unchecked")
        ViewModelHolder<MeiziViewModel> retainedViewModel =
                (ViewModelHolder<MeiziViewModel>) getSupportFragmentManager()
                        .findFragmentByTag(MEIZI_VIEWMODEL_TAG);

        if (retainedViewModel != null && retainedViewModel.getViewModel() != null) {
            // If the model was retained, return it.
            return retainedViewModel.getViewModel();
        } else {
            // There is no ViewModel yet, create it.
            MeiziViewModel viewModel = new MeiziViewModel(
                    getApplicationContext());
            // and bind it to this Activity's lifecycle using the Fragment Manager.
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    ViewModelHolder.createContainer(viewModel),
                    MEIZI_VIEWMODEL_TAG);
            return viewModel;
        }
    }
}
