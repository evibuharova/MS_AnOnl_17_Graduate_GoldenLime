package by.evisun.goldenlime.categories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import by.evisun.goldenlime.R
import by.evisun.goldenlime.databinding.LayoutCategoryItemBinding
import com.bumptech.glide.Glide


class CategoryAdapter(
    private val onClickListener: (CategoryModel) -> Unit,
) : RecyclerView.Adapter<CategoryViewHolder>() {

    private val differ: AsyncListDiffer<CategoryModel> =
        AsyncListDiffer(this, CategoryItemCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_category_item, parent, false)
        return CategoryViewHolder(itemView, onClickListener)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    fun update(newItems: List<CategoryModel>) {
        differ.submitList(newItems)
    }
}

class CategoryViewHolder(
    itemView: View,
    private val onClickListener: (CategoryModel) -> Unit,
) : RecyclerView.ViewHolder(itemView) {

    private val binding = LayoutCategoryItemBinding.bind(itemView)

    fun bind(model: CategoryModel) {
        itemView.setOnClickListener { onClickListener.invoke(model) }
        binding.titleTextView.text = model.title
        binding.rightArrow.isVisible = !model.isLast
        Glide.with(binding.iconImageView)
            .load(model.firebaseImageUrl)
            .into(binding.iconImageView)
    }
}

object CategoryItemCallback : DiffUtil.ItemCallback<CategoryModel>() {

    override fun areItemsTheSame(oldItem: CategoryModel, newItem: CategoryModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: CategoryModel, newItem: CategoryModel): Boolean =
        oldItem == newItem
}