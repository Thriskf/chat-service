//package com.elteq
//
//import com.elteq.logic.users.role.service.RoleService
//import io.quarkus.runtime.StartupEvent
//import jakarta.enterprise.context.ApplicationScoped
//import jakarta.enterprise.event.Observes
//import jakarta.enterprise.inject.Default
//import jakarta.inject.Inject
//import org.slf4j.Logger
//import org.slf4j.LoggerFactory
//
//@ApplicationScoped
//class StartUpRunners {
//
//    private val logger: Logger = LoggerFactory.getLogger(this::class.java)
//
//    @field:Inject
//    @field:Default
//    private lateinit var roleService: RoleService
//
//
//    fun onStart(@Observes startupEvent: StartupEvent){
//        logger.info("Running start up configs ")
//
//        logger.info("ROLE SESSION")
////        roleService.importRoles()
//
//        logger.info("ROLE SESSION DONE")
//
//
//    }
//
//}