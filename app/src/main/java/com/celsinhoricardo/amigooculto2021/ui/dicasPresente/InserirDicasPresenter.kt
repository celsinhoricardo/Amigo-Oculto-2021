package com.celsinhoricardo.amigooculto2021.ui.dicasPresente

import android.content.Context
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.AdapterView
import com.celsinhoricardo.amigooculto2021.model.Usuario
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class InserirDicasPresenter(val context: Context, val view: InserirDicasContract.View) : InserirDicasContract.Presenter {
    private var database: DatabaseReference
    private var myUser: Usuario? = null


    init {
        database = Firebase.database.reference
        getMyUser()

    }

        override fun getMyUser() {

            database.child("Usuario").get().addOnSuccessListener {
                val children = it?.children
                children?.forEach {
                    it.getValue(Usuario::class.java)?.let { it1 ->
                        if (it1.android_id == getAndroidId()) {
                            myUser = it1
                            view.bindMyName(myUser?.nome!!)
                            myUser?.dicas?.let {
                                it.dica1?.let { dica1 -> view.bindDica1(dica1) }
                                it.dica2?.let { dica2 -> view.bindDica2(dica2) }
                                it.dica3?.let { dica3 -> view.bindDica3(dica3) }

                            }
                        }
                    }
                }
            }.addOnFailureListener {
                view.showError()
                Log.e("firebase", "Error getting data", it)
            }
        }


    override fun salvarDicas(dica1: String?, dica2:String?, dica3:String?) {

        if (dica1 != null) {
            database.child("Usuario").child(myUser?.nome!!).child("dicas").child("dica1").setValue(dica1)
                .addOnSuccessListener {
                    salvarDica2(dica2,dica3)
                }
                .addOnFailureListener {
                    view.showError()
                }
        } else {
            salvarDica2(dica2,dica3)

        }
    }

    fun salvarDica2(dica2:String?, dica3:String?){
        if (dica2 != null) {
            database.child("Usuario").child(myUser?.nome!!).child("dicas").child("dica2").setValue(dica2)
                .addOnSuccessListener {
                    salvarDica3(dica3)
                }
                .addOnFailureListener {
                    view.showError()
                }
        } else {
            salvarDica3(dica3)

        }
    }
    fun salvarDica3(dica3:String?){
        if (dica3 != null) {
            database.child("Usuario").child(myUser?.nome!!).child("dicas").child("dica3").setValue(dica3)
                .addOnSuccessListener {
                    view.showSuccess()
                }
                .addOnFailureListener {
                    view.showError()
                }
        } else {
            view.showSuccess()
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