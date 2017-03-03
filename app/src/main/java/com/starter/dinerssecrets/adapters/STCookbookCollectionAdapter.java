package com.starter.dinerssecrets.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.starter.dinerssecrets.R;
import com.starter.dinerssecrets.adapters.viewholders.STCookbookViewHolder;
import com.starter.dinerssecrets.databases.STCollectionsDBHelper;
import com.starter.dinerssecrets.managers.ImageDownloadManager;
import com.starter.dinerssecrets.models.STCookbookItem;
import com.starter.dinerssecrets.utilities.StringHelper;

import java.util.List;

import static com.starter.dinerssecrets.managers.ImageDownloadManager.IMAGE_TYPE_THUMB;

/**
 * Created by wulei on 2017/3/1.
 */

public class STCookbookCollectionAdapter extends STRecyclerViewAdapter {

    public class TagHolder {
        public STCookbookItem item;
        public int position;
    }

    private Context mContext;
    private List<STCookbookItem> items;
    private ImageDownloadManager mImageDownloadManager;

    private View.OnClickListener mOnClickListener;

    public void setOnClickListener(View.OnClickListener listener) {
        mOnClickListener = listener;
    }

    public STCookbookCollectionAdapter(Context context) {
        mContext = context;
        mImageDownloadManager = new ImageDownloadManager(mContext);

        STCollectionsDBHelper dbHelper = new STCollectionsDBHelper(mContext);
        items = dbHelper.getCollections();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.st_cookbook_item, parent, false);
        STCookbookViewHolder holder = new STCookbookViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        STCookbookViewHolder viewHolder = (STCookbookViewHolder) holder;
        STCookbookItem item = items.get(position);
        viewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(null != mOnClickListener) {
                    mOnClickListener.onClick(v);
                }
            }
        });
        TagHolder th = new TagHolder();
        th.item = item;
        th.position = position;
        viewHolder.container.setTag(th);

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
        return (null == items) ? 0 : items.size();
    }

    public void removeItem(int position) {
        items.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }
}
