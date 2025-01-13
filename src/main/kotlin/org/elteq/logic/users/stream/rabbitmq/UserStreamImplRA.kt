//package org.elteq.logic.users.stream.rabbitmq
//
//import jakarta.enterprise.context.ApplicationScoped
//import jakarta.inject.Inject
//import org.eclipse.microprofile.reactive.messaging.Channel
//import org.eclipse.microprofile.reactive.messaging.Emitter
//import org.eclipse.microprofile.reactive.messaging.Incoming
//import org.slf4j.Logger
//import org.slf4j.LoggerFactory
//
//@ApplicationScoped
//class UserStreamImplRA : UserStreamRA {
//    private val log: Logger = LoggerFactory.getLogger(this::class.java)
//
//    @Inject
//    @Channel("sample_rabbit_producer")
//    private lateinit var emitter: Emitter<String>
//    override fun produce(msg: String) {
//        val queue = "sample_rabbit_producer"
//        log.info("about to send  message to rabbit queue:: $queue - message ::  $msg")
//        try {
//            log.info("sending message to rabbit queue:: $queue - message ::  $msg")
//            emitter.send(msg)
//            log.info("message sent to rabbit  queue:: $queue - message ::  $msg")
//        } catch (e: Exception) {
//            log.error("Failed to send message to rabbit queue:: $queue - message ::  $msg")
//        }
//    }
//
//    @Incoming("sample_rabbit_consumer")
//    override fun consume(msg: String) {
//        val queue = "sample_rabbit_consumer"
//
//        log.info("Consumed message from rabbit queue:: $queue - message ::  $msg")
//        try {
//            log.info("processing message")
//        } catch (e: Exception) {
//            log.error("Failed to process message from rabbit queue:: $queue - message ::  $msg")
//        } finally {
//            log.info("message processed")
//        }
//    }
//
////
////    private fun convertToDueBilling(input: JsonObject): DueBillingMessage? {
////        return try {
////            val typeRef = object : TypeReference<DueBillingMessage>() {}
////            MessageSerializer.mapper.readValue(input.toString(), typeRef)
////        } catch (ex: Exception) {
////            log.info("Error happened while deserializing filtration data. Error: ${ex.localizedMessage}. Check payload: $input")
////            return null
////        }
////    }
//
//}