//package logic
//
//import com.elteq.logic.patient.api.PatientApi
//import com.elteq.logic.patient.model.CreatePatientDto
//import com.elteq.logic.patient.model.PatientDto
//import com.elteq.logic.users.role.api.UserRoleApi
//import com.elteq.logic.users.user.api.UserApi
//import org.elteq.base.apiResponse.domain.ResponseMessage
//import org.elteq.commons.models.user.groupRoles.CreateRoleDTO
//import org.elteq.commons.models.user.groupRoles.RolesDTO
//import org.elteq.commons.models.user.user.CreateUserDTO
//import org.elteq.commons.models.user.user.UserDTO
//
//import uk.co.jemos.podam.api.PodamFactoryImpl
//
//class TestSetupUtil() {
//    private val podamFactoryImpl: PodamFactoryImpl = PodamFactoryImpl()
//
//    var patient: List<PatientDto>? = null
//    var userRole: List<RolesDTO>? = null
//    var user: List<UserDTO>? = null
//
//    fun setup(userRoleApi: UserRoleApi, patientApi: PatientApi, userApi: UserApi) {
//        if (user == null) {
//            user = createDummyUser(userApi) ?: throw IllegalStateException("Failed to create user")
//        }
//
//        if (userRole == null) {
//            userRole = createDummyUserRole(userRoleApi) ?: throw IllegalStateException("Failed to create role")
//        }
//
//        if (patient == null) {
//            patient = createDummyPatient(patientApi) ?: throw IllegalStateException("Failed to create patient")
//        }
//
//    }
//
//    private fun createDummyUserRole(userRoleApi: UserRoleApi): List<RolesDTO>? {
//        val createDto = podamFactoryImpl.manufacturePojo(CreateRoleDTO::class.java)
//        val apiResponse = userRoleApi.create(createDto)
//
//        if (apiResponse.code != ResponseMessage.SUCCESS.code) {
//            return null
//        }
//        return apiResponse.data
//    }
//
//    private fun createDummyPatient(patientApi: PatientApi): List<PatientDto>? {
//        val createDto = podamFactoryImpl.manufacturePojo(CreatePatientDto::class.java)
//        val apiResponse = patientApi.create(createDto)
//
//        if (apiResponse.code != ResponseMessage.SUCCESS.code) {
//            return null
//        }
//        return apiResponse.data
//    }
//
//    private fun createDummyUser(userApi: UserApi): List<UserDTO>? {
//        val createDto = podamFactoryImpl.manufacturePojo(CreateUserDTO::class.java)
//        val apiResponse = userApi.create(createDto)
//
//        if (apiResponse.code != ResponseMessage.SUCCESS.code) {
//            return null
//        }
//        return apiResponse.data
//    }
//
//}