package ru.coursework.akinator.dto;

public class ChatMessage {
    private MessageType type;
    private String content;
    private String sender;
    private String senderName;
    private String senderId;
    private String senderAvatar;
    private String number;
    private String currentAkinator;

    private String characterName;
    private String guessed;
    private String sphinxName;
    private String akinatorName;
    private String akinatorsName;
    private String causer;

    private String characterRepeat;
    private String sphinxScore;
    private String akinatorScore;

    private String akinatorsScore;
    private String winnerAkinatorScore;
    private String bandname;
    private String bandScore;

    private GameType gameType;

    private PlayerType playerType;

    private long gameId;


    public enum PlayerType {
        AKINATOR,
        SPHINX1,
        SPHINX2,
        PARTY;
    }
    public enum MessageType {
        GAME_CREATED,
        JOIN,
        JOIN2,
        START,
        LEAVE,
        CHARACTER,
        QUESTION,
        ANSWER,
        END,
        CHANGE

    }


    public enum GameType {
        ONE_VS_ONE,
        ONE_VS_PARTY;
    }
    public long getGameId() {
        return gameId;
    }
    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public void setSenderAvatar(String senderAvatar) {
        this.senderAvatar = senderAvatar;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getSenderAvatar() {
        return senderAvatar;
    }

    public String getCharacterName() {
        return characterName;
    }

    public String getCharacterRepeat() {
        return characterRepeat;
    }

    public String getGuessed() {
        return guessed;
    }

    public String getSphinxScore() {
        return sphinxScore;
    }

    public String getAkinatorScore() {
        return akinatorScore;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public void setCharacterRepeat(String characterRepeat) {
        this.characterRepeat = characterRepeat;
    }

    public void setGuessed(String guessed) {
        this.guessed = guessed;
    }

    public void setSphinxScore(String sphinxScore) {
        this.sphinxScore = sphinxScore;
    }

    public void setAkinatorScore(String akinatorScore) {
        this.akinatorScore = akinatorScore;
    }

    public String getSphinxName() {
        return sphinxName;
    }

    public String getAkinatorName() {
        return akinatorName;
    }

    public void setSphinxName(String sphinxName) {
        this.sphinxName = sphinxName;
    }

    public void setAkinatorName(String akinatorName) {
        this.akinatorName = akinatorName;
    }

    public String getCauser() {
        return causer;
    }

    public void setCauser(String causer) {
        this.causer = causer;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAkinatorsScore() {
        return akinatorsScore;
    }

    public String getWinnerAkinatorScore() {
        return winnerAkinatorScore;
    }

    public String getBandScore() {
        return bandScore;
    }

    public void setAkinatorsScore(String akinatorsScore) {
        this.akinatorsScore = akinatorsScore;
    }

    public void setWinnerAkinatorScore(String winnerAkinatorScore) {
        this.winnerAkinatorScore = winnerAkinatorScore;
    }

    public void setBandScore(String bandScore) {
        this.bandScore = bandScore;
    }

    public String getAkinatorsName() {
        return akinatorsName;
    }

    public void setAkinatorsName(String akinatorsName) {
        this.akinatorsName = akinatorsName;
    }

    public String getBandname() {
        return bandname;
    }

    public void setBandname(String bandname) {
        this.bandname = bandname;
    }

    public String getCurrentAkinator() {
        return currentAkinator;
    }

    public void setCurrentAkinator(String currentAkinator) {
        this.currentAkinator = currentAkinator;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public void setPlayerType(PlayerType playerType) {
        this.playerType = playerType;
    }

    public ChatMessage() {
    }
}
