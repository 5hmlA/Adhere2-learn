package com.jonas.materialdesign_learn.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jonas.materialdesign_learn.R;
import com.jonas.yun_library.tablayout.JPagerSlidingTabStrip;


/**
 * Created by Gordon Wong on 7/17/2015.
 *
 * Pager adapter for main activity.
 */
public class MainPagerAdapter extends FragmentPagerAdapter implements JPagerSlidingTabStrip.IconTabProvider {

	public static final int NUM_ITEMS = 3;
	public static final int ALL_POS = 0;
	public static final int SHARED_POS = 1;
	public static final int FAVORITES_POS = 2;

	private Context context;
	private final int[] mPressed;
	private final int[] mNormal;

	public MainPagerAdapter(Context context, FragmentManager fm) {
		super(fm);

		mNormal = new int[]{R.drawable.ic_account_box_black_18dp,R.drawable.ic_flight_land_black_18dp,R.drawable.ic_alarm_black_18dp};
		mPressed = new int[]{R.drawable.ic_account_circle_black_18dp,R.drawable.ic_flight_takeoff_black_18dp,R.drawable.ic_alarm_off_black_18dp};
		this.context = context;
	}

	@Override
	public Fragment getItem(int position) {
		switch (position) {
		case ALL_POS:
			return AllFragment.newInstance();
		case SHARED_POS:
			return SharedFragment.newInstance();
		case FAVORITES_POS:
			return FavoritesFragment.newInstance();
		default:
			return null;
		}
	}

	@Override
	public CharSequence getPageTitle(int position) {
		switch (position) {
		case ALL_POS:
			return context.getString(R.string.all);
		case SHARED_POS:
			return context.getString(R.string.shared);
		case FAVORITES_POS:
			return context.getString(R.string.favorites);
		default:
			return "";
		}
	}

	@Override
	public int getCount() {
		return NUM_ITEMS;
	}

	@Override
	public int[] getPageIconResIds(int position) {
		return new int[]{mNormal[position],mPressed[position]};
	}

	@Override
	public int getPageIconResId(int position) {
		return 0;
	}
}
