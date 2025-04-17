package org.elteq.base.utils.email

import io.quarkus.qute.CheckedTemplate
import io.quarkus.qute.TemplateInstance
import jakarta.inject.Named

@CheckedTemplate(basePath = "HtmlEmailTemplates")
object HtmlEmailTemplates {

//    fun welcome(name: String): TemplateInstance = throw NotImplementedError()
//    fun resetPassword(password: String): TemplateInstance = throw NotImplementedError()
//    fun signUp(name: String, password: String): TemplateInstance = throw NotImplementedError()
//    fun updatePassword(name: String): TemplateInstance = throw NotImplementedError()

    @JvmStatic
    external fun welcome(name: String): TemplateInstance

    @JvmStatic
//    external fun resetPassword(@Named("password") password: String): TemplateInstance
    external fun resetPassword(password: String): TemplateInstance

    @JvmStatic
//    external fun signUp(@Named("name") name: String,
//                        @Named("password") password: String): TemplateInstance
    external fun signUp(name: String, password: String): TemplateInstance

    @JvmStatic
    external fun updatePassword(name: String): TemplateInstance
}