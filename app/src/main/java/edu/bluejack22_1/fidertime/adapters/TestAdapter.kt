package edu.bluejack22_1.fidertime.adapters

import FirestoreAdapter
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import edu.bluejack22_1.fidertime.R
import edu.bluejack22_1.fidertime.common.*
import edu.bluejack22_1.fidertime.databinding.FragmentChatFileItemInBinding
import edu.bluejack22_1.fidertime.databinding.FragmentChatFileItemOutBinding
import edu.bluejack22_1.fidertime.databinding.FragmentChatImageItemInBinding
import edu.bluejack22_1.fidertime.databinding.FragmentChatImageItemOutBinding
import edu.bluejack22_1.fidertime.databinding.FragmentChatItemInBinding
import edu.bluejack22_1.fidertime.databinding.FragmentChatItemOutBinding
import edu.bluejack22_1.fidertime.databinding.FragmentChatVideoItemInBinding
import edu.bluejack22_1.fidertime.databinding.FragmentChatVideoItemOutBinding
import edu.bluejack22_1.fidertime.databinding.FragmentChatVoiceItemInBinding
import edu.bluejack22_1.fidertime.databinding.FragmentChatVoiceItemOutBinding
import edu.bluejack22_1.fidertime.models.*
import java.text.DecimalFormat

class TestAdapter(
    private val messageType: String,
    private val chats : ArrayList<Chat>,
    private val context: Context,
    private val notificationStatus : Boolean
    )  : RecyclerView.Adapter<TestAdapter.ViewHolder>() {

    val CHAT_IN = 1
    val CHAT_OUT = 2
    val CHAT_IN_IMAGE = 3
    val CHAT_OUT_IMAGE = 4
    val CHAT_IN_VIDEO = 5
    val CHAT_OUT_VIDEO = 6
    val CHAT_IN_FILE = 7
    val CHAT_OUT_FILE = 8
    val CHAT_IN_VOICE = 9
    val CHAT_OUT_VOICE = 10

    private val userId = Firebase.auth.currentUser!!.uid

//    override fun onDocumentAdded(change: DocumentChange) {
//        super.onDocumentAdded(change)
//
//        val chat = change.document.toObject<Chat>()
//        var message = ""
//        message = if (chat.chatType == "text") {
//            chat.chatText
//        } else {
//            if (chat.chatType == "image") {
//                "Sent an image"
//            } else {
//                "Sent a " + chat.chatType
//            }
//        }
//
//        NotificationHelper.createNotification(context, "You have a new message", message)
//    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        if (viewType == CHAT_OUT) {
            val binding = FragmentChatItemOutBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
            return ChatOutViewHolder(binding, binding.root, messageType)
        }
        if (viewType == CHAT_IN) {
            val binding = FragmentChatItemInBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
            return ChatInViewHolder(binding, binding.root)
        }
        if (viewType == CHAT_OUT_IMAGE) {
            val binding = FragmentChatImageItemOutBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
            return ChatImageOutViewHolder(binding, binding.root, messageType)
        }
        if (viewType == CHAT_IN_IMAGE) {
            val binding = FragmentChatImageItemInBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
            return ChatImageInViewHolder(binding, binding.root)
        }
        if (viewType == CHAT_OUT_VIDEO) {
            val binding = FragmentChatVideoItemOutBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
            return ChatVideoOutViewHolder(binding, binding.root, messageType)
        }
        if (viewType == CHAT_IN_VIDEO) {
            val binding = FragmentChatVideoItemInBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
            return ChatVideoInViewHolder(binding, binding.root)
        }
        if (viewType == CHAT_OUT_FILE) {
            val binding = FragmentChatFileItemOutBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
            return ChatFileOutViewHolder(binding, binding.root, messageType)
        }
        if (viewType == CHAT_IN_FILE) {
            val binding = FragmentChatFileItemInBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
            return ChatFileInViewHolder(binding, binding.root)
        }
        if (viewType == CHAT_OUT_VOICE) {
            val binding = FragmentChatVoiceItemOutBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
            return ChatVoiceOutViewHolder(binding, binding.root, messageType)
        }
        if (viewType == CHAT_IN_VOICE) {
            val binding = FragmentChatVoiceItemInBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
            return ChatVoiceInViewHolder(binding, binding.root)
        }
        val binding = FragmentChatImageItemInBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ChatImageInViewHolder(binding, binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (getItemViewType(position) == CHAT_OUT) {
            val chat = chats[position]
            (holder as ChatOutViewHolder).bind(chat)
        }
        else if (getItemViewType(position) == CHAT_IN) {
            val chat = chats[position]
            (holder as ChatInViewHolder).bind(chat)
        }
        else if (getItemViewType(position) == CHAT_OUT_IMAGE) {
            val chat = chats[position]
            (holder as ChatImageOutViewHolder).bind(chat)
        }
        else if (getItemViewType(position) == CHAT_IN_IMAGE) {
            val chat = chats[position]
            (holder as ChatImageInViewHolder).bind(chat)
        }
        else if (getItemViewType(position) == CHAT_OUT_VIDEO) {
            val chat = chats[position]
            (holder as ChatVideoOutViewHolder).bind(chat)
        }
        else if (getItemViewType(position) == CHAT_IN_VIDEO) {
            val chat = chats[position]
            (holder as ChatVideoInViewHolder).bind(chat)
        }
        else if (getItemViewType(position) == CHAT_OUT_FILE) {
            val chat = chats[position]
            (holder as ChatFileOutViewHolder).bind(chat)
        }
        else if (getItemViewType(position) == CHAT_IN_FILE) {
            val chat = chats[position]
            (holder as ChatFileInViewHolder).bind(chat)
        }
        else if (getItemViewType(position) == CHAT_OUT_VOICE) {
            val chat = chats[position]
            (holder as ChatVoiceOutViewHolder).bind(chat)
        }
        else if (getItemViewType(position) == CHAT_IN_VOICE) {
            val chat = chats[position]
            (holder as ChatVoiceInViewHolder).bind(chat)
        }
    }

    private fun getChatType(position: Int): TypeEnum {
        return when(chats[position].chatType) {
            "text" -> TypeEnum.TEXT
            "image" -> TypeEnum.IMAGE
            "video" -> TypeEnum.VIDEO
            "file" -> TypeEnum.FILE
            "voice" -> TypeEnum.VOICE
            else -> TypeEnum.TEXT
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (chats[position].senderUserId == userId && getChatType(position) == TypeEnum.VOICE) {
            return CHAT_OUT_VOICE
        }
        if (chats[position].senderUserId == userId && getChatType(position) == TypeEnum.FILE) {
            return CHAT_OUT_FILE
        }
        if (chats[position].senderUserId == userId && getChatType(position) == TypeEnum.VIDEO) {
            return CHAT_OUT_VIDEO
        }
        if (chats[position].senderUserId == userId && getChatType(position) == TypeEnum.IMAGE) {
            return CHAT_OUT_IMAGE
        }
        if (chats[position].senderUserId == userId && getChatType(position) == TypeEnum.TEXT) {
            return CHAT_OUT
        }
        if (chats[position].senderUserId != userId && getChatType(position) == TypeEnum.VOICE) {
            return CHAT_IN_VOICE
        }
        if (chats[position].senderUserId != userId && getChatType(position) == TypeEnum.FILE) {
            return CHAT_IN_FILE
        }
        if (chats[position].senderUserId != userId && getChatType(position) == TypeEnum.VIDEO) {
            return CHAT_IN_VIDEO
        }
        if (chats[position].senderUserId != userId && getChatType(position) == TypeEnum.IMAGE) {
            return CHAT_IN_IMAGE
        }
        if (chats[position].senderUserId != userId && getChatType(position) == TypeEnum.TEXT) {
            return CHAT_IN
        }
        return CHAT_IN
    }

    open class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        open fun bind(chat: Chat) {}
    }

    class ChatInViewHolder(private val binding: FragmentChatItemInBinding, itemView: View) : ViewHolder(
        itemView
    ) {
        override fun bind(chat: Chat) {
            RichTextHelper.linkAndMentionRecognizer(itemView.context, binding.textViewChat, chat.chatText)
            binding.textViewTimestamp.text = chat.timestamp?.toDate()
                ?.let { RelativeDateAdapter(it).getHourMinuteFormat() }
            FirebaseQueries.subscribeToUser(chat.senderUserId) {
                binding.textViewName.text = it.name
                if(it.profileImageUrl != ""){
                    binding.imageViewProfile.load(it.profileImageUrl)
                }else{
                    binding.imageViewProfile.setBackgroundResource(R.drawable.default_avatar)
                }
            }
        }
    }

    class ChatOutViewHolder(private val binding: FragmentChatItemOutBinding, itemView: View, private val messageType: String) : ViewHolder(
        itemView
    ) {
        override fun bind(chat: Chat) {
            RichTextHelper.linkAndMentionRecognizer(itemView.context, binding.textViewChat, chat.chatText)
            FirebaseQueries.subscribeToMemberLastVisit(chat.messageId, chat.timestamp!!) { totalReadBy ->
                if (totalReadBy == 0) {
                    binding.textViewReadBy.text = ""
                }
                else {
                    if (messageType == "personal") {
                        binding.textViewReadBy.text = "Read"
                    }
                    else {
                        binding.textViewReadBy.text = "Read By $totalReadBy"
                    }
                }
            }
            binding.textViewTimestamp.text = chat.timestamp?.toDate()
                ?.let { RelativeDateAdapter(it).getHourMinuteFormat() }
            FirebaseQueries.subscribeToUser(chat.senderUserId) {
                if(it.profileImageUrl != ""){
                    binding.imageViewProfile.load(it.profileImageUrl)
                }else{
                    binding.imageViewProfile.setBackgroundResource(R.drawable.default_avatar)
                }
            }
        }
    }

    class ChatImageInViewHolder(private val binding: FragmentChatImageItemInBinding, itemView: View) : ViewHolder(
        itemView
    ) {
        override fun bind(chat: Chat) {
            binding.imageViewChat.load(chat.mediaUrl) {
                crossfade(true)
                crossfade(300)
                placeholder(R.drawable.image_placeholder)
            }
            binding.textViewTimestamp.text = chat.timestamp?.toDate()
                ?.let { RelativeDateAdapter(it).getHourMinuteFormat() }
            FirebaseQueries.subscribeToUser(chat.senderUserId) {
                binding.textViewName.text = it.name
                if(it.profileImageUrl != ""){
                    binding.imageViewProfile.load(it.profileImageUrl)
                }else{
                    binding.imageViewProfile.setBackgroundResource(R.drawable.default_avatar)
                }
            }
        }
    }

    class ChatImageOutViewHolder(private val binding: FragmentChatImageItemOutBinding, itemView: View, private val messageType: String) : ViewHolder(
        itemView
    ) {
        override fun bind(chat: Chat) {
            binding.imageViewChat.load(chat.mediaUrl) {
                crossfade(true)
                crossfade(300)
                placeholder(R.drawable.image_placeholder)
            }
            FirebaseQueries.subscribeToMemberLastVisit(chat.messageId, chat.timestamp!!) { totalReadBy ->
                if (totalReadBy == 0) {
                    binding.textViewReadBy.text = ""
                }
                else {
                    if (messageType == "personal") {
                        binding.textViewReadBy.text = itemView.context.getString(R.string.read)
                    }
                    else {
                        binding.textViewReadBy.text = itemView.context.getString(R.string.readBy) + " $totalReadBy"
                    }
                }
            }
            binding.textViewTimestamp.text = chat.timestamp?.toDate()
                ?.let { RelativeDateAdapter(it).getHourMinuteFormat() }
            FirebaseQueries.subscribeToUser(chat.senderUserId) {
                if(it.profileImageUrl != ""){
                    binding.imageViewProfile.load(it.profileImageUrl)
                }else{
                    binding.imageViewProfile.setBackgroundResource(R.drawable.default_avatar)
                }
            }
        }
    }

    class ChatVideoInViewHolder(private val binding: FragmentChatVideoItemInBinding, itemView: View) : ViewHolder(
        itemView
    ) {
        override fun bind(chat: Chat) {
            val uri = Uri.parse(chat.mediaUrl)
            Glide.with(itemView.context)
                .asBitmap()
                .load(uri)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?
                    ) {
                        binding.imageViewChat.setImageBitmap(resource)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                    }
                })

            binding.textViewTimestamp.text = chat.timestamp?.toDate()
                ?.let { RelativeDateAdapter(it).getHourMinuteFormat() }
            FirebaseQueries.subscribeToUser(chat.senderUserId) {
                binding.textViewName.text = it.name
                if(it.profileImageUrl != ""){
                    binding.imageViewProfile.load(it.profileImageUrl)
                }else{
                    binding.imageViewProfile.setBackgroundResource(R.drawable.default_avatar)
                }
            }
        }
    }

    class ChatVideoOutViewHolder(private val binding: FragmentChatVideoItemOutBinding, itemView: View, private val messageType: String) : ViewHolder(
        itemView
    ) {
        override fun bind(chat: Chat) {

            val uri = Uri.parse(chat.mediaUrl)
            Glide.with(itemView.context)
                .asBitmap()
                .load(uri)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?
                    ) {
                        binding.imageViewChat.setImageBitmap(resource)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                    }
                })

            FirebaseQueries.subscribeToMemberLastVisit(chat.messageId, chat.timestamp!!) { totalReadBy ->
                if (totalReadBy == 0) {
                    binding.textViewReadBy.text = ""
                }
                else {
                    if (messageType == "personal") {
                        binding.textViewReadBy.text = itemView.context.getString(R.string.read)
                    }
                    else {
                        binding.textViewReadBy.text = itemView.context.getString(R.string.readBy) + " $totalReadBy"
                    }
                }
            }

            binding.textViewTimestamp.text = chat.timestamp?.toDate()
                ?.let { RelativeDateAdapter(it).getHourMinuteFormat() }
            FirebaseQueries.subscribeToUser(chat.senderUserId) {
                if(it.profileImageUrl != ""){
                    binding.imageViewProfile.load(it.profileImageUrl)
                }else{
                    binding.imageViewProfile.setBackgroundResource(R.drawable.default_avatar)
                }
            }
        }
    }

    class ChatFileInViewHolder(private val binding: FragmentChatFileItemInBinding, itemView: View) : ViewHolder(
        itemView
    ) {
        override fun bind(chat: Chat) {

            val size : Double? = chat.mediaSize.toDouble()?.div(1024.0)
            val covertSizeToDecimal = DecimalFormat("#.#").format(size)
            val convertToDate = chat.timestamp?.toDate()
                ?.let { RelativeDateAdapter(it).getDateFormat() }
            val convertToTime = chat.timestamp?.toDate()
                ?.let { RelativeDateAdapter(it).getTimeFormat() }

            val resultDate = "$convertToDate at $convertToTime"
            binding.name.text = chat.mediaName
            binding.fileDescription.text = covertSizeToDecimal.toString() + " KB, " + resultDate

            binding.textViewTimestamp.text = chat.timestamp?.toDate()
                ?.let { RelativeDateAdapter(it).getHourMinuteFormat() }
            FirebaseQueries.subscribeToUser(chat.senderUserId) {
                binding.textViewName.text = it.name
                if(it.profileImageUrl != ""){
                    binding.imageViewProfile.load(it.profileImageUrl)
                }else{
                    binding.imageViewProfile.setBackgroundResource(R.drawable.default_avatar)
                }
            }
        }
    }

    class ChatFileOutViewHolder(private val binding: FragmentChatFileItemOutBinding, itemView: View, private val messageType: String) : ViewHolder(
        itemView
    ) {
        override fun bind(chat: Chat) {

            FirebaseQueries.subscribeToMemberLastVisit(chat.messageId, chat.timestamp!!) { totalReadBy ->
                if (totalReadBy == 0) {
                    binding.textViewReadBy.text = ""
                }
                else {
                    if (messageType == "personal") {
                        binding.textViewReadBy.text = itemView.context.getString(R.string.read)
                    }
                    else {
                        binding.textViewReadBy.text = itemView.context.getString(R.string.readBy) + " $totalReadBy"
                    }
                }
            }

            val size : Double? = chat.mediaSize.toDouble()?.div(1024.0)
            val covertSizeToDecimal = DecimalFormat("#.#").format(size)
            val convertToDate = chat.timestamp?.toDate()
                ?.let { RelativeDateAdapter(it).getDateFormat() }
            val convertToTime = chat.timestamp?.toDate()
                ?.let { RelativeDateAdapter(it).getTimeFormat() }

            val resultDate = "$convertToDate at $convertToTime"
            binding.name.text = chat.mediaName
            binding.fileDescription.text = covertSizeToDecimal.toString() + " KB, " + resultDate

            binding.textViewTimestamp.text = chat.timestamp?.toDate()
                ?.let { RelativeDateAdapter(it).getHourMinuteFormat() }
            FirebaseQueries.subscribeToUser(chat.senderUserId) {
                if(it.profileImageUrl != ""){
                    binding.imageViewProfile.load(it.profileImageUrl)
                }else{
                    binding.imageViewProfile.setBackgroundResource(R.drawable.default_avatar)
                }
            }
        }
    }

    class ChatVoiceInViewHolder(private val binding: FragmentChatVoiceItemInBinding, itemView: View) : ViewHolder(
        itemView
    ) {
        override fun bind(chat: Chat) {
            binding.textViewTimestamp.text = chat.timestamp?.toDate()
                ?.let { RelativeDateAdapter(it).getHourMinuteFormat() }
            FirebaseQueries.subscribeToUser(chat.senderUserId) {
                binding.textViewName.text = it.name
                if(it.profileImageUrl != ""){
                    binding.imageViewProfile.load(it.profileImageUrl)
                }else{
                    binding.imageViewProfile.setBackgroundResource(R.drawable.default_avatar)
                }
            }
            binding.voicePlayerView.setAudio(chat.mediaUrl)
        }
    }

    class ChatVoiceOutViewHolder(private val binding: FragmentChatVoiceItemOutBinding, itemView: View, private val messageType: String) : ViewHolder(
        itemView
    ) {
        override fun bind(chat: Chat) {
            FirebaseQueries.subscribeToMemberLastVisit(chat.messageId, chat.timestamp!!) { totalReadBy ->
                if (totalReadBy == 0) {
                    binding.textViewReadBy.text = ""
                }
                else {
                    if (messageType == "personal") {
                        binding.textViewReadBy.text = itemView.context.getString(R.string.read)
                    }
                    else {
                        binding.textViewReadBy.text = itemView.context.getString(R.string.readBy) + " $totalReadBy"
                    }
                }
            }
            binding.textViewTimestamp.text = chat.timestamp?.toDate()
                ?.let { RelativeDateAdapter(it).getHourMinuteFormat() }
            FirebaseQueries.subscribeToUser(chat.senderUserId) {
                if(it.profileImageUrl != ""){
                    binding.imageViewProfile.load(it.profileImageUrl)
                }else{
                    binding.imageViewProfile.setBackgroundResource(R.drawable.default_avatar)
                }
            }
            binding.voicePlayerView.setAudio(chat.mediaUrl)
        }
    }

    override fun getItemCount(): Int {
        return this.chats.size
    }

    fun getData(): ArrayList<Chat> {
        return this.chats
    }
}