package com.walagran.wwf.ui.common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.walagran.wwf.R;
import com.walagran.wwf.ui.MainActivity;

/**
 * A UI element on the top of each activity for common controls.
 */
public class ControlsBar extends Fragment {
    private static final String CONTROL_BAR_TITLE = "ControlBarTitleParam";
    private static final String SHOW_HOME = "ShowHomeParam";
    private static final String SHOW_TITLE = "ShowTitleParam";

    private Context context;

    private String controlBarTitle = "Control Bar";
    private boolean showHome = true;
    private boolean showTitle = true;

    public ControlsBar() {
        // Required empty public constructor
    }

    /**
     * Controls Bar Factory with all the parameters.
     *
     * @param controlBarTitle Title shown on the control bar.
     * @param showHome        Whether the home button must be shown.
     * @param showTitle       Whether the title must be shown.
     * @return A new instance of fragment ControlsBar.
     */
    public static ControlsBar newInstance(String controlBarTitle,
                                          boolean showHome, boolean showTitle) {
        ControlsBar fragment = new ControlsBar();
        Bundle args = new Bundle();
        args.putString(CONTROL_BAR_TITLE, controlBarTitle);
        args.putBoolean(SHOW_HOME, showHome);
        args.putBoolean(SHOW_TITLE, showTitle);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Controls Bar Factory when everything is shown.
     *
     * @param controlBarTitle Title shown on the control bar.
     * @return A new instance of fragment ControlsBar.
     */
    public static ControlsBar newInstance(String controlBarTitle) {
        ControlsBar fragment = new ControlsBar();
        Bundle args = new Bundle();
        args.putString(CONTROL_BAR_TITLE, controlBarTitle);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            controlBarTitle = getArguments().getString(CONTROL_BAR_TITLE,
                    "Control Bar");
            showHome = getArguments().getBoolean(SHOW_HOME, true);
            showTitle = getArguments().getBoolean(SHOW_TITLE, false);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_controls_bar,
                container, false);

        TextView controlBarTitleView =
                rootView.findViewById(R.id.control_bar_title);
        controlBarTitleView.setText(controlBarTitle);
        controlBarTitleView.setVisibility(showTitle ? View.VISIBLE : View.GONE);

        Button controlBarHomeButtonView =
                rootView.findViewById(R.id.control_bar_home);
        controlBarHomeButtonView.setOnClickListener(this::onHomeButtonClicked);
        controlBarHomeButtonView.setVisibility(showHome ? View.VISIBLE :
                View.GONE);

        return rootView;
    }

    private void onHomeButtonClicked(View view) {
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
        requireActivity().finish();
    }
}