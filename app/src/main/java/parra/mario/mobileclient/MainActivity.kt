package parra.mario.mobileclient

import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.DataOutputStream
import java.net.Socket


class MainActivity : AppCompatActivity() {
    lateinit var et_message: EditText
    lateinit var myThread: MyThread

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        et_message = findViewById(R.id.et_msg)


        myThread = MyThread()

        Thread(myThread).start()

    }

    fun btnClickSnd(v: View?) {
        var msg: String = et_message.getText().toString()
        myThread.sendMsg(msg)
    }

    fun showToast(toast: String?) {
        runOnUiThread { Toast.makeText(this, toast, Toast.LENGTH_SHORT).show() }
    }

      class MyThread : Runnable{
        var msg: String = ""
        lateinit var socket: Socket
        lateinit var dos: DataOutputStream

        override fun run() {
            socket = Socket("158.97.91.129", 5578)

            dos = DataOutputStream(socket.getOutputStream())
            dos.writeUTF(msg)
            dos.close()
            dos.flush()
            socket.close()


        }

        fun sendMsg(msg: String){
            this.msg = msg
            run()
        }


    }


}