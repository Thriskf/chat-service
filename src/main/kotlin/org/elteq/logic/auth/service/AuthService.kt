package org.elteq.logic.auth.service

import org.elteq.base.apiResponse.domain.ApiResponse
import org.elteq.logic.auth.dtos.LoginDTO
import org.elteq.logic.auth.dtos.RefreshTokenRequest
import org.elteq.logic.auth.dtos.UserChangePasswordDTO
import org.elteq.logic.auth.dtos.UserForgetPasswordDTO
import org.elteq.logic.users.dtos.LoginResponse
import org.elteq.logic.users.dtos.UserAddDTO
import org.elteq.logic.users.dtos.UserDTO
import org.elteq.logic.users.dtos.UserResponse

interface AuthService {
    fun register(dto: UserAddDTO): ApiResponse<UserDTO>
    fun login(dto: LoginDTO): LoginResponse
    fun resetPassword(dto: UserForgetPasswordDTO): UserResponse
    fun updatePassword(dto: UserChangePasswordDTO): UserResponse
    fun logout()
    fun refreshToken(dto: RefreshTokenRequest): ApiResponse<UserDTO>
}