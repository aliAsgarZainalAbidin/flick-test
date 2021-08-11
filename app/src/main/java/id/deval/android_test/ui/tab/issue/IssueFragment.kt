package id.deval.android_test.ui.tab.issue

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.deval.android_test.R
import id.deval.android_test.adapter.IssueRecyclerViewAdapter
import id.deval.android_test.model.Issue

val listIssue: MutableList<Issue?> = mutableListOf(
    Issue("tmns/a", "2021-06-02T09:39:45Z", "2021-06-02T09:39:45Z", 12),
    Issue("flick-test/android", "2021-06-02T09:39:45Z", "2021-06-02T09:39:45Z", 12),
    Issue("flick-android/a", "2021-06-02T09:39:45Z", "2021-06-02T09:39:45Z", 12),
    Issue("flick", "2021-06-02T09:39:45Z", "2021-06-02T09:39:45Z", 12),
    Issue("test-android", "2021-06-02T09:39:45Z", "2021-06-02T09:39:45Z", 12),
)

class IssueFragment : Fragment() {

    lateinit var rvIssue: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_issue, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvIssue = view.findViewById(R.id.rv_issue_container)

        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rvIssue.layoutManager = layoutManager
        rvIssue.isNestedScrollingEnabled = false
        val adapter = IssueRecyclerViewAdapter(listIssue)
        rvIssue.adapter = adapter
    }

}