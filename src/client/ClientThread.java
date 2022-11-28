package client;

import model.Response2;
import model.states.Etat;

import java.io.*;
import java.net.Socket;

public class ClientThread extends Thread {
    private Socket socket;
    private Socket clientSocket;
    private Etat state;

    private BufferedReader in;
    private PrintStream out;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;


    public ClientThread(Socket s, String name) throws IOException {
        this.socket = s;
        this.out = new PrintStream(this.socket.getOutputStream());
        this.oos = new ObjectOutputStream(this.socket.getOutputStream());
        this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        this.ois = new ObjectInputStream(this.socket.getInputStream());

        this.state = null;

        this.out.println(name);
    }

    @Override
    public void run() {
        try {
            while (true) {
                Response2 response = (Response2) ois.readObject();
                System.out.println(response + "\n");

                // If the client's state change, change the local state and print the commands
                if (response.getState() != this.state) {
                    this.state = response.getState();
                    System.out.println(this.state.getClientInstruction());
                }
            }
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            try {
                end();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void send(String message) {
        out.println(message);
    }

    public void end() {
        try {
            in.close();
            out.close();
            ois.close();
            oos.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
