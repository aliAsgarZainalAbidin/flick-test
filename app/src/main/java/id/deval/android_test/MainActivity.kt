package id.deval.android_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import id.deval.android_test.BuildConfig.TAG
import id.deval.android_test.adapter.ViewPagerAdapter
import id.deval.android_test.model.Issue
import id.deval.android_test.model.ModelWrapper
import id.deval.android_test.model.Repository
import id.deval.android_test.model.User
import id.deval.android_test.repository.DataRepository
import id.deval.android_test.ui.tab.issue.*
import id.deval.android_test.ui.tab.repository.RepositoryFragment
import id.deval.android_test.ui.tab.repository.getRepositories
import id.deval.android_test.ui.tab.repository.listRepository
import id.deval.android_test.ui.tab.repository.repoAdapter
import id.deval.android_test.ui.tab.user.UserFragment
import id.deval.android_test.ui.tab.user.getUsers
import id.deval.android_test.ui.tab.user.listUser
import id.deval.android_test.ui.tab.user.userAdapter

class MainActivity : AppCompatActivity() {

    private val LIST_TAB = arrayListOf("User", "Repository", "Issue")
    private lateinit var viewpager: ViewPager2
    private lateinit var appbar: AppBarLayout
    private lateinit var tabLayout: TabLayout
    private lateinit var toolbar: MaterialToolbar
    private lateinit var root: SwipeRefreshLayout
    private lateinit var fragmentManager: FragmentManager
    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewpager = findViewById(R.id.vp_main_container)
        tabLayout = findViewById(R.id.tablayout_main_container)
        appbar = findViewById(R.id.appbar_main_container)
        toolbar = findViewById(R.id.toolbar)
        root = findViewById(R.id.root)
        fragmentManager = supportFragmentManager

        viewModel.dataRepository = DataRepository(restForeground)

        root.setOnRefreshListener {
            val tag = when (tabLayout.selectedTabPosition) {
                0 -> "f0"
                1 -> "f1"
                2 -> "f2"
                else -> "f0"
            }
            val fragment = fragmentManager.findFragmentByTag(tag)
            when (fragment) {
                is UserFragment -> {
                    listUser.clear()
                    viewModel.users.observe(this, observerUser)
//                    getUsers(1)
                }
                is RepositoryFragment -> {
                    listRepository.clear()
                    viewModel.repo.observe(this, observerRepo)
//                    getRepositories(1)
                }
                is IssueFragment -> {
                    listIssue.clear()
                    viewModel.issue.observe(this, observerIssue)
//                    getIssues(1)
                }
                else -> {
                    root.isRefreshing = false
                }
            }

        }

        val adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        viewpager.adapter = adapter
        TabLayoutMediator(tabLayout, viewpager) { tab, position ->
            tab.text = LIST_TAB[position]
        }.attach()

        var searchView: SearchView = toolbar.findViewById(R.id.search)
        searchView.setOnCloseListener {
            when (tabLayout.selectedTabPosition) {
                0 -> {
                    listUser.clear()
                    viewModel.users.observe(this, observerUser)
                    return@setOnCloseListener false
                }
                1 -> {
                    listRepository.clear()
                    viewModel.repo.observe(this, observerRepo)
                    return@setOnCloseListener false
                }
                2 -> {
                    listIssue.clear()
                    viewModel.repo.observe(this, observerRepo)
                    return@setOnCloseListener false
                }
            }
            return@setOnCloseListener false
        }
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null && query != "") {
                    when (tabLayout.selectedTabPosition) {
                        0 -> {
                            listUser.clear()
                            viewModel.q = query
                            viewModel.users.observe(this@MainActivity, observerUser)
                        }
                        1 -> {
                            listRepository.clear()
                            viewModel.q = query
                            viewModel.repo.observe(this@MainActivity, observerRepo)
                        }
                        2 -> {
                            listIssue.clear()
                            viewModel.q = query
                            viewModel.issue.observe(this@MainActivity, observerIssue)
                        }
                    }
                } else {
                    when (tabLayout.selectedTabPosition) {
                        0 -> {
                            listUser.clear()
                            viewModel.users.observe(this@MainActivity, observerUser)
                        }
                        1 -> {
                            listRepository.clear()
                            viewModel.repo.observe(this@MainActivity, observerRepo)
                        }
                        2 -> {
                            listIssue.clear()
                            viewModel.issue.observe(this@MainActivity, observerIssue)
                        }
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.page = 1
                viewModel.q = newText ?: "a"
                if (newText != null && newText != "") {
                    when (tabLayout.selectedTabPosition) {
                        0 -> {
                            listUser.clear()
                            viewModel.users.observe(this@MainActivity, observerUser)
                        }
                        1 -> {
                            listRepository.clear()
                            viewModel.repo.observe(this@MainActivity, observerRepo)
                        }
                        2 -> {
                            listIssue.clear()
                            viewModel.issue.observe(this@MainActivity, observerIssue)
                        }
                    }
                } else {
                    when (tabLayout.selectedTabPosition) {
                        0 -> {
                            listUser.clear()
                            viewModel.users.observe(this@MainActivity, observerUser)
                        }
                        1 -> {
                            listRepository.clear()
                            viewModel.repo.observe(this@MainActivity, observerRepo)
                        }
                        2 -> {
                            listIssue.clear()
                            viewModel.issue.observe(this@MainActivity, observerIssue)
                        }
                    }
                }
                return true
            }

        })
        Log.d(TAG, "onCreate: $searchView")
    }

    val observerUser = Observer<ModelWrapper<User>> {
        it?.items?.let { data -> listUser.addAll(data) }
        userAdapter.notifyDataSetChanged()
        root.isRefreshing = false
    }

    val observerRepo = Observer<ModelWrapper<Repository>> {
        it.items?.let { data -> listRepository.addAll(data) }
        repoAdapter.notifyDataSetChanged()
        root.isRefreshing = false
    }

    val observerIssue = Observer<ModelWrapper<Issue>> {
        it.items?.let { data -> listIssue.addAll(data) }
        issueAdapter.notifyDataSetChanged()
        root.isRefreshing = false
    }
}