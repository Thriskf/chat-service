package org.elteq.logic.users.api

import jakarta.inject.Inject
import jakarta.transaction.Transactional
import jakarta.ws.rs.core.Response
import org.elteq.base.apiResponse.GenericHttpResponse.Companion.wrapApiResponse
import org.elteq.base.apiResponse.domain.ApiResponse
import org.elteq.base.apiResponse.wrapFailureInResponse
import org.elteq.base.apiResponse.wrapInApiResponse
import org.elteq.base.exception.ServiceException
import org.elteq.base.utils.MapperUtil.Mapper
import org.elteq.logic.users.db.Users
import org.elteq.logic.users.models.*
import org.elteq.logic.users.service.UserService
import org.elteq.logic.users.spec.UserSpec
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*
import java.util.stream.Collectors

@Transactional
class UserApiImpl(@Inject var service: UserService) : UserApi {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)
    private val modelMapper = Mapper.mapper

    override fun add(dto: UserAddDTO): ApiResponse<UserDTO> {
        logger.info("Adding new user: $dto")
        try {
            val ent = service.add(dto)
            val dto = modelMapper.map(ent, UserDTO::class.java)
            val response = wrapInApiResponse(dto)
            logger.info("Added new user response: $response")
            return response
        } catch (e: Exception) {
            logger.error("Failed to add new user", e)
            return wrapFailureInResponse("Could not add new user")
        }
    }


    override fun updateName(dto: UserUpdateNameDTO): ApiResponse<UserDTO> {
        logger.info("Updating user name with : $dto")
        try {
            val ent = service.updateName(dto)
            val dto = modelMapper.map(ent, UserDTO::class.java)
            val response = wrapInApiResponse(dto)
            logger.info("update user name response: $response")
            return response
        } catch (e: Exception) {
            logger.error("Failed to update user name", e)
            return wrapFailureInResponse("Could not update user name")
        }
    }

    override fun updateContact(dto: UserUpdateContactDTO): ApiResponse<UserDTO> {
        logger.info("Updating user contact with : $dto")
        try {
            val ent = service.updateContact(dto)
            val dto = modelMapper.map(ent, UserDTO::class.java)
            val response = wrapInApiResponse(dto)
            logger.info("update user contact response: $response")
            return response
        } catch (e: Exception) {
            logger.error("Failed to update user contact", e)
            return wrapFailureInResponse("Could not update user contact")
        }
    }

    override fun getById(id: UUID): ApiResponse<UserDTO> {
        logger.info("Getting user by id: $id")
        try {
            val ent = service.getById(id)
            val dto = modelMapper.map(ent, UserDTO::class.java)
            val response = wrapInApiResponse(dto)
            logger.info("Get user by id response: $response")
            return response
        } catch (e: Exception) {
            logger.error("Failed to get user by id", e)
            return wrapFailureInResponse("Failed to get user by id $id")
        }

    }

    override fun search(spec: UserSpec): ApiResponse<List<UserDTO>> {
        logger.info("Searching users with spec: $spec")
        try {
            val ents = service.all(spec)
//            val dtos = modelMapper.map(ents, Array<UserDTO>::class.java).toList()
            val dtos = ents.stream<Users>().map {
                logger.info("mapped users: $it to dto:")
                modelMapper.map(it, UserDTO::class.java)
            }.collect(Collectors.toList())
            val response = wrapInApiResponse(dtos)
            logger.info("Search users response: $response")
            return response

        } catch (e: Exception) {
            logger.error("Failed to search users", e)
            return wrapFailureInResponse("Failed to filter users")
        }
    }

    override fun getByContact(contact: String): ApiResponse<UserDTO> {
        logger.info("Getting user by contact: $contact")
        try {
            val ent = service.getByContact(contact)
            val dto = modelMapper.map(ent, UserDTO::class.java)
            val response = wrapInApiResponse(dto)
            logger.info("Get user by contact response: $response")
            return response
        } catch (e: Exception) {
            logger.error("Failed to get user by contact", e)
            return wrapFailureInResponse("Failed to get user by contact $contact")
        }
    }

    override fun delete(id: UUID): ApiResponse<String> {
        logger.info("Deleting user with id: $id")
        try {
            val result = service.delete(id)
            val response = wrapInApiResponse(result)
            logger.info("Delete user response: $response")
            return response
        } catch (e: Exception) {
            logger.error("Failed to delete user with id: $id", e)
            return wrapFailureInResponse("Failed to delete user with id $id")
        }
    }

    override fun updateStatus(dto: UserUpdateStatusDTO): ApiResponse<UserDTO> {
        logger.info("Updating user status with : $dto")
        try {
            val ent = service.updateStatus(dto)
            val dto = modelMapper.map(ent, UserDTO::class.java)
            val response = wrapInApiResponse(dto)
            logger.info("update user status response: $response")
            return response
        } catch (e: Exception) {
            logger.error("Failed to update user status", e)
            return wrapFailureInResponse("Could not update user status")
        }
    }

    override fun deleteAll(): ApiResponse<String> {
        logger.info("Deleting all users")
        try {
            val result = service.deleteAll()
            val response = wrapInApiResponse(result)
            logger.info("Delete all users response: $response")
            return response
        } catch (e: Exception) {
            logger.error("Failed to delete all users", e)
            return wrapFailureInResponse("Failed to delete all users")
        }
    }

    override fun count(): Response {
        logger.info("Counting users")
        try {
            val count = service.count()
            val response = wrapApiResponse(count)
            logger.info("count response $response")
            return response
        } catch (e: Exception) {
            logger.error("could not count users")
            throw ServiceException(-3, "Could not get count of users")
        }
    }
}