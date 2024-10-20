package com.example.qlsp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class EditCategoryActivity : AppCompatActivity() {

    private lateinit var editTextCategoryName: EditText
    private lateinit var editTextCategoryCode: EditText
    private lateinit var buttonSave: Button
    private lateinit var buttonDelete: Button
    private var categoryId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_category)

        // Nhận ID loại sản phẩm từ Intent
        categoryId = intent.getStringExtra("CATEGORY_ID")

        editTextCategoryName = findViewById(R.id.editTextCategoryName)
        editTextCategoryCode = findViewById(R.id.editTextCategoryCode)
        buttonSave = findViewById(R.id.buttonSave)
        buttonDelete = findViewById(R.id.buttonDelete)

        loadCategoryData()

        buttonSave.setOnClickListener {
            updateCategory()
        }

        buttonDelete.setOnClickListener {
            deleteCategory()
        }
    }

    private fun loadCategoryData() {
        // Tải dữ liệu loại sản phẩm từ Firebase
        val database = FirebaseDatabase.getInstance().getReference("categories/$categoryId")
        database.get().addOnSuccessListener { snapshot ->
            val category = snapshot.getValue(Category::class.java)
            category?.let {
                editTextCategoryName.setText(it.name)
                editTextCategoryCode.setText(it.code)
            }
        }
    }

    private fun updateCategory() {
        val name = editTextCategoryName.text.toString()
        val code = editTextCategoryCode.text.toString()

        if (name.isEmpty() || code.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
            return
        }

        // Cập nhật loại sản phẩm lên Firebase
        val database = FirebaseDatabase.getInstance().getReference("categories/$categoryId")
        val updatedCategory = Category(categoryId ?: "", name, code)

        database.setValue(updatedCategory).addOnSuccessListener {
            Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show()
            finish() // Quay lại màn hình trước
        }.addOnFailureListener {
            Toast.makeText(this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteCategory() {
        val database = FirebaseDatabase.getInstance().getReference("categories/$categoryId")
        database.removeValue().addOnSuccessListener {
            Toast.makeText(this, "Xóa loại sản phẩm thành công", Toast.LENGTH_SHORT).show()
            finish()
        }.addOnFailureListener {
            Toast.makeText(this, "Xóa loại sản phẩm thất bại", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val EXTRA_CATEGORY_ID = "EXTRA_CATEGORY_ID"
    }
}
