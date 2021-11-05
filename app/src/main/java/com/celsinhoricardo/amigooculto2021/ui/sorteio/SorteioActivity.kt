package com.celsinhoricardo.amigooculto2021.ui.sorteio

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.celsinhoricardo.amigooculto2021.R
import com.celsinhoricardo.amigooculto2021.databinding.ActivitySorteioBinding
import com.celsinhoricardo.amigooculto2021.ui.dicasPresente.InserirDicasActivity


class SorteioActivity : AppCompatActivity(), SorteioContract.View {
    private lateinit var binding: ActivitySorteioBinding
    private lateinit var presenter: SorteioPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sorteio)
        presenter = SorteioPresenter(this, this)
        configBtnReveal()
        setSupportActionBar(binding.toolbar)

    }
    override fun bindDica1(dica1: String?) {
        binding.txtDica1.setText(dica1)
    }

    override fun bindDica2(dica2: String?) {
        binding.txtDica2.setText(dica2)
    }

    override fun bindDica3(dica3: String?) {
        binding.txtDica3.setText(dica3)
    }

    override fun showAwaitView() {
        val alertDialog: AlertDialog? = this@SorteioActivity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Seja bem vindo(a), o sorteio ainda não esta disponível")
            builder.setMessage("Mas não se preocupe, você receberá uma notificação no seu celular assim que a função de sortear estiver disponível.\n" +
                    "Até breve!")
            builder.apply {
                setPositiveButton("Ok",
                    DialogInterface.OnClickListener { dialog, id ->
                        finish()
                    })
            }
            builder.create()
            builder.show()
        }
    }

    override fun showDicasView() {
        binding.dicasView.visibility = View.VISIBLE
    }

    override fun hideDicasView() {
        binding.dicasView.visibility = View.GONE
    }

    override fun bindDicasTitle(nome: String) {
        binding.txtDicaTitle.text = "Dicas de presente para $nome:"
    }

    override fun bindTxtInfo(text: String) {
        binding.txtInfo.text = text
    }

    override fun showSortButton() {
        binding.btnSortear.visibility = View.VISIBLE
        binding.btnSortear.setOnClickListener { presenter.getAllUsersToSort() }
    }
    override fun hideSortButton() {
        binding.btnSortear.visibility = View.INVISIBLE
    }

    override fun bindMyName(name: String) {
        binding.welcomeText.text = "Olá $name,"
    }

    override fun showSortUser(nomeSorteado: String) {
        binding.nomeSorteado.visibility = View.VISIBLE
        binding.nomeSorteado.text = nomeSorteado
    }

    override fun showMainView() {
        binding.llmain.visibility = View.VISIBLE
    }

    override fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }
    override fun showMainProgressBar() {
        binding.mainProgressBar.visibility = View.VISIBLE
    }
    override fun hideMainProgressBar() {
        binding.mainProgressBar.visibility = View.GONE
    }
    override fun configBtnReveal() {
        val btnReveal = binding.btnReveal
        val nomeSorteado = binding.nomeSorteado
        btnReveal.setOnClickListener {
            if (nomeSorteado.visibility == View.VISIBLE) {
                btnReveal.setImageResource(R.drawable.eye)
                nomeSorteado.visibility = View.INVISIBLE
            } else {
                btnReveal.setImageResource(R.drawable.eye_off)
                nomeSorteado.visibility = View.VISIBLE
            }
        }

    }

    override fun showName() {
        binding.nomeSorteado.visibility = View.VISIBLE
    }

    override fun hideName() {
        binding.nomeSorteado.visibility = View.INVISIBLE
    }

    override fun showError() {
        val alertDialog: AlertDialog? = this@SorteioActivity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Erro")
            builder.setMessage("Ocorreu um erro na identificação do dispositivo")
            builder.apply {
                setPositiveButton("Ok",
                    DialogInterface.OnClickListener { dialog, id ->
                        finish()
                    })
            }
            builder.create()
            builder.show()
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.getItemId()) {

            R.id.action_settings -> {
                startActivity(Intent(this@SorteioActivity,InserirDicasActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}