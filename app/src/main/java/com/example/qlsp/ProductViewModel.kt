import androidx.lifecycle.ViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import Product

class ProductViewModel : ViewModel() {
    private val dbRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("products")

    // Lấy sản phẩm theo ID từ Firebase
    fun getProductById(productId: String, callback: (Product?) -> Unit) {
        dbRef.child(productId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val product = snapshot.getValue(Product::class.java)
                callback(product)
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null)
            }
        })
    }

    // Cập nhật sản phẩm lên Firebase
    fun updateProduct(product: Product, callback: (Boolean) -> Unit) {
        dbRef.child(product.id).setValue(product).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                callback(true) // Thành công
            } else {
                callback(false) // Thất bại
            }
        }
    }

    // Xóa sản phẩm từ Firebase
    fun deleteProduct(productId: String, callback: (Boolean) -> Unit) {
        dbRef.child(productId).removeValue().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                callback(true) // Xóa thành công
            } else {
                callback(false) // Xóa thất bại
            }
        }
    }
}
