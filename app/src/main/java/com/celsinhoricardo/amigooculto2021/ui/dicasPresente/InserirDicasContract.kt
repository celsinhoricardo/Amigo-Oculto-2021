package com.celsinhoricardo.amigooculto2021.ui.dicasPresente

import android.widget.AdapterView
import com.celsinhoricardo.amigooculto2021.model.Usuario

interface InserirDicasContract {
    interface Presenter {
        fun getMyUser()
        fun salvarDicas(dica1: String?, dica2:String?, dica3:String?)
        fun getAndroidId() : String
    }
    interface View {
        fun bindDica1(dica1: String?)
        fun bindDica2(dica2:String?)
        fun bindDica3(dica3: String?)
        fun bindMyName(name: String)
        fun showError()
        fun showSuccess()

    }

}