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
                        doc.toObject(News::class.java)?.copy(id = doc.id)
                    }
                    trySend(newsList)
                }
            }
        awaitClose { listener.remove() }
    }

    suspend fun getNewsById(newsId: String): Result<News> {
        return try {
            val documentSnapshot = db.collection("news").document(newsId).get().await()

            if (documentSnapshot.exists()) {
                val news = documentSnapshot.toObject(News::class.java)?.copy(id = documentSnapshot.id)
                Result.success(news ?: throw Exception("Noticia no encontrada"))
            } else {
                Result.failure(Exception("Noticia no encontrada"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun addNews(news: News): Result<String> {
        return try {
            val documentReference = db.collection("news").add(
                mapOf(
                    "title" to news.title,
                    "description" to news.description
                )
            ).await()
            Result.success(documentReference.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateNews(news: News): Result<String> {
        return try {
            if (news.id.isNullOrEmpty()) {
                return Result.failure(Exception("ID de la noticia no puede estar vacío"))
            }

            db.collection("news").document(news.id!!).update(
                mapOf(
                    "title" to news.title,
                    "description" to news.description
                )
            ).await()
            Result.success("Noticia actualizada con éxito")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteNews(newsId: String): Result<String> {
        return try {
            db.collection("news").document(newsId).delete().await()
            Result.success("Noticia eliminada con éxito")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
