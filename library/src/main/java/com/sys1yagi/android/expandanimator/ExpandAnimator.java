package com.sys1yagi.android.expandanimator;

import android.animation.Animator;
import android.animation.ValueAnimator;
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

        void onStartContract(ExpandAnimator e);

        void onExpanded(ExpandAnimator e);

        void onContract(ExpandAnimator e);
    }

    private int duration = 1000;

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
        target.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, originHeight));
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

    public boolean isExpand() {
        return target.getHeight() > 0;
    }

    public void contract() {
        if (target.getHeight() <= 0) {
            if (listener != null) {
                listener.onContract(this);
            }
            return;
        }
        ValueAnimator animator = ValueAnimator.ofInt(originHeight, 0)
                .setDuration(duration);
        animator.setInterpolator(interpolator);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer height = (Integer) animation.getAnimatedValue();
                target.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, height));
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (listener == null) {
                    return;
                }
                listener.onStartContract(This());
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (listener == null) {
                    return;
                }
                listener.onContract(This());
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                if (listener == null) {
                    return;
                }
                // TODO
                listener.onContract(This());
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // no op
            }
        });
        animator.start();
    }

    public void expand() {
        adjustSize();
        if (target.getHeight() >= originHeight) {
            if (listener != null) {
                listener.onExpanded(This());
            }
            return;
        }
        ValueAnimator animator = ValueAnimator.ofInt(0, originHeight)
                .setDuration(duration);
        animator.setInterpolator(interpolator);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer height = (Integer) animation.getAnimatedValue();
                target.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, height));
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (listener == null) {
                    return;
                }
                listener.onStartExpand(This());
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (listener == null) {
                    return;
                }
                listener.onExpanded(This());
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                if (listener == null) {
                    return;
                }
                // TODO
                listener.onExpanded(This());
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // no op
            }
        });
        animator.start();
    }
}
