package id.deval.android_test.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import id.deval.android_test.R
import id.deval.android_test.model.Issue

class IssueRecyclerViewAdapter(var listIssues: MutableList<Issue?>) :
    RecyclerView.Adapter<IssueRecyclerViewAdapter.IssueViewHolder>() {


    class IssueViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTitle: MaterialTextView
        var tvCreatedDate: MaterialTextView
        var tvUpdateDate: MaterialTextView
        var tvComments: MaterialTextView

        init {
            tvTitle = itemView.findViewById(R.id.tv_issue_name)
            tvCreatedDate = itemView.findViewById(R.id.tv_issue_date)
            tvUpdateDate = itemView.findViewById(R.id.tv_issue_update)
            tvComments = itemView.findViewById(R.id.tv_issue_comment)
        }

        fun bind(
            issue: Issue
        ) {
            tvTitle.text = issue.title
            tvCreatedDate.text = "Created at : ${issue.createDate}"
            tvUpdateDate.text = "Update at : ${issue.updateDate}"
            tvComments.text = "${issue.comments} Comments"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.issues_item, parent, false)
        return IssueViewHolder(view)
    }

    override fun onBindViewHolder(holder: IssueViewHolder, position: Int) {
        val view = IssueViewHolder(holder.itemView)
        listIssues.get(position)?.let { view.bind(it) }
    }

    override fun getItemCount(): Int {
        return listIssues.size
    }
}