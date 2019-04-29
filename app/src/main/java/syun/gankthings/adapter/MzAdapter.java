package syun.gankthings.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import syun.gankthings.R;
import syun.gankthings.bean.Meizi;
import syun.gankthings.databinding.MzItemBinding;
import syun.gankthings.mvvmfuli.MeiziItemViewModel;
import syun.gankthings.mvvmfuli.MeiziViewModel;

public class MzAdapter extends RecyclerView.Adapter<MzAdapter.ViewHolder> {

    private List<Meizi> mList;
    private MeiziViewModel mMeiziViewModel;

    public MzAdapter(MeiziViewModel mMeiziViewModel) {
        this.mMeiziViewModel = mMeiziViewModel;
        mList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        MzItemBinding itemBinding = DataBindingUtil.inflate(inflater, R.layout.mz_item,parent,false);
        MeiziItemViewModel itemViewModel = new MeiziItemViewModel(parent.getContext());
        itemBinding.setViewModel(itemViewModel);
        ViewHolder viewHolder = new ViewHolder(itemBinding.getRoot());
        viewHolder.setItemBinding(itemBinding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Meizi meizi = mList.get(position);
        meizi.setUrl(meizi.getUrl().replace("http:","https:"));
        holder.getItemBinding().setMeizi(meizi);
        holder.getItemBinding().getViewModel().setMeizi(meizi);
        holder.getItemBinding().getViewModel().setView(holder.getItemBinding().ivMeizi);
        holder.getItemBinding().getViewModel().setActivity((Activity)holder.getItemBinding().ivMeizi.getContext());
        holder.getItemBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public void setmList(List<Meizi> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        MzItemBinding itemBinding;

        public MzItemBinding getItemBinding() {
            return itemBinding;
        }

        public void setItemBinding(MzItemBinding itemBinding) {
            this.itemBinding = itemBinding;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
