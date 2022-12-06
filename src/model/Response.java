package model;

import model.states.Etat;

import java.io.Serializable;

/*
    Response class used to send a response to the client with the same structure
 */
public class Response implements Serializable {
    private String message;  // Additional message to send to the client
    private Etat state;  // State of the player in the game

    /*
        Constructor of the Response class
     */
    public Response(String message, Etat state) {
        this.message = message;
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public Etat getState() {
        return state;
    }

    @Override
    public String toString() {
        return message;
    }
}
