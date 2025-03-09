package es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.remote.datasources

import com.google.firebase.firestore.FirebaseFirestore
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.News
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class NewsDataSource(private val db: FirebaseFirestore) {

    fun getNews(): Flow<List<News>> = callbackFlow {
        val listener = db.collection("news")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val newsList = snapshot.documents.mapNotNull { doc ->
                        doc.toObject(News::class.java)
                    }
                    trySend(newsList)
                }
            }
        awaitClose { listener.remove() }
    }

    suspend fun addNews(news: News): Result<String> {
        return try {
            val documentReference = db.collection("news").add(news).await()

            Result.success("Noticia añadida con éxito: ${documentReference.id}")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
