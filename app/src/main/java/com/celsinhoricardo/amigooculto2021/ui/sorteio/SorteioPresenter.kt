package com.celsinhoricardo.amigooculto2021.ui.sorteio

import android.content.Context
import android.os.Handler
import android.provider.Settings
import android.util.Base64
import android.util.Log
import com.celsinhoricardo.amigooculto2021.model.Usuario
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*

class SorteioPresenter(val context: Context, val view: SorteioContract.View) :
    SorteioContract.Presenter {
    private var database: DatabaseReference
    private var myUser: Usuario? = null

    init {
        database = Firebase.database.reference
        view.showMainProgressBar()
        getAwaitFlag()
    }

    override fun getMyUser() {

        database.child("Usuario").get().addOnSuccessListener {
            val children = it?.children
            children?.forEach {
                it.getValue(Usuario::class.java)?.let { it1 ->
                    if (it1.android_id == getAndroidId()) {
                        myUser = it1
                        myUser?.nome.let { view.bindMyName(it!!) }

                        if (!myUser!!.amigo_secreto.isNullOrEmpty()) {
                            getAmigoSecreto(decodeString(myUser?.amigo_secreto!!))

                        } else {
                            view.bindTxtInfo("É hora de sortear o seu amigo secreto.\nClique no botão SORTEAR e revele seu amigo secreto.")
                            view.showSortButton()
                        }
                        view.hideMainProgressBar()
                        view.showMainView()
                        return@forEach
                    }
                }
            }
        }.addOnFailureListener {
            view.hideMainProgressBar()
            view.showError()
            Log.e("firebase", "Error getting data", it)
        }
    }


    override fun getAwaitFlag() {

        database.child("sorteioLiberado").get().addOnSuccessListener {

            it?.let { it1 ->
                val sorteioLiberado: Boolean = it1.getValue(Boolean::class.java)!!
                if (sorteioLiberado) {
                    getMyUser()
                } else {
                    view.hideMainProgressBar()
                    view.showAwaitView()
                }
            }
        }.addOnFailureListener {
            view.hideMainProgressBar()
            view.showAwaitView()
            Log.e("firebase", "Error getting data", it)
        }
    }

    override fun saveAmigoSecreto(usuario: Usuario) {
        val amigoSecreto = usuario.nome
        val strEncrypt = encodeString(amigoSecreto!!)
        if (myUser != null) {
            database.child("Usuario").child(myUser?.nome!!).child("amigo_secreto")
                .setValue(strEncrypt)
                .addOnSuccessListener {
                    view.hideProgressBar()
                    view.hideSortButton()
                    view.bindTxtInfo("O seu amigo oculto é:")
                    view.showSortUser(usuario.nome!!)
                }
                .addOnFailureListener {

                }
        } else {
            view.hideProgressBar()
            view.showError()
        }
    }

    override fun getAmigoSecreto(nome: String) {
        database.child("Usuario").get().addOnSuccessListener {
            val children = it?.children
            children?.forEach {
                it.getValue(Usuario::class.java)?.let { user ->
                    if(user.nome == nome){
                        view.hideSortButton()
                        view.bindTxtInfo("O seu amigo secreto é:")
                        view.showSortUser(user.nome!!)

                        user?.dicas?.let {
                            it.dica1?.let { dica1 -> view.bindDica1(dica1) }
                            it.dica2?.let { dica2 -> view.bindDica2(dica2) }
                            it.dica3?.let { dica3 -> view.bindDica3(dica3) }
                            view.bindDicasTitle(user.nome!!)
                            view.showDicasView()
                        }
                        return@forEach
                    }
                }

            }
        }.addOnFailureListener {
        }
    }

    override fun getAllUsersToSort() {
        view.showProgressBar()
        val delayedHandler = Handler()
        delayedHandler.postDelayed({

            val list: MutableList<Usuario> = mutableListOf()
            database.child("Usuario").get().addOnSuccessListener {
                val children = it?.children
                children?.forEach {
                    it.getValue(Usuario::class.java)?.let { it1 ->
                        list.add(it1)
                    }
                }
                getAvailableUsers(list)

            }.addOnFailureListener {
                Log.e("firebase", "Error getting data", it)
            }
        }, 3000)
    }

    override fun getAvailableUsers(allUsers: List<Usuario>) {
        val list: MutableList<Usuario> = mutableListOf()
        database.child("Usuario").get().addOnSuccessListener {
            val children = it?.children
            children?.forEach {
                it.getValue(Usuario::class.java)?.let { it1 ->
                    var enable = true
                    allUsers.forEach { allUser ->
                        var userDecoded: String? = null
                        val strDecoded = it1.nome!!
                        val myNameDecoded = myUser?.nome!!
                        allUser.amigo_secreto?.let { ass -> userDecoded = decodeString(ass) }
                        if (strDecoded == userDecoded || strDecoded == myNameDecoded) {
                            enable = false
                        }
                    }
                    if (enable) {
                        list.add(it1)
                    }
                }
            }
            sortUser(list)

        }.addOnFailureListener {
            Log.e("firebase", "Error getting data", it)
        }
    }

    override fun sortUser(list: List<Usuario>) {

        if (list.isNullOrEmpty()) {
            view.hideProgressBar()
            view.showError()
            return
        }
        Collections.shuffle(list)
        list[0]?.let { saveAmigoSecreto(it) }
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

    private fun encodeString(text: String): String {
        val data = text.toByteArray()
        val base64Encoded = Base64.encodeToString(data, Base64.DEFAULT)
        return base64Encoded
    }

    private fun decodeString(text: String): String {
        val data = text.toByteArray()
        val decodedBytes = Base64.decode(data, Base64.DEFAULT)
        val decodedString = String(decodedBytes)
        return decodedString
    }
}