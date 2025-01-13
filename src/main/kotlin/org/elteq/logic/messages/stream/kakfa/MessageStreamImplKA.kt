//package org.elteq.logic.messages.stream.kakfa
//
//import io.smallrye.reactive.messaging.annotations.Broadcast
//import jakarta.enterprise.context.ApplicationScoped
//import jakarta.inject.Inject
//import org.eclipse.microprofile.reactive.messaging.Channel
//import org.eclipse.microprofile.reactive.messaging.Emitter
//import org.eclipse.microprofile.reactive.messaging.Incoming
//import org.eclipse.microprofile.reactive.messaging.OnOverflow
//import org.slf4j.Logger
//import org.slf4j.LoggerFactory
//
//@ApplicationScoped
//class MessageStreamImplKA : MessageStreamKA {
//    private val log: Logger = LoggerFactory.getLogger(this::class.java)
//
//
//    @Inject
//    @Channel("sample_kafka_producer")
//    @OnOverflow(OnOverflow.Strategy.BUFFER)
//    @Broadcast
//    private lateinit var emitter: Emitter<String>
//    override fun produce(msg: String) {
//        val queue = "sample_kafka_producer"
//        log.info("about to send  message to Kafka queue:: $queue - message ::  $msg")
//        try {
//            log.info("sending message to Kafka queue:: $queue - message ::  $msg")
//            emitter.send(msg)
//            log.info("message sent to Kafka  queue:: $queue - message ::  $msg")
//        } catch (e: Exception) {
//            log.error("Failed to send message to Kafka queue:: $queue - message ::  $msg")
//        }
//    }
//
//    @Incoming("sample_kafka_consumer")
//    override fun consume(msg: String) {
//        val queue = "sample_kafka_consumer"
//
//        log.info("Consumed message from Kafka queue:: $queue - message ::  $msg")
//        try {
//            log.info("processing message")
//        }catch (e: Exception){
//            log.error("Failed to process message from Kafka queue:: $queue - message ::  $msg")
//        }finally {
//            log.info("message processed")
//        }
//    }
//
//}