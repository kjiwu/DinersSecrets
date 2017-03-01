package com.starter.dinerssecrets.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.starter.dinerssecrets.R;
import com.starter.dinerssecrets.databases.STCollectionsDBHelper;
import com.starter.dinerssecrets.managers.AppManager;
import com.starter.dinerssecrets.managers.ImageDownloadManager;
import com.starter.dinerssecrets.models.STCookbookItem;
import com.starter.dinerssecrets.utilities.StringHelper;

import java.util.List;

import static com.starter.dinerssecrets.managers.ImageDownloadManager.IMAGE_TYPE_THUMB;

/**
 * Created by wulei on 2017/3/1.
 */

public class STCookbookCollectionAdapter extends RecyclerView.Adapter {

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
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("你确定要删除当前收藏菜谱吗?");
                builder.setCancelable(true);
                builder.setTitle(mContext.getResources().getString(R.string.app_name));
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(AppManager.APP_TAG, "现在要删除[" + position + "]位置处的收藏");
                        notifyItemRemoved(position);
                        items.remove(position);
                        notifyDataSetChanged();
                        mOnClickListener.onClick(v);
                    }
                });
                builder.setNegativeButton("取消", null);
                builder.show();
            }
        });
        viewHolder.container.setTag(item.cooking_id);

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
}
