package com.example.rssreader.ui.article.detail;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class PagerFragmentStateAdapter extends FragmentStateAdapter {
    private List<String> lists;

    public PagerFragmentStateAdapter(@NonNull @NotNull FragmentActivity fragmentActivity, List<String> value) {
        super(fragmentActivity);
        lists = value;
    }

    @NonNull
    @NotNull
    @Override
    public Fragment createFragment(int position) {
        return PagerFragment.newInstance(lists.get(position));
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
}
