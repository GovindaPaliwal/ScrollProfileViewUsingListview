package com.govinda.profilelistview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;
import com.govinda.profilelistview.R;

// TODO: Auto-generated Javadoc
/**
 * The Class ProfileScollListView.
 */
public class ProfileScollListView extends ListView implements OnScrollListener {

    /** The Constant NO_ZOOM. */
    public final static double NO_ZOOM = 1;
    
    /** The Constant ZOOM_X2. */
    public final static double ZOOM_X2 = 2;

    /** The m image view. */
    private ImageView mImageView;
    
    /** The m drawable max height. */
    private int mDrawableMaxHeight = -1;
    
    /** The m image view height. */
    private int mImageViewHeight = -1;
    
    /** The m default image view height. */
    private int mDefaultImageViewHeight = 0;
	
	/** The m zoom ratio. */
	private double mZoomRatio;

    /**
     * The listener interface for receiving onOverScrollBy events.
     * The class that is interested in processing a onOverScrollBy
     * event implements this interface, and the object created
     * with that class is registered with a component using the
     * component's <code>addOnOverScrollByListener<code> method. When
     * the onOverScrollBy event occurs, that object's appropriate
     * method is invoked.
     *
     * @see OnOverScrollByEvent
     */
    private interface OnOverScrollByListener {
        
        /**
         * Over scroll by.
         *
         * @param deltaX the delta x
         * @param deltaY the delta y
         * @param scrollX the scroll x
         * @param scrollY the scroll y
         * @param scrollRangeX the scroll range x
         * @param scrollRangeY the scroll range y
         * @param maxOverScrollX the max over scroll x
         * @param maxOverScrollY the max over scroll y
         * @param isTouchEvent the is touch event
         * @return true, if successful
         */
        public boolean overScrollBy(int deltaX, int deltaY, int scrollX,
                                    int scrollY, int scrollRangeX, int scrollRangeY,
                                    int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent);
    }

    /**
     * The listener interface for receiving onTouchEvent events.
     * The class that is interested in processing a onTouchEvent
     * event implements this interface, and the object created
     * with that class is registered with a component using the
     * component's <code>addOnTouchEventListener<code> method. When
     * the onTouchEvent event occurs, that object's appropriate
     * method is invoked.
     *
     * @see OnTouchEventEvent
     */
    private interface OnTouchEventListener {
        
        /**
         * On touch event.
         *
         * @param ev the ev
         */
        public void onTouchEvent(MotionEvent ev);
    }

    /**
     * Instantiates a new profile scoll list view.
     *
     * @param context the context
     * @param attrs the attrs
     * @param defStyle the def style
     */
    public ProfileScollListView(Context context, AttributeSet attrs,
                                 int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    /**
     * Instantiates a new profile scoll list view.
     *
     * @param context the context
     * @param attrs the attrs
     */
    public ProfileScollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * Instantiates a new profile scoll list view.
     *
     * @param context the context
     */
    public ProfileScollListView(Context context) {
        super(context);
        init(context);
    }

    /**
     * Inits the.
     *
     * @param context the context
     */
    public void init(Context context) {
        mDefaultImageViewHeight = context.getResources().getDimensionPixelSize(R.dimen.size_default_height);
    }

    /* (non-Javadoc)
     * @see android.widget.AbsListView#onLayout(boolean, int, int, int, int)
     */
    @Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		initViewsBounds(mZoomRatio);
	}

	/* (non-Javadoc)
	 * @see android.widget.AbsListView.OnScrollListener#onScrollStateChanged(android.widget.AbsListView, int)
	 */
	@Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    /* (non-Javadoc)
     * @see android.view.View#overScrollBy(int, int, int, int, int, int, int, int, boolean)
     */
    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX,
                                   int scrollY, int scrollRangeX, int scrollRangeY,
                                   int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        boolean isCollapseAnimation = false;

        isCollapseAnimation = scrollByListener.overScrollBy(deltaX, deltaY,
                scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX,
                maxOverScrollY, isTouchEvent)
                || isCollapseAnimation;

        return isCollapseAnimation ? true : super.overScrollBy(deltaX, deltaY,
                scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX,
                maxOverScrollY, isTouchEvent);
    }

    /* (non-Javadoc)
     * @see android.widget.AbsListView.OnScrollListener#onScroll(android.widget.AbsListView, int, int, int)
     */
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
    }

    /* (non-Javadoc)
     * @see android.view.View#onScrollChanged(int, int, int, int)
     */
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        View firstView = (View) mImageView.getParent();
        // firstView.getTop < getPaddingTop means mImageView will be covered by top padding,
        // so we can layout it to make it shorter
        if (firstView.getTop() < getPaddingTop() && mImageView.getHeight() > mImageViewHeight) {
            mImageView.getLayoutParams().height = Math.max(mImageView.getHeight() - (getPaddingTop() - firstView.getTop()), mImageViewHeight);
            // to set the firstView.mTop to 0,
            // maybe use View.setTop() is more easy, but it just support from Android 3.0 (API 11)
            firstView.layout(firstView.getLeft(), 0, firstView.getRight(), firstView.getHeight());
            mImageView.requestLayout();
        }
    }

    /* (non-Javadoc)
     * @see android.widget.AbsListView#onTouchEvent(android.view.MotionEvent)
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        touchListener.onTouchEvent(ev);
        return super.onTouchEvent(ev);
    }

    /**
     * Sets the parallax image view.
     *
     * @param iv the new parallax image view
     */
    public void setParallaxImageView(ImageView iv) {
        mImageView = iv;
        mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

    /**
     * Inits the views bounds.
     *
     * @param zoomRatio the zoom ratio
     */
    private void initViewsBounds(double zoomRatio) {
        if (mImageViewHeight == -1) {
            mImageViewHeight = mImageView.getHeight();
            if (mImageViewHeight <= 0) {
                mImageViewHeight = mDefaultImageViewHeight;
            }
            double ratio = ((double) mImageView.getDrawable().getIntrinsicWidth()) / ((double) mImageView.getWidth());

            mDrawableMaxHeight = (int) ((mImageView.getDrawable().getIntrinsicHeight() / ratio) * (zoomRatio > 1 ?
                    zoomRatio : 1));
        }
    }
    
    /**
     * Sets the zoom ratio.
     *
     * @param zoomRatio the new zoom ratio
     */
    public void setZoomRatio(double zoomRatio) {
    	mZoomRatio = zoomRatio;
    }

    /** The scroll by listener. */
    private OnOverScrollByListener scrollByListener = new OnOverScrollByListener() {
        @Override
        public boolean overScrollBy(int deltaX, int deltaY, int scrollX,
                                    int scrollY, int scrollRangeX, int scrollRangeY,
                                    int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
            if (mImageView.getHeight() <= mDrawableMaxHeight && isTouchEvent) {
                if (deltaY < 0) {
                    if (mImageView.getHeight() - deltaY / 2 >= mImageViewHeight) {
                        mImageView.getLayoutParams().height = mImageView.getHeight() - deltaY / 2 < mDrawableMaxHeight ?
                                mImageView.getHeight() - deltaY / 2 : mDrawableMaxHeight;
                        mImageView.requestLayout();
                    }
                } else {
                    if (mImageView.getHeight() > mImageViewHeight) {
                        mImageView.getLayoutParams().height = mImageView.getHeight() - deltaY > mImageViewHeight ?
                                mImageView.getHeight() - deltaY : mImageViewHeight;
                        mImageView.requestLayout();
                        return true;
                    }
                }
            }
            return false;
        }
    };

    /** The touch listener. */
    private OnTouchEventListener touchListener = new OnTouchEventListener() {
        @Override
        public void onTouchEvent(MotionEvent ev) {
            if (ev.getAction() == MotionEvent.ACTION_UP) {
                if (mImageViewHeight - 1 < mImageView.getHeight()) {
                    ResetAnimimation animation = new ResetAnimimation(
                            mImageView, mImageViewHeight);
                    animation.setDuration(300);
                    mImageView.startAnimation(animation);
                }
            }
        }
    };

    /**
     * The Class ResetAnimimation.
     */
    public class ResetAnimimation extends Animation {
        
        /** The target height. */
        int targetHeight;
        
        /** The original height. */
        int originalHeight;
        
        /** The extra height. */
        int extraHeight;
        
        /** The m view. */
        View mView;

        /**
         * Instantiates a new reset animimation.
         *
         * @param view the view
         * @param targetHeight the target height
         */
        protected ResetAnimimation(View view, int targetHeight) {
            this.mView = view;
            this.targetHeight = targetHeight;
            originalHeight = view.getHeight();
            extraHeight = this.targetHeight - originalHeight;
        }

        /* (non-Javadoc)
         * @see android.view.animation.Animation#applyTransformation(float, android.view.animation.Transformation)
         */
        @Override
        protected void applyTransformation(float interpolatedTime,
                                           Transformation t) {

            int newHeight;
            newHeight = (int) (targetHeight - extraHeight * (1 - interpolatedTime));
            mView.getLayoutParams().height = newHeight;
            mView.requestLayout();
        }
    }
}
