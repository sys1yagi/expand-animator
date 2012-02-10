/*
 * Created by sys1yagi on 12/02/10
 * Copyright (C) 2012 sys1yagi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jp.dip.sys1.yagi.android.expandanimator.sample;

import java.util.Random;

import jp.dip.sys1.yagi.android.expandanimator.ExpandAnimator;
import jp.dip.sys1.yagi.android.expandanimator.ExpandAnimatorManager;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * ExpandAnimator、ExpandAnimatorManagerを利用するサンプルプログラムです。
 * 
 * @author sys1yagi
 * 
 */
public class ExpandAnimatorSampleActivity extends Activity {
    private final static String TAG = ExpandAnimatorSampleActivity.class.getSimpleName();
    private ExpandAnimatorSampleActivity This(){
        return this;
    }
    private final static String CONTAINER_1 = "1";
    private final static String CONTAINER_2 = "2";
    private final static String CONTAINER_3 = "3";
    private final static String CONTAINER_4 = "4";
    private final static String CONTAINER_5 = "5";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // マネージャを初期化
        final ExpandAnimatorManager manager = new ExpandAnimatorManager();

        // コンテナ初期化
        addExpandAnimator(R.id.trigger1, R.id.container1, CONTAINER_1, manager);
        initContainer2(manager);
        addExpandAnimator(R.id.trigger3, R.id.container3, CONTAINER_3, manager);
        addExpandAnimator(R.id.trigger4, R.id.container4, CONTAINER_4, manager);
        addExpandAnimator(R.id.trigger5, R.id.container5, CONTAINER_5, manager);
    }

    /**
     * 
     * @param manager
     */
    private void initContainer2(final ExpandAnimatorManager manager) {
        final Button trigger = (Button) findViewById(R.id.trigger2);
        trigger.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (manager.get(CONTAINER_2).isExpand()) {
                    manager.unexpand(CONTAINER_2);
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
        final LinearLayout container = (LinearLayout)findViewById(R.id.container2_1);
        findViewById(R.id.add).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = new View(This());
                int c = Color.rgb(random.nextInt(255-margin)+margin,
                        random.nextInt(255-margin)+margin,
                        random.nextInt(255-margin)+margin
                        );
                view.setBackgroundColor(c);
                container.addView(view, 0, new LayoutParams(LayoutParams.FILL_PARENT, 100));
                manager.adjustSizeImmediately(CONTAINER_2);
            }
        });
        findViewById(R.id.remove).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(container.getChildCount() > 0){
                    container.removeViewAt(0);
                    manager.adjustSizeImmediately(CONTAINER_2);
                }
            }
        });
    }

    /**
     * ExpandAnimatorをExpandAnimatorManagerに追加します。
     * 
     * @param triggerId
     *            開閉のトリガとなるViewのidを指定して下さい。このViewはクリックが可能である必要があります。
     * @param containerId
     *            開閉の対象となるViewを指定して下さい。
     * @param key
     *            ExpandAnimatorManagerにputする為のキーを指定して下さい。
     * @param manager
     *            ExpandAnimatorを追加するExpandAnimatorManagerを指定して下さい。
     */
    private void addExpandAnimator(int triggerId, int containerId, final String key, final ExpandAnimatorManager manager) {
        manager.put(key, createAnimator(findViewById(containerId)));
        findViewById(triggerId).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (manager.get(key).isExpand()) {
                    manager.unexpand(key);
                } else {
                    manager.expand(key);
                    // 他のコンテナを閉じながら自分のコンテナを開きます
                    // manager.exclusiveExpand(key);
                }
            }
        });
    }

    /**
     * ExpandAnimatorを初期化します。expand/unexpandのリスナやアニメーションの時間、インタポレータをセットする事が出来ます。
     * 
     * @param v
     *            expand/unexpandの対象となるViewを指定して下さい。
     * @return 初期化したExpandAnimator
     */
    private ExpandAnimator createAnimator(View v) {
        ExpandAnimator animator = new ExpandAnimator(v, new ExpandAnimator.OnAnimationListener() {
            @Override
            public void onExpanded(ExpandAnimator e) {

            }

            @Override
            public void onStartExpand(ExpandAnimator e) {

            }

            @Override
            public void onStartUnexpand(ExpandAnimator e) {

            }

            @Override
            public void onUnexpanded(ExpandAnimator e) {

            }
        });
        animator.setDuration(700);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        return animator;
    }

}