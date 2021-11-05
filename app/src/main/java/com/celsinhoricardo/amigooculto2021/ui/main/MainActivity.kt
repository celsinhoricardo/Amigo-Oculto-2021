package com.celsinhoricardo.amigooculto2021.ui.main

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
import com.celsinhoricardo.amigooculto2021.ui.sorteio.SorteioActivity
import com.celsinhoricardo.amigooculto2021.model.Usuario
import com.celsinhoricardo.amigooculto2021.databinding.ActivityMainBinding
import com.onesignal.OneSignal

private lateinit var binding: ActivityMainBinding
private lateinit var presenter: MainPresenter

class MainActivity : AppCompatActivity(), MainContract.View {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        presenter = MainPresenter(this, this)

        setSupportActionBar(binding.toolbar)


        // Logging set to help debug issues, remove before releasing your app.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
        // OneSignal Initialization
        OneSignal.initWithContext(this)
        OneSignal.setAppId(resources.getString(R.string.onesignal_app_id))


    }

    override fun setUsers(list: List<Usuario>?) {
        val names: MutableList<String> = mutableListOf()
        val spinner = binding.spinnerName
        list?.forEach {
            it.nome?.let { it1 -> names.add(it1) }
        }
        val adapter = ArrayAdapter(
            this,
            R.layout.spinner_item_name, names
        )
        spinner.adapter = adapter
        spinner.onItemSelectedListener = presenter.OnItemSelectedListener(list)

        binding.btnNext.setOnClickListener { presenter.selectedItem?.nome?.let { it1 ->
            val alertDialog: AlertDialog? = this@MainActivity?.let {
                val builder = AlertDialog.Builder(it)
                builder.setMessage("Confirma que você é $it1 ?")
                builder.apply {
                    setPositiveButton("Sim",
                        DialogInterface.OnClickListener { dialog, id ->
                            presenter.updateAndroidId(it1)
                        })
                    setNegativeButton("Não",
                        DialogInterface.OnClickListener { dialog, id ->
                        })
                }
                builder.create()
                builder.show()
            }

        } }

    }

    override fun navigateToSorteio() {
        val intent = Intent(this@MainActivity, SorteioActivity::class.java)
        //intent.putExtra(EXTRA_USER, presenter.myAndroidId)
        startActivity(intent)
        finish()
    }

    override fun hideNextButton() {
        binding.btnNext.visibility = View.GONE
    }

    override fun showNextButton() {
        binding.btnNext.visibility = View.VISIBLE
    }

    override fun hideMainView() {
        binding.llmain.visibility = View.GONE
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
    override fun showError() {
        val alertDialog: AlertDialog? = this@MainActivity?.let {
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

    companion object{
        val EXTRA_USER = "EXTRA_USER"
    }
}