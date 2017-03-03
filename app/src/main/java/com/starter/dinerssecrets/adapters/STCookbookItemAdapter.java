package com.starter.dinerssecrets.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.starter.dinerssecrets.R;
import com.starter.dinerssecrets.adapters.viewholders.STCookbookViewHolder;
import com.starter.dinerssecrets.managers.AppManager;
import com.starter.dinerssecrets.managers.ImageDownloadManager;
import com.starter.dinerssecrets.models.STCookbookItem;
import com.starter.dinerssecrets.utilities.StringHelper;

import java.util.List;

import static com.starter.dinerssecrets.managers.ImageDownloadManager.IMAGE_TYPE_THUMB;

/**
 * Created by wulei on 2017/2/27.
 */

public class STCookbookItemAdapter extends STRecyclerViewAdapter {

    private Context mContext;

    private List<STCookbookItem> items;

    private ImageDownloadManager mImageDownloadManager;

    private View.OnClickListener mOnClickListener;

    public void setOnClickListener(View.OnClickListener listener) {
        mOnClickListener = listener;
    }

    public void setData(List<STCookbookItem> data) {
        items = data;
        notifyDataSetChanged();
    }

    public STCookbookItemAdapter(Context context) {
        mContext = context;
        mImageDownloadManager = new ImageDownloadManager(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(AppManager.APP_TAG, "adapter viewtype: " + viewType);
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
            if(false == getIsScrolling()) {
                mImageDownloadManager.downloadImage(imageName,
                        item.image,
                        item.cooking_id,
                        IMAGE_TYPE_THUMB,
                        viewHolder.header);
            } else {
                //viewHolder.header.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher));
            }
        }

        viewHolder.difficultyView.setText(item.difficulty);
        viewHolder.introView.setText(item.intro);
        viewHolder.titleView.setText(item.name);
        if(item.isCollection) {
            viewHolder.collectionIcon.setVisibility(View.VISIBLE);
        } else {
            viewHolder.collectionIcon.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return (null != items) ? items.size() : 0;
    }

}
