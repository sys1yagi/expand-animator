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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
/**
 * ExpandAnimatorを管理するクラスです。内部でHashMap<String, ExpandAnimator>を持ち、管理を行なっています。
 * @author sys1yagi
 *
 */
public class ExpandAnimatorManager {

	/**
	 * ExpandAnimatorを保持する為のHashMap。
	 */
	private Map<String, ExpandAnimator> mAnimators = new HashMap<String, ExpandAnimator>();

	/**
	 * コンストラクタ。特になにも行わない。
	 */
	public ExpandAnimatorManager() {

	}

	/**
	 * ExpandAnimatorを追加する。keyが同じ場合は上書きする。
	 * @param key ExpandAnimatorを識別するユニークなキー 
	 * @param anim ExpandAnimator 
	 */
	public void put(String key, ExpandAnimator anim) {
		mAnimators.put(key, anim);
	}
	/**
	 * キーに対応するExpandAnimatorを取得する事が出来ます。
	 * @param key ExpandAnimatorを識別するキー
	 * @return ExpandAnimator。キーに対応するExpandAnimatorが無い場合はnullを返す 
	 */
	public ExpandAnimator get(String key){
		return mAnimators.get(key);
	}

	/**
	 * キーに対応するExpandAnimatorの{@link ExpandAnimator#expand()}を実行します。
	 * @param key ExpandAnimatorを識別するキー
	 */
	public void expand(String key) {
		if(mAnimators.containsKey(key)){
			mAnimators.get(key).expand();
		}
	}

	/**
	 * キーに対応するExpandAnimatorの{@link ExpandAnimator#unexpand()}を実行します。
	 * @param key ExpandAnimatorを識別するキー
	 */
	public void unexpand(String key) {
		if(mAnimators.containsKey(key)){
			mAnimators.get(key).unexpand();
		}
	}

	/**
	 * キーに対応するExpandAnimatorの{@link ExpandAnimator#expand()}を実行します。
	 * また、ExpandAnimatorManager内の他のExpandAnimatorの{@link ExpandAnimator#unexpand()}を実行します。
	 * @param key ExpandAnimatorを識別するキー
	 */
	public void exclusiveExpand(String key) {
		Set<String> keys = mAnimators.keySet();
		for (String k : keys) {
			if (k.equals(key)) {
				expand(k);
			} else {
				unexpand(k);
			}
		}
	}

	/**
	 * キーに対応するExpandAnimatorの{@link ExpandAnimator#unexpand()}を実行します。
	 * また、ExpandAnimatorManager内の他のExpandAnimatorの{@link ExpandAnimator#expand()}を実行します。
	 * @param key ExpandAnimatorを識別するキー
	 */
	public void exclusiveUnexpand(String key) {
		Set<String> keys = mAnimators.keySet();
		for (String k : keys) {
			if (!k.equals(key)) {
				expand(k);
			} else {
				unexpand(k);
			}
		}
	}

	/**
	 * キーに対応するExpandAnimatorの{@link ExpandAnimator#adjustSize()}を実行します。
	 * @param key ExpandAnimatorを識別するキー
	 */
	public void adjustSize(String key){
		ExpandAnimator anim = mAnimators.get(key);
		if(anim != null){
			anim.adjustSize();
		}
	}
	/**
	 * キーに対応するExpandAnimatorの{@link ExpandAnimator#adjustSizeImmediately()}を実行します。
	 * @param key ExpandAnimatorを識別するキー
	 */
	public void adjustSizeImmediately(String key){
		ExpandAnimator anim = mAnimators.get(key);
		if(anim != null){
			anim.adjustSizeImmediately();
		}
	}
}
