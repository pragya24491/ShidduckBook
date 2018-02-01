package com.shidduckbook.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.shidduckbook.R;
import com.shidduckbook.Util.AppPreferences;

/**
 * A simple {@link Fragment} subclass.
 */
public class PreviewFragment extends Fragment {


    TextView input_fname, input_lname, input_email, input_fathername, input_mothersname, input_contact_no, input_country, input_state, input_city, dob;

    public PreviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_preview, container, false);
        initView(rootView);

        if (!AppPreferences.getUserId(getActivity()).equalsIgnoreCase("")) {

            input_fname.setText(AppPreferences.getFirstName(getActivity()));
            input_lname.setText(AppPreferences.getLastName(getActivity()));
            input_email.setText(AppPreferences.getEmailId(getActivity()));
            input_fathername.setText(AppPreferences.getFatherName(getActivity()));
            input_mothersname.setText(AppPreferences.getMotherName(getActivity()));
            input_contact_no.setText(AppPreferences.getPhoneNumber(getActivity()));
            input_country.setText(AppPreferences.getCountry(getActivity()));
            input_state.setText(AppPreferences.getState(getActivity()));
            input_city.setText(AppPreferences.getCity(getActivity()));
            dob.setText(AppPreferences.getDob(getActivity()));
        }

        return rootView;

    }

    private void initView(View rootView) {

        input_fname = (TextView) rootView.findViewById(R.id.input_fname);
        input_lname = (TextView) rootView.findViewById(R.id.input_lname);
        input_email = (TextView) rootView.findViewById(R.id.input_email);
        input_fathername = (TextView) rootView.findViewById(R.id.input_fathername);
        input_mothersname = (TextView) rootView.findViewById(R.id.input_mothersname);
        input_contact_no = (TextView) rootView.findViewById(R.id.input_contact_no);
        input_country = (TextView) rootView.findViewById(R.id.input_country);
        input_state = (TextView) rootView.findViewById(R.id.input_state);
        input_city = (TextView) rootView.findViewById(R.id.input_city);
        dob = (TextView) rootView.findViewById(R.id.dob);

    }


}
