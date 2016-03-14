package com.sys1yagi.android.expandanimator.sample.fragment;

import com.sys1yagi.android.expandanimator.sample.R;
import com.sys1yagi.android.expandanimator.sample.databinding.FragmentMainBinding;
import com.sys1yagi.android.expandanimator.sample.view.ListItemAdapter;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainFragment extends Fragment {

    static final String TAG = MainFragment.class.getSimpleName();

    FragmentMainBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = DataBindingUtil.bind(view);
        setupUi();
    }

    void setupUi() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ListItemAdapter adapter = new ListItemAdapter();
        adapter.add("Expand, Collapse");
        adapter.add("Nested Layout");
        adapter.add("Partial expand or collapse");
        binding.recyclerView.setAdapter(adapter);

        adapter.setListener(new ListItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                openFragment(position);
            }
        });
    }

    void openFragment(int position) {
        switch (position) {
            case 0:
                getFragmentManager()
                        .beginTransaction()
                        .addToBackStack(TAG)
                        .replace(R.id.container,
                                ExpandAndCollapseFragmentCreator.newBuilder().build())
                        .commit();
                break;
            default:
                // no op
        }
    }
}
