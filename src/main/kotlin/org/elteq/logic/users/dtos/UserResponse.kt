package org.elteq.logic.users.dtos

import org.elteq.base.apiResponse.domain.BaseResponse
import org.elteq.base.apiResponse.domain.PaginatedBaseResponse

class UserResponse(data: List<UserDTO>?) : BaseResponse() {
    val data: List<UserDTO>? = data
}

class UserPaginatedResponse(data: List<UserDTO>?) : PaginatedBaseResponse() {
    val data: List<UserDTO>? = data
}


class UserLoginResponse(data: LoginResponse?) : BaseResponse() {
    val data: LoginResponse? = data
}

class LoginResponse(data: UserDTO?) {
    val user: UserDTO? = data
    var accessToken: String? = null
    var refreshToken: String? = null
    var accessTokenExpiresIn: Long? = null
    var refreshTokenExpiresIn: Long? = null

}