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
import com.starter.dinerssecrets.utilities.StringHelper;

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

    private View.OnClickListener mOnClickListener;

    public void setOnClickListener(View.OnClickListener listener) {
        mOnClickListener = listener;
    }

    public STCookbookItemAdapter(Context context) {
        mContext = context;
        mImageDownloadManager = new ImageDownloadManager(mContext);
        mDBHelper = new STCookbookDBHelper(mContext);
        items = mDBHelper.getCookBookLists();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.st_cookbook_item, parent, false);
        return new STCookbookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        STCookbookItem item = items.get(position);
        STCookbookViewHolder viewHolder = (STCookbookViewHolder) holder;
        viewHolder.container.setTag(item);
        viewHolder.container.setOnClickListener(mOnClickListener);

        String imageName = item.imageName;
        if(null == item.imageName && null != item.image) {
            imageName = StringHelper.getImageName(item.image);
        }
        if(null != item.image && null != imageName) {
            mImageDownloadManager.downloadImage(imageName,
                    item.image,
                    item.cooking_id,
                    IMAGE_TYPE_THUMB,
                    viewHolder.header);
        }

        viewHolder.difficultyView.setText(item.difficulty);
        viewHolder.introView.setText(item.intro);
        viewHolder.titleView.setText(item.name);
    }

    @Override
    public int getItemCount() {
        return (null != items) ? items.size() : 0;
    }

}
