package com.example.fragmentexample

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM = "message"

class BlankFragmentB : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance(param: String) =
            BlankFragmentB().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM, param)
                }
            }
    }
    private var message: String? = null

    interface OnMessageSendListener {
        fun onMessageSend(message: String)
    }
    private var messageListener: OnMessageSendListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnMessageSendListener) {
            messageListener = context
        } else {
            throw RuntimeException("$context debe implementar OnMessageSendListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            message = it.getString(ARG_PARAM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank_b, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.fragment_b_textview).text = message

        view.findViewById<View>(R.id.fragment_b_send_button).setOnClickListener {
            onClickButtonSend(view)
        }
    }

    fun onClickButtonSend(view: View) {
        val messageTextView = view.findViewById<TextView>(R.id.fragment_b_message_box)
        val message = messageTextView.text.toString()
        messageListener?.onMessageSend(message)
    }
}