//package logic.autoDebit
//
//import com.elteq.logic.patient.api.PatientApi
//import com.elteq.logic.users.role.api.UserRoleApi
//import com.elteq.logic.users.user.api.UserApi
//import io.quarkus.test.junit.QuarkusTest
//import io.restassured.RestAssured
//import io.restassured.http.ContentType
//import jakarta.enterprise.context.control.ActivateRequestContext
//import jakarta.inject.Inject
//import logic.TestSetupUtil
//import org.elteq.base.apiResponse.domain.ResponseMessage
//import org.hamcrest.CoreMatchers
//import org.hamcrest.Matchers
//import org.junit.jupiter.api.BeforeAll
//import org.junit.jupiter.api.Test
//import org.junit.jupiter.api.TestInstance
//import uk.co.jemos.podam.api.PodamFactoryImpl
//
//@QuarkusTest
//@ActivateRequestContext
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//class UserApiTest {
//    private val path = "/api/v1/users"
//
//    private val podamFactoryImpl: PodamFactoryImpl = PodamFactoryImpl()
//    private lateinit var testSetupUtil: TestSetupUtil
//
//    @Inject
//    private lateinit var userRoleApi: UserRoleApi
//
//    @Inject
//    private lateinit var patientApi: PatientApi
//
//    @Inject
//    private lateinit var userApi: UserApi
//
//
//    @BeforeAll
//    fun setup() {
////        testSetupUtil = TestSetupUtil(
////            providerResource,
////            credentialResource,
////            countryResource,
////        )
//        testSetupUtil.setup(
//            userRoleApi,
//            patientApi,
//            userApi
//        )
//    }
//
//    @Test
//    fun create() {
//        val create = testSetupUtil.user
//        val url = path
//        RestAssured.given()
//            .contentType(ContentType.JSON)
//            .body(create)
//            .`when`()
//            .post(url)
//            .then()
//            .time(Matchers.lessThan(1500L))
//            .log().body()
//            .statusCode(200)
//            .body(
//                "code", CoreMatchers.`is`(ResponseMessage.SUCCESS.code),
//                "message", CoreMatchers.`is`(ResponseMessage.SUCCESS.message),
//            )
//    }
//
//    @Test
//    fun get() {
//        val url ="$path/${testSetupUtil.user?.first()?.id}"
//
//        RestAssured.given()
//            .`when`()
//            .get(url)
//            .then()
//            .time(Matchers.lessThan(1500L))
//            .log().body()
//            .statusCode(200)
//            .body(
//                "code", CoreMatchers.`is`(ResponseMessage.SUCCESS.code),
//                "message", CoreMatchers.`is`(ResponseMessage.SUCCESS.message),
//            )
//    }
//
////    @Test
////    fun cancelMandate() {
////
////        RestAssured.given()
////            .contentType(ContentType.JSON)
////            .body(cancelMandateMandate)
////            .`when`()
////            .post("$path/mandate/cancel")
////            .then()
//////            .time(Matchers.lessThan(1500L))
////            .log().body()
////            .statusCode(200)
////            .body(
////                "code", CoreMatchers.`is`(ResponseMessage.SUCCESS.code),
////                "message", CoreMatchers.`is`(ResponseMessage.SUCCESS.message),
////            )
////    }
//
//
//}