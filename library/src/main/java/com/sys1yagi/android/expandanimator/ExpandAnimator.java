package com.sys1yagi.android.expandanimator;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout.LayoutParams;

public class ExpandAnimator {

    private ExpandAnimator This() {
        return this;
    }

    public interface OnAnimationListener {

        void onStartExpand(ExpandAnimator e);

        void onStartUnexpand(ExpandAnimator e);

        void onExpanded(ExpandAnimator e);

        void onUnexpanded(ExpandAnimator e);
    }

    private float duration = 1000.0f;

    private Interpolator interpolator = new LinearInterpolator();

    private View target = null;

    private OnAnimationListener listener = null;

    private int originHeight = 0;

    public ExpandAnimator(View v, OnAnimationListener listener) {
        target = v;
        originHeight = target.getHeight();
        this.listener = listener;
        adjustSize();
    }

    public void adjustSize() {
        int spec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        target.measure(spec, spec);
        originHeight = target.getMeasuredHeight();
    }

    public void adjustSizeImmediately() {
        adjustSize();
        target.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, originHeight));
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Interpolator getInterpolator() {
        return interpolator;
    }

    public void setInterpolator(Interpolator interpolator) {
        this.interpolator = interpolator;
    }

    public View getView() {
        return target;
    }

    private int move(int origin, long time) {
        long diff = (System.currentTimeMillis() - time);
        if (diff >= duration) {
            return origin;
        } else {
            float t = interpolator.getInterpolation(diff / duration);
            return (int) (origin * t);
        }
    }

    public boolean isExpand() {
        return target.getHeight() > 0;
    }

    public void contract() {
        if (target.getHeight() <= 0) {
            if (listener != null) {
                listener.onUnexpanded(this);
            }
            return;
        }
        final long start = System.currentTimeMillis();
        Handler animationHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                msg.arg1 = originHeight - move(originHeight, start);
                if (msg.arg1 < 0) {
                    msg.arg1 = 0;
                }
                target.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, msg.arg1));
                if (msg.arg1 <= 0) {
                    if (listener != null) {
                        listener.onUnexpanded(This());
                    }
                } else {
                    try {
                        Thread.sleep(1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Message m = this.obtainMessage(0);
                    m.arg1 = msg.arg1;
                    this.sendMessage(m);
                }
            }
        };
        Message msg = animationHandler.obtainMessage(0);
        msg.arg1 = target.getHeight();
        animationHandler.sendMessage(msg);
        if (listener != null) {
            listener.onStartUnexpand(this);
        }
    }

    public void expand() {
        adjustSize();
        if (target.getHeight() >= originHeight) {
            if (listener != null) {
                listener.onExpanded(This());
            }
            return;
        }
        final long start = System.currentTimeMillis();
        Handler animationHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                msg.arg1 = move(originHeight, start);
                if (msg.arg1 > originHeight) {
                    msg.arg1 = originHeight;
                }
                target.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, msg.arg1));
                if (msg.arg1 >= originHeight) {
                    if (listener != null) {
                        listener.onExpanded(This());
                    }
                } else {
                    try {
                        Thread.sleep(1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Message m = this.obtainMessage(0);
                    m.arg1 = msg.arg1;
                    this.sendMessage(m);
                }
            }
        };
        Message msg = animationHandler.obtainMessage(0);
        msg.arg1 = 0;
        animationHandler.sendMessage(msg);
        if (listener != null) {
            listener.onStartExpand(this);
        }
    }
}
