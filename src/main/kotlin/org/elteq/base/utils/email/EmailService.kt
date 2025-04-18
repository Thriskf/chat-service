package org.elteq.base.utils.email


import io.quarkus.mailer.Mail
import io.quarkus.mailer.Mailer
import io.quarkus.mailer.reactive.ReactiveMailer
import io.quarkus.qute.TemplateInstance
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
    //    public Mailer mailer;
    //    @Inject
    //    public EmailService(Mailer mailer) {
    //        this.mailer = mailer;
    //    }


    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    fun sendTextMail(@Valid dto: EmailDTO) {
        CompletableFuture.runAsync {
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
    }

    fun sendHtmlMail(@Valid dto: EmailDTO, htmlMail: TemplateInstance) {
        logger.info("about to send html email")
        CompletableFuture.runAsync {
            runCatching {
                mailer.send(
                    Mail.withHtml(
                        dto.recipientName,
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
        }
    }


//    fun sendEmailReactive(@Valid dto: EmailDTO): Uni<Void> {
//        return reactiveMailer.send(
//            Mail.withText(dto.recipient, dto.subject, dto.body)
//        ).onItem().invoke(() -> {
//            logger.info("Email sent successfully to ${dto.recipient}")
//        }).onFailure().invoke(failure -> {
//            logger.error("Failed to send email to ${dto.recipient}: ${failure.getMessage()}");
//        })
//    }

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