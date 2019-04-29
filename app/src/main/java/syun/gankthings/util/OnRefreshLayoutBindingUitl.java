package syun.gankthings.util;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import androidx.databinding.BindingAdapter;

public class OnRefreshLayoutBindingUitl {

    @BindingAdapter("bind:refresh")
    public static void setRefreshLayoutOnRefreshListener(SmartRefreshLayout layout,boolean refresh){
        if(refresh){
            layout.autoRefresh();
        }else{
            layout.finishRefresh();
        }
    }

    @BindingAdapter("bind:loadMore")
    public static void setRefreshLayoutLoadMoreListener(SmartRefreshLayout layout,boolean loadMore){
        if(loadMore){

        }else{
            layout.finishLoadMore();
        }
    }
}
