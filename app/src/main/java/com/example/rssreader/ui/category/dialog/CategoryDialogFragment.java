package com.example.rssreader.ui.category.dialog;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import dagger.hilt.android.AndroidEntryPoint;

import android.view.LayoutInflater;

import com.example.rssreader.R;
import com.example.rssreader.databinding.CategoryFragmentDialogBinding;

import org.jetbrains.annotations.NotNull;

@AndroidEntryPoint
public class CategoryDialogFragment extends DialogFragment {
    private CategoryDialogFragmentViewModel mViewModel;

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
        CategoryFragmentDialogBinding binding = CategoryFragmentDialogBinding.inflate(requireActivity().getLayoutInflater());
        mViewModel = getDefaultViewModelProviderFactory().create(CategoryDialogFragmentViewModel.class);
        binding.setViewModel(mViewModel);
        binding.setLifecycleOwner(this);
        mViewModel.loadCategory(mCategoryId);
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(binding.getRoot())
                // Add action buttons
                .setPositiveButton(R.string.alert_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        mViewModel.regist();
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