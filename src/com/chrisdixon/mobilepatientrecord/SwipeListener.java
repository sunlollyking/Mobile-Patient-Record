package com.chrisdixon.mobilepatientrecord;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
// TODO: Auto-generated Javadoc
/**
 * This class is part of the extra features set designed by Steve448 which provides functionality such as a
 * swipe listener and a shake listener. It is held under the Lesser Genneral Public License Version 3 as published by the Free Software Foundation
 * and is free for usage in this project.
 * https://github.com/NexusTools/ExtraFeaturesProvider
 * @author Steve448
 *
 */
public class SwipeListener implements OnTouchListener{
	
	/** The gd. */
	private final GestureDetector gd;
	
	/**
	 * Instantiates a new swipe listener.
	 *
	 * @param ctx the ctx
	 */
	public SwipeListener (Context ctx){
        gd = new GestureDetector(ctx, new GestureListener());
    }

    /**
     * The listener interface for receiving gesture events.
     * The class that is interested in processing a gesture
     * event implements this interface, and the object created
     * with that class is registered with a component using the
     * component's <code>addGestureListener<code> method. When
     * the gesture event occurs, that object's appropriate
     * method is invoked.
     *
     * @see GestureEvent
     */
    private final class GestureListener extends SimpleOnGestureListener {

        /** The Constant SWIPE_THRESHOLD. */
        private static final int SWIPE_THRESHOLD = 100;
        
        /** The Constant SWIPE_VELOCITY_THRESHOLD. */
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        /* (non-Javadoc)
         * @see android.view.GestureDetector.SimpleOnGestureListener#onDown(android.view.MotionEvent)
         */
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        /* (non-Javadoc)
         * @see android.view.GestureDetector.SimpleOnGestureListener#onFling(android.view.MotionEvent, android.view.MotionEvent, float, float)
         */
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight();
                        } else {
                            onSwipeLeft();
                        }
                    }
                } else {
                    if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            onSwipeBottom();
                        } else {
                            onSwipeTop();
                        }
                    }
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }
    }

    /**
     * On swipe right.
     */
    public void onSwipeRight() {
    }

    /**
     * On swipe left.
     */
    public void onSwipeLeft() {
    }

    /**
     * On swipe top.
     */
    public void onSwipeTop() {
    }

    /**
     * On swipe bottom.
     */
    public void onSwipeBottom() {
    }

	/* (non-Javadoc)
	 * @see android.view.View.OnTouchListener#onTouch(android.view.View, android.view.MotionEvent)
	 */
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		 return gd.onTouchEvent(event);
	}
}
