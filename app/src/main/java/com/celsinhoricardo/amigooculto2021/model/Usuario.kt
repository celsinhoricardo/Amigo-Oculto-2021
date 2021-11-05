package com.celsinhoricardo.amigooculto2021.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Usuario(var nome: String? = null,var android_id : String? = null, var amigo_secreto: String? = null,
                   var dicas: Dicas? = null)
