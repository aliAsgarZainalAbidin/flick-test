package id.deval.android_test.ui.tab.user

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
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
import id.deval.android_test.databinding.UserItemBinding
import id.deval.android_test.model.ModelWrapper
import id.deval.android_test.model.User
import id.deval.android_test.repository.DataRepository
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
private val restForeground by lazy { ApiFactory.create() }
lateinit var rvUser: RecyclerView
lateinit var userAdapter: UserRecyclerViewAdapter
lateinit var tvLoadMore: MaterialTextView
var root: SwipeRefreshLayout? = null

class UserFragment : Fragment() {

    var page = 1
    lateinit var layoutManager: GridLayoutManager
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_user, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvUser = view.findViewById(R.id.rv_user_container)
        tvLoadMore = view.findViewById(R.id.tv_userFrag_more)

        layoutManager = GridLayoutManager(activity, 3, LinearLayoutManager.VERTICAL, false)
        rvUser.isNestedScrollingEnabled = false
        rvUser.layoutManager = layoutManager
        userAdapter = UserRecyclerViewAdapter(listUser)
        rvUser.adapter = userAdapter

        var mainActivity: MainActivity? = this.activity as MainActivity
        root = mainActivity?.findViewById(R.id.root)
//        getUsers(page)
        tvLoadMore.setOnClickListener {
            userViewModel.page += 1
            userViewModel.users.observe(viewLifecycleOwner, observer)
            //            getUsers(page)
        }

        userViewModel.dataRepository = DataRepository(
            restForeground
        )
        userViewModel.users.observe(viewLifecycleOwner, observer)
    }

    val observer = Observer<ModelWrapper<User>> {
        it?.items?.let { data -> listUser.addAll(data) }
        tvLoadMore.visibility = View.VISIBLE
        userAdapter.notifyDataSetChanged()
    }
}