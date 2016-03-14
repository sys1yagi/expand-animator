package com.sys1yagi.android.expandanimator.sample.fragment;

import com.sys1yagi.android.expandanimator.DefaultOnAnimationListener;
import com.sys1yagi.android.expandanimator.ExpandAnimator;
import com.sys1yagi.android.expandanimator.ExpandAnimatorManager;
import com.sys1yagi.android.expandanimator.sample.R;
import com.sys1yagi.android.expandanimator.sample.databinding.FragmentExpandCollapseBinding;
import com.sys1yagi.fragmentcreator.annotation.FragmentCreator;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;

import java.util.Random;


@FragmentCreator
public class ExpandAndCollapseFragment extends Fragment {

    private final static String CONTAINER_1 = "1";

    private final static String CONTAINER_2 = "2";

    private final static String CONTAINER_3 = "3";

    private final static String CONTAINER_4 = "4";

    private final static String CONTAINER_5 = "5";

    FragmentExpandCollapseBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_expand_collapse, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = DataBindingUtil.bind(view);
        ExpandAnimatorManager manager = new ExpandAnimatorManager();

        addExpandAnimator(binding.menu01.trigger1, binding.menu01.container1, CONTAINER_1, manager);
        initContainer(manager);
        addExpandAnimator(binding.menu03.trigger3, binding.menu03.container3, CONTAINER_3, manager);
        addExpandAnimator(binding.menu04.trigger4, binding.menu04.container4, CONTAINER_4, manager);
        addExpandAnimator(binding.menu05.trigger5, binding.menu05.container5, CONTAINER_5, manager);
    }

    private void initContainer(final ExpandAnimatorManager manager) {

        binding.menu02.trigger2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (manager.get(CONTAINER_2).isExpand()) {
                    manager.contract(CONTAINER_2);
                } else {
                    manager.exclusiveExpand(CONTAINER_2);
                }
            }
        });

        ExpandAnimator animator = new ExpandAnimator(binding.menu02.container2,
                new ExpandAnimator.OnAnimationListener() {
                    @Override
                    public void onExpanded(ExpandAnimator e) {
                        binding.menu02.trigger2.setText("Opened");
                    }

                    @Override
                    public void onStartExpand(ExpandAnimator e) {
                        binding.menu02.trigger2.setText("Opening");
                    }

                    @Override
                    public void onStartContract(ExpandAnimator e) {
                        binding.menu02.trigger2.setText("Closing");
                    }

                    @Override
                    public void onContract(ExpandAnimator e) {
                        binding.menu02.trigger2.setText("Closed");
                    }
                });
        animator.setDuration(1200);
        animator.setInterpolator(new BounceInterpolator());
        manager.put(CONTAINER_2, animator);
        final Random random = new Random(System.currentTimeMillis());
        final int margin = 155;

        binding.menu02.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = new View(getContext());
                int c = Color.rgb(random.nextInt(255 - margin) + margin,
                        random.nextInt(255 - margin) + margin,
                        random.nextInt(255 - margin) + margin
                );
                view.setBackgroundColor(c);
                binding.menu02.container21.addView(view, 0,
                        new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));
                manager.adjustSizeImmediately(CONTAINER_2);
            }
        });
        binding.menu02.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.menu02.container21.getChildCount() > 0) {
                    binding.menu02.container21.removeViewAt(0);
                    manager.adjustSizeImmediately(CONTAINER_2);
                }
            }
        });
    }

    private void addExpandAnimator(View trigger, View target, final String key,
            final ExpandAnimatorManager manager) {
        manager.put(key, createAnimator(target));
        trigger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (manager.get(key).isExpand()) {
                    manager.contract(key);
                } else {
                    manager.expand(key);
                    // manager.exclusiveExpand(key);
                }
            }
        });
    }

    private ExpandAnimator createAnimator(View v) {
        ExpandAnimator animator = new ExpandAnimator(v, new DefaultOnAnimationListener());
        animator.setDuration(700);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        return animator;
    }
}
