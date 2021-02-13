package com.example.firebaseapp

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.dynamiclinks.ktx.shortLinkAsync
import com.google.firebase.ktx.Firebase

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_first).setOnClickListener {
            createDeeplink("https://my_link.com/?page=1", view.context)
        }
        view.findViewById<Button>(R.id.button_second).setOnClickListener {
            createDeeplink("https://my_link.com/?page=2", view.context)
        }
    }

    private fun createDeeplink(uri: String, context: Context) {
        val clipboardManager =
            context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        Firebase.dynamicLinks.shortLinkAsync {
            link = Uri.parse(uri)
            domainUriPrefix = "https://konst007.page.link"
        }.addOnSuccessListener { link ->
            val shortLink = link.shortLink.toString()
            Log.d("checkk", shortLink)
            clipboardManager.setPrimaryClip(ClipData.newPlainText("link", shortLink))
        }.addOnFailureListener { e ->
            Log.d("checkk", e.message.orEmpty())
        }
    }
}