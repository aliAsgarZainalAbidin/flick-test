package id.deval.android_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import id.deval.android_test.adapter.ViewPagerAdapter

class MainActivity : AppCompatActivity() {

    private val LIST_TAB = arrayListOf<String>("User", "Repository", "Issue")
    private lateinit var viewpager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewpager = findViewById(R.id.vp_main_container)
        tabLayout = findViewById(R.id.tablayout_main_container)

        val adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        viewpager.adapter = adapter

        TabLayoutMediator(tabLayout, viewpager) { tab, position ->
            tab.text = LIST_TAB[position]
        }.attach()
    }
}