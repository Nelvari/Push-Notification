package com.example.threadrunnablehandler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import static com.example.threadrunnablehandler.App.CHANNEL_ID;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private Button buttonStartThread;

    private Handler mainHandler = new Handler();

    private volatile boolean stopThread = false;

    private NotificationManagerCompat notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonStartThread = findViewById(R.id.button_start_thread);

        notificationManager = NotificationManagerCompat.from(this);

        ExampleRunnable runnable = new ExampleRunnable();
        new Thread(runnable).start();

    }

    public void startThread(View view){

        stopThread = false;

//        ExampleThread thread = new ExampleThread(10);
//        thread.start();

        ExampleRunnable runnable = new ExampleRunnable();
        new Thread(runnable).start();

        /*new Thread(new Runnable() {
            @Override
            public void run() {
                //work
            }
        }).start();
*/
    }

    public void stopThread(View view){
        stopThread = true;
    }

//    class ExampleThread extends Thread{
//
//        int seconds;
//
//        ExampleThread(int seconds){
//            this.seconds = seconds;
//        }
//
//        @Override
//        public void run() {
//            for (int i = 0; i < seconds; i++) {
//                Log.d(TAG, "startThread: " + i);
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }

    class ExampleRunnable implements Runnable{


        ExampleRunnable(){
        }

        @Override
        public void run() {
            while (true){
                if (stopThread)
                    return;


//                    /*Handler threadHandler = new Handler(Looper.getMainLooper());
//                    threadHandler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            buttonStartThread.setText("50%");
//                        }
//                    });*/
//
//                    /*buttonStartThread.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            buttonStartThread.setText("50%");
//                        }
//                    });*/
//
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            onChannel();
                        }
                    });



                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void onChannel(){

        Notification notification = new  NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_message)
                .setContentTitle("Title")
                .setContentText("Message")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManager.notify(1, notification);

    }

}
