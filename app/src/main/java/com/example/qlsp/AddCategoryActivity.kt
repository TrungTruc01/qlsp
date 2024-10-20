package com.example.qlsp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class AddCategoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_category)

        val nameEditText = findViewById<EditText>(R.id.editTextCategoryName)
        val codeEditText = findViewById<EditText>(R.id.editTextCategoryCode)
        val addButton = findViewById<Button>(R.id.btnAddCategory)

        addButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val code = codeEditText.text.toString()
            addCategory(name, code)
        }
    }

    private fun addCategory(name: String, code: String) {
        val database = FirebaseDatabase.getInstance().getReference("categories")
        val categoryId = database.push().key!!

        val category = Category(categoryId, name, code)
        database.child(categoryId).setValue(category).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(this, "Thêm loại sản phẩm thành công", Toast.LENGTH_SHORT).show()
                finish() // Quay lại giao diện chính sau khi thêm thành công
            } else {
                Toast.makeText(this, "Lỗi khi thêm loại sản phẩm", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
