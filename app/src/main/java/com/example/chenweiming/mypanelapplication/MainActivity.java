package com.example.chenweiming.mypanelapplication;

import android.content.res.Configuration;
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
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chenweiming.mypanelapplication.model.Gift;
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
    private TabLayout tabLayout;
    private int orientation;
    private Disposable giftDisposable;
    private BounceBallsLoading loadingView;

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
                final SlidingUpPanelLayout panel = findViewById(R.id.sliding_layout);
                Log.d("Panel", "current state: " + panel.getPanelState());
                toggle();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d("Panel", "activity config change");
        if (orientation != newConfig.orientation) {
            orientation = newConfig.orientation;

            final SlidingUpPanelLayout panel = findViewById(R.id.sliding_layout);
            View panelView = panel.getChildAt(1);
            final ViewPager viewPager = panelView.findViewById(R.id.giftPager);
            ViewGroup.LayoutParams params = viewPager.getLayoutParams();
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                params.height = (int) getResources().getDimension(R.dimen.panel_height);
            } else {
                params.height = (int) getResources().getDimension(R.dimen.panel_height_land);
            }
        }
    }

    @Override
    public void onBackPressed() {
        final SlidingUpPanelLayout panel = findViewById(R.id.sliding_layout);
        if (panel == null) {
            return;
        }
        if ((panel.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED || panel.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED)) {
            panel.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
        } else {
            panel.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
        }
    }

    private void fetchGift() {
        giftDisposable = Observable.fromCallable(new Callable<List<GiftSection>>() {
            @Override
            public List<GiftSection> call() throws Exception {
                return GiftFactory.provideGiftSections(6);
            }
        }).subscribeOn(Schedulers.io()).delay(2000, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<GiftSection>>() {
            @Override
            public void accept(List<GiftSection> giftSections) throws Exception {
                Log.d("Panel", "gift section size: " + giftSections.size());
                loadingView.cancel();
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
    }

    private void setupPanel() {
        final SlidingUpPanelLayout panel = findViewById(R.id.sliding_layout);
        loadingView = findViewById(R.id.loading_view);
        final TextView tvBalance = findViewById(R.id.tv_balance);
        final TextView tvDonate = findViewById(R.id.tv_donate);
        tvDonate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gift gift = pagerAdapter.getSelectedGift();
                Log.d("PANEL", "selected gift: " + gift);

                String text = gift == null ? "null" : gift.text;
                Toast.makeText(view.getContext(), "select: " + text, Toast.LENGTH_LONG).show();
            }
        });

        panel.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                Log.d("Panel", "newState: " + newState);
                if (newState == SlidingUpPanelLayout.PanelState.HIDDEN || newState == SlidingUpPanelLayout.PanelState.DRAGGING) {
                    if (giftDisposable != null && !giftDisposable.isDisposed()) {
                        giftDisposable.dispose();
                    }
                }
                if (newState == SlidingUpPanelLayout.PanelState.HIDDEN) {
                    loadingView.cancel();
                    tabLayout.removeAllTabs();
                    pagerAdapter.clear();
                    tvBalance.setText("-");
                } else if (newState == SlidingUpPanelLayout.PanelState.EXPANDED) {
                    loadingView.start();
                    tvBalance.setText("1234");
                    fetchGift();
                }
            }
        });

        panel.setTouchEnabled(false);

        View panelView = panel.getChildAt(1);
        tabLayout = panelView.findViewById(R.id.tabLayout);
        final ViewPager viewPager = panelView.findViewById(R.id.giftPager);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(2);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d("Panel", "onTabSelected");
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
        if (panel == null) {
            return;
        }
        if ((panel.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED || panel.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED)) {
            panel.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
        } else {
            panel.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
        }
    }
}
