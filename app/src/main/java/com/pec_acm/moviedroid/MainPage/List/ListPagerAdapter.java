package com.pec_acm.moviedroid.MainPage.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ListPagerAdapter extends FragmentStateAdapter {
    public ListPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            default: return new AllListFragment();
            case 1: return new WatchingListFragment();
            case 2: return new CompletedListFragment();
            case 3: return new OnHoldListFragment();
            case 4: return new DroppedListFragment();
            case 5: return new PlanToWatchListFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 6;
    }
}
