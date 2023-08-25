package vn.techres.android.supplier.model.eventbus

/**
 * @Author: NGUYEN THE DAT
 * @Date: 4/15/2023
 */

// event dùng để gọi lại api ở activity list chờ duyệt khi tạo thành công
// type là dùng để phân biệt màn hình nào
//các loại type ở CreateEventBusConstants
class CreateSuccessEventBus(var type:Int) {
}