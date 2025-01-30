package org.elteq.health

import com.sun.management.OperatingSystemMXBean
import io.smallrye.health.api.AsyncHealthCheck
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import org.eclipse.microprofile.health.HealthCheckResponse
import org.eclipse.microprofile.health.Liveness
import java.lang.management.ManagementFactory

@Liveness
@ApplicationScoped
class MyHealthCheck : AsyncHealthCheck {

    override fun call(): Uni<HealthCheckResponse> {

        val osBean: OperatingSystemMXBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean::class.java)
        val osName: String = osBean.name
        val osVersion: String = osBean.version
        val architecture: String = osBean.arch
        val cpuUsage: Double = osBean.systemLoadAverage
        val freeMemory = Runtime.getRuntime().freeMemory()
        val totalMemory = Runtime.getRuntime().totalMemory()
        val maxMemory = Runtime.getRuntime().maxMemory()
        val availableProcessors = Runtime.getRuntime().availableProcessors()
        val memoryUsed = totalMemory - freeMemory

        return Uni.createFrom()
            .item(
                HealthCheckResponse.builder()
                    .withData("OS Name", osName)
                    .withData("OS Architecture", architecture)
                    .withData("OS Version", osVersion)
                    .withData("CPU Usage", cpuUsage.toString())
                    .withData("Free Memory", freeMemory)
                    .withData("Total Memory", totalMemory)
                    .withData("Memory Usage", memoryUsed)
                    .withData("Maximum Memory ", maxMemory)
                    .withData("Available Processors", availableProcessors.toLong())
                    .name("System Health Check")
                    .status(true)
                    .up()
                    .build()
            )
    }
}