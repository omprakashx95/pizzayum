package co.pizzayum.pizzayum_android.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import co.pizzayum.pizzayum_android.R;
import co.pizzayum.pizzayum_android.utility.DatabaseHelper;
import co.pizzayum.pizzayum_android.utility.SessionManager;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    RelativeLayout logout_button_view;
    SessionManager session;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        logout_button_view = view.findViewById(R.id.logout_button_container);
        session = new SessionManager(getActivity());

        logout_button_view.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logout_button_container:
                session.logoutUser();
                new DatabaseHelper(getActivity()).clearCart();
                break;
        }
    }
}
