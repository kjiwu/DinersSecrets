package com.starter.dinerssecrets.adapters;

import android.content.Context;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.starter.dinerssecrets.R;
import com.starter.dinerssecrets.adapters.viewholders.STDetailContentViewHolder;
import com.starter.dinerssecrets.adapters.viewholders.STDetailDifficultyViewHolder;
import com.starter.dinerssecrets.adapters.viewholders.STDetailHeaderViewHolder;
import com.starter.dinerssecrets.adapters.viewholders.STDetailMaterialViewHolder;
import com.starter.dinerssecrets.managers.ImageDownloadManager;
import com.starter.dinerssecrets.managers.NetwrokManager;
import com.starter.dinerssecrets.models.STCookbookDetail;
import com.starter.dinerssecrets.models.STCookbookMaterial;
import com.starter.dinerssecrets.models.STCookbookStep;
import com.starter.dinerssecrets.models.STMaterialsItem;
import com.starter.dinerssecrets.utilities.StringHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wulei on 2017/3/3.
 */

public class STDetailAdapter extends RecyclerView.Adapter {
    public final static int ITEM_IMAGE_HEADER_POSITION = 0;
    public final static int ITEM_INTRO_POSITION = 1;
    public final static int ITEM_DIFFICULTY_POSITION = 2;
    public final static int ITEM_MAIN_MATERIALS_TITLE_POSITION = 3;
    public final static int ITEM_MAIN_MATERIALS_POSITION = 4;
    public final static int ITEM_ASSIST_MATERIALS_TITLE_POSITION = 5;
    public final static int ITEM_ASSIST_MATERIALS_POSITION = 6;
    public final static int ITEM_STEPS_TITLE_POSITION = 7;
    public final static int ITEM_STEPS_POSITION = 8;
    public final static int ITEM_COMPLETED_TITLE_POSITION = 9;
    public final static int ITEM_COMPLETED_POSITION = 10;
    public final static int ITEM_TIPS_TITLE_POSITION = 11;
    public final static int ITEM_TIPS_POSITION = 12;

    public final static int ITEM_TOTAL_COUNT = 13;

    private Context mContext;
    private STCookbookDetail mDetail;

    private ImageDownloadManager mImageDownloadManager;


    public STDetailAdapter(Context context, STCookbookDetail detail) {
        mContext = context;
        mDetail = detail;
        mImageDownloadManager = new ImageDownloadManager(context);
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
            case 3:
            case 5:
            case 7:
            case 9:
            case 11:
                return 0;
            case 2:
                return 2;
            case 4:
            case 6:
                return 3;
            default:
                return 1;
        }
    }

    @Override
    public int getItemCount() {
        return ITEM_TOTAL_COUNT;
    }

    protected STDetailHeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.detail_common_title, parent, false);
        STDetailHeaderViewHolder holder = new STDetailHeaderViewHolder(view);
        return holder;
    }

    protected STDetailContentViewHolder onCreateItemViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.detail_content, parent, false);
        STDetailContentViewHolder holder = new STDetailContentViewHolder(view);
        return holder;
    }

    protected STDetailDifficultyViewHolder onCreateDifficultyViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.detail_difficulty, parent, false);
        STDetailDifficultyViewHolder holder = new STDetailDifficultyViewHolder(view);
        return holder;
    }

    protected STDetailMaterialViewHolder onCreateMaterialViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.detail_material, parent, false);
        STDetailMaterialViewHolder holder = new STDetailMaterialViewHolder(view);
        return holder;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case 0:
                holder = onCreateHeaderViewHolder(parent);
                break;
            case 1:
                holder = onCreateItemViewHolder(parent);
                break;
            case 2:
                holder = onCreateDifficultyViewHolder(parent);
                break;
            case 3:
                holder = onCreateMaterialViewHolder(parent);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (position) {
            case ITEM_IMAGE_HEADER_POSITION:
                loadHeaderImage((STDetailHeaderViewHolder) holder);
                break;
            case ITEM_INTRO_POSITION:
                loadIntro((STDetailContentViewHolder) holder);
                break;
            case ITEM_DIFFICULTY_POSITION:
                loadDifficulty((STDetailDifficultyViewHolder) holder);
                break;
            case ITEM_MAIN_MATERIALS_TITLE_POSITION:
                showCommonTilte((STDetailHeaderViewHolder) holder, R.string.detail_main_material_title, true);
                break;
            case ITEM_MAIN_MATERIALS_POSITION:
                loadMaterials((STDetailMaterialViewHolder) holder, mDetail.materials, true);
                break;
            case ITEM_ASSIST_MATERIALS_TITLE_POSITION:
                showCommonTilte((STDetailHeaderViewHolder) holder, R.string.detail_assist_material_title, true);
                break;
            case ITEM_ASSIST_MATERIALS_POSITION:
                loadMaterials((STDetailMaterialViewHolder) holder, mDetail.materials, false);
                break;
            case ITEM_STEPS_TITLE_POSITION:
                showCommonTilte((STDetailHeaderViewHolder) holder, R.string.detail_steps_title, false);
                break;
            case ITEM_STEPS_POSITION:
                loadSteps((STDetailContentViewHolder) holder, mDetail.steps);
                break;
            case ITEM_COMPLETED_TITLE_POSITION:
                loadCompleteTitle((STDetailHeaderViewHolder) holder);
                break;
            case ITEM_COMPLETED_POSITION:
                loadCompleteImages((STDetailContentViewHolder) holder);
                break;
            case ITEM_TIPS_TITLE_POSITION:
                showCommonTilte((STDetailHeaderViewHolder) holder, R.string.detail_tips_title, false);
                break;
            case ITEM_TIPS_POSITION:
                loadTips((STDetailContentViewHolder) holder);
                break;
        }
    }

    private void loadHeaderImage(STDetailHeaderViewHolder holder) {
        if (null != mDetail && null != mDetail.image) {
            holder.container.setVisibility(View.VISIBLE);
            if (NetwrokManager.isNetworkAvailable(mContext)) {
                if (null != holder.headerImage) {
                    holder.headerImage.setVisibility(View.VISIBLE);
                    mImageDownloadManager.downloadImage(StringHelper.getImageName(mDetail.image),
                            mDetail.image,
                            mDetail.cooking_id,
                            ImageDownloadManager.IMAGE_TYPE_ORIGIN,
                            holder.headerImage);
                }
            }
        }
    }

    private void loadIntro(STDetailContentViewHolder holder) {
        if (null == holder || null == mDetail || null == mDetail.intro) {
            return;
        }

        holder.contentView.setVisibility(View.VISIBLE);
        holder.contentList.removeAllViews();
        holder.contentView.setText(mDetail.intro);
    }

    private void loadDifficulty(STDetailDifficultyViewHolder holder) {
        if (null != holder && null != mDetail) {
            if (null != mDetail.difficulty) {
                holder.difficultyView.setText(mDetail.difficulty);
            }

            if (null != mDetail.time) {
                holder.timeView.setText(mDetail.time);
            }
        }
    }

    private void showCommonTilte(STDetailHeaderViewHolder holder, int id, boolean showBorder) {
        if (null != holder) {
            String title = mContext.getResources().getString(id);
            holder.container.setVisibility(View.VISIBLE);
            holder.headerTitle.setVisibility(View.VISIBLE);
            holder.headerTitle.setText(title);
            holder.headerImage.setVisibility(View.GONE);
            if (showBorder == true) {
                holder.container.setBackgroundResource(R.drawable.border_background);
            } else {
                holder.container.setBackgroundResource(android.R.color.darker_gray);
            }
        }
    }

    private void loadMaterials(STDetailMaterialViewHolder holder, List<STCookbookMaterial> materials, boolean isMain) {
        if (null != holder && null != materials) {
            holder.materialsView.setVisibility(View.VISIBLE);
            for (STCookbookMaterial material : materials) {
                if (isMain) {
                    if (material.type == STCookbookMaterial.MATERIAL_TYPE_MAIN) {
                        View view = LayoutInflater.from(mContext).inflate(R.layout.detail_material_item, null);
                        TextView nameView = (TextView) view.findViewById(R.id.detail_material_name);
                        nameView.setText(material.name);
                        TextView countView = (TextView) view.findViewById(R.id.detail_material_count);
                        countView.setText(material.count);
                        holder.materialsView.addView(view);
                    }
                } else {
                    if (material.type == STCookbookMaterial.MATERIAL_TYPE_ASSIST) {
                        View view = LayoutInflater.from(mContext).inflate(R.layout.detail_material_item, null);
                        TextView nameView = (TextView) view.findViewById(R.id.detail_material_name);
                        nameView.setText(material.name);
                        TextView countView = (TextView) view.findViewById(R.id.detail_material_count);
                        countView.setText(material.count);
                        holder.materialsView.addView(view);
                    }
                }
            }
        } else {
            holder.materialsView.setVisibility(View.GONE);
        }
    }

    private void loadSteps(STDetailContentViewHolder holder, List<STCookbookStep> steps) {
        if (null == holder || null == steps) {
            return;
        }

        holder.contentList.removeAllViews();
        for (STCookbookStep step : steps) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.detail_step_item, null);
            TextView tv = (TextView) view.findViewById(R.id.detail_step_content);
            ImageView iv = (ImageView) view.findViewById(R.id.detail_step_image);
            tv.setText(step.order + "." + step.step);
            if (null != step.image) {
                if (NetwrokManager.isNetworkAvailable(mContext)) {
                    mImageDownloadManager.downloadImage(StringHelper.getImageName(step.image),
                            step.image,
                            mDetail.cooking_id,
                            ImageDownloadManager.IMAGE_TYPE_ORIGIN,
                            iv);
                }
            } else {
                iv.setVisibility(View.GONE);
            }

            holder.contentList.addView(view);
        }
    }

    private void loadCompleteTitle(STDetailHeaderViewHolder holder) {
        if (null != holder) {
            holder.container.setVisibility(View.GONE);
        }
    }

    private void loadCompleteImages(STDetailContentViewHolder holder) {
        if(null != holder) {
            holder.contentList.setVisibility(View.GONE);
            holder.contentView.setVisibility(View.GONE);
        }
    }

    private void loadTips(STDetailContentViewHolder holder) {
        if(null != holder && null != mDetail.tips) {
            holder.contentList.setVisibility(View.VISIBLE);
            holder.contentList.removeAllViews();
            for (String tip : mDetail.tips) {
                View view = LayoutInflater.from(mContext).inflate(R.layout.detail_step_item, null);
                TextView tv = (TextView) view.findViewById(R.id.detail_step_content);
                ImageView iv = (ImageView) view.findViewById(R.id.detail_step_image);
                iv.setVisibility(View.GONE);
                tv.setText(tip);
                holder.contentList.addView(view);
            }
        }
    }
}
