package com.starter.dinerssecrets.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.starter.dinerssecrets.R;
import com.starter.dinerssecrets.databases.STCookbookDBHelper;
import com.starter.dinerssecrets.managers.ImageDownloadManager;
import com.starter.dinerssecrets.models.STCookbookItem;

import java.util.List;

import static com.starter.dinerssecrets.managers.ImageDownloadManager.IMAGE_TYPE_THUMB;

/**
 * Created by wulei on 2017/2/27.
 */

public class STCookbookItemAdapter extends RecyclerView.Adapter {

    private Context mContext;

    private List<STCookbookItem> items;

    private ImageDownloadManager mImageDownloadManager;
    private STCookbookDBHelper mDBHelper;

    public STCookbookItemAdapter(Context context) {
        mContext = context;
        mImageDownloadManager = new ImageDownloadManager(mContext);
        mDBHelper = new STCookbookDBHelper(mContext);
        items = mDBHelper.getCookBookLists();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
            header = (ImageView) itemView.findViewById(R.id.ci_header);
            titleView = (TextView) itemView.findViewById(R.id.ci_title);
            introView = (TextView) itemView.findViewById(R.id.ci_intro);
            difficultyView = (TextView) itemView.findViewById(R.id.ci_difficulty);
        }

        public ImageView header;
        public TextView titleView;
        public TextView introView;
        public TextView difficultyView;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.st_cookbook_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        STCookbookItem item = items.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;

        mImageDownloadManager.downloadImage(item.imageName,
                item.image,
                item.cooking_id,
                IMAGE_TYPE_THUMB,
                viewHolder.header);

        viewHolder.difficultyView.setText(item.difficulty);
        viewHolder.introView.setText(item.intro);
        viewHolder.titleView.setText(item.name);
    }

    @Override
    public int getItemCount() {
        return (null != items) ? items.size() : 0;
    }

}
