package com.example.rssreader.ui.channel.list;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.rssreader.R;
import com.example.rssreader.databinding.CategoryRecycleFragmentBinding;
import com.example.rssreader.databinding.FragmentChannelListBinding;
import com.example.rssreader.entities.ChannelData;
import com.example.rssreader.ui.ItemClickListener;
import com.example.rssreader.ui.category.dialog.CategoryDialogFragment;
import com.example.rssreader.ui.category.list.CategoryRecycleViewModel;
import com.example.rssreader.ui.channel.dialog.ChannelDialogFragment;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * A fragment representing a list of Items.
 */
@AndroidEntryPoint
public class ChannelFragment extends Fragment implements ItemClickListener<ChannelData> {

    private static final String CATEGORY_ID = "category-id";
    private int mCategoryId = 0;

    public static ChannelFragment newInstance(int categoryId) {
        ChannelFragment fragment = new ChannelFragment();
        Bundle args = new Bundle();
        args.putInt(CATEGORY_ID, categoryId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mCategoryId = getArguments().getInt(CATEGORY_ID);
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull @NotNull MenuItem item) {
        int position = -1;
        try {
            position = mAdapter.getPosition();
        } catch (Exception e) {
            Log.d(TAG, e.getLocalizedMessage(), e);
            return super.onContextItemSelected(item);
        }
        switch (item.getItemId()) {
            case R.string.edit:
                ChannelData data = mAdapter.getCurrentList().get(position);
                showEditDialog(data);
                break;
            case R.string.delete:
                mViewModel.delete(mAdapter.getCurrentList().get(position));
                break;
            default:
                Snackbar.make(getView(), "Unexpected value: " + item.getItemId(),  Snackbar.LENGTH_SHORT).show();
        }
        return super.onContextItemSelected(item);
    }

    private ChannelRecyclerViewAdapter mAdapter;
    private ChannelViewModel mViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentChannelListBinding binding = FragmentChannelListBinding.inflate(getLayoutInflater());
        binding.setLifecycleOwner(this);
        mViewModel = new ViewModelProvider(this).get(ChannelViewModel.class);
        binding.setViewModel(mViewModel);
        mViewModel.setOnClickListenr(this);
        mAdapter = new ChannelRecyclerViewAdapter(binding);
        final Observer<List<ChannelData>> observer = new Observer<List<ChannelData>>() {
            @Override
            public void onChanged(@Nullable final List<ChannelData> data) {
                mAdapter.submitList(data);
            }
        };
        mViewModel.channel.observe(binding.getLifecycleOwner(), observer);
        binding.channelList.setAdapter(mAdapter);
        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(this.getActivity(), DividerItemDecoration.VERTICAL);
        binding.channelList.addItemDecoration(itemDecoration);

        return binding.getRoot();
    }

    private void reload() {
        mViewModel.reload(mCategoryId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(value -> mAdapter.submitList(value),
                        throwable -> Snackbar.make(getView(), "Unable to submit channel data", Snackbar.LENGTH_SHORT).show());
    }

    @Override
    public void onResume() {
        super.onResume();
        reload();
    }

    private void showEditDialog(ChannelData data) {
        ChannelDialogFragment.newInstance(data.link, data.categoryId).show(getParentFragmentManager(), ChannelDialogFragment.class.getName());
    }

    @Override
    public void onItemClicked(ChannelData item) {
        showEditDialog(item);
    }
}