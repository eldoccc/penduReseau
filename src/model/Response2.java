package model;

import model.states.Etat;

import java.io.Serializable;

public class Response2 implements Serializable {
    private String message;
    private Etat state;


    public Response2(String message, Etat state) {
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
