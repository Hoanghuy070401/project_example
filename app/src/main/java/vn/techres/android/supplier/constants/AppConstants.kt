package vn.techres.android.supplier.constants

/**
 * @Author: Bùi Hửu Thắng
 * @Date: 03/10/2022
 */
object
AppConstants {
    const val ENABLE = 1  // đang hoạt động
    const val DISABLE = 0 // tạm khóa
    const val ALL_STATUS = -1 // Tất cả trạng thái

    //Config call api
    const val ANDROID_SUPPLIER = 5
    const val APP_OS_NAME = "tms_supplier_android"
    const val HTTP_METHOD_POST = 1
    const val HTTP_METHOD_GET = 0
    const val PROJECT_ID = "net.techres.supplier.api"

    const val METHOD_POST = "POST"
    const val METHOD_GET = "GET"

    const val SECOND_INSTALL = "SECOND_INSTALL"
    const val CACHE_USER = "CACHE_USER"
    const val CACHE_SETTING = "CACHES_SETTING"
    const val CACHE_BRANCH = "CACHES_BRANCH"
    const val CACHE_CONFIG = "CACHE_CONFIG"
    const val TERMS_OF_USE = "https://techres.vn/quy-dinh-su-dung"
    const val PRIVACY_POLICY = "https://techres.vn/chinh-sach-bao-mat"

    /**
     * Level nhà hàng
     */
    const val LEVEL_1 = 1
    const val LEVEL_2 = 2
    const val LEVEL_3 = 3
    const val LEVEL_4 = 4
    const val LEVEL_5 = 5
    const val LEVEL_6 = 6
    const val LEVEL_7 = 7
    const val LEVEL_8 = 8
    const val LEVEL_9 = 9
    const val LEVEL_10 = 10
    const val LEVEL_11 = 11

    //upload
    const val HEADER_NAME = "Authorization"
    const val UPLOAD_IMAGE = 0 // type up image
    const val UPLOAD_VIDEO = 1 // type up video
    const val UPLOAD_AUDIO = 2 // type up audio
    const val UPLOAD_FILE = 3 // type up file

    const val MEDIA_ID = "medias[0][media_id]"
    const val TYPE = "medias[0][type]"
    const val FILE = "medias[0][file]"

    const val CITY = "CITY"
    const val WARDS = "WARDS"
    const val DISTRICTS = "DISTRICTS"
    const val PUSH_TOKEN = "PUSH_TOKEN"
    const val USER_ID = "USER_ID"
    const val KEY_BRANCH_NAME = "KEY_BRANCH_NAME"
    const val KEY_FULL_NAME = "KEY_FULL_NAME"
    const val KEY_AVATAR = "KEY_AVATAR"
    const val KEY_ROLE = "KEY_ROLE"
    const val KEY_USE_YEARLY_OFF = "KEY_USE_YEARLY_OFF"
    const val KEY_ALLOW_YEARLY_OFF = "KEY_ALLOW_YEARLY_OFF"
    const val KEY_USE_MONTH_OFF = "KEY_USE_MONTH_OFF"
    const val KEY_ALLOW_MONTH_OFF = "KEY_ALLOW_MONTH_OFF"
    const val KEY_OFF_WITH_SALARY = "KEY_OFF_WITH_SALARY"
    const val KEY_OFF_WITHOUT_SALARY = "KEY_OFF_WITHOUT_SALARY"
    const val KEY_IS_DILIGENCE = "KEY_IS_DILIGENCE"
    const val KEY_LIST_DILIGENCE = "KEY_LIST_DILIGENCE"
    const val KEY_OFF_NOT_ALLOW = "KEY_OFF_NOT_ALLOW"
    const val KEY_ACTION = "KEY_ACTION"
    const val KEY_MATERIAL_CATEGORY_TYPE_PARENT_ID = "KEY_MATERIAL_CATEGORY_TYPE_PARENT_ID"
    const val KEY_MATERIAL_DATA = "KEY_MATERIAL_DATA"
    const val KEY_BRANCH_ID = "KEY_BRANCH_ID"
    const val KEY_TIME_KEEPING_DATA = "KEY_TIME_KEEPING_DATA"
    const val KEY_REQUEST_DATA = "KEY_REQUEST_DATA"
    const val KEY_ORDER_DATA = "KEY_ORDER_DATA"
    const val KEY_DATE = "KEY_DATE"
    const val KEY_IS_PUNISH = "KEY_IS_BONUS"
    const val KEY_LIST_DATA = "KEY_LIST_DATA"
    const val KEY_ID = "KEY_ID"
    const val KEY_POSITION = "KEY_POSITION"

    const val MEDIA_DATA = "MEDIA_DATA"
    const val MEDIA_DATA_TYPE = "MEDIA_DATA_TYPE"
    const val MEDIA_POSITION = "MEDIA_POSITION"
    const val DATA_MEDIA_TYPE = "DATA_MEDIA_TYPE"
    const val TYPE_FILE_CHAT = "TYPE_FILE_CHAT"

    //Dialog Input Type
    const val INTEGER_INPUT_TABLE = 1
    const val DECIMAL_INPUT_TABLE = 3
    const val SHIFT_INPUT = -1
    const val MAX_TEXT_LENGTH = 255

    const val TYPE_INVENTORY = "TYPE_INVENTORY"
    const val KEY_RESTAURANT_BRANCH_ID = "KEY_RESTAURANT_BRANCH_ID"
    const val KEY_TOTAL = "KEY_TOTAL"
    const val KEY_BOOLEAN = "KEY_BOOLEAN"



    const val FOLDER_APP = "techres/seemt"//folder lưu file của App
    const val EXPIRED = 1
    const val LOCATION = 2
    const val PROGRESS = "progress"
    const val DOWNLOAD_CACHE = "DOWNLOAD_CACHE"
    const val FILE_DOWNLOAD = "FILE_DOWNLOAD"
    const val TYPE_DOWNLOAD = "TYPE_DOWNLOAD"

    const val KEY_DATA = "KEY_DATA"
}