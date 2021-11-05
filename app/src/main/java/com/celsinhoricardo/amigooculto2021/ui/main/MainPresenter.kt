package com.celsinhoricardo.amigooculto2021.ui.main

import android.content.Context
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.AdapterView
import com.celsinhoricardo.amigooculto2021.model.Usuario
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainPresenter(val context: Context, val view: MainContract.View) : MainContract.Presenter {
    private var database: DatabaseReference
    var selectedItem: Usuario? = null

    init {
        database = Firebase.database.reference
        view.showProgressBar()
        getAllUsers()
    }

    override fun getAllUsers() {

        database.child("Usuario").get().addOnSuccessListener {
            val list: MutableList<Usuario> = mutableListOf()
            val listAll: MutableList<Usuario> = mutableListOf()
            val children = it?.children
            children?.forEach {
                it.getValue(Usuario::class.java)?.let { it1 ->
                    listAll.add(it1)
                    if(it1.android_id.isNullOrEmpty()) {
                        list.add(it1)
                    }
                }
            }

            redirect(list, listAll)
        }.addOnFailureListener {
            Log.e("firebase", "Error getting data", it)
        }
    }

    override fun redirect(list: List<Usuario>, listAll :List<Usuario>) {
        var newUser = true
        val androidId = getAndroidId()
        listAll.forEach {
            if (it.android_id == androidId && !androidId.isNullOrEmpty()) {
                newUser = false
            }
        }
        if (newUser) {
            view.hideProgressBar()
            view.showMainView()
            view.setUsers(list)
        } else {
            view.hideProgressBar()
            view.navigateToSorteio()
        }

    }

    override fun updateAndroidId(nomeUsuario: String) {
        val androidId = getAndroidId()
        if (!androidId.isNullOrEmpty()) {
            database.child("Usuario").child(nomeUsuario).child("android_id").setValue(androidId)
                .addOnSuccessListener {
                    view.navigateToSorteio()
                }
                .addOnFailureListener {

                }
        } else {
            view.showError()
        }
    }

    override fun OnItemSelectedListener(list: List<Usuario>?): AdapterView.OnItemSelectedListener {

        return object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, vieww: View?, pos: Int, id: Long) {
                view.showNextButton()
                selectedItem = list?.get(pos)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }

    override fun getAndroidId(): String {
        val android_id = Settings.Secure.getString(
            context.getContentResolver(),
            Settings.Secure.ANDROID_ID
        )
        if (android_id == null) {
            return ""
        }
        return android_id
    }
}