package be.tftic.web2023.exo03

import android.content.Intent
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import be.tftic.web2023.exo03.adapters.FileNoteAdapter
import be.tftic.web2023.exo03.databinding.ActivityMainBinding
import be.tftic.web2023.exo03.models.FileNote
import java.io.File
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var fileNotes : MutableList<FileNote>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setCustomTitleColor()

        // Charger la liste des "FileNote"
        fileNotes = mutableListOf()

        // RecylcerView
        val adapter = FileNoteAdapter(this, fileNotes)

        binding.rvMainFiles.adapter = adapter
        binding.rvMainFiles.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )

        // Listener
        // - Click sur le bouton "create"
        binding.btnMainFilenameCreate.setOnClickListener{ openEditOnNewFile() }

        // - Click d'un element de la RecyclerView
        adapter.setOnFileNoteClickListener { fileNote ->
            openEditOnExistingFile(fileNote)
        }
    }

    override fun onStart() {
        super.onStart()

        fileNotes.clear()
        fileNotes.addAll(getFileNodes())

        binding.rvMainFiles.adapter?.notifyDataSetChanged()
    }

    private fun getFileNodes(): List<FileNote> {

        val result : MutableList<FileNote> = mutableListOf()

        for(filename in fileList()) {
            val file = File(filesDir, filename)

            val note = FileNote(
                file.name,
                LocalDateTime.ofInstant(Instant.ofEpochMilli(file.lastModified()), ZoneId.systemDefault())
            )

            result.add(note)
        }

        return result.toList()
    }

    private fun openEditOnNewFile() {
        // Récuperation du nom du fichier à créer
        val filename : String = binding.etMainFilenameNew.text.toString()

        // Check si le nom du fichier est valid
        if(filename.isBlank()) {
            showErrorDialog(R.string.dialog_file_invalid)
            return
        }

        // Check si le fichier exists
        if(fileList().contains(filename)) {
            showErrorDialog(R.string.dialog_exist_file)
            return
        }

        // Clear du form
        binding.etMainFilenameNew.text.clear()
        binding.etMainFilenameNew.clearFocus()

        // Navigation
        val intentEdit : Intent = Intent(this, EditActivity::class.java).apply {
            putExtra(EditActivity.EXTRA_NEW_FILE, true)
            putExtra(EditActivity.EXTRA_FILENAME, filename)
        }
        startActivity(intentEdit)
    }

    private fun openEditOnExistingFile(fileNote: FileNote) {

        val intentEdit : Intent = Intent(this, EditActivity::class.java).apply {
            putExtra(EditActivity.EXTRA_FILENAME, fileNote.fileName)
            putExtra(EditActivity.EXTRA_NEW_FILE, false)
        }

        startActivity(intentEdit)
    }

    private fun showErrorDialog(@StringRes messageId: Int) {
        showErrorDialog(getString(messageId))
    }
    private fun showErrorDialog(message: String) {
        // Création d'une AlertDialog
        val builderDialog = AlertDialog.Builder(this).apply {
            setMessage(message)
            setPositiveButton(android.R.string.ok) { dialog, id ->
                // Code necessaire si on souhaite avoir le bouton "ok"
            }
            setCancelable(false)
        }
       val dialog : AlertDialog = builderDialog.create()

        // Affiche l'alert
        dialog.show()
    }

    private fun setCustomTitleColor() {
        // Mise en couleur du titre avec un dégradé
        val width = binding.titleApp.paint.measureText(binding.titleApp.text.toString())
        val height = binding.titleApp.textSize

        val textShader: Shader = LinearGradient(
            0f,  1f, width, height,
            intArrayOf(
                Color.parseColor("#FF0000"),
                Color.parseColor("#FF12FB"),
                Color.parseColor("#1DFFEC"),
                Color.parseColor("#00ff00"),
                Color.parseColor("#FFFFFB")
            ), null, Shader.TileMode.CLAMP)

        binding.titleApp.paint.shader = textShader
    }
}