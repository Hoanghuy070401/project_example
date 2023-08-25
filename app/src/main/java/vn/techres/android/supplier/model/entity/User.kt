package vn.techres.android.supplier.model.entity


import com.google.gson.annotations.SerializedName

/**
 * @Author: Nguyễn Thế Đạt
 * @Date: 07/04/2023
 */
class User {
    @SerializedName("id")
    var id = 0L

    @SerializedName("supplier_id")
    var supplierId = 0L

    @SerializedName("name")
    var name = ""

    @SerializedName("email")
    var email = ""

    @SerializedName("phone")
    var phone = ""

    @SerializedName("avatar")
    var avatar = ""

    @SerializedName("username")
    var username = ""

    @SerializedName("supplier_name")
    var supplierName = ""

    @SerializedName("password")
    var password = ""

    @SerializedName("supplier_role_id")
    var supplierRoleId = 0L

    @SerializedName("supplier_role_name")
    var supplierRoleName = ""

    @SerializedName("access_token")
    var accessToken = ""

    @SerializedName("jwt_token")
    var jwtToken = ""

    @SerializedName("token_type")
    var tokenType = ""

    @SerializedName("address")
    var address = ""

    var isUsedBiometric = false
    var otpCode = ""

}
