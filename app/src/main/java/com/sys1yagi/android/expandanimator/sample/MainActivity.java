package com.sys1yagi.android.expandanimator.sample;

import com.sys1yagi.android.expandanimator.DefaultOnAnimationListener;
import com.sys1yagi.android.expandanimator.ExpandAnimator;
import com.sys1yagi.android.expandanimator.ExpandAnimatorManager;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private final static String CONTAINER_1 = "1";

    private final static String CONTAINER_2 = "2";

    private final static String CONTAINER_3 = "3";

    private final static String CONTAINER_4 = "4";

    private final static String CONTAINER_5 = "5";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ExpandAnimatorManager manager = new ExpandAnimatorManager();

        addExpandAnimator(R.id.trigger1, R.id.container1, CONTAINER_1, manager);
        initContainer(manager);
        addExpandAnimator(R.id.trigger3, R.id.container3, CONTAINER_3, manager);
        addExpandAnimator(R.id.trigger4, R.id.container4, CONTAINER_4, manager);
        addExpandAnimator(R.id.trigger5, R.id.container5, CONTAINER_5, manager);
    }

    private void initContainer(final ExpandAnimatorManager manager) {
        final Button trigger = (Button) findViewById(R.id.trigger2);
        trigger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (manager.get(CONTAINER_2).isExpand()) {
                    manager.contract(CONTAINER_2);
                } else {
                    // 他のコンテナを閉じながら自分のコンテナを開きます
                    manager.exclusiveExpand(CONTAINER_2);
                }
            }
        });

        View v = findViewById(R.id.container2);
        ExpandAnimator animator = new ExpandAnimator(v, new ExpandAnimator.OnAnimationListener() {
            @Override
            public void onExpanded(ExpandAnimator e) {
                trigger.setText("開いた");
            }

            @Override
            public void onStartExpand(ExpandAnimator e) {
                trigger.setText("開いている");
            }

            @Override
            public void onStartUnexpand(ExpandAnimator e) {
                trigger.setText("閉じている");
            }

            @Override
            public void onUnexpanded(ExpandAnimator e) {
                trigger.setText("閉じた");
            }
        });
        animator.setDuration(1200);
        animator.setInterpolator(new BounceInterpolator());
        manager.put(CONTAINER_2, animator);
        final Random random = new Random(System.currentTimeMillis());
        final int margin = 155;
        final LinearLayout container = (LinearLayout) findViewById(R.id.container2_1);
        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = new View(MainActivity.this);
                int c = Color.rgb(random.nextInt(255 - margin) + margin,
                        random.nextInt(255 - margin) + margin,
                        random.nextInt(255 - margin) + margin
                );
                view.setBackgroundColor(c);
                container.addView(view, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));
                manager.adjustSizeImmediately(CONTAINER_2);
            }
        });
        findViewById(R.id.remove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (container.getChildCount() > 0) {
                    container.removeViewAt(0);
                    manager.adjustSizeImmediately(CONTAINER_2);
                }
            }
        });
    }

    private void addExpandAnimator(int triggerId, int containerId, final String key,
            final ExpandAnimatorManager manager) {
        manager.put(key, createAnimator(findViewById(containerId)));
        findViewById(triggerId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (manager.get(key).isExpand()) {
                    manager.contract(key);
                } else {
                    manager.expand(key);
                    // 他のコンテナを閉じながら自分のコンテナを開きます
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
