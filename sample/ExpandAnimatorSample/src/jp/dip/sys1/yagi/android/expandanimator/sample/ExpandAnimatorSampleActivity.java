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

import jp.dip.sys1.yagi.android.expandanimator.ExpandAnimator;
import jp.dip.sys1.yagi.android.expandanimator.ExpandAnimatorManager;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * ExpandAnimator、ExpandAnimatorManagerを利用するサンプルプログラムです。
 * 
 * @author sys1yagi
 * 
 */
public class ExpandAnimatorSampleActivity extends Activity {
	private final static String TAG = ExpandAnimatorSampleActivity.class.getSimpleName();

	private final static String CONTAINER_1 = "1";
	private final static String CONTAINER_2 = "2";
	private final static String CONTAINER_3 = "3";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// マネージャを初期化
		final ExpandAnimatorManager manager = new ExpandAnimatorManager();

		// コンテナ初期化
		addExpandAnimator(R.id.trigger1, R.id.container1, CONTAINER_1, manager);
		addExpandAnimator(R.id.trigger2, R.id.container2, CONTAINER_2, manager);
		addExpandAnimator(R.id.trigger3, R.id.container3, CONTAINER_3, manager);
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
				
			}@Override
			public void onUnexpanded(ExpandAnimator e) {
				
			}
		});
		animator.setDuration(700);
		animator.setInterpolator(new AccelerateDecelerateInterpolator());
		return animator;
	}

}