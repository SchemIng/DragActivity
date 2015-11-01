package org.scheming.drag;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by Scheming on 2015/10/31.
 */
public class DragLinearLayout extends LinearLayout {
    private ViewDragHelper dragHelper;
    private View content;
    private Point contentEndPoint;
    private Point contentStartPoint;
    private int deanLine;
    private OnFinishListener listener;

    public DragLinearLayout(Context context) {
        super(context);

        dragHelper = ViewDragHelper.create(this, 1f, new DragCallBack());
        contentStartPoint = new Point();
        contentEndPoint = new Point();
    }

    public DragLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return dragHelper.shouldInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        content = getChildAt(0);

        contentStartPoint.x = content.getLeft();
        contentStartPoint.y = content.getTop();

        contentEndPoint.x = getRight();
        contentEndPoint.y = getTop();

        deanLine = (contentEndPoint.x - contentStartPoint.x) / 3;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (dragHelper.continueSettling(true))
            invalidate();
    }

    public void setOnFinishListener(OnFinishListener listener) {
        this.listener = listener;
    }

    public interface OnFinishListener {
        public void onFinish();
    }

    private class DragCallBack extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return content == child;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (left < 0)
                return super.clampViewPositionHorizontal(child, left, dx);
            return left;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            if (releasedChild == content) {
                if (releasedChild.getX() < deanLine) {
                    dragHelper.settleCapturedViewAt(contentStartPoint.x, contentStartPoint.y);
                } else {
                    dragHelper.settleCapturedViewAt(contentEndPoint.x, contentEndPoint.y);
                }
                invalidate();
            }
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            if (changedView == content && left == contentEndPoint.x && top == contentEndPoint.y && listener != null)
                listener.onFinish();
        }
    }

}
