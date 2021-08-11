package id.deval.android_test.ui.tab.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import id.deval.android_test.R
import id.deval.android_test.adapter.UserRecyclerViewAdapter
import id.deval.android_test.model.User

val listUser: MutableList<User?> = mutableListOf(
    User("Ali", 2, "https://avatars.githubusercontent.com/u/45491?v=4"),
    User("Asgar", 2, "https://avatars.githubusercontent.com/u/45491?v=4"),
    User("Zainal", 2, "https://avatars.githubusercontent.com/u/313874?v=4"),
    User("Abidin", 2, "https://avatars.githubusercontent.com/u/3309?v=4"),
    User("Asgar", 2, "https://avatars.githubusercontent.com/u/45491?v=4"),
    User("Zainal", 2, "https://avatars.githubusercontent.com/u/313874?v=4"),
    User("Abidin", 2, "https://avatars.githubusercontent.com/u/3309?v=4")
)

class UserFragment : Fragment() {

    lateinit var rvUser: RecyclerView

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

        rvUser.layoutManager = GridLayoutManager(activity, 3, LinearLayoutManager.VERTICAL, false)
        rvUser.isNestedScrollingEnabled = false
        val userAdapter = UserRecyclerViewAdapter(listUser = listUser)
        rvUser.adapter = userAdapter
    }
}