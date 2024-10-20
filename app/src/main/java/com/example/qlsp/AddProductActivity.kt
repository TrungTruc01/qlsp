package com.example.qlsp

import Product
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class AddProductActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        // Initialize Views
        val nameEditText = findViewById<EditText>(R.id.editTextName)
        val priceEditText = findViewById<EditText>(R.id.editTextPrice)
        val quantityEditText = findViewById<EditText>(R.id.editTextQuantity)
        val categoryEditText = findViewById<EditText>(R.id.editTextCategory)
        val addButton = findViewById<Button>(R.id.btnAddProduct)

        // Handle add product button click
        addButton.setOnClickListener {
            // Get data from input fields
            val name = nameEditText.text.toString().trim()
            val priceStr = priceEditText.text.toString().trim()
            val quantityStr = quantityEditText.text.toString().trim()
            val category = categoryEditText.text.toString().trim()

            // Validate input data
            if (validateInput(name, priceStr, quantityStr, category)) {
                val price = priceStr.toDouble()
                val quantity = quantityStr.toInt()
                addProduct(name, price, quantity, category)

                // Clear input fields after successful addition
                nameEditText.text.clear()
                priceEditText.text.clear()
                quantityEditText.text.clear()
                categoryEditText.text.clear()
            }
        }
    }

    // Validate user input
    private fun validateInput(name: String, priceStr: String, quantityStr: String, category: String): Boolean {
        return when {
            name.isEmpty() -> {
                showToast("Tên sản phẩm không được để trống")
                false
            }
            priceStr.isEmpty() -> {
                showToast("Giá sản phẩm không được để trống")
                false
            }
            quantityStr.isEmpty() -> {
                showToast("Số lượng sản phẩm không được để trống")
                false
            }
            category.isEmpty() -> {
                showToast("Loại sản phẩm không được để trống")
                false
            }
            priceStr.toDoubleOrNull() == null || priceStr.toDouble() < 0 -> {
                showToast("Giá sản phẩm không hợp lệ")
                false
            }
            quantityStr.toIntOrNull() == null || quantityStr.toInt() <= 0 -> {
                showToast("Số lượng phải là số dương")
                false
            }
            else -> true
        }
    }

    // Add product to Firebase
    private fun addProduct(name: String, price: Double, quantity: Int, category: String) {
        val database = FirebaseDatabase.getInstance().getReference("products")
        val productId = database.push().key!!

        val product = Product(productId, name, price, quantity.toString(), category)
        database.child(productId).setValue(product).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                showToast("Thêm sản phẩm thành công")
                finish() // Go back to main screen after successful addition
            } else {
                showToast("Lỗi khi thêm sản phẩm: ${task.exception?.message}")
            }
        }
    }

    // Show toast message
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
