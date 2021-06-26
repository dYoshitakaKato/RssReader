package com.example.rssreader.ui.channel.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

import com.example.rssreader.R;
import com.example.rssreader.databinding.FragmentChannelDialogBinding;
import com.example.rssreader.entities.ChannelData;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChannelDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
public class ChannelDialogFragment extends DialogFragment {

    private ChannelDialogViewModel mViewModel;
    private String mLink = "";
    private int mCategoryId = 0;
    private boolean mIsEdit = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mLink = getArguments().getString(CHANNEL_LINK);
            mCategoryId = getArguments().getInt(CATEGORY_ID);
            mIsEdit = getArguments().getBoolean(IS_EDIT);
        }
    }

    @NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        FragmentChannelDialogBinding binding = FragmentChannelDialogBinding.inflate(requireActivity().getLayoutInflater());
        mViewModel = new ViewModelProvider(this).get(ChannelDialogViewModel.class);
        binding.setViewModel(mViewModel);
        ChannelData data = mViewModel.channel.getValue();
        data.link = mLink;
        data.categoryId = mCategoryId;
        mViewModel.loadChannel().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(value -> mViewModel.channel.postValue(value),
                        throwable -> Snackbar.make(getView(), "Unable to submit channel data", Snackbar.LENGTH_SHORT).show());
        binding.setLifecycleOwner(this);
        // Get the layout inflater
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(binding.getRoot())
                // Add action buttons
                .setPositiveButton(R.string.alert_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        mViewModel.regist(mIsEdit);
                    }
                })
                .setNegativeButton(R.string.alert_negative, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();;
                    }
                });
        return builder.create();
    }

    private static final String CHANNEL_LINK = "channel-link";
    private static final String CATEGORY_ID = "category-id";
    private static final String IS_EDIT = "is-edit";

    public static ChannelDialogFragment newInstance() {
        return newInstance(0);
    }

    public static ChannelDialogFragment newInstance(int categoryId) {
        return newInstance("", categoryId, false);
    }

    public static ChannelDialogFragment newInstance(String link, int categoryId) {
        return newInstance(link, categoryId, true);
    }

    private static ChannelDialogFragment newInstance(String link, int categoryId, boolean isEdit) {
        ChannelDialogFragment fragment = new ChannelDialogFragment();
        Bundle args = new Bundle();
        args.putString(CHANNEL_LINK, link);
        args.putInt(CATEGORY_ID, categoryId);
        args.putBoolean(IS_EDIT, isEdit);
        fragment.setArguments(args);
        return fragment;
    }


}