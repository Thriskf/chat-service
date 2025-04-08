package org.elteq.logic.users.dtos

import org.elteq.base.apiResponse.domain.BaseResponse
import org.elteq.base.apiResponse.domain.PaginatedBaseResponse
import java.time.Instant

class UserResponse(data: List<UserDTO>?) : BaseResponse() {
    val data: List<UserDTO>? = data
}

class UserPaginatedResponse(data: List<UserDTO>?) : PaginatedBaseResponse() {
    val data: List<UserDTO>? = data
}


class UserLoginResponse(data: LoginResponse?) : BaseResponse() {
    val data: LoginResponse? = data
}

class LoginResponse(data: UserDTO?) : BaseResponse() {
    val user: UserDTO? = data
    var accessToken: String? = null
    var refreshToken: String? = null
    var accessTokenExpiresIn: Instant? = null
    var refreshTokenExpiresIn: Instant? = null

}