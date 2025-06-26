package com.example.suraachat.milad.data.repository

import com.example.suraachat.milad.data.local.MessageDao
import com.example.suraachat.milad.data.local.MessageEntity
import com.example.suraachat.milad.data.network.ApiService
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class MessageRepository(
private val api: ApiService,
private val dao: MessageDao
) {
suspend fun fetchMessages(user2: String) {
val messages = api.getMessages(user2)
dao.insertMessages(messages)
}

fun getMessagesFromRoom(user1: String, user2: String): Flow<List<MessageEntity>> {  
    return dao.getChatMessages(user1, user2)  
}  

suspend fun sendMessage(receiverId: String, text: String?, audioFile: File?) {  
    val receiverPart = receiverId.toRequestBody("text/plain".toMediaType())  
    val textPart = text?.toRequestBody("text/plain".toMediaType())  

    val audioPart = audioFile?.let {  
        val reqFile = it.asRequestBody("audio/*".toMediaType())  
        MultipartBody.Part.createFormData("audio", it.name, reqFile)  
    }  

    val message = api.sendMessage(receiverPart, textPart, audioPart)  
    dao.insertMessages(listOf(message))  
}

}


package com.example.suraachat.milad.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.suraachat.milad.data.local.AppDatabase
import com.example.suraachat.milad.data.local.MessageEntity
import com.example.suraachat.milad.data.repository.MessageRepository
import com.example.suraachat.milad.data.network.ApiClient
import kotlinx.coroutines.launch
import java.io.File

class ChatViewModel(
application: Application,
val userId: String,
val chatWithUserId: String
) : AndroidViewModel(application) {

private val dao = AppDatabase.getDatabase(application).messageDao()  
private val repo = MessageRepository(ApiClient.getApiService(getApplication()), dao)  

val messages: LiveData<List<MessageEntity>> =  
    repo.getMessagesFromRoom(userId, chatWithUserId).asLiveData()  

fun fetchMessages() {  
    viewModelScope.launch { repo.fetchMessages(chatWithUserId) }  
}  

fun sendMessage(text: String?, audioFile: File?) {  
    viewModelScope.launch { repo.sendMessage(chatWithUserId, text, audioFile) }  
}

}
package com.example.suraachat.milad.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ChatViewModelFactory(
private val application: Application,
private val userId: String,
private val chatWithUserId: String
) : ViewModelProvider.Factory {
override fun <T : ViewModel> create(modelClass: Class<T>): T {
return ChatViewModel(application, userId, chatWithUserId) as T
}
}

