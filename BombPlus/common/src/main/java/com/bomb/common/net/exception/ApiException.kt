package com.bomb.common.net.exception


class ApiException : Exception {

    var code: Int
    var errMsg: String
    var errDetail: String? = null

    constructor(error: Error, e: Throwable? = null) : super(e) {
        code = error.code
        errMsg = error.errorMsg
        errDetail = e?.message
    }

    constructor(code: Int, msg: String, e: Throwable? = null) : super(e) {
        this.code = code
        this.errMsg = msg
        errDetail = e?.message
    }

}