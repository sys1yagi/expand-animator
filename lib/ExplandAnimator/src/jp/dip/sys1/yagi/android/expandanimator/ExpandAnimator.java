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
package jp.dip.sys1.yagi.android.expandanimator;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout.LayoutParams;

/**
 * 開閉アニメーションを実現するクラス
 * 
 * @author sys1yagi
 * 
 */
public class ExpandAnimator {
	/** ログ出力用のタグ */
	private final static String TAG = ExpandAnimator.class.getSimpleName();

	/**
	 * 無名インタフェースなどからthisを参照する為のメソッド
	 * 
	 * @return this
	 */
	private ExpandAnimator This() {
		return this;
	}

	/**
	 * 開閉アニメーションの状態リスナ
	 * 
	 * @author sys1yagi
	 * 
	 */
	public interface OnAnimationListener {
		/**
		 * 開くアニメーションが開始された時に呼び出されます。
		 */
		public void onStartExpand(ExpandAnimator e);

		/**
		 * 閉じるアニメーションが開始された時に呼び出されます。
		 */
		public void onStartUnexpand(ExpandAnimator e);

		/**
		 * 開くアニメーションが完了した時に呼び出されます。
		 */
		public void onExpanded(ExpandAnimator e);

		/**
		 * 閉じるアニメーションが完了した時に呼び出されます。
		 */
		public void onUnexpanded(ExpandAnimator e);
	}

	/**
	 * アニメーションの時間
	 */
	private float mDuration = 1000.0f;
	/**
	 * アニメーションのインタポレータ
	 */
	private Interpolator mInterpolator = new LinearInterpolator();
	/**
	 * 開閉を行うView
	 */
	private View mView = null;
	/**
	 * アニメーション状態リスナ
	 */
	private OnAnimationListener mListener = null;
	/**
	 * 開閉対象となるViewの元の高さ
	 */
	private int mOriginHeight = 0;

	/**
	 * コンストラクタ。開閉対象となるViewと開閉アニメーションの状態リスナを受け取り初期化する。
	 * 
	 * @param v
	 *            開閉対象となるView
	 * @param listener
	 *            開閉アニメーションの状態リスナ
	 */
	public ExpandAnimator(View v, OnAnimationListener listener) {
		mView = v;
		mOriginHeight = mView.getHeight();
		mListener = listener;
		adjustSize();
	}

	/**
	 * 開閉対象となるViewの本来の高さを計算します。 開閉対象となるViewに子要素を追加/削除した場合に呼び出す必要があります。
	 */
	public void adjustSize() {
		int spec = View.MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		mView.measure(spec, spec);
		mOriginHeight = mView.getMeasuredHeight();
	}

	/**
	 * 開閉対象となるViewの本来の高さを計算します。 高さを計算後直ちにViewサイズを変更します。
	 * 開閉対象となるViewに子要素を追加/削除した場合に呼び出す必要があります。
	 */
	public void adjustSizeImmediately() {
		adjustSize();
		mView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, mOriginHeight));
	}

	/**
	 * アニメーションの時間を取得する事が出来ます。
	 * 
	 * @return アニメーションの時間(ミリ秒)
	 */
	public float getDuration() {
		return mDuration;
	}

	/**
	 * アニメーションの時間をミリ秒単位でセットする事が出来ます。
	 * 
	 * @param duration
	 *            アニメーションの時間(ミリ秒)
	 */
	public void setDuration(int duration) {
		mDuration = duration;
	}

	/**
	 * 現在セットされているインタポレータを取得する事が出来ます。
	 * 
	 * @return インタポレータ
	 */
	public Interpolator getInterpolator() {
		return mInterpolator;
	}

	/**
	 * インタポレータをセットする事が出来ます。
	 * 
	 * @param interpolator
	 *            android.view.animation.Interpolatorを継承したものであれば何でも使えます。
	 *            http://developer
	 *            .android.com/reference/android/view/animation/Interpolator
	 *            .html
	 */
	public void setInterpolator(Interpolator interpolator) {
		mInterpolator = interpolator;
	}

	/**
	 * 開閉対象となるViewを取得する事が出来ます。
	 * 
	 * @return
	 */
	public View getView() {
		return mView;
	}

	/**
	 * あるアニメーションの経過時点の変化量を計算し、返します。
	 * 具体的には、経過時間/アニメーション時間の値をインタポレータに掛け、得られた結果と開閉対象のViewの本来の高さを乗じた値を返します。
	 * 
	 * @param origin
	 *            全体変化量。開閉対象のViewの本来の高さを利用する
	 * @param time
	 *            アニメーションを開始してからの経過時間。
	 * @return 合計変化量
	 */
	private int move(int origin, long time) {
		long diff = (System.currentTimeMillis() - time);
		if (diff >= mDuration) {
			return origin;
		} else {
			float t = mInterpolator.getInterpolation(diff / mDuration);
			return (int) (origin * t);
		}
	}

	/**
	 * 開閉対象となるViewが開いた状態かどうかを返します。
	 * 
	 * @return true:開いている false:
	 */
	public boolean isExpand() {
		return mView.getHeight() > 0;
	}

	/**
	 * 開閉対象となるViewを閉じます。既に閉じている場合は
	 * {@link OnAnimationListener#onUnexpanded(ExpandAnimator)} を呼び出して直ちに終了します。
	 */
	public void unexpand() {
		if (mView.getHeight() <= 0) {
			if (mListener != null) {
				mListener.onUnexpanded(this);
			}
			return;
		}
		final long start = System.currentTimeMillis();
		Handler animationHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				msg.arg1 = mOriginHeight - move(mOriginHeight, start);
				if (msg.arg1 < 0) {
					msg.arg1 = 0;
				}
				mView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, msg.arg1));
				if (msg.arg1 <= 0) {
					if (mListener != null) {
						mListener.onUnexpanded(This());
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
		msg.arg1 = mView.getHeight();
		animationHandler.sendMessage(msg);
		if (mListener != null) {
			mListener.onStartUnexpand(this);
		}
	}

	/**
	 * 開閉対象となるViewを開きます。既に開いている場合は
	 * {@link OnAnimationListener#onExpanded(ExpandAnimator)} を呼び出して直ちに終了します。
	 */
	public void expand() {
		adjustSize();
		if (mView.getHeight() >= mOriginHeight) {
			if (mListener != null) {
				mListener.onExpanded(This());
			}
			return;
		}
		final long start = System.currentTimeMillis();
		Handler animationHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				msg.arg1 = move(mOriginHeight, start);
				if (msg.arg1 > mOriginHeight) {
					msg.arg1 = mOriginHeight;
				}
				mView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, msg.arg1));
				if (msg.arg1 >= mOriginHeight) {
					Log.d(TAG, "end");
					if (mListener != null) {
						mListener.onExpanded(This());
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
		if (mListener != null) {
			mListener.onStartExpand(this);
		}
	}
}
