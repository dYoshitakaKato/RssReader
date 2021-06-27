package com.example.rssreader;

import android.os.Bundle;
import android.view.View;

import com.example.rssreader.databinding.ActivityMainBinding;
import com.example.rssreader.entities.CategoryData;
import com.example.rssreader.ui.CategorySelectListener;
import com.example.rssreader.ui.article.list.ArticlesFragment;
import com.example.rssreader.ui.category.dialog.CategoryDialogFragment;
import com.example.rssreader.ui.category.list.CategoryRecycleFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity implements CategorySelectListener {

    private ActivityMainBinding mBinding;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            return;
        }
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);
        mBinding.setLifecycleOwner(this);
        mBinding.setOwner(this);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, ArticlesFragment.newInstance(0)).commitNow();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.recycle_fragment, CategoryRecycleFragment.newInstance()).commitNow();
    }

    public void onClick(){
        CategoryDialogFragment.newInstance().show(getSupportFragmentManager(), CategoryDialogFragment.class.getName());
    }

    @Override
    public void onSelectedCategory(CategoryData category) {
        if (mBinding == null) {
            return;
        }
        mBinding.drawerLayout.closeDrawers();
    }
}