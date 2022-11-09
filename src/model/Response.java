package model;

public class Response {
    private int triesLeft;
    private String hiddenWord;
    private int stateGame; // 0 = wrong letter, 1 = right letter, 3 = win, 4 = lose

    public Response(int triesLeft, String hiddenWord, int stateGame) {
        this.triesLeft = triesLeft;
        this.hiddenWord = hiddenWord;
        this.stateGame = stateGame;
    }

    public int getTriesLeft() {
        return triesLeft;
    }

    public void setTriesLeft(int triesLeft) {
        this.triesLeft = triesLeft;
    }

    public String getHiddenWord() {
        return hiddenWord;
    }

    public void setHiddenWord(String hiddenWord) {
        this.hiddenWord = hiddenWord;
    }

    public int getStateGame() {
        return stateGame;
    }

    public void setStateGame(int stateGame) {
        this.stateGame = stateGame;
    }


}
