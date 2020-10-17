package masli.prof.speedtimer;

import android.app.Activity;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Client extends Thread {
    private static final String DELETE_CONNECTION = "delete_connection";

    private WaitingListActivity activity;

    private static String END = "end";
    private Socket client;
    private DataInputStream inputStream;
    private PrintWriter outputStream;

    public Client(WaitingListActivity activity) {
        this.activity = activity;
    }

    public void run() {
        setUp();
    }

    private void setUp() {
        try {
            client = new Socket("46.181.131.231", 1337);
            inputStream = (DataInputStream) new DataInputStream(client.getInputStream());
            outputStream = (PrintWriter) new PrintWriter(client.getOutputStream());

            outputStream.println(MainActivity.name);
            outputStream.flush();

            new CheckInterrupted().start();

            while (true) {
                String message = inputStream.readLine(); //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                if (!message.equals(END)){
                    activity.addToList(message);
                } else break;
            }

            listenFromServer();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listenFromServer() {
        while (true) {
            try {
                String message = inputStream.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendToServer(String message) {
        outputStream.println(message);
        outputStream.flush();
    }

    private class CheckInterrupted extends Thread {
        public void run() {
            check();
        }

        public void check() {
            while (!Client.this.isInterrupted()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            outputStream.println(DELETE_CONNECTION); // если поток прерван
            outputStream.flush();
            this.interrupt();
        }
    }

}

// сделать другой поток который смотрит isInterrupted
