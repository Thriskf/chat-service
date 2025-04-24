package org.elteq.base.utils.email


import io.quarkus.mailer.Mail
import io.quarkus.mailer.Mailer
import io.quarkus.mailer.reactive.ReactiveMailer
import io.quarkus.qute.TemplateInstance
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.validation.Valid
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.concurrent.CompletableFuture


@ApplicationScoped
class EmailService(
    @Inject var mailer: Mailer,
    @Inject var reactiveMailer: ReactiveMailer,
) {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    fun sendTextMail(@Valid dto: EmailDTO) {
            logger.info("about to send text email")
            runCatching {
                val mail = Mail.withText(dto.recipientEmail, dto.subject, dto.body)
                mailer.send(
                    mail
                )
            }.fold(
                onSuccess = {
                    logger.info("Text email sent successfully to :: ${dto.recipientEmail}")
                },
                onFailure = {
                    logger.error("Error while sending text email to :: ${dto.recipientEmail}", it)
                }
            )

    }

    fun sendHtmlMail1(@Valid dto: EmailDTO, htmlContent: TemplateInstance) {
            logger.info("Starting html mail process")
            runCatching {
                val mail = Mail.withHtml(
                    dto.recipientEmail,
                    dto.subject,
                    htmlContent.render()
                )
                mailer.send(mail)
            }.fold(
                onSuccess = {
                    logger.info("Html email sent successfully to :: ${dto.recipientEmail}")
                }, onFailure = {
                    logger.error("Error while sending html email to :: ${dto.recipientEmail}", it)
                })

    }

    fun sendHtmlMail2(@Valid dto: EmailDTO, htmlMail: TemplateInstance) {
        logger.info("about to send html email")
        CompletableFuture.runAsync {
            runCatching {
                mailer.send(
                    Mail.withHtml(
                        dto.recipientEmail,
                        dto.subject,
                        htmlMail.render()
                    )
                )
            }.fold(
                onSuccess = {
                    logger.info("Html email sent successfully to :: ${dto.recipientEmail}")
                }, onFailure = {
                    logger.error("Error while sending html email to :: ${dto.recipientEmail}", it)
                })
        }.exceptionally { ex ->
            logger.error("Async email sending failed for ${dto.recipientEmail}", ex)
            null
        }
    }

    // send reactive email
    fun sendHtmlMail(dto: EmailDTO, htmlMail: TemplateInstance): Uni<Void> {
        logger.info("about to send html email - ")
        return Uni.createFrom().item {
            htmlMail.render()
        }.onItem().transformToUni { renderedHtml ->
            logger.info("sending html email")
            reactiveMailer.send(
                Mail.withHtml(dto.recipientEmail, dto.subject, renderedHtml)
            )
        }.onItem().invoke { ->
            logger.info("Html Email sent successfully to ${dto.recipientEmail}")
        }.onFailure().invoke { e ->
            logger.error("Html Failed to send email to ${dto.recipientEmail}", e)
        }
    }

}


//class TryEmail {
//    @Inject
//    var emailService: base.EmailService? = null
//
//    private val logger: Logger = LoggerFactory.getLogger(TryEmail::class.java)
//
//    private fun send() {
//        val dto: EmailDTO = EmailDTO("", "", "")
//        emailService.sendEmail(dto).subscribe().with(
//            { success ->
//                logger.info("Email sent successfully (reactive).")
//            },
//            { failure ->
//                logger.error("Failed to send email (reactive): {}", failure.getMessage())
//            }
//        )
//    }
//}