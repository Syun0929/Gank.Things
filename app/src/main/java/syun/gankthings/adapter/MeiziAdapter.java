package syun.gankthings.adapter;

import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import syun.gankthings.R;
import syun.gankthings.bean.Meizi;
import syun.gankthings.listener.CardViewOnClickListener;

import static androidx.core.util.Preconditions.checkNotNull;


public class MeiziAdapter extends RecyclerView.Adapter<MeiziAdapter.ViewHolder> {


    private List<Meizi> mList;
    private CardViewOnClickListener mCardViewOnClickListener;

    public MeiziAdapter(){
        mList = new ArrayList<Meizi>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meizi, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Meizi meizi = mList.get(position);
        holder.card.setTag(meizi.get_id());
        holder.meizi = meizi;
        holder.tvTitle.setText(meizi.getDesc());
        Uri uri = Uri.parse(meizi.getUrl().replace("http:","https:"));
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setProgressiveRenderingEnabled(true)
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(holder.ivMeizi.getController())
                .build();
        holder.ivMeizi.setController(controller);
//        Glide.with(holder.itemView).load(meizi.getUrl()).into(holder.ivMeizi);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setMeiziData(@NonNull List<Meizi> list){
        this.mList.clear();
        this.mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addMeiziData(@NonNull List<Meizi> list){
        this.mList.addAll(list);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.iv_meizi)
        SimpleDraweeView ivMeizi;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.card_meizi)
        CardView cardMeizi;

        View card;
        Meizi meizi;

        public ViewHolder(View itemView) {
            super(itemView);
            card = itemView;
            ButterKnife.bind(this,itemView);
            card.setOnClickListener(this);
            ivMeizi.setOnClickListener(this);
            tvTitle.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            if(mCardViewOnClickListener!=null){
                mCardViewOnClickListener.onClick(view,card,ivMeizi,meizi);
            }
        }
    }

    public void setCardViewOnClickListener(CardViewOnClickListener mCardViewOnClickListener) {
        this.mCardViewOnClickListener = mCardViewOnClickListener;
    }

}
