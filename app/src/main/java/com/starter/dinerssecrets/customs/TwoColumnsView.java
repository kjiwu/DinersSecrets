package com.starter.dinerssecrets.customs;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.starter.dinerssecrets.R;
import com.starter.dinerssecrets.utilities.DipPixelHelper;

/**
 * Created by wulei on 2017/3/3.
 */

public class TwoColumnsView extends ViewGroup {

    public TwoColumnsView(Context context) {
        super(context);
    }

    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    public TwoColumnsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }



    /**
     * @param context
     * @param attrs
     */
    public TwoColumnsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int chilrenCount = getChildCount();
        int actualWidth = r - l;
        int row = 0;
        for(int i = 0; i < chilrenCount; i++) {
            View child = getChildAt(i);
            int childHeight = child.getMeasuredHeight();

            if(i % 2 == 0) {
                child.layout(0, row * childHeight, actualWidth / 2, childHeight * (row + 1));
            } else if(i % 2 == 1) {
                child.layout(actualWidth / 2 - DipPixelHelper.dipToPixels(getContext(), 1), row * childHeight, actualWidth, childHeight * (row + 1));
                row++;
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /**
         * 获得此ViewGroup上级容器为其推荐的宽和高，以及计算模式
         */
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);


        // 计算出所有的childView的宽和高
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        /**
         * 记录如果是wrap_content是设置的宽和高
         */
        int width = 0;
        int height = 0;

        int cCount = getChildCount();

        int cWidth = 0;
        int cHeight = 0;

        // 用于计算左边两个childView的高度
        int lHeight = 0;
        // 用于计算右边两个childView的高度，最终高度取二者之间大值
        int rHeight = 0;

        // 用于计算上边两个childView的宽度
        int tWidth = 0;
        // 用于计算下面两个childiew的宽度，最终宽度取二者之间大值
        int bWidth = 0;

        for (int i = 0; i < cCount; i++)
        {
            View childView = getChildAt(i);
            cWidth = childView.getMeasuredWidth();
            cHeight = childView.getMeasuredHeight();
            childView.measure(MeasureSpec.makeMeasureSpec(cWidth / 2, widthMode),
                    MeasureSpec.makeMeasureSpec(cHeight, heightMode));
            cWidth = childView.getMeasuredWidth();
            cHeight = childView.getMeasuredHeight();

            if (i == 0 || i == 1)
            {
                tWidth += cWidth;
            }

            if (i == 2 || i == 3)
            {
                bWidth += cWidth;
            }

            if (i == 0 || i == 2)
            {
                lHeight += cHeight;
            }

            if (i == 1 || i == 3)
            {
                rHeight += cHeight;
            }
        }

        width = Math.max(tWidth, bWidth);
        height = Math.max(lHeight, rHeight);
        setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? sizeWidth
                : width / 2, (heightMode == MeasureSpec.EXACTLY) ? sizeHeight
                : height);
    }
}
