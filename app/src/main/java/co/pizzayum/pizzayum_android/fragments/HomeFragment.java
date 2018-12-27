package co.pizzayum.pizzayum_android.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import co.pizzayum.pizzayum_android.R;
import co.pizzayum.pizzayum_android.adapters.PizzaListAdapter;
import co.pizzayum.pizzayum_android.adapters.TabListAdapter;
import co.pizzayum.pizzayum_android.models.PizzaDetailsModel;
import co.pizzayum.pizzayum_android.models.SelectedCatResponse;
import co.pizzayum.pizzayum_android.services.PizzaService;
import co.pizzayum.pizzayum_android.utility.CustomItemClickListener;
import co.pizzayum.pizzayum_android.utility.PizzaConstants;
import co.pizzayum.pizzayum_android.utility.RecyclerTouchListener;
import co.pizzayum.pizzayum_android.utility.SessionManager;

public class HomeFragment extends Fragment {

    List<String> tab_list;
    RecyclerView tabular_view;
    TabListAdapter tabListAdapter;

    List<PizzaDetailsModel> pizzaListData;
    PizzaListAdapter adapter;
    RecyclerView pizza_list;
    RelativeLayout custom_loader_container;
    ImageView loader_img_view;

    /**
     * Create an instance of the Fragment
     *
     * @return Fragment instance
     */
    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_frag, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // calling service to fetch pizza and tab data
        SessionManager session = new SessionManager(getActivity());
        if (session.isLoggedIn()) {
            // Toolbar stuff
            Toolbar toolbar = view.findViewById(R.id.toolbar);
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

            custom_loader_container = view.findViewById(R.id.custom_loader);
            loader_img_view = view.findViewById(R.id.loader_view);
            pizzaCustomLoader();
            custom_loader_container.setVisibility(View.INVISIBLE);

            // initialising horizontal custom tab view using recycler view
            initialisingTabView(view);

            // Creating Pizza List
            initializingPizzaListView(view);

            // collapsible toolbar initializer
            toolBarInitializer(view);

            new PizzaService(getActivity()).fetchingSelectedCat(tabListAdapter, tab_list, adapter,
                    pizzaListData, custom_loader_container);

            tabular_view.addOnItemTouchListener(new RecyclerTouchListener(getContext(),
                    tabular_view, new CustomItemClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    PizzaConstants.ENABLE_POSITION = position;
                    tabListAdapter.notifyDataSetChanged();
                    pizzaListData.clear();
                    for (SelectedCatResponse a : PizzaConstants.getResult) {
                        if (a.getCategory().equals(tab_list.get(position))) {
                            PizzaDetailsModel model = new PizzaDetailsModel(
                                    a.getId(), a.getName(), a.getUrl(), a.getRegular(),
                                    a.getMedium(), a.getLarge(), a.getContent(), a.getCategory(),
                                    a.getVeg(), " ", "");
                            pizzaListData.add(model);
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            }));

        } else {
            session.checkLogin();
        }

    }

    void pizzaCustomLoader() {
        Animation rotation = AnimationUtils.loadAnimation(getActivity(), R.anim.rotation_animation);
        loader_img_view.startAnimation(rotation);
    }

    void toolBarInitializer(View v) {
        final CollapsingToolbarLayout collapsingToolbarLayout = v.findViewById(R.id.collapsing_toolbar);
        AppBarLayout appBarLayout = v.findViewById(R.id.appbar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle("Pizzyum");
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbarLayout.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    void initialisingTabView(View v) {
        // Crating Tab Horizontal scrolling view with the help of Recycler View
        tabular_view = v.findViewById(R.id.tabular_view);
        tab_list = new ArrayList<>();
        tabular_view.setHasFixedSize(false);
        tabular_view.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,
                false));
        tabListAdapter = new TabListAdapter(getContext(), tab_list);
        tabular_view.setItemAnimator(new DefaultItemAnimator());
        tabular_view.setAdapter(tabListAdapter);
    }

    void initializingPizzaListView(View v) {
        pizza_list = v.findViewById(R.id.recycler_view);
        pizzaListData = new ArrayList<>();
        adapter = new PizzaListAdapter(getActivity(), pizzaListData);
        pizza_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        pizza_list.setItemAnimator(new DefaultItemAnimator());
        pizza_list.setAdapter(adapter);
    }
}



