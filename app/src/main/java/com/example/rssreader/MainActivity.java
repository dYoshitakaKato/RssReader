package com.example.rssreader;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.example.rssreader.databinding.ActivityMainBinding;
import com.example.rssreader.entities.CategoryData;
import com.example.rssreader.ui.ItemClickListener;
import com.example.rssreader.ui.article.list.ArticlesFragment;
import com.example.rssreader.ui.category.dialog.CategoryDialogFragment;
import com.example.rssreader.ui.category.list.CategoryRecycleFragment;
import com.google.android.material.navigation.NavigationView;

import androidx.fragment.app.Fragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            return;
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, ArticlesFragment.newInstance(0)).commitNow();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.setLifecycleOwner(this);
        binding.setOwner(this);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.recycle_fragment, CategoryRecycleFragment.newInstance()).commitNow();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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