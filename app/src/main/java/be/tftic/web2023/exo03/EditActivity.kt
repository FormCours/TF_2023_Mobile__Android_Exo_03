package be.tftic.web2023.exo03

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import be.tftic.web2023.exo03.databinding.ActivityEditBinding

class EditActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_FILENAME = "file_name"
        const val EXTRA_NEW_FILE = "file_new"
    }

    private lateinit var binding : ActivityEditBinding

    private lateinit var filename : String
    private var isNewFile : Boolean = false
    private var isModify : Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Récuperation les infos de l'intent
        filename = intent.getStringExtra(EXTRA_FILENAME)!!
        isNewFile = intent.getBooleanExtra(EXTRA_NEW_FILE, false)

        // Update Views
        binding.tvEditFilename.setText(filename)
        if(!isNewFile) {
            displayContentFile()
        }

        // Listener
        binding.btnEditSave.setOnClickListener { saveEdit() }
        binding.btnEditCancel.setOnClickListener { cancelEdit() }

        binding.etEditContent.addTextChangedListener { text: Editable? ->
            isModify = true
        }

        // - Back press
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                backPressedCallback()
            }
        })
    }

    fun backPressedCallback() {
        if(isModify) {
            showLostDataDialog()
            return
        }

        finish()
    }

    private fun showLostDataDialog() {
        // Builder pour créer une dialog
        val dialogBuilder = AlertDialog.Builder(this).apply {
            // Définition du titre et du message
            setTitle(R.string.dialog_lost_data_title)
            setMessage(R.string.dialog_lost_data_message)

            // Définition des boutons d'action
            setPositiveButton(android.R.string.ok) { dialog, id ->
                // Retour sur l'activité principal
                finish()
            }
            setNegativeButton(android.R.string.cancel) { dialog, id -> }
            setNeutralButton(R.string.dialog_btn_save) { dialog, id ->
                // Save et retour sur l'activité principal
                saveEdit()
                finish()
            }

            // Force l'utilisation des boutons pour valider la dialog
            setCancelable(false)
        }

        dialogBuilder.create().show()
    }

    private fun displayContentFile() {
        openFileInput(filename).bufferedReader().useLines { lines ->

            // Convertir le resultat du bufferedReader en une seul chaine de caracteres
            val content : String = lines.joinToString("\n")

            // Afficher le resultat
            binding.etEditContent.setText(content)
        }

    }

    private fun saveEdit() {
        // Récuperation du contenu
        val content : String =  binding.etEditContent.text.toString()

        // Sauvegarde de contenu
        openFileOutput(filename, MODE_PRIVATE).use { fos ->
            fos.write(content.toByteArray())
        }

        // Prévenir l'utilisateur que c'est save
        Toast.makeText(this, R.string.toast_file_save, Toast.LENGTH_SHORT).show()


        isModify = false
    }

    private fun cancelEdit() {
        if(isNewFile) {
            binding.etEditContent.text.clear()
        }
        else {
            displayContentFile()
        }
    }



}


