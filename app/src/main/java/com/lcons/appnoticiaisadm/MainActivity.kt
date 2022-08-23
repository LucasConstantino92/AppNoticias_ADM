package com.lcons.appnoticiaisadm

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.lcons.appnoticiaisadm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        publicarNoticia()
    }

    private fun publicarNoticia() {
        binding.BtnPublicar.setOnClickListener {
            val titulo = binding.EdTitulo.text.toString()
            val noticia = binding.EdNoticia.text.toString()
            val data = binding.EdData.text.toString()
            val autor = binding.EdAutor.text.toString()

            if (titulo.isEmpty() || noticia.isEmpty()
                || data.isEmpty() || autor.isEmpty()
            ) {
                Toast.makeText(
                    this, "Preencha todos os campos",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                salvarNoticias(titulo, noticia, data, autor)
            }
        }
    }

    private fun salvarNoticias(
        titulo: String, noticia: String,
        data: String, autor: String
    ) {

        val mapnoticias = hashMapOf(
            "titulo" to titulo,
            "noticia" to noticia,
            "data" to data,
            "autor" to autor
        )

        db.collection("noticias").document("noticia")
            .set(mapnoticias).addOnCompleteListener{tarefa ->
                if (tarefa.isSuccessful){
                    Toast.makeText(this, "Noticia salva com sucesso",
                    Toast.LENGTH_SHORT).show()

                    limparCampos()
                }
            }
    }

    private fun limparCampos(){
        binding.EdTitulo.text.clear()
        binding.EdNoticia.text.clear()
        binding.EdData.text.clear()
        binding.EdAutor.text.clear()
    }
}