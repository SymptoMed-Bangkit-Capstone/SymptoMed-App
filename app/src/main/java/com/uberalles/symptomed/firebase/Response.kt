package com.uberalles.symptomed.firebase

import java.lang.Exception

data class Response(
    var userId: List<User>? = null,
    var exception: Exception? = null
)
