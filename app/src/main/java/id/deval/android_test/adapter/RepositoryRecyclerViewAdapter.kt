package id.deval.android_test.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.textview.MaterialTextView
import id.deval.android_test.R
import id.deval.android_test.model.Repository

class RepositoryRecyclerViewAdapter(var listRepository: MutableList<Repository?>) :
    RecyclerView.Adapter<RepositoryRecyclerViewAdapter.RepositoryViewHolder>() {

    class RepositoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: MaterialTextView
        var tvDesc: MaterialTextView
        var ivColor: ImageView
        var tvLang: MaterialTextView
        var tvStars: MaterialTextView
        var tvForks: MaterialTextView

        init {
            tvName = itemView.findViewById(R.id.tv_repo_name)
            tvDesc = itemView.findViewById(R.id.tv_repo_desc)
            ivColor = itemView.findViewById(R.id.iv_repo_color)
            tvLang = itemView.findViewById(R.id.tv_repo_lang)
            tvStars = itemView.findViewById(R.id.tv_repo_star)
            tvForks = itemView.findViewById(R.id.tv_repo_fork)
        }

        fun bind(
            repository: Repository
        ) {
            tvName.text = repository.fullName
            tvDesc.text = repository.description

            val color = when (repository.language) {
                "Java" -> R.drawable.circle_red
                "Go" -> R.drawable.circle_blue
                "Vue" -> R.drawable.circle_green
                "JavaScript" -> R.drawable.circle_white
                else -> R.drawable.circle_yellow
            }

            Glide.with(itemView.context)
                .load(color)
                .into(ivColor)

            tvLang.text = repository.language
            tvForks.text = repository.fork.toString()
            tvStars.text = repository.star.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.repository_item, parent, false)
        return RepositoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        val view = RepositoryViewHolder(holder.itemView)
        listRepository.get(position)?.let { view.bind(it) }
    }

    override fun getItemCount(): Int {
        return listRepository.size
    }
}