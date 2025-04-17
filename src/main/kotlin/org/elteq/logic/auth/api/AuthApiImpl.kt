package org.elteq.logic.auth.api

import jakarta.inject.Inject
import jakarta.validation.Valid
import org.elteq.base.apiResponse.domain.ApiResponse
import org.elteq.logic.auth.dtos.*
import org.elteq.logic.auth.service.AuthService
import org.elteq.logic.users.dtos.UserAddDTO
import org.elteq.logic.users.dtos.UserDTO
import org.elteq.logic.users.dtos.UserLoginResponse
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

    override fun login(@Valid dto: LoginDTO): UserLoginResponse {
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

    override fun logout(token: String): LogOutResponse {
        logger.info("Logout request received")
        return authService.logout(token)
    }

    override fun refreshToken(dto: RefreshTokenRequest): RefreshResponse {
        logger.info("refresh token request received")
        return authService.refreshToken(dto)
    }
}