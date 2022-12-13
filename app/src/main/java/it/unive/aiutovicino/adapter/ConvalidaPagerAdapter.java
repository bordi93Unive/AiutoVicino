package it.unive.aiutovicino.adapter;
import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import it.unive.aiutovicino.R;
import it.unive.aiutovicino.ui.fragment.ConvalidaCorsiFragment;
import it.unive.aiutovicino.ui.fragment.ConvalidaUsersFragment;

public class ConvalidaPagerAdapter extends FragmentPagerAdapter {
    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_users, R.string.tab_courses};
    private final Context mContext;

    public ConvalidaPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        Fragment fragment = null;
        switch(position) {
            case 0:
                fragment =  ConvalidaUsersFragment.newInstance();
                break;
            case 1:
                fragment = ConvalidaCorsiFragment.newInstance();
                 break;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
