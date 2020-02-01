package ru.aumsu.www.application.ui.send

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_send.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.aumsu.www.application.BaseActivity
import ru.aumsu.www.application.MainActivity
import ru.aumsu.www.application.R
import ru.aumsu.www.application.models.Entity
import ru.aumsu.www.application.models.Message
import ru.aumsu.www.application.retrofit.RequestAPI

class SendFragment : Fragment() {

    private lateinit var sendViewModel: SendViewModel
    private var entities: Array<Entity>? = null
    private var choosedEntity: Entity? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sendViewModel =
            ViewModelProviders.of(this).get(SendViewModel::class.java)
        return inflater.inflate(R.layout.fragment_send, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getEntities()

        view.send_button.setOnClickListener {
            if(choosedEntity != null && view.message.text!!.isNotEmpty())
                sendMessage(Runnable {
                    view.message.text = null
                    (activity as MainActivity).messageSendDialog()
                })
        }
    }

    private fun getEntities() {
        if((activity as BaseActivity).hasConnection()) {
            val service = (activity as BaseActivity).getRetrofit().create<RequestAPI>(RequestAPI::class.java)

            val messages = service.getEntities(BaseActivity.userData!!.token)

            messages.enqueue(object : Callback<Array<Entity>> {
                override fun onFailure(call: Call<Array<Entity>>, t: Throwable) {}

                override fun onResponse(
                    call: Call<Array<Entity>>,
                    response: Response<Array<Entity>>
                ) {
                    if (response.code() == 200) {
                        entities = response.body()

                        if(entities!!.isEmpty()) {
                            Toast.makeText(context, "Вам некому отправить сообщение", Toast.LENGTH_LONG).show()
                            return
                        }

                        val entityTitles = entities!!.map { it.title }
                        val adapter = ArrayAdapter<String>(context!!, android.R.layout.simple_spinner_dropdown_item,  entityTitles)
                        view!!.entity_select.setAdapter(adapter)
                        view!!.entity_select.threshold = 0
                        view!!.entity_select.setOnFocusChangeListener { _, b -> if(b) view!!.entity_select.showDropDown() }
                        view!!.entity_select.setOnClickListener { view!!.entity_select.showDropDown() }
                        view!!.entity_select.onItemClickListener =
                            AdapterView.OnItemClickListener { p0, _, p2, _ -> choosedEntity = entities!!.find { it.title == p0.getItemAtPosition(p2) } }
                    }
                }
            })
        } else {
            Toast.makeText(context, "Отсутствует интернет соединение", Toast.LENGTH_LONG).show()
        }
    }

    private fun sendMessage(r: Runnable) {
        if((activity as BaseActivity).hasConnection()) {
            val service = (activity as BaseActivity).getRetrofit().create<RequestAPI>(RequestAPI::class.java)

            val messages = service.sendMessage(BaseActivity.userData!!.token, Message(
                BaseActivity.userData!!.id!!,
                choosedEntity!!.id,
                view!!.message.text.toString(),
                null
            ))

            messages.enqueue(object : Callback<Message> {
                override fun onFailure(call: Call<Message>, t: Throwable) {}

                override fun onResponse(
                    call: Call<Message>,
                    response: Response<Message>
                ) {
                    if (response.code() == 200) {
                        Handler().post(r)
                    }
                }
            })
        } else {
            Toast.makeText(context, "Отсутствует интернет соединение", Toast.LENGTH_LONG).show()
        }
    }
}