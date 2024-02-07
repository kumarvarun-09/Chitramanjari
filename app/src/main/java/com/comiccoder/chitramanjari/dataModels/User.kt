package com.comiccoder.chitramanjari.dataModels

import com.comiccoder.chitramanjari.constants.DEFAULT_USER_PROFILE_PIC

data class User(
    var image: String? = DEFAULT_USER_PROFILE_PIC,
    var name: String? = null,
    var email: String? = null,
    var id: String?= null
)


