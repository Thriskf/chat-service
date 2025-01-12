package com.generis.business.autoDebit.stream.kafka

import com.generis.redSave.commons.dto.subscriber.SubscriptionResultDto
import com.generis.redSave.commons.stream.kafka.KafkaMessageType
import com.generis.redSave.commons.stream.kafka.KafkaProducer
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.eclipse.microprofile.reactive.messaging.Channel
import org.eclipse.microprofile.reactive.messaging.Emitter
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@ApplicationScoped
class TelcoUnSubscriptionResultProducer {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @Inject
    @Channel("red_save_telco_cancel_subscription_result_producer")
    private lateinit var emitter: Emitter<String>

    @Inject
    private lateinit var kafkaProducer: KafkaProducer

    fun send(subscriptionResultDto: SubscriptionResultDto) {
        logger.info("Producing to channel red_save_telco_cancel_subscription_result_producer with payload: $subscriptionResultDto")

        kafkaProducer.broadcast(subscriptionResultDto, KafkaMessageType.DELETE, emitter)
    }
}