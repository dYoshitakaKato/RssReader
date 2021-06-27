package com.example.rssreader.ui.category.dialog;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import dagger.hilt.android.AndroidEntryPoint;

import android.view.LayoutInflater;

import com.example.rssreader.R;
import com.example.rssreader.databinding.CategoryFragmentDialogBinding;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

@AndroidEntryPoint
public class CategoryDialogFragment extends DialogFragment {
    private CategoryDialogFragmentViewModel mViewModel;
    private CategoryFragmentDialogBinding mBinding;

    public static CategoryDialogFragment newInstance() {
        return newInstance(0);
    }
    public static CategoryDialogFragment newInstance(int categoryId) {
        CategoryDialogFragment fragment = new CategoryDialogFragment();
        Bundle args = new Bundle();
        args.putInt(CATEGORY_ID, categoryId);
        fragment.setArguments(args);
        return fragment;
    }

    private static final String CATEGORY_ID = "category-id";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mCategoryId = getArguments().getInt(CATEGORY_ID);
        }
    }

    private int mCategoryId = -1;

    @NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        mBinding = CategoryFragmentDialogBinding.inflate(requireActivity().getLayoutInflater());
        mViewModel = getDefaultViewModelProviderFactory().create(CategoryDialogFragmentViewModel.class);
        mBinding.setViewModel(mViewModel);
        mBinding.setLifecycleOwner(this);
        mViewModel.loadCategory(mCategoryId);
        mViewModel.pSnackbar.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s == null) {
                    return;
                }
                if (s.isEmpty()) {
                    return;
                }
                Snackbar.make(getActivity().getWindow().getDecorView().getRootView(), s, Snackbar.LENGTH_LONG).show();
            }
        });
        builder.setView(mBinding.getRoot())
                .setPositiveButton(R.string.regist, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if (mViewModel.isValid()) {
                            mViewModel.regist();
                            return;
                        }
                        mViewModel.pSnackbar.setValue("名前が入力されていないため、登録に失敗しました。");
                    }
                })
                .setNegativeButton(R.string.alert_negative, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();;
                    }
                });
        return builder.create();
    }
}