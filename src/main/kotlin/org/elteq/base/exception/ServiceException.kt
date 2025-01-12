package org.elteq.base.exception

class ServiceException(override val message: String?, val code: Int, override val cause: Throwable?) :
    RuntimeException(message, cause) {
    constructor(message: String?) : this(message, -5, null)

    constructor(code: Int, message: String?) : this(message, code, null)

    constructor(code: Int, message: String?, cause: Throwable?) : this(message, code, cause)

    constructor(cause: Throwable?) : this(cause?.toString(), 500, cause)

    constructor() : this("We're fucked", 500, null)

}