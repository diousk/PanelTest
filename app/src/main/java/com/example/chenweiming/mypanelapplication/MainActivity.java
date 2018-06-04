package com.example.chenweiming.mypanelapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.chenweiming.mypanelapplication.model.GiftFactory;
import com.example.chenweiming.mypanelapplication.model.GiftSection;
import com.example.chenweiming.mypanelapplication.panel.SlidingUpPanelLayout;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private CompositeDisposable compositeDisposable;
    private PagerAdapter pagerAdapter;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        compositeDisposable = new CompositeDisposable();
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupPanel();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });

        fetchGift();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }

    @Override
    public void onBackPressed() {
        final SlidingUpPanelLayout panel = findViewById(R.id.sliding_layout);
        if (panel != null &&
                (panel.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED || panel.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED)) {
            panel.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
        } else {
            panel.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
        }
    }

    private void fetchGift() {
        Disposable disposable = Observable.fromCallable(new Callable<List<GiftSection>>() {
            @Override
            public List<GiftSection> call() throws Exception {
                return GiftFactory.provideGiftSections(6);
            }
        }).subscribeOn(Schedulers.io()).delay(3, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<GiftSection>>() {
            @Override
            public void accept(List<GiftSection> giftSections) throws Exception {
                Log.d("Panel", "gift section size: " + giftSections.size());
                if (giftSections.size() == 0) {
                    return;
                }

                tabLayout.removeAllTabs();
                for (GiftSection section : giftSections) {
                    tabLayout.addTab(tabLayout.newTab().setText(section.categoryTitle));
                }
                pagerAdapter.setGiftSections(giftSections);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        });
        compositeDisposable.add(disposable);
    }

    private void setupPanel() {
        final SlidingUpPanelLayout panel = findViewById(R.id.sliding_layout);
        panel.setFadeOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("PANEL", "click");
                panel.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
            }
        });

        panel.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                Log.d("Panel", "newState: " + newState);
            }
        });

//        panel.setTouchEnabled(false);

        View panelView = panel.getChildAt(1);
        final ViewPager viewPager = panelView.findViewById(R.id.giftPager);

        List<GiftSection> giftSections = GiftFactory.provideGiftSections(0);
        pagerAdapter.setGiftSections(giftSections);

        viewPager.setAdapter(pagerAdapter);
        tabLayout = panelView.findViewById(R.id.tabLayout);
        for (GiftSection section : giftSections) {
            tabLayout.addTab(tabLayout.newTab().setText(section.categoryTitle));
        }
        viewPager.setOffscreenPageLimit(5);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d("Panel", "onPageSelected: " + position);
                View scrollableView = pagerAdapter.getScrollableView();
                panel.setScrollableView(scrollableView);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d("Panel", "onTabSelected");
                //TODO: find and set scrollable view (recyclerview) here
                View scrollableView = pagerAdapter.getScrollableView();
                panel.setScrollableView(scrollableView);
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
        });
    }

    private void toggle() {
        Log.d("Panel", "toggle");
        final SlidingUpPanelLayout panel = findViewById(R.id.sliding_layout);
        if (panel.getPanelState() != SlidingUpPanelLayout.PanelState.HIDDEN) {
            panel.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
        } else {
            panel.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
        }
    }
}
