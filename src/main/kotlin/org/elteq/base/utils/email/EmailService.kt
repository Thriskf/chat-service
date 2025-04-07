package org.elteq.base.utils.email


import io.quarkus.mailer.Mail
import io.quarkus.mailer.Mailer
import io.quarkus.mailer.reactive.ReactiveMailer
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.validation.Valid
import org.slf4j.Logger
import org.slf4j.LoggerFactory


@ApplicationScoped
class EmailService {
    //    public Mailer mailer;
    //    @Inject
    //    public EmailService(Mailer mailer) {
    //        this.mailer = mailer;
    //    }
    @Inject
    private lateinit var mailer: Mailer

    @Inject
    private lateinit var reactiveMailer: ReactiveMailer

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    fun sendTextEmail(@Valid dto: EmailDTO) {
        mailer.send(
            Mail.withText(dto.recipientEmail, dto.subject, dto.body)
        )
    }

    fun sendHtmlEmail(@Valid dto: EmailDTO) {
        val htmlContent = EmailTemplates.welcome(dto.recipientName!!).render()
        mailer.send(
            Mail.withHtml(
                dto.recipientName,
                "Welcome to Our App!",
                htmlContent
            )
        )
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