package ru.aumsu.www.application.ui.schedule

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_schedule.view.*
import ru.aumsu.www.application.BaseActivity
import ru.aumsu.www.application.R

class ScheduleFragment : Fragment() {

    private lateinit var scheduleViewModel: ScheduleViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        scheduleViewModel =
            ViewModelProviders.of(this).get(ScheduleViewModel::class.java)
        return inflater.inflate(R.layout.fragment_schedule, container, false)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.schedule.settings.javaScriptEnabled = true
        view.schedule.webViewClient = ResultWebViewClient()
        view.schedule.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url=" + BaseActivity.userData!!.entities.find {
            it.schedule != null && it.schedule.isNotEmpty()
        }!!.schedule)
    }

    class ResultWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView,
            url: String
        ): Boolean {
            view.loadUrl(url)
            return true
        }
    }
}