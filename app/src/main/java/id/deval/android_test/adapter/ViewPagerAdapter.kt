package id.deval.android_test.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import id.deval.android_test.ui.tab.issue.IssueFragment
import id.deval.android_test.ui.tab.repository.RepositoryFragment
import id.deval.android_test.ui.tab.user.UserFragment

private val NUM_TABS = 3

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return UserFragment()
            1 -> return RepositoryFragment()
            2 -> return IssueFragment()
        }
        return UserFragment()
    }

}