package client;

import model.Response;
import model.states.Etat;

import java.io.*;
import java.net.Socket;

/**
 * This class is the thread of the client that is only waiting a response from the server and send a command to the server
 */
public class ClientThread extends Thread {
    private Socket socket;
    private Etat state;

    private BufferedReader in;  // The input stream (not used)
    private PrintStream out;  // The output stream (only used to send the name of the player)
    private ObjectOutputStream oos;  // The object output stream (not used)
    private ObjectInputStream ois;  // The object input stream (used to receive the response from the server)


    public ClientThread(Socket s, String name) throws IOException {
        this.socket = s;
        this.out = new PrintStream(this.socket.getOutputStream());
        this.oos = new ObjectOutputStream(this.socket.getOutputStream());
        this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        this.ois = new ObjectInputStream(this.socket.getInputStream());

        this.state = null;

        this.out.println(name);  // Send the name of the player to the server
    }

    /**
     * This method is called when the thread is started
     * It waits a response from the server and then call the method of the state to handle the response received
     * And then it print the response in the console
     */
    @Override
    public void run() {
        try {
            while (true) {
                Response response = (Response) ois.readObject();  // Wait a response from the server
                System.out.println(response + "\n");  // Print the response in the console

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
                end();  // Close the socket if an exception is thrown <=> the client disconnect
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*
        * This method is used to send a command to the server
     */
    public void send(String message) {
        out.println(message);
    }

    /**
     * This method is used to close the socket
     * @throws IOException If there is an error with the input or the output
     */
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
