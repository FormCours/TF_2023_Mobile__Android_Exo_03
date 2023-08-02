package be.tftic.web2023.exo03.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import be.tftic.web2023.exo03.R
import be.tftic.web2023.exo03.models.FileNote
import java.time.format.DateTimeFormatter

class FileNoteAdapter(val context: Context, val notes: List<FileNote>) :
    RecyclerView.Adapter<FileNoteAdapter.FileNoteViewHolder>() {

    // region Listener "onClick" sur un element
    fun interface OnFileNoteClickListener {
        fun onNoteItemClick(fileNote: FileNote)
    }

    private var onFileNoteClickListener: OnFileNoteClickListener? = null
    fun setOnFileNoteClickListener(onFileNoteClickListener: OnFileNoteClickListener) {
        this.onFileNoteClickListener = onFileNoteClickListener
    }
    // endregion


    class FileNoteViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        private val tvFileName: TextView
        private val tvDateTime: TextView

        fun setFileName(name: String) {
            tvFileName.text = name
        }

        fun setLastUpdate(name: String) {
            tvDateTime.text = name
        }

        init {
            tvFileName = v.findViewById(R.id.tv_item_note_filename)
            tvDateTime = v.findViewById(R.id.tv_item_note_last_update)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileNoteViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_note, parent, false)
        return FileNoteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: FileNoteViewHolder, position: Int) {
        val element : FileNote = notes[position]

        // Le nom du fichier
        holder.setFileName(element.fileName)

        // La date de la derniere modif
        val formatDate = DateTimeFormatter.ofPattern(context.getString(R.string.format_date))
        val updateDate = formatDate.format(element.lastUpdate)

        holder.setLastUpdate(updateDate)

        // Set onClick sur l'element
        holder.itemView.setOnClickListener { v ->
            onFileNoteClickListener?.onNoteItemClick(element)
        }
    }


}