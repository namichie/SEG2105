package com.qwerty123.cookhelper.View.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qwerty123.cookhelper.R;

/**
 * Fragment allowing for the display of helpful information for the user.
 */
public class HelpFragment extends Fragment
{

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View helpView = inflater.inflate(R.layout.fragment_help, container, false);

        return helpView;
    }

}
