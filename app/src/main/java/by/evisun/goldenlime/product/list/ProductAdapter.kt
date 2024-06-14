package by.evisun.goldenlime.product.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import by.evisun.goldenlime.R
import by.evisun.goldenlime.databinding.LayoutProductItemBinding
import com.bumptech.glide.Glide


class ProductAdapter(
    private val onClickListener: (ProductListModel) -> Unit,
) : RecyclerView.Adapter<CategoryViewHolder>() {

    private val differ: AsyncListDiffer<ProductListModel> =
        AsyncListDiffer(this, CategoryItemCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_product_item, parent, false)
        return CategoryViewHolder(itemView, onClickListener)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    fun update(newItems: List<ProductListModel>) {
        differ.submitList(newItems)
    }
}

class CategoryViewHolder(
    itemView: View,
    private val onClickListener: (ProductListModel) -> Unit,
) : RecyclerView.ViewHolder(itemView) {

    private val binding = LayoutProductItemBinding.bind(itemView)

    fun bind(model: ProductListModel) {
        itemView.setOnClickListener { onClickListener.invoke(model) }
        binding.titleTextView.text = model.title
        binding.priceTextView.text = (model.price / 100.0).toString()
        Glide.with(binding.iconImageView)
            .load(model.firebaseImageUrl)
            .into(binding.iconImageView)
    }
}

object CategoryItemCallback : DiffUtil.ItemCallback<ProductListModel>() {

    override fun areItemsTheSame(oldItem: ProductListModel, newItem: ProductListModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ProductListModel, newItem: ProductListModel): Boolean =
        oldItem == newItem
}