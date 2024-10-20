package com.example.qlsp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class CategoryAdapter(
    private val context: Context,
    private val categories: List<Category>
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.bind(category)

        // Bắt sự kiện khi nhấn vào loại sản phẩm
        holder.itemView.setOnClickListener {
            val intent = Intent(context, EditCategoryActivity::class.java)
            intent.putExtra("CATEGORY_ID", category.id) // Truyền ID loại sản phẩm qua Intent
            context.startActivity(intent) // Mở EditCategoryActivity

            // Thông báo cho người dùng
            Toast.makeText(context, "Đang mở loại sản phẩm: ${category.name}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val categoryNameTextView: TextView = itemView.findViewById(R.id.textViewCategoryName)
        private val categoryCodeTextView: TextView = itemView.findViewById(R.id.textViewCategoryCode)

        fun bind(category: Category) {
            categoryNameTextView.text = "Tên loại: ${category.name}" // Thêm tiền tố để hiển thị rõ ràng hơn
            categoryCodeTextView.text = "Mã loại: ${category.code}" // Thêm tiền tố để hiển thị rõ ràng hơn
        }
    }
}
