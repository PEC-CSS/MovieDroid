package com.pec_acm.moviedroid.mainpage.list;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.pec_acm.moviedroid.R;

public class ListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_list, container, false);
        ViewPager2 listPager=(ViewPager2) view.findViewById(R.id.list_pager);
        TabLayout listTabLayout=(TabLayout) view.findViewById(R.id.list_tab_layout);
        listPager.setAdapter(new ListPagerAdapter(this));
        new TabLayoutMediator(listTabLayout, listPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                String tabText="All";
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
            }
        }).attach();
        return view;
    }
}