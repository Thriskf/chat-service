package org.elteq.logic.auth.service

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.elteq.base.apiResponse.domain.ApiResponse
import org.elteq.base.apiResponse.domain.ResponseMessage
import org.elteq.base.apiResponse.wrapFailureInResponse
import org.elteq.base.apiResponse.wrapInSuccessResponse
import org.elteq.base.utils.MapperUtil.Mapper
import org.elteq.logic.auth.JwtUtils
import org.elteq.logic.auth.dtos.*
import org.elteq.logic.users.dtos.LoginResponse
import org.elteq.logic.users.dtos.UserAddDTO
import org.elteq.logic.users.dtos.UserDTO
import org.elteq.logic.users.dtos.UserResponse
import org.elteq.logic.users.service.UserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@ApplicationScoped
class AuthServiceImp(
    @Inject var userService: UserService,
    @Inject var jwtUtils: JwtUtils,
) : AuthService {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)
    private val modelMapper = Mapper.mapper

    override fun register(dto: UserAddDTO): ApiResponse<UserDTO> {

        return runCatching {
            val ent = userService.add(dto)
            modelMapper.map(ent, UserDTO::class.java)
        }.fold(
            onSuccess = { dto ->
                val response = wrapInSuccessResponse(dto)
                logger.info("Added new user response message: ${response.message}")
                response
            },
            onFailure = { e ->
                logger.error("Failed to add new user", e)
                wrapFailureInResponse("Could not add new user. ${e.message}")
            }
        )
    }

    override fun login(dto: LoginDTO): LoginResponse {
        var tokenPair: TokenPair? = null

        return runCatching {
            val user = userService.getByContact(dto.username!!)
            val verified = userService.verifyPassword(dto.password!!, user.password?.password!!)
            if (!verified) {
                return LoginResponse(UserDTO()).apply {
                    this.code = ResponseMessage.FAIL.code
                    this.systemCode = ResponseMessage.FAIL.code
                    this.message = ResponseMessage.FAIL.message
                    this.systemMessage = "Invalid Credential!"
                }
            }

            tokenPair = jwtUtils.generateTokenPair(user)
            modelMapper.map(user, UserDTO::class.java)
        }.fold(onSuccess = {
            logger.info("User login success for ${dto.username}")
            LoginResponse(it).apply {
                this.code = ResponseMessage.SUCCESS.code
                this.systemMessage = "Login Successful"
                this.message = ResponseMessage.SUCCESS.message
                this.systemCode = ResponseMessage.SUCCESS.code
                this.accessToken = tokenPair?.accessToken
                this.refreshToken = tokenPair?.refreshToken
                this.refreshTokenExpiresIn = tokenPair?.refreshTokenExpiry
                this.accessTokenExpiresIn = tokenPair?.accessTokenExpiry


            }
        }, onFailure = {
            logger.error("Login failure! Error :: ", it)
            LoginResponse(UserDTO()).apply {
                this.code = ResponseMessage.FAIL.code
                this.systemMessage = "Login Failed"
                this.message = ResponseMessage.FAIL.message
                this.systemCode = ResponseMessage.FAIL.code
            }
        })
    }

    override fun resetPassword(dto: UserForgetPasswordDTO): UserResponse {
        return userService.resetPassword(dto)
    }

    override fun updatePassword(dto: UserChangePasswordDTO): UserResponse {
        return userService.updatePassword(dto)
    }

    override fun logout() {
        TODO("Not yet implemented")
    }

    override fun refreshToken(dto: RefreshTokenRequest): ApiResponse<UserDTO> {
        TODO("Not yet implemented")
    }

}