package com.shidduckbook.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shidduckbook.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PartnerFragment extends Fragment {

    private FragmentManager mFragmentManager;
    public PartnerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_partner, container, false);
        mFragmentManager = getActivity().getSupportFragmentManager();

        callNextFragment();

        return rootView;
    }

    private void callNextFragment() {

        FragmentTransaction fragmentTransaction;
        Fragment argumentFragment = new Partner_Characterstics_Tab();
        fragmentTransaction = mFragmentManager.beginTransaction();
        getActivity().getSupportFragmentManager().popBackStack();
        fragmentTransaction.replace(R.id.fragment_mainLayout, argumentFragment).commit();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
