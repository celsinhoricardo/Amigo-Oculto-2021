package com.celsinhoricardo.amigooculto2021.ui.dicasPresente

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.celsinhoricardo.amigooculto2021.R
import com.celsinhoricardo.amigooculto2021.databinding.ActivityInserirDicasBinding
import com.celsinhoricardo.amigooculto2021.ui.sorteio.SorteioActivity
import com.celsinhoricardo.amigooculto2021.model.Usuario
import com.celsinhoricardo.amigooculto2021.databinding.ActivityMainBinding
import com.onesignal.OneSignal

private lateinit var binding: ActivityInserirDicasBinding
private lateinit var presenter: InserirDicasPresenter

class InserirDicasActivity : AppCompatActivity(), InserirDicasContract.View {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_inserir_dicas)
        presenter = InserirDicasPresenter(this, this)
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener { finish() }
        binding.btnSave.setOnClickListener {
            val dica1 = binding.etDica1.text.toString()
            val dica2 = binding.etDica2.text.toString()
            val dica3 = binding.etDica3.text.toString()
            presenter.salvarDicas(dica1 ,dica2 ,dica3 ) }

    }

    override fun bindDica1(dica1: String?) {
        binding.etDica1.setText(dica1)
    }

    override fun bindDica2(dica2: String?) {
        binding.etDica2.setText(dica2)
    }

    override fun bindDica3(dica3: String?) {
        binding.etDica3.setText(dica3)
    }


    override fun bindMyName(name: String) {
        binding.welcomeText.text = "Olá $name,"
    }

    override fun showError() {
        val alertDialog: AlertDialog? = this@InserirDicasActivity?.let {
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

    override fun showSuccess() {
        val alertDialog: AlertDialog? = this@InserirDicasActivity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Sucesso")
            builder.setMessage("Suas dicas de presentes foram salvas com sucesso.")
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

}