package org.elteq.logic.auth.service

import org.elteq.base.apiResponse.domain.ApiResponse
import org.elteq.logic.auth.dtos.*
import org.elteq.logic.users.dtos.*

interface AuthService {
    fun register(dto: UserAddDTO): ApiResponse<UserDTO>
    fun login(dto: LoginDTO): UserLoginResponse
    fun resetPassword(dto: UserForgetPasswordDTO): UserResponse
    fun updatePassword(dto: UserChangePasswordDTO): UserResponse
    fun logout(token:String): LogOutResponse
    fun refreshToken(dto: RefreshTokenRequest): RefreshResponse
}