package org.elteq.base.utils.email

import io.quarkus.qute.CheckedTemplate
import io.quarkus.qute.TemplateInstance

@CheckedTemplate
object EmailTemplates {
    fun welcome(name: String): TemplateInstance = throw NotImplementedError()
    fun resetPassword(password: String): TemplateInstance = throw NotImplementedError()
    fun updatePassword(password: String, newPassword: String): TemplateInstance = throw NotImplementedError()
}