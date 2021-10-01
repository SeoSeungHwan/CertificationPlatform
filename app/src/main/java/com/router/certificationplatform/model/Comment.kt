package com.router.certificationplatform.model

import java.sql.Timestamp

data class Comment(
    val commentId : String="",
    val userId: String="",
    val nickname : String="",
    val contents: String="",
    val time : String=""
)
