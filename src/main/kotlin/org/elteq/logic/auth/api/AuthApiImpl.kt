package org.elteq.logic.auth.api

import jakarta.inject.Inject
import org.elteq.base.apiResponse.domain.ApiResponse
import org.elteq.logic.auth.dtos.LoginDTO
import org.elteq.logic.auth.dtos.RefreshTokenRequest
import org.elteq.logic.auth.dtos.UserChangePasswordDTO
import org.elteq.logic.auth.dtos.UserForgetPasswordDTO
import org.elteq.logic.auth.service.AuthService
import org.elteq.logic.users.dtos.LoginResponse
import org.elteq.logic.users.dtos.UserAddDTO
import org.elteq.logic.users.dtos.UserDTO
import org.elteq.logic.users.dtos.UserResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class AuthApiImpl(
    @Inject var authService: AuthService,
) : AuthApi {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)
    override fun register(dto: UserAddDTO): ApiResponse<UserDTO> {
        logger.info("Adding new user: $dto")
        return authService.register(dto)
    }

    override fun login(dto: LoginDTO): LoginResponse {
        logger.info("User login request for ${dto.username}")
        return authService.login(dto)
    }

    override fun resetPassword(dto: UserForgetPasswordDTO): UserResponse {
        logger.info("reset password request for ${dto.email}")
        return authService.resetPassword(dto)
    }

    override fun updatePassword(dto: UserChangePasswordDTO): UserResponse {
        logger.info("update password request for ${dto.userId}")
        return authService.updatePassword(dto)
    }

    override fun logout() {
        logger.info("Logout request received")
        return authService.logout()
    }

    override fun refreshToken(dto: RefreshTokenRequest): ApiResponse<UserDTO> {
        logger.info("refresh token request received")
        return authService.refreshToken(dto)
    }
}