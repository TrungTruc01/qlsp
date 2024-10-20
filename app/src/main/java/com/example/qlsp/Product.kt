import android.os.Parcel
import android.os.Parcelable

data class Product(
    val id: String = "", // Thêm giá trị mặc định
    var name: String = "", // Thêm giá trị mặc định
    var price: Double = 0.0, // Thêm giá trị mặc định
    var quantity: String = "", // Thêm giá trị mặc định
    var category: String = "" // Thêm giá trị mặc định
) : Parcelable {
    // Constructor không đối số để Firebase có thể khôi phục đối tượng
    constructor() : this("", "", 0.0, "", "")

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readDouble(),
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeDouble(price)
        parcel.writeString(quantity)
        parcel.writeString(category)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }
}
