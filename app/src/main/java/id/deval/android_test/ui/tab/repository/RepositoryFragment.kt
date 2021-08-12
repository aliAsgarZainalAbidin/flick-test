package id.deval.android_test.ui.tab.repository

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.textview.MaterialTextView
import id.deval.android_test.BuildConfig.TAG
import id.deval.android_test.MainActivity
import id.deval.android_test.R
import id.deval.android_test.adapter.RepositoryRecyclerViewAdapter
import id.deval.android_test.api.ApiFactory
import id.deval.android_test.api.ApiInterface
import id.deval.android_test.model.ModelWrapper
import id.deval.android_test.model.Repository
import id.deval.android_test.repository.DataRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//val listRepository: MutableList<Repository?> = mutableListOf(
//    Repository(
//        "flick-text/android",
//        "Test yang dilakukan untuk menguji kemampuan peserta atau calon pekerja yang nanti akan ditempatkan askdjasdkl klkaskj asdlk",
//        120230,
//        "Java",
//        12312,
//        null
//    ),
//    Repository(
//        "flick-text/android",
//        "Test yang dilakukan untuk menguji kemampuan peserta atau calon pekerja yang nanti akan ditempatkan askdjasdkl klkaskj asdlk",
//        120230,
//        "Vue",
//        12312,
//        null
//    ),
//    Repository(
//        "flick-text/android",
//        "Test yang dilakukan untuk menguji kemampuan peserta atau calon pekerja yang nanti akan ditempatkan askdjasdkl klkaskj asdlk",
//        120230,
//        "Go",
//        12312,
//        null
//    ),
//    Repository(
//        "flick-text/android",
//        "Test yang dilakukan untuk menguji kemampuan peserta atau calon pekerja yang nanti akan ditempatkan askdjasdkl klkaskj asdlk",
//        120230,
//        "JavaScript",
//        12312,
//        null
//    ),
//    Repository(
//        "flick-text/android",
//        "Test yang dilakukan untuk menguji kemampuan peserta atau calon pekerja yang nanti akan ditempatkan askdjasdkl klkaskj asdlk",
//        120230,
//        "Kotlin",
//        12312,
//        null
//    ),
//)

var listRepository: MutableList<Repository?> = mutableListOf()
val restForeground by lazy { ApiFactory.create() }
lateinit var rvRepository: RecyclerView
lateinit var tvLoadMore: MaterialTextView
lateinit var repoAdapter: RepositoryRecyclerViewAdapter
var root: SwipeRefreshLayout? = null

fun getRepositories(page: Int, q: String = "q") {
    try {
        var result = restForeground.getRepositories(page = page, query = q)
        result.enqueue(object : Callback<ModelWrapper<Repository>> {
            override fun onResponse(
                call: Call<ModelWrapper<Repository>>,
                response: Response<ModelWrapper<Repository>>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        data.items?.let { listRepository.addAll(it) }
                        repoAdapter.notifyDataSetChanged()
                        Log.d(TAG, "onResponse: $data")
                        tvLoadMore.visibility = View.VISIBLE
                        root?.isRefreshing = false
                    }
                } else {
                    Log.d(TAG, "onResponse: $response")
                }
            }

            override fun onFailure(call: Call<ModelWrapper<Repository>>, t: Throwable) {
                Log.d(TAG, "onFailure: $t")
            }

        })
    } catch (e: Exception) {
        Log.d(TAG, "getRepositories: $e")
    }
}

class RepositoryFragment : Fragment() {

    val repoViewModel: RepositoryViewModel by viewModels()
    var page = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_repository, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvRepository = view.findViewById(R.id.rv_repo_container)
        tvLoadMore = view.findViewById(R.id.tv_repoFrag_more)

        rvRepository.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rvRepository.isNestedScrollingEnabled = false
        repoAdapter = RepositoryRecyclerViewAdapter(listRepository)
        rvRepository.adapter = repoAdapter

//        getRepositories(page)
        val mainActivity: MainActivity = this.activity as MainActivity
        root = mainActivity.findViewById(R.id.root)

        repoViewModel.dataRepository = DataRepository(restForeground)
        repoViewModel.repo.observe(viewLifecycleOwner, observer)

        tvLoadMore.setOnClickListener {
            repoViewModel.page += 1
//            getRepositories(page)
            repoViewModel.repo.observe(viewLifecycleOwner, observer)
        }
    }

    val observer = Observer<ModelWrapper<Repository>> {
        it.items?.let { data -> listRepository.addAll(data) }
        tvLoadMore.visibility = View.VISIBLE
        repoAdapter.notifyDataSetChanged()
    }
}