package com.danan.storyapp.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.danan.storyapp.activities.DetailActivity
import com.danan.storyapp.data.response.StoryItem
import com.danan.storyapp.databinding.RvItemStoryBinding

class ListStoryAdapter(private val listStory: ArrayList<StoryItem>) :
    RecyclerView.Adapter<ListStoryAdapter.ViewHolder>() {

    class ViewHolder(var binding: RvItemStoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RvItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = listStory[position]

        with(holder.binding) {
            Glide.with(root.context)
                .load(item.photoUrl)
                .into(ivItemPhoto)
            tvItemName.text = item.name
            tvItemDesc.text = item.description
            val context = holder.itemView.context

            holder.itemView.setOnClickListener {
                val moveWithDataIntent = Intent(context, DetailActivity::class.java)
                moveWithDataIntent.putExtra(DetailActivity.EXTRA_STORY, item)

                val optionsCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        context as Activity,
                        androidx.core.util.Pair(ivItemPhoto, "photo"),
                        androidx.core.util.Pair(tvItemDesc, "description"),
                        androidx.core.util.Pair(tvItemName, "name"),
                    )
                context.startActivity(moveWithDataIntent, optionsCompat.toBundle())
            }
        }

    }

    override fun getItemCount(): Int = listStory.size
}