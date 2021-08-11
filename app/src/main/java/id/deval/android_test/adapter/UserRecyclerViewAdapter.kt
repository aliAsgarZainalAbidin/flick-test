package id.deval.android_test.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.textview.MaterialTextView
import de.hdodenhof.circleimageview.CircleImageView
import id.deval.android_test.R
import id.deval.android_test.model.User
import id.deval.android_test.util.GlideApp

class UserRecyclerViewAdapter(var listUser: MutableList<User?>) :
    RecyclerView.Adapter<UserRecyclerViewAdapter.UserViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val view = UserViewHolder(holder.itemView)
        listUser.get(position)?.let { view.bind(it) }
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var iv_user_avatar: CircleImageView
        var tv_user_name: MaterialTextView
        var tv_user_id: MaterialTextView

        init {
            iv_user_avatar = itemView.findViewById(R.id.iv_user_avatar)
            tv_user_name = itemView.findViewById(R.id.tv_user_name)
            tv_user_id = itemView.findViewById(R.id.tv_user_id)
        }

        fun bind(
            user: User
        ) {
            with(itemView) {

                Glide.with(itemView.context)
                    .load(user.avatar_url)
                    .centerCrop()
                    .error(R.drawable.github)
                    .into(iv_user_avatar)

                tv_user_name.text = user.login
                tv_user_id.text = user.id.toString()
            }
        }
    }
}