package org.elteq.logic.auth.service

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import org.elteq.base.apiResponse.domain.ApiResponse
import org.elteq.base.apiResponse.domain.ResponseMessage
import org.elteq.base.apiResponse.wrapFailureInResponse
import org.elteq.base.apiResponse.wrapInSuccessResponse
import org.elteq.base.utils.MapperUtil.Mapper
import org.elteq.logic.auth.JwtUtils
import org.elteq.logic.auth.dtos.*
import org.elteq.logic.users.dtos.*
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
            onSuccess = {
                val response = wrapInSuccessResponse(it)
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
        var tokenPair: TokenPair? = null
        val loginResponse = LoginResponse(UserDTO())


        return runCatching {
            val user = userService.getByContact(dto.username!!)
            val verified = userService.verifyPassword(dto.password!!, user.password?.password!!)
            if (!verified) {
                return UserLoginResponse(loginResponse).apply {
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

            val lResponse = LoginResponse(it).apply {
                this.accessToken = tokenPair?.accessToken
                this.refreshToken = tokenPair?.refreshToken
                this.refreshTokenExpiresIn = tokenPair?.refreshTokenExpiry
                this.accessTokenExpiresIn = tokenPair?.accessTokenExpiry
            }

            UserLoginResponse(lResponse).apply {
                this.code = ResponseMessage.SUCCESS.code
                this.systemMessage = "Login Successful"
                this.message = ResponseMessage.SUCCESS.message
                this.systemCode = ResponseMessage.SUCCESS.code

            }
        }, onFailure = {
            logger.error("Login failure! Error :: ", it)
            UserLoginResponse(loginResponse).apply {
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

    @Transactional
    override fun logout(token: String): LogOutResponse {
        return runCatching {
            jwtUtils.revokeAccessToken(token)
        }.fold(
            onSuccess = {
                LogOutResponse(it).apply {
                    this.message = "Success"
                    this.code = ResponseMessage.SUCCESS.code
                    this.systemCode = ResponseMessage.SUCCESS.code
                    this.systemMessage = "Logout Successful"
                }
            },
            onFailure = {
                logger.error("Log out failed", it)
                LogOutResponse(false).apply {
                    this.message = "Failed"
                    this.code = ResponseMessage.FAIL.code
                    this.systemCode = ResponseMessage.SUCCESS.code
                    this.systemMessage = "Logout Failed"
                }
            }
        )
    }

    override fun refreshToken(dto: RefreshTokenRequest): RefreshResponse {
        val token = jwtUtils.refreshAccessToken(dto.refreshToken!!)
        return RefreshResponse(token)

    }

}