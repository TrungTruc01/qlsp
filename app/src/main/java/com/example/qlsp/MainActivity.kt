package com.example.qlsp

import Product
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var productList: MutableList<Product>
    private lateinit var productAdapter: ProductAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var database: DatabaseReference
    private lateinit var searchBar: EditText // Thêm biến cho EditText tìm kiếm
    private lateinit var allProducts: MutableList<Product> // Thêm biến để lưu tất cả sản phẩm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Khởi tạo Firebase database
        database = FirebaseDatabase.getInstance().getReference("products")

        // Khởi tạo RecyclerView
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Khởi tạo danh sách sản phẩm và adapter
        productList = mutableListOf()
        allProducts = mutableListOf() // Khởi tạo allProducts
        productAdapter = ProductAdapter(productList) { product: Product ->
            val intent = Intent(this, ProductDetailActivity::class.java)
            intent.putExtra(ProductDetailActivity.EXTRA_PRODUCT, product)
            startActivity(intent)
        }
        recyclerView.adapter = productAdapter

        // Khởi tạo nút thêm sản phẩm
        val addProductButton = findViewById<ImageButton>(R.id.btnAddProduct)

        // Bottom Navigation
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_product -> {
                    // Quản lý sản phẩm
                    loadProducts()
                    true
                }
                R.id.nav_category -> {
                    // Quản lý loại sản phẩm
                    val intent = Intent(this, CategoryActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

        // Thêm sản phẩm
        addProductButton.setOnClickListener {
            val intent = Intent(this, AddProductActivity::class.java)
            startActivity(intent)
        }

        // Lấy tham chiếu đến EditText tìm kiếm
        searchBar = findViewById(R.id.searchBar)
        searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterProducts(s.toString()) // Gọi phương thức filter khi văn bản thay đổi
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        loadProducts() // Tải sản phẩm từ Firebase
    }

    private fun loadProducts() {
        database.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                productList.clear()
                allProducts.clear() // Xóa tất cả sản phẩm cũ trong allProducts
                for (productSnapshot in snapshot.children) {
                    val product = productSnapshot.getValue(Product::class.java)
                    product?.let {
                        productList.add(it)
                        allProducts.add(it) // Lưu sản phẩm vào allProducts
                    }
                }
                productAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, "Lỗi khi đọc dữ liệu", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun filterProducts(query: String) {
        // Lọc danh sách sản phẩm theo chuỗi tìm kiếm
        val filteredProducts = if (query.isEmpty()) {
            allProducts // Nếu không có từ khóa tìm kiếm, hiển thị tất cả
        } else {
            allProducts.filter { product ->
                product.name.contains(query, ignoreCase = true) || // Lọc theo tên sản phẩm
                        product.category.contains(query, ignoreCase = true) // Lọc theo mã loại
            }
        }
        productAdapter.updateProducts(filteredProducts) // Cập nhật adapter với danh sách đã lọc
    }
}
