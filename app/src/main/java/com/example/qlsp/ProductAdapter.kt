package com.example.qlsp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import Product
import java.text.NumberFormat
import java.util.Locale

class ProductAdapter(
    private var products: MutableList<Product>, // Sử dụng MutableList để có thể cập nhật
    private val onProductClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productName: TextView = itemView.findViewById(R.id.textViewProductName)
        val productPrice: TextView = itemView.findViewById(R.id.textViewProductPrice)
        val productQuantity: TextView = itemView.findViewById(R.id.textViewProductQuantity)
        val productDate: TextView = itemView.findViewById(R.id.textViewProductDate)

        fun bind(product: Product) {
            val numberFormat = NumberFormat.getCurrencyInstance(Locale("vi", "VN")) // Định dạng tiền tệ theo Việt Nam
            productName.text = product.name
            productPrice.text = "Giá: ${numberFormat.format(product.price)}" // Sử dụng định dạng tiền tệ
            productQuantity.text = "Số lượng: ${product.quantity} cái" // Thêm đơn vị 'cái'
            productDate.text = "Mã loại: ${product.category}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.bind(product) // Gọi phương thức bind để hiển thị thông tin sản phẩm
        holder.itemView.setOnClickListener {
            onProductClick(product) // Callback khi nhấn vào sản phẩm
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }

    // Phương thức cập nhật danh sách sản phẩm
    fun updateProducts(newProducts: List<Product>) {
        products.clear() // Xóa tất cả sản phẩm hiện tại
        products.addAll(newProducts) // Thêm sản phẩm mới vào danh sách
        notifyDataSetChanged() // Thông báo adapter để cập nhật giao diện
    }
}
