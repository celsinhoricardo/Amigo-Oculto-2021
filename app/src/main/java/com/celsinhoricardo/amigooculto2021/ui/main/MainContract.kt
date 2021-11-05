package com.celsinhoricardo.amigooculto2021.ui.main

import android.widget.AdapterView
import com.celsinhoricardo.amigooculto2021.model.Usuario

interface MainContract {
    interface Presenter {
        fun getAllUsers()
        fun updateAndroidId(nomeUsuario : String)
        fun OnItemSelectedListener(list: List<Usuario>?) : AdapterView.OnItemSelectedListener
        fun getAndroidId() : String
        fun redirect(list: List<Usuario>,listAll: List<Usuario>)
    }
    interface View {
        fun setUsers(list: List<Usuario>?)
        fun hideNextButton()
        fun showNextButton()
        fun hideMainView()
        fun showMainView()
        fun showProgressBar()
        fun hideProgressBar()
        fun navigateToSorteio()
        fun showError()

    }

}