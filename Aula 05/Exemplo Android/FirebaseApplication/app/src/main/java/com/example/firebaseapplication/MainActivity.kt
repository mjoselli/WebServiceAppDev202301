package com.example.firebaseapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Date


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val firestore = FirebaseFirestore.getInstance()
        val data = hashMapOf(
            "name" to "Los Angeles",
            "state" to "CA",
            "country" to "USA"
        )
        firestore.collection("cities").document("LA").set(data)
        firestore.collection("comida").add(hashMapOf(
            "restaurante" to "Pizzaria Boa",
            "valor" to 10.0,
            "pagoNoCartao" to true,
            "data" to "2021-10-10",
            "numeroDePizas" to 2
        ))
        firestore.collection("comida").document("sabado").set(
            hashMapOf(
                "restaurante" to "Wikimaki",
                "valor" to 200.0,
                "pagoNoCartao" to false,
                "data" to Timestamp(Date()),
                "numeroDeTemakis" to 4
            )
        ).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d("TAG", "Deu certo wiki")
            } else {
                Log.d("TAG", "Deu errado")
            }
        }
        val docRef = firestore.collection("comida").document("sabado")
        docRef.get().addOnSuccessListener {
            Log.d("TAG", "Deu certo get")
            Log.d("TAG", it.data.toString())
        }.addOnFailureListener {
            Log.d("TAG", "Deu errado")
        }
        docRef.addSnapshotListener({ snapshot,e ->
            if (e != null) {
                Log.d("TAG", "Deu errado")
                return@addSnapshotListener
            }
            if (snapshot != null && snapshot.exists()) {
                Log.d("TAG", "Deu certo snapshot")
                Log.d("TAG", "Snapshot" + snapshot.data.toString())
            } else {
                Log.d("TAG", "Deu errado")
            }
        })
        firestore.collection("comida").document("sabado")
            .update("valor", 300.0)
        firestore.collection("cities").document("LA").delete()
        firestore.collection("comida").whereEqualTo("pagoNoCartao",false)
            .get().addOnSuccessListener {
                Log.d("TAG", "Deu certo where")
                Log.d("TAG", "where" + it.documents.toString())
            }.addOnFailureListener {
                Log.d("TAG", "Deu errado")
            }
        FirebaseFirestore.getInstance().runTransaction { transaction ->
            val docRef = FirebaseFirestore.getInstance()
                .collection("comida").document("sabado")
            val snapshot = transaction.get(docRef)
            val newValor = snapshot.getLong("numeroDeTemakis")!! + 10
            Log.d("TAG", "numeroDeTemakis {$newValor}")

            transaction.update(docRef, "numeroDeTemakis", newValor)
            null
        }.addOnSuccessListener {
            Log.d("TAG", "Deu certo transaction")
        }.addOnFailureListener {
            Log.d("TAG", "Deu errado")
        }
    }

    fun buttonCrashClicked(view: View) {
        throw RuntimeException("Test Crash") // Force a crash
    }
}