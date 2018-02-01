package com.shidduckbook.Fragment;

import android.support.v4.app.Fragment;

import com.shidduckbook.Interface.BackPressImpl;
import com.shidduckbook.Interface.OnBackPressListener;


/**
 * Created by shahabuddin on 6/6/14.
 */
public class RootFragment extends Fragment implements OnBackPressListener {

    @Override
    public boolean onBackPressed() {
        return new BackPressImpl(this).onBackPressed();
    }
}
