package syun.gankthings.fuli;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.scwang.smartrefresh.header.BezierCircleHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import syun.gankthings.GankApp;
import syun.gankthings.R;
import syun.gankthings.adapter.MeiziAdapter;
import syun.gankthings.base.BaseObserver;
import syun.gankthings.bean.BaseEntity;
import syun.gankthings.bean.Meizi;
import syun.gankthings.listener.CardViewOnClickListener;
import syun.gankthings.ui.MeiziDetailsActivity;

public class FuliFragment extends Fragment {

    @BindView(R.id.fuli_recyclerView)
    RecyclerView fuliRecyclerView;
    @BindView(R.id.fuli_refreshLayout)
    SmartRefreshLayout fuliRefreshLayout;
    private Unbinder unbinder;
    MeiziAdapter meiziAdapter;

    FuliPresenter fuliPresenter;

    int page = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fuli, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        EventBus.getDefault().register(this);
        meiziAdapter = new MeiziAdapter();
        meiziAdapter.setCardViewOnClickListener(cardViewOnClickListener);
        fuliRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        fuliRecyclerView.setAdapter(meiziAdapter);
        fuliPresenter = new FuliPresenter();
        fuliPresenter.getMeizi(1);
        fuliRefreshLayout.autoRefresh();
        fuliRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                fuliPresenter.getMeizi(1);
//                refreshLayout.finishRefresh(2000);
            }
        });
        fuliRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                fuliPresenter.getMeizi(++page);
//                refreshLayout.finishLoadMore(2000);
            }
        });
        return rootView;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    CardViewOnClickListener cardViewOnClickListener = new CardViewOnClickListener() {

        @Override
        public void onClick(View v, View cardView, View meiziView, Meizi meizi) {
            if(v == meiziView){
                startDetailActivity(meizi,meiziView);
            }
        }
    };

    private void startDetailActivity(Meizi meizi, View transitView){
        Intent intent = new Intent(getActivity(),MeiziDetailsActivity.class);
        intent.putExtra(MeiziDetailsActivity.PIC_TITLE,meizi.desc);
        intent.putExtra(MeiziDetailsActivity.PIC_URL,meizi.url);
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                getActivity(), transitView, MeiziDetailsActivity.PIC);
        ActivityCompat.startActivity(getActivity(),intent,optionsCompat.toBundle());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoadData(FuliEvent.LoadMeiziDataEvent event){
        meiziAdapter.setMeiziData(event.getList());
        fuliRefreshLayout.finishRefresh();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoadMoreData(FuliEvent.LoadMoreMeiziDataEvent event){
        meiziAdapter.addMeiziData(event.getList());
        fuliRefreshLayout.finishLoadMore();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showErrorMsg(FuliEvent.LoadDataErrorEvent event){
//        fuliRefreshLayout.finishRefresh();
        Toast.makeText(getContext(),event.getMsg(),Toast.LENGTH_LONG).show();
    }
}
