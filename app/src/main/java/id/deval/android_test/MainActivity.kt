package id.deval.android_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
import id.deval.android_test.ui.tab.issue.listIssue
import id.deval.android_test.ui.tab.repository.RepositoryFragment
import id.deval.android_test.ui.tab.repository.listRepository
import id.deval.android_test.ui.tab.user.UserFragment
import id.deval.android_test.ui.tab.user.listUser

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
                    fragment.userAdapter.notifyDataSetChanged()
                    fragment.getUsers(1)
                }
                is RepositoryFragment -> {
                    listRepository.clear()
                    fragment.repoAdapter.notifyDataSetChanged()
                    fragment.getRepositories(1)
                }
                is IssueFragment -> {
                    listIssue.clear()
                    fragment.issueAdapter.notifyDataSetChanged()
                    fragment.getIssues(1)
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


        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.refresh -> {

                    true
                }
                R.id.search -> {
                    true
                }
                else -> false
            }
        }

    }
}