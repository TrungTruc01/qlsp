package com.example.qlsp

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import Product
import ProductViewModel

class EditProductActivity : AppCompatActivity() {

    private val productViewModel: ProductViewModel by viewModels()
    private lateinit var product: Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_product)

        // Lấy ID sản phẩm từ Intent
        val productId = intent.getStringExtra(EXTRA_PRODUCT_ID) ?: run {
            Toast.makeText(this, "Không tìm thấy ID sản phẩm", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Khởi tạo các View
        val editTextName: EditText = findViewById(R.id.editTextProductName)
        val editTextPrice: EditText = findViewById(R.id.editTextProductPrice)
        val editTextQuantity: EditText = findViewById(R.id.editTextProductQuantity)
        val editTextDate: EditText = findViewById(R.id.editTextProductDate)
        val buttonSave: Button = findViewById(R.id.buttonSave)
        val buttonDelete: Button = findViewById(R.id.buttonDelete)

        // Lấy sản phẩm từ Firebase thông qua ViewModel
        productViewModel.getProductById(productId) { product ->
            if (product != null) {
                this.product = product
                // Hiển thị thông tin sản phẩm lên các EditText
                editTextName.setText(product.name)
                editTextPrice.setText(product.price.toString())
                editTextQuantity.setText(product.quantity)
                editTextDate.setText(product.category)
            } else {
                Toast.makeText(this, "Sản phẩm không tồn tại", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        // Xử lý sự kiện nút Lưu
        buttonSave.setOnClickListener {
            val updatedName = editTextName.text.toString()
            val updatedPrice = editTextPrice.text.toString().toDoubleOrNull()
            val updatedQuantity = editTextQuantity.text.toString()
            val updatedDate = editTextDate.text.toString()

            if (updatedPrice != null && updatedQuantity.isNotEmpty()) {
                product.name = updatedName
                product.price = updatedPrice
                product.quantity = updatedQuantity
                product.category = updatedDate

                // Cập nhật sản phẩm thông qua ViewModel
                productViewModel.updateProduct(product) { success ->
                    if (success) {
                        Toast.makeText(this, "Cập nhật sản phẩm thành công", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Vui lòng nhập giá và số lượng hợp lệ", Toast.LENGTH_SHORT).show()
            }
        }

        // Xử lý sự kiện nút Xóa
        buttonDelete.setOnClickListener {
            finish()
        }
    }

    companion object {
        const val EXTRA_PRODUCT_ID = "EXTRA_PRODUCT_ID"
    }
}
