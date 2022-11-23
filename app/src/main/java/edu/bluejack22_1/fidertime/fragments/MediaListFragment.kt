package edu.bluejack22_1.fidertime.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import edu.bluejack22_1.fidertime.activities.MessageActivity
import edu.bluejack22_1.fidertime.adapters.MediaListRecyclerViewAdapter
import edu.bluejack22_1.fidertime.databinding.FragmentMediaListBinding
import edu.bluejack22_1.fidertime.models.Media

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MediaListFragment (
        private val mediaRef : Query
    ) : Fragment() {

    private var _binding: FragmentMediaListBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerViewMessages: RecyclerView
    private lateinit var userMediaListener : ListenerRegistration


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMediaListBinding.inflate(inflater, container, false)
        recyclerViewMessages = binding.recyclerViewMedia
        recyclerViewMessages.layoutManager = GridLayoutManager(this.context , 3)

        subscribeToUserMedia()
        return binding.root
    }

    private fun subscribeToUserMedia() {
        userMediaListener = mediaRef.addSnapshotListener { value, e ->
            if (e != null) {
                return@addSnapshotListener
            }
            if (value != null && !value.isEmpty) {
                val userMedia = arrayListOf<Media>()
                for (doc in value) {
                    val media = doc.toObject<Media>()
                    media.id = doc.id
                    userMedia.add(media)
                }
                attachRecyclerViewAdapter(userMedia)
            }
        }
    }

    private fun attachRecyclerViewAdapter(userMedia: ArrayList<Media>) {
        val adapter = MediaListRecyclerViewAdapter(userMedia)
        recyclerViewMessages.adapter = adapter
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}