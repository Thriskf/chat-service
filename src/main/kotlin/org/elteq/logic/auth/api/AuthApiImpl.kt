package org.elteq.logic.auth.api

import jakarta.inject.Inject
import org.elteq.base.apiResponse.domain.ApiResponse
import org.elteq.base.apiResponse.domain.ResponseMessage
import org.elteq.base.apiResponse.wrapFailureInResponse
import org.elteq.base.apiResponse.wrapInSuccessResponse
import org.elteq.base.utils.MapperUtil.Mapper
import org.elteq.base.utils.PasswordUtils
import org.elteq.logic.auth.JwtUtils
import org.elteq.logic.users.dtos.*
import org.elteq.logic.users.service.UserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class AuthApiImpl(
    @Inject var userService: UserService,
    @Inject var jwtUtils: JwtUtils,
) : AuthApi {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)
    private val modelMapper = Mapper.mapper
    private val passwordUtils = PasswordUtils


    override fun register(dto: UserAddDTO): ApiResponse<UserDTO> {
        logger.info("Adding new user: $dto")

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


    override fun login(dto: LoginDTO): UserLoginResponse {
        logger.info("User login request for ${dto.username}")
        var data: UserDTO? = null
        var token: String? = null

        return runCatching {
            val user = userService.getByContact(dto.username!!)
            val verified = userService.verifyPassword(dto.password!!, user.password?.password!!)
            if (!verified) {
                return UserLoginResponse(data).apply {
                    this.code = ResponseMessage.FAIL.code
                    this.systemCode = ResponseMessage.FAIL.code
                    this.message = ResponseMessage.FAIL.message
                    this.systemMessage = "Invalid Credential!"
                }
            }

            token = jwtUtils.generateToken(user, dto.username!!)
            modelMapper.map(user, UserDTO::class.java)
        }.fold(onSuccess = {
            data = it
            logger.info("User login success for ${dto.username}")
            UserLoginResponse(data).apply {
                this.code = ResponseMessage.SUCCESS.code
                this.systemMessage = "Login Successful"
                this.message = ResponseMessage.SUCCESS.message
                this.systemCode = ResponseMessage.SUCCESS.code
                this.accessToken = token
            }
        }, onFailure = {
            logger.error("Login failure! Error :: ", it)
            UserLoginResponse(data).apply {
                this.code = ResponseMessage.FAIL.code
                this.systemMessage = "Login Failed"
                this.message = ResponseMessage.FAIL.message
                this.systemCode = ResponseMessage.FAIL.code
            }
        })
    }

    override fun resetPassword(dto: UserForgetPasswordDTO): UserResponse {
        logger.info("reset password request for ${dto.email}")
        return userService.resetPassword(dto)
    }

    override fun updatePassword(dto: UserChangePasswordDTO): UserResponse {
        logger.info("update password request for ${dto.userId}")
        return userService.updatePassword(dto)
    }

    override fun logout() {
        TODO("Not yet implemented")
    }
}