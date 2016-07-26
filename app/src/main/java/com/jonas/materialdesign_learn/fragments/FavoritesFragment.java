package com.jonas.materialdesign_learn.fragments;


import com.jonas.materialdesign_learn.R;

/**
 * Created by Gordon Wong on 7/17/2015.
 *
 * Favorite items fragment.
 */
public class FavoritesFragment extends NotesListFragment {

	public static FavoritesFragment newInstance() {
		return new FavoritesFragment();
	}

	@Override
	protected int getLayoutResId() {
		return R.layout.list_item_note;
	}

	@Override
	protected int getNumColumns() {
		return 1;
	}

	@Override
	protected int getNumItems() {
		return 7;
	}
}
