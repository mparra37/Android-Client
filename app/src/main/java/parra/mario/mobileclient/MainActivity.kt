package parra.mario.mobileclient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import parra.mario.mobileclient.databinding.ActivityMainBinding
import java.io.DataOutputStream
import java.net.Socket


class MainActivity : AppCompatActivity() {
    lateinit var et_message: EditText
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val myThread: MyThread = MyThread()

        Thread(myThread).start()

    }

    class MyThread : Runnable{
        var msg: String = ""
        lateinit var socket: Socket
        lateinit var dos: DataOutputStream

        override fun run() {
            socket = Socket("158.97.91.129", 5555)
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

        fun btnClickSend(v: View){

        }
    }
}