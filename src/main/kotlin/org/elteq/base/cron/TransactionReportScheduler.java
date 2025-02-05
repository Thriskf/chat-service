//package org.elteq.base.cron;
//
//import jakarta.enterprise.context.ApplicationScoped;
//import lombok.val;
//
//import java.time.LocalDate;
//
//@ApplicationScoped
//class TransactionReportScheduler {
//    @ConfigProperty(name = "support.email.addresses")
//    private lateinit var recipients: String
//
//    @ConfigProperty(name = "email.signature.id")
//    private lateinit var signature: String
//
//    @ConfigProperty(name = "email.sender.id")
//    private lateinit var senderName: String
//    private val logger = LoggerFactory.getLogger(this::class.java)
//
//    @Inject
//    private lateinit var transactionReportService: TransactionReportService
//
//    @Inject
//    private lateinit var excelGenerator: ExcelGenerator
//
//    @Inject
//    private lateinit var mailClient: MailService
//
//    @Scheduled(cron = "0 0 0 * * ?") // At 12:00 AM every day
//    fun sendTransactionReport() {
//        logger.info("About to send daily transaction report.")
//        try {
//            val spec = TransactionReportSpec(transactionDate = LocalDate.now().minusDays(1))
//
//            val transactions = transactionReportService.search(spec)
//                .list()
//                .map { tnxReport ->
//                    TransactionReport(
//                        transactionId = tnxReport.transactionId,
//                        transactionDate = tnxReport.transactionDate,
//                        merchantName = tnxReport.merchantName,
//                        narration = tnxReport.narration,
//                        balanceBefore = tnxReport.balanceBefore,
//                        balanceAfter = tnxReport.balanceAfter,
//                        accountNumber = tnxReport.accountNumber
//                    )
//                }
//            logger.info("Data:     $transactions")
//            val fileName = "daily-transaction-report-${LocalDateTime.now()}.xlsx"
//            val excelFile = excelGenerator.writeExcel(
//                fileName,
//                "Daily Transaction Report",
//                listOf(
//                    "Transaction ID",
//                    "Merchant Name",
//                    "Transaction Date",
//                    "Narration",
//                    "Balance Before",
//                    "Balance After",
//                    "Account Number"
//                ),
//                transactions
//            ) { transactionReport ->
//                listOf(
//                    transactionReport.transactionId,
//                    transactionReport.merchantName,
//                    transactionReport.transactionDate,
//                    transactionReport.narration,
//                    transactionReport.balanceBefore,
//                    transactionReport.balanceAfter,
//                    transactionReport.accountNumber,
//                )
//            }
//            val dto = MultipartMailRequestDto(
//                recipients = recipients,
//                subject = "Daily Transaction Report",
//                message = "Please find the attached transaction history report.",
//                signature = signature,
//                senderName = senderName,
//                file = excelFile
//            )
//            logger.info("Sending mail for transaction report with payload: $dto")
//            val mailResponse = mailClient.sendMailWithAttachment(fileName, dto)
//            logger.info("Mail response received: $mailResponse")
//        } catch (ex: Exception) {
//            logger.error("An error occurred", ex)
//            ex.printStackTrace();
//        }
//    }
//}