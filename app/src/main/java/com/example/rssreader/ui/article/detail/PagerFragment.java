package com.example.rssreader.ui.article.detail;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.rssreader.databinding.PagerFragmentBinding;

public class PagerFragment extends Fragment {

    private PagerViewModel mViewModel;
    private PagerFragmentBinding binding;

    private static String EXTRA_KEY = "url";
    public static PagerFragment newInstance(String url) {
        Bundle arguments = new Bundle();
        arguments.putString(EXTRA_KEY, url);
        PagerFragment fragment = new PagerFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    private String mUrl = "";
    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUrl = getArguments().getString(EXTRA_KEY, "");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(PagerViewModel.class);
        binding = PagerFragmentBinding.inflate(getLayoutInflater());
        mViewModel.pUrl.postValue(mUrl);
        binding.setLifecycleOwner(this);
        binding.setViewModel(mViewModel);
        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.setHorizontalScrollBarEnabled(false);
        binding.webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                binding.loadingIndicator.show();
                view.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                binding.loadingIndicator.hide();
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }

            @Override
            public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
                return super.shouldOverrideKeyEvent(view, event);
            }
        });
        return binding.getRoot();
    }
}