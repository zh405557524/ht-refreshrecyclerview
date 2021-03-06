/*
 * This source code is licensed under the MIT-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.netease.hearttouch.htrefreshrecyclerview.viewimpl;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 垂直方向刷新的实现
 */
public class HTVerticalDownRecyclerViewImpl extends HTVerticalRecyclerViewImpl {

    public HTVerticalDownRecyclerViewImpl(Context context) {
        super(context);
    }

    public HTVerticalDownRecyclerViewImpl(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HTVerticalDownRecyclerViewImpl(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public Boolean handleMoveAction(MotionEvent event) {
        float offsetX = mHTViewHolderTracker.getOffsetX();
        float offsetY = mHTViewHolderTracker.getRealOffsetXY();
        //如果X轴方向移动
        if ((Math.abs(offsetX) > mTouchSlop && Math.abs(offsetX) > Math.abs(offsetY))) {
            if (mHTViewHolderTracker.isIdlePosition()) {
                return defaultDispatchTouchEvent(event);
            }
        }
        boolean moveDown = offsetY > 0;
        boolean moveUp = offsetY < 0;
        boolean canMoveUp = mHTViewHolderTracker.hasLeftIdlePosition();

        if (moveDown && checkChildScroll()) {
            return defaultDispatchTouchEvent(event);
        }

        if (((moveUp && canMoveUp) || moveDown) && shouldHandleRefresh()) {
            updatePos(mHTViewHolderTracker.getOffsetY());//传递PULL_DISTANCE_SCALE计算之后的offset
            return true;
        }
        return null;
    }

    public void performUpdateViewPosition(int offset) {
        //移动内容
        ViewCompat.offsetTopAndBottom(mRefreshContainerView, offset);
        ViewCompat.offsetTopAndBottom(mRecyclerView, offset);
        invalidate();
    }
}
