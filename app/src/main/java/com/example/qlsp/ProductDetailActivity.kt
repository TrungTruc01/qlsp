package com.example.qlsp

import Product
import ProductViewModel
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

@Suppress("DEPRECATION")
class ProductDetailActivity : AppCompatActivity() {

    private lateinit var product: Product
    private val productViewModel: ProductViewModel by viewModels() // Sử dụng ViewModel

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        // Lấy thông tin sản phẩm từ Intent
        product = intent.getParcelableExtra<Product>(EXTRA_PRODUCT)!!

        // Kiểm tra sản phẩm không null

        // Khởi tạo các View
        val textViewName: TextView = findViewById(R.id.textViewProductName)
        val textViewPrice: TextView = findViewById(R.id.textViewProductPrice)
        val textViewQuantity: TextView = findViewById(R.id.textViewProductQuantity)
        val textViewDate: TextView = findViewById(R.id.textViewProductDate)

        // Hiển thị thông tin sản phẩm
        textViewName.text = product.name
        textViewPrice.text = product.price.toString()
        textViewQuantity.text = product.quantity
        textViewDate.text = product.category

        // Khởi tạo nút sửa
        val buttonEdit: Button = findViewById(R.id.buttonEdit)
        buttonEdit.setOnClickListener {
            // Mở giao diện sửa sản phẩm
            val editIntent = Intent(this, EditProductActivity::class.java).apply {
                putExtra(EditProductActivity.EXTRA_PRODUCT_ID, product.id)
            }
            startActivity(editIntent)
        }

        // Khởi tạo nút xóa
        val buttonDelete: Button = findViewById(R.id.buttonDelete)
        buttonDelete.setOnClickListener {
            // Xử lý xóa sản phẩm
            deleteProduct(product.id)
        }

        // Khởi tạo nút thoát
        val buttonExit: Button = findViewById(R.id.buttonExit)
        buttonExit.setOnClickListener {
            finish() // Quay lại giao diện trước
        }
    }

    private fun deleteProduct(productId: String) {
        // Gọi hàm xóa trong ViewModel và truyền callback
        productViewModel.deleteProduct(productId) { success ->
            if (success) {
                // Hiển thị thông báo cho người dùng nếu xóa thành công
                Toast.makeText(this, "Sản phẩm đã được xóa", Toast.LENGTH_SHORT).show()
                // Quay lại giao diện danh sách sản phẩm
                finish()
            } else {
                // Hiển thị thông báo nếu việc xóa thất bại
                Toast.makeText(this, "Xóa sản phẩm thất bại", Toast.LENGTH_SHORT).show()
            }
        }
    }


    companion object {
        const val EXTRA_PRODUCT = "EXTRA_PRODUCT"
    }
}
