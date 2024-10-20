package com.example.qlsp

import Product

class ProductRepository {

    // Đây là danh sách sản phẩm giả định. Bạn có thể thay thế bằng cơ sở dữ liệu thực tế.
    private val products = mutableListOf<Product>()

    init {
        // Khởi tạo danh sách sản phẩm (nếu cần)
        products.add(Product("1", "Sản phẩm A", 10000.0, 5.toString(), "2024-10-01"))
        products.add(Product("2", "Sản phẩm B", 20000.0, 3.toString(), "2024-10-02"))
        // Thêm nhiều sản phẩm hơn nếu cần
    }

    fun getProductById(productId: String): Product? {
        return products.find { it.id == productId }
    }

    fun updateProduct(updatedProduct: Product) {
        val index = products.indexOfFirst { it.id == updatedProduct.id }
        if (index != -1) {
            products[index] = updatedProduct
        }
    }

    // Bạn có thể thêm các phương thức khác như thêm, xóa sản phẩm, v.v.
}
