package io.github.donut.proj.model;

public enum SceneName {
    Main("fxml/menuPage.fxml"),
    START("fxml/startPage.fxml"),
    ABOUT("fxml/aboutUs.fxml"),
    BOARD_PAGE("fxml/boardPage.fxml"),
    CREATEACCOUNT_PAGE("fxml/createAccountPage.fxml"),
    LOBBY_PAGE("fxml/lobbyPage.fxml"),
    LOGIN_PAGE("fxml/loginPage.fxml"),
    SINGLEPLAYER_PAGE("fxml/singlePlayerPage.fxml");

    private final String value;

    SceneName(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
