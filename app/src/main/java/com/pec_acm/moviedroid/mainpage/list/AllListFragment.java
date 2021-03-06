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
import com.pec_acm.moviedroid.databinding.FragmentAllListBinding;
import com.pec_acm.moviedroid.firebase.User;

public class AllListFragment extends Fragment {
    private FragmentAllListBinding binding;
    private ListViewModel listViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentAllListBinding.inflate(inflater,container,false);
        View view=binding.getRoot();

        RecyclerView allList = binding.allList;
        listViewModel = new ViewModelProvider(this).get(ListViewModel.class);
        listViewModel.getUser(FirebaseAuth.getInstance().getUid());
        ListAdapter listAdapter = new ListAdapter(requireContext(),listViewModel,this);
        allList.setAdapter(listAdapter);
        listViewModel.getUser().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if(user==null) return;
                listAdapter.setItemList(user.getUserList());
            }
        });
        return view;
    }
}