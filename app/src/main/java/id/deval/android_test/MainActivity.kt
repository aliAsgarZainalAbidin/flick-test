package id.deval.android_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.FragmentManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import id.deval.android_test.BuildConfig.TAG
import id.deval.android_test.adapter.ViewPagerAdapter
import id.deval.android_test.ui.tab.issue.IssueFragment
import id.deval.android_test.ui.tab.issue.getIssues
import id.deval.android_test.ui.tab.issue.issueAdapter
import id.deval.android_test.ui.tab.issue.listIssue
import id.deval.android_test.ui.tab.repository.RepositoryFragment
import id.deval.android_test.ui.tab.repository.getRepositories
import id.deval.android_test.ui.tab.repository.listRepository
import id.deval.android_test.ui.tab.repository.repoAdapter
import id.deval.android_test.ui.tab.user.UserFragment
import id.deval.android_test.ui.tab.user.getUsers
import id.deval.android_test.ui.tab.user.listUser
import id.deval.android_test.ui.tab.user.userAdapter

class MainActivity : AppCompatActivity() {

    private val LIST_TAB = arrayListOf<String>("User", "Repository", "Issue")
    private lateinit var viewpager: ViewPager2
    private lateinit var appbar: AppBarLayout
    private lateinit var tabLayout: TabLayout
    private lateinit var toolbar: MaterialToolbar
    private lateinit var root: SwipeRefreshLayout
    private lateinit var fragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewpager = findViewById(R.id.vp_main_container)
        tabLayout = findViewById(R.id.tablayout_main_container)
        appbar = findViewById(R.id.appbar_main_container)
        toolbar = findViewById(R.id.toolbar)
        root = findViewById(R.id.root)
        fragmentManager = supportFragmentManager

        root.setOnRefreshListener {
            val tag = when (tabLayout.selectedTabPosition) {
                0 -> "f0"
                1 -> "f1"
                2 -> "f2"
                else -> "f0"
            }
            val fragment = fragmentManager.findFragmentByTag(tag)

            Log.d(
                TAG,
                "onCreate:$fragment ${fragment} =>> position ${tabLayout.selectedTabPosition}"
            )
            when (fragment) {
                is UserFragment -> {
                    listUser.clear()
                    userAdapter.notifyDataSetChanged()
                    getUsers(1)
                }
                is RepositoryFragment -> {
                    listRepository.clear()
                    repoAdapter.notifyDataSetChanged()
                    getRepositories(1)
                }
                is IssueFragment -> {
                    listIssue.clear()
                    issueAdapter.notifyDataSetChanged()
                    getIssues(1)
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
                    userAdapter.notifyDataSetChanged()
                    getUsers(page = 1)
                    return@setOnCloseListener false
                }
                1 -> {
                    listRepository.clear()
                    repoAdapter.notifyDataSetChanged()
                    getRepositories(page = 1)
                    return@setOnCloseListener false
                }
                2 -> {
                    listIssue.clear()
                    issueAdapter.notifyDataSetChanged()
                    getIssues(page = 1)
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
                            userAdapter.notifyDataSetChanged()
                            getUsers(page = 1, q = query)
                        }
                        1 -> {
                            listRepository.clear()
                            repoAdapter.notifyDataSetChanged()
                            getRepositories(page = 1, q = query)
                        }
                        2 -> {
                            listIssue.clear()
                            issueAdapter.notifyDataSetChanged()
                            getIssues(page = 1, q = query)
                        }
                    }
                } else {
                    when (tabLayout.selectedTabPosition) {
                        0 -> {
                            listUser.clear()
                            userAdapter.notifyDataSetChanged()
                            getUsers(page = 1)
                        }
                        1 -> {
                            listRepository.clear()
                            repoAdapter.notifyDataSetChanged()
                            getRepositories(page = 1)
                        }
                        2 -> {
                            listIssue.clear()
                            issueAdapter.notifyDataSetChanged()
                            getIssues(page = 1)
                        }
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Handler().postDelayed({
                    if (newText != null && newText != "") {
                        when (tabLayout.selectedTabPosition) {
                            0 -> {
                                listUser.clear()
                                userAdapter.notifyDataSetChanged()
                                getUsers(page = 1, q = newText)
                            }
                            1 -> {
                                listRepository.clear()
                                repoAdapter.notifyDataSetChanged()
                                getRepositories(page = 1, q = newText)
                            }
                            2 -> {
                                listIssue.clear()
                                issueAdapter.notifyDataSetChanged()
                                getIssues(page = 1, q = newText)
                            }
                        }
                    } else {
                        when (tabLayout.selectedTabPosition) {
                            0 -> {
                                listUser.clear()
                                userAdapter.notifyDataSetChanged()
                                getUsers(page = 1)
                            }
                            1 -> {
                                listRepository.clear()
                                repoAdapter.notifyDataSetChanged()
                                getRepositories(page = 1)
                            }
                            2 -> {
                                listIssue.clear()
                                issueAdapter.notifyDataSetChanged()
                                getIssues(page = 1)
                            }
                        }
                    }
                }, 100)
                return true
            }

        })
        Log.d(TAG, "onCreate: $searchView")

    }
}