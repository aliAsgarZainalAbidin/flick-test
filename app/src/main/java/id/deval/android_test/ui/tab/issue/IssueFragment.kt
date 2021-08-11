package id.deval.android_test.ui.tab.issue

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.textview.MaterialTextView
import id.deval.android_test.BuildConfig.TAG
import id.deval.android_test.MainActivity
import id.deval.android_test.R
import id.deval.android_test.adapter.IssueRecyclerViewAdapter
import id.deval.android_test.api.ApiFactory
import id.deval.android_test.model.Issue
import id.deval.android_test.model.ModelWrapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//val listIssue: MutableList<Issue?> = mutableListOf(
//    Issue("tmns/a", "2021-06-02T09:39:45Z", "2021-06-02T09:39:45Z", 12),
//    Issue("flick-test/android", "2021-06-02T09:39:45Z", "2021-06-02T09:39:45Z", 12),
//    Issue("flick-android/a", "2021-06-02T09:39:45Z", "2021-06-02T09:39:45Z", 12),
//    Issue("flick", "2021-06-02T09:39:45Z", "2021-06-02T09:39:45Z", 12),
//    Issue("test-android", "2021-06-02T09:39:45Z", "2021-06-02T09:39:45Z", 12),
//)

var listIssue: MutableList<Issue?> = mutableListOf()

class IssueFragment : Fragment() {

    val restForeground by lazy { ApiFactory.create() }
    lateinit var tvLoadMore: MaterialTextView
    lateinit var rvIssue: RecyclerView
    lateinit var issueAdapter: IssueRecyclerViewAdapter
    var page = 1
    var root: SwipeRefreshLayout? = null

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
        tvLoadMore = view.findViewById(R.id.tv_issueFrag_more)

        getIssues(page)

        tvLoadMore.setOnClickListener {
            page += 1
            getIssues(page)
        }

        val mainActivity: MainActivity = this.activity as MainActivity
        root = mainActivity.findViewById(R.id.root)

        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rvIssue.layoutManager = layoutManager
        rvIssue.isNestedScrollingEnabled = false
        issueAdapter = IssueRecyclerViewAdapter(listIssue)
        rvIssue.adapter = issueAdapter
    }

    fun getIssues(page: Int) {
        try {
            var result = restForeground.getIssues(page = page)
            result.enqueue(object : Callback<ModelWrapper<Issue>> {
                override fun onResponse(
                    call: Call<ModelWrapper<Issue>>,
                    response: Response<ModelWrapper<Issue>>
                ) {
                    if (response.isSuccessful) {
                        var data = response.body()
                        if (data != null) {
                            data.items?.let { listIssue.addAll(it) }
                            issueAdapter.notifyDataSetChanged()
                            tvLoadMore.visibility = View.VISIBLE
                            root?.isRefreshing = false
                        }
                    } else {
                        Log.d(TAG, "onResponse: $response")
                    }
                }

                override fun onFailure(call: Call<ModelWrapper<Issue>>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        } catch (e: Exception) {
            Log.d(TAG, "getIssues: $e")
        }
    }

}