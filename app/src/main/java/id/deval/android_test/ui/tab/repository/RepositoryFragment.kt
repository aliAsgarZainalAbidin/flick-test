package id.deval.android_test.ui.tab.repository

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.deval.android_test.R
import id.deval.android_test.adapter.RepositoryRecyclerViewAdapter
import id.deval.android_test.model.Repository

val listRepository: MutableList<Repository?> = mutableListOf(
    Repository(
        "flick-text/android",
        "Test yang dilakukan untuk menguji kemampuan peserta atau calon pekerja yang nanti akan ditempatkan askdjasdkl klkaskj asdlk",
        120230,
        "Java",
        12312,
        null
    ),
    Repository(
        "flick-text/android",
        "Test yang dilakukan untuk menguji kemampuan peserta atau calon pekerja yang nanti akan ditempatkan askdjasdkl klkaskj asdlk",
        120230,
        "Vue",
        12312,
        null
    ),
    Repository(
        "flick-text/android",
        "Test yang dilakukan untuk menguji kemampuan peserta atau calon pekerja yang nanti akan ditempatkan askdjasdkl klkaskj asdlk",
        120230,
        "Go",
        12312,
        null
    ),
    Repository(
        "flick-text/android",
        "Test yang dilakukan untuk menguji kemampuan peserta atau calon pekerja yang nanti akan ditempatkan askdjasdkl klkaskj asdlk",
        120230,
        "JavaScript",
        12312,
        null
    ),
    Repository(
        "flick-text/android",
        "Test yang dilakukan untuk menguji kemampuan peserta atau calon pekerja yang nanti akan ditempatkan askdjasdkl klkaskj asdlk",
        120230,
        "Kotlin",
        12312,
        null
    ),
)

class RepositoryFragment : Fragment() {

    lateinit var rvRepository: RecyclerView

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

        rvRepository.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rvRepository.isNestedScrollingEnabled = false
        rvRepository.adapter = RepositoryRecyclerViewAdapter(listRepository)
    }

}