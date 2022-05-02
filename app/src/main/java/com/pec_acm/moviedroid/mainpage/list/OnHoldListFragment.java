package com.pec_acm.moviedroid.mainpage.list;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.pec_acm.moviedroid.R;
import com.pec_acm.moviedroid.firebase.ListItem;
import com.pec_acm.moviedroid.firebase.User;

import java.util.ArrayList;

public class OnHoldListFragment extends Fragment {

    private ListViewModel listViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_on_hold_list, container, false);

        RecyclerView onHoldList = view.findViewById(R.id.on_hold_list);
        listViewModel = new ViewModelProvider(this).get(ListViewModel.class);
        listViewModel.getUser(FirebaseAuth.getInstance().getUid());
        ListAdapter listAdapter = new ListAdapter(requireContext(),listViewModel, this);
        onHoldList.setAdapter(listAdapter);
        listViewModel.getUser().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if(user==null) return;
                ArrayList<ListItem> itemList = new ArrayList<>();
                for(int i=0;i<user.getUserList().size();i++)
                {
                    ListItem listItem = user.getUserList().get(i);
                    if(listItem.getStatus()==3) itemList.add(listItem);
                }
                listAdapter.setItemList(itemList);
            }
        });
        return view;
    }
}