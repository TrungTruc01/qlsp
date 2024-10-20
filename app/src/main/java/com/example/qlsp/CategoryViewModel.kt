package com.example.qlsp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CategoryViewModel : ViewModel() {

    private val _categories = MutableLiveData<MutableList<Category>>()
    val categories: LiveData<MutableList<Category>> get() = _categories

    private val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("categories")

    init {
        loadCategories()
    }

    private fun loadCategories() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val categoryList = mutableListOf<Category>()
                for (categorySnapshot in snapshot.children) {
                    val category = categorySnapshot.getValue(Category::class.java)
                    category?.let { categoryList.add(it) }
                }
                _categories.postValue(categoryList)
            }

            override fun onCancelled(error: DatabaseError) {
                // Xử lý lỗi nếu cần
            }
        })
    }

    fun addCategory(category: Category) {
        val categoryId = database.push().key // Tạo ID ngẫu nhiên cho loại sản phẩm
        category.id = categoryId ?: return
        database.child(categoryId).setValue(category)
        loadCategories() // Tải lại danh sách
    }

    fun updateCategory(updatedCategory: Category) {
        database.child(updatedCategory.id).setValue(updatedCategory)
        loadCategories() // Tải lại danh sách
    }

    fun deleteCategory(categoryId: String) {
        database.child(categoryId).removeValue()
        loadCategories() // Tải lại danh sách
    }

    fun getCategoryById(categoryId: String): Category? {
        return _categories.value?.find { it.id == categoryId }
    }

    fun getAllCategories(): LiveData<MutableList<Category>> {
        return categories
    }
}
