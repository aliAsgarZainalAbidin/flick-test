package id.deval.android_test.ui.tab.user

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.textview.MaterialTextView
import id.deval.android_test.BuildConfig.TAG
import id.deval.android_test.MainActivity
import id.deval.android_test.R
import id.deval.android_test.adapter.UserRecyclerViewAdapter
import id.deval.android_test.api.ApiFactory
import id.deval.android_test.model.ModelWrapper
import id.deval.android_test.model.User
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//val listUser: MutableList<User?> = mutableListOf(
//    User("Ali", 2, "https://avatars.githubusercontent.com/u/45491?v=4"),
//    User("Asgar", 2, "https://avatars.githubusercontent.com/u/45491?v=4"),
//    User("Zainal", 2, "https://avatars.githubusercontent.com/u/313874?v=4"),
//    User("Abidin", 2, "https://avatars.githubusercontent.com/u/3309?v=4"),
//    User("Asgar", 2, "https://avatars.githubusercontent.com/u/45491?v=4"),
//    User("Zainal", 2, "https://avatars.githubusercontent.com/u/313874?v=4"),
//    User("Abidin", 2, "https://avatars.githubusercontent.com/u/3309?v=4"),
//    User("Ali", 2, "https://avatars.githubusercontent.com/u/45491?v=4"),
//    User("Asgar", 2, "https://avatars.githubusercontent.com/u/45491?v=4"),
//    User("Zainal", 2, "https://avatars.githubusercontent.com/u/313874?v=4"),
//    User("Abidin", 2, "https://avatars.githubusercontent.com/u/3309?v=4"),
//    User("Asgar", 2, "https://avatars.githubusercontent.com/u/45491?v=4"),
//    User("Zainal", 2, "https://avatars.githubusercontent.com/u/313874?v=4"),
//    User("Abidin", 2, "https://avatars.githubusercontent.com/u/3309?v=4"),
//    User("Ali", 2, "https://avatars.githubusercontent.com/u/45491?v=4"),
//    User("Asgar", 2, "https://avatars.githubusercontent.com/u/45491?v=4"),
//    User("Zainal", 2, "https://avatars.githubusercontent.com/u/313874?v=4"),
//    User("Abidin", 2, "https://avatars.githubusercontent.com/u/3309?v=4"),
//    User("Asgar", 2, "https://avatars.githubusercontent.com/u/45491?v=4"),
//    User("Zainal", 2, "https://avatars.githubusercontent.com/u/313874?v=4"),
//    User("Abidin", 2, "https://avatars.githubusercontent.com/u/3309?v=4")
//)

val listUser: MutableList<User?> = mutableListOf()

class UserFragment : Fragment() {

    private val restForeground by lazy { ApiFactory.create() }
    lateinit var rvUser: RecyclerView
    lateinit var userAdapter: UserRecyclerViewAdapter
    var finished: String = "false"
    var page = 1
    var root: SwipeRefreshLayout? = null

    //    lateinit var swipe: SwipeRefreshLayout
    lateinit var layoutManager: GridLayoutManager
    lateinit var tvLoadMore: MaterialTextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvUser = view.findViewById(R.id.rv_user_container)
        tvLoadMore = view.findViewById(R.id.tv_userFrag_more)
//        swipe = view.findViewById(R.id.root)

        layoutManager = GridLayoutManager(activity, 3, LinearLayoutManager.VERTICAL, false)
        rvUser.isNestedScrollingEnabled = false
        rvUser.layoutManager = layoutManager
        userAdapter = UserRecyclerViewAdapter(listUser)
        rvUser.adapter = userAdapter
        getUsers(page)

        var mainActivity : MainActivity? = this.activity as MainActivity
        root = mainActivity?.findViewById(R.id.root)

        tvLoadMore.setOnClickListener {
            page += 1
            getUsers(page)
        }

//        root?.viewTreeObserver?.addOnScrollChangedListener {
//            var view = root?.getChildAt(root?.childCount!! - 1) as View
//            var diff = view.bottom - (root?.height!! + root?.scrollY!!)
//
//            if (diff == 0 && finished == "false") {
//                if (layoutManager.findLastVisibleItemPosition() == layoutManager.itemCount - 1) {
//                    finished = "waiting"
//                    Handler().postDelayed({
//                        page += 1
//                        getUsers(page)
//                    }, 500)
//                }
//            }
//        }
    }

    fun getUsers(page: Int?) {
        val result = restForeground.getUsers(page = page)
        try {
            result.enqueue(object : Callback<ModelWrapper<User>> {
                override fun onResponse(
                    call: Call<ModelWrapper<User>>,
                    response: Response<ModelWrapper<User>>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()
                        if (data != null) {
                            data.items?.let { listUser.addAll(it) }
                            Log.d(TAG, "onResponse: ${data.items}")
                            userAdapter.notifyDataSetChanged()
                            tvLoadMore.visibility = View.VISIBLE
                            root?.isRefreshing = false
                        }
                    } else {
                        Log.d(TAG, "onResponse: $response")
                    }
                }

                override fun onFailure(call: Call<ModelWrapper<User>>, t: Throwable) {
                    Log.d(TAG, "onFailure: $t.")
                }

            })
        } catch (e: Exception) {
            Log.d(TAG, "getUsers: $e")
        }
    }
}