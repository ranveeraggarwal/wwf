package com.walagran.wwf.ui.common

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.walagran.wwf.R
import com.walagran.wwf.ui.MainActivity

/**
 * A UI element on the top of each activity for common controls.
 */
class ControlsBar : Fragment() {
    private var controlBarTitle = "Control Bar"
    private var showHome = true
    private var showTitle = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            controlBarTitle = requireArguments().getString(CONTROL_BAR_TITLE,
                "Control Bar")
            showHome = requireArguments().getBoolean(SHOW_HOME, true)
            showTitle = requireArguments().getBoolean(SHOW_TITLE, false)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_controls_bar,
            container, false)
        val controlBarTitleView =
            rootView.findViewById<TextView>(R.id.control_bar_title)
        controlBarTitleView.text = controlBarTitle
        controlBarTitleView.visibility =
            if (showTitle) View.VISIBLE else View.GONE
        val controlBarHomeButtonView =
            rootView.findViewById<Button>(R.id.control_bar_home)
        controlBarHomeButtonView.setOnClickListener {
            onHomeButtonClicked()
        }
        controlBarHomeButtonView.visibility =
            if (showHome) View.VISIBLE else View.GONE
        return rootView
    }

    private fun onHomeButtonClicked() {
        val intent = Intent(context, MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    companion object {
        private const val CONTROL_BAR_TITLE = "ControlBarTitleParam"
        private const val SHOW_HOME = "ShowHomeParam"
        private const val SHOW_TITLE = "ShowTitleParam"

        /**
         * Controls Bar Factory with all the parameters.
         *
         * @param controlBarTitle Title shown on the control bar.
         * @param showHome        Whether the home button must be shown.
         * @param showTitle       Whether the title must be shown.
         * @return A new instance of fragment ControlsBar.
         */
        fun newInstance(
            controlBarTitle: String?,
            showHome: Boolean, showTitle: Boolean
        ): ControlsBar {
            val fragment = ControlsBar()
            val args = Bundle()
            args.putString(CONTROL_BAR_TITLE, controlBarTitle)
            args.putBoolean(SHOW_HOME, showHome)
            args.putBoolean(SHOW_TITLE, showTitle)
            fragment.arguments = args
            return fragment
        }

        /**
         * Controls Bar Factory when everything is shown.
         *
         * @param controlBarTitle Title shown on the control bar.
         * @return A new instance of fragment ControlsBar.
         */
        @JvmStatic
        fun newInstance(controlBarTitle: String?): ControlsBar {
            val fragment = ControlsBar()
            val args = Bundle()
            args.putString(CONTROL_BAR_TITLE, controlBarTitle)
            fragment.arguments = args
            return fragment
        }
    }
}