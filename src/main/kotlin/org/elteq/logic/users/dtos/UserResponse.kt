package org.elteq.logic.users.dtos

import org.elteq.base.apiResponse.domain.BaseResponse
import org.elteq.base.apiResponse.domain.PaginatedBaseResponse

class UserResponse(data: List<UserDTO>?) : BaseResponse() {
    val data: List<UserDTO>? = data
}

class UserPaginatedResponse(data: List<UserDTO>?) : PaginatedBaseResponse() {
    val data: List<UserDTO>? = data
}


class UserLoginResponse(data: UserDTO?) : BaseResponse() {
    val data: UserDTO? = data
    var accessToken: String? = null
}