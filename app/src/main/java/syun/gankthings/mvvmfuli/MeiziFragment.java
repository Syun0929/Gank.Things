package syun.gankthings.mvvmfuli;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.Observable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import syun.gankthings.adapter.MzAdapter;
import syun.gankthings.databinding.MeiziFragBinding;

public class MeiziFragment extends Fragment {

    private MeiziViewModel mMeiziViewModel;
    private MeiziFragBinding mMeiziFragBinding;
    private SmartRefreshLayout refreshLayout;
    private MzAdapter mzAdapter;
    public MeiziFragment(){}

    public static MeiziFragment newInstance() {

        Bundle args = new Bundle();

        MeiziFragment fragment = new MeiziFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mMeiziFragBinding = MeiziFragBinding.inflate(inflater,container,false);
        mMeiziFragBinding.setView(this);
        mMeiziFragBinding.setViewModel(mMeiziViewModel);
        View view = mMeiziFragBinding.getRoot();
        mMeiziViewModel.start();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupRefreshLayout();
        setupListView();
        setupToast();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    public void setMeiziViewModel(MeiziViewModel meiziViewModel) {
        this.mMeiziViewModel = meiziViewModel;
    }

    private void setupRefreshLayout(){
        refreshLayout = mMeiziFragBinding.meiziRefreshLayout;
//        refreshLayout.autoRefresh();
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//                refreshLayout.finishRefresh(2000);
                mMeiziViewModel.start();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
//                refreshLayout.finishLoadMore(2000);
                mMeiziViewModel.loadMore();
            }
        });
    }

    private void setupListView(){
        RecyclerView recyclerView = mMeiziFragBinding.meiziRecyclerView;
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        mzAdapter = new MzAdapter(mMeiziViewModel);
        recyclerView.setAdapter(mzAdapter);
    }

    private void setupToast() {
        mMeiziViewModel.errorMessage.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                Toast.makeText(getContext(),mMeiziViewModel.errorMessage.get(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
