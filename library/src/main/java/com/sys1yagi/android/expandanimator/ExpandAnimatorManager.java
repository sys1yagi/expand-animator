package com.sys1yagi.android.expandanimator;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ExpandAnimatorManager {

    private Map<String, ExpandAnimator> animators = new HashMap<String, ExpandAnimator>();

    public void put(String key, ExpandAnimator anim) {
        animators.put(key, anim);
    }

    public ExpandAnimator get(String key) {
        return animators.get(key);
    }

    public void expand(String key) {
        if (animators.containsKey(key)) {
            animators.get(key).expand();
        }
    }

    public void contract(String key) {
        if (animators.containsKey(key)) {
            animators.get(key).contract();
        }
    }

    public void exclusiveExpand(String key) {
        Set<String> keys = animators.keySet();
        for (String k : keys) {
            if (k.equals(key)) {
                expand(k);
            } else {
                contract(k);
            }
        }
    }

    public void exclusiveContract(String key) {
        Set<String> keys = animators.keySet();
        for (String k : keys) {
            if (!k.equals(key)) {
                expand(k);
            } else {
                contract(k);
            }
        }
    }

    public void adjustSize(String key) {
        ExpandAnimator anim = animators.get(key);
        if (anim != null) {
            anim.adjustSize();
        }
    }

    public void adjustSizeImmediately(String key) {
        ExpandAnimator anim = animators.get(key);
        if (anim != null) {
            anim.adjustSizeImmediately();
        }
    }
}
