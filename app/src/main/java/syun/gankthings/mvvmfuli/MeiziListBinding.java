package syun.gankthings.mvvmfuli;

import java.util.List;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;
import syun.gankthings.adapter.MzAdapter;
import syun.gankthings.bean.Meizi;

public class MeiziListBinding {

    @BindingAdapter("bind:setItems")
    public static void updateList(RecyclerView recyclerView, List<Meizi> list){
        MzAdapter mzAdapter = (MzAdapter) recyclerView.getAdapter();
        if(mzAdapter!=null){
            mzAdapter.setmList(list);
        }
    }
}
