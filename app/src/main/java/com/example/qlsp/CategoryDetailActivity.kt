package com.example.qlsp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

@Suppress("DEPRECATION", "INFERRED_TYPE_VARIABLE_INTO_POSSIBLE_EMPTY_INTERSECTION")
class CategoryDetailActivity : AppCompatActivity() {

    private lateinit var category: Category
    private val categoryViewModel: CategoryViewModel by viewModels() // Sử dụng ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_detail)

        // Lấy thông tin loại sản phẩm từ Intent
        category = intent.getParcelableExtra(EXTRA_CATEGORY)!!

        // Khởi tạo các View
        val textViewName: TextView = findViewById(R.id.textViewCategoryName)
        val textViewId: TextView = findViewById(R.id.textViewCategoryId)

        // Hiển thị thông tin loại sản phẩm
        textViewName.text = category.name
        textViewId.text = category.id

        // Khởi tạo nút sửa
        val buttonEdit: Button = findViewById(R.id.buttonEdit)
        buttonEdit.setOnClickListener {
            // Mở giao diện sửa loại sản phẩm
            val editIntent = Intent(this, EditCategoryActivity::class.java).apply {
                putExtra(EditCategoryActivity.EXTRA_CATEGORY_ID, category.id)
            }
            startActivity(editIntent)
        }

        // Khởi tạo nút xóa
        val buttonDelete: Button = findViewById(R.id.buttonDelete)
        buttonDelete.setOnClickListener {
            // Xử lý xóa loại sản phẩm
            deleteCategory(category.id)
        }

        // Khởi tạo nút thoát
        val buttonExit: Button = findViewById(R.id.buttonExit)
        buttonExit.setOnClickListener {
            finish() // Quay lại giao diện trước
        }
    }

    private fun deleteCategory(categoryId: String) {
        // Gọi hàm xóa trong ViewModel
        categoryViewModel.deleteCategory(categoryId)

        // Hiển thị thông báo cho người dùng
        Toast.makeText(this, "Loại sản phẩm đã được xóa", Toast.LENGTH_SHORT).show()

        // Quay lại giao diện danh sách loại sản phẩm
        finish()
    }

    companion object {
        const val EXTRA_CATEGORY = "EXTRA_CATEGORY"
    }
}
