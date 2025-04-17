package org.elteq.logic.auth.service

import org.elteq.base.apiResponse.domain.ApiResponse
import org.elteq.logic.auth.dtos.*
import org.elteq.logic.users.dtos.UserAddDTO
import org.elteq.logic.users.dtos.UserDTO
import org.elteq.logic.users.dtos.UserLoginResponse
import org.elteq.logic.users.dtos.UserResponse

interface AuthService {
    fun register(dto: UserAddDTO): ApiResponse<UserDTO>
    fun login(dto: LoginDTO): UserLoginResponse
    fun resetPassword(dto: UserForgetPasswordDTO): UserResponse
    fun updatePassword(dto: UserChangePasswordDTO): UserResponse
    fun logout(token:String):LogOutResponse
    fun refreshToken(dto: RefreshTokenRequest): RefreshResponse
}