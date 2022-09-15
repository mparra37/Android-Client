package parra.mario.mobileclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity2 extends AppCompatActivity {
    EditText edTxt;
    MsgThread myThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        edTxt = findViewById(R.id.et_msg);
        myThread = new MsgThread();
        new Thread((myThread)).start();


    }

    public void showToast(String toast)
    {
        runOnUiThread(() -> Toast.makeText(this, toast, Toast.LENGTH_SHORT).show());
    }

    private class MsgThread implements Runnable{
        private volatile String msg = "";
        Socket socket;
        DataOutputStream dos;
        //private PrintWriter out;

        @Override
        public void run(){
            try {
                socket = new Socket("158.97.91.129", 5678);
                //showToast("llego 1");
                //out = new PrintWriter(socket.getOutputStream(), true);
                dos = new DataOutputStream(socket.getOutputStream());
                dos.writeUTF(msg);
                //showToast("llego 2");
                dos.close();
                dos.flush();
                socket.close();
            } catch (IOException e) {
                showToast("error");
                e.printStackTrace();
            }
        }

        public void sendMsg(String msg){
            this.msg = msg;
            run();
        }


    }

    public void btnClickSnd(View v){
        String msg = edTxt.getText().toString();
        myThread.sendMsg(msg);
    }
}