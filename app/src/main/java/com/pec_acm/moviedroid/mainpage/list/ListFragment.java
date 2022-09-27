package com.pec_acm.moviedroid.mainpage.list;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.pec_acm.moviedroid.databinding.FragmentListBinding;

public class ListFragment extends Fragment {
    private FragmentListBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding=FragmentListBinding.inflate(inflater,container,false);
        View view=binding.getRoot();

        ViewPager2 listPager=(ViewPager2) binding.listPager;
        TabLayout listTabLayout=(TabLayout) binding.listTabLayout;

        listPager.setAdapter(new ListPagerAdapter(this));
        new TabLayoutMediator(listTabLayout, listPager, (tab, position) -> {
            String tabText;
            switch (position)
            {
                default:
                    tabText="All";
                    break;
                case 1:
                    tabText="Watching";
                    break;
                case 2:
                    tabText="Completed";
                    break;
                case 3:
                    tabText="On Hold";
                    break;
                case 4:
                    tabText="Dropped";
                    break;
                case 5:
                    tabText="Plan to Watch";
                    break;
            }
            tab.setText(tabText);
        }).attach();

        return view;
    }
}