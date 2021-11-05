package com.celsinhoricardo.amigooculto2021.ui.sorteio

import android.widget.Button
import com.celsinhoricardo.amigooculto2021.model.Usuario

interface SorteioContract {
    interface Presenter {
        fun getAllUsersToSort()
        fun getAvailableUsers(allUsers: List<Usuario>)
        fun getMyUser()
        fun sortUser(list: List<Usuario>)
        fun getAndroidId() : String
        fun getAwaitFlag()
        fun saveAmigoSecreto(usuario: Usuario)
        fun getAmigoSecreto(nome: String)

    }
    interface View {
        fun showMainView()
        fun showAwaitView()
        fun showDicasView()
        fun hideDicasView()
        fun bindDica1(dica1: String?)
        fun bindDica2(dica2:String?)
        fun bindDica3(dica3: String?)
        fun bindDicasTitle(nome: String)
        fun showSortButton()
        fun hideSortButton()
        fun showSortUser(nomeSorteado: String)
        fun bindMyName(name: String)
        fun showError()
        fun showName()
        fun hideName()
        fun bindTxtInfo(text : String)
        fun configBtnReveal()
        fun showProgressBar()
        fun hideProgressBar()
        fun showMainProgressBar()
        fun hideMainProgressBar()
    }

}