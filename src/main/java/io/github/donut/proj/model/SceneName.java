package io.github.donut.proj.model;

public enum SceneName {
    Main("fxml/menuPage.fxml"),
    START("fxml/startPage.fxml"),
    ABOUT("fxml/aboutUs.fxml"),
    BOARD_PAGE("fxml/boardPage.fxml"),
    CREATEACCOUNT_PAGE("fxml/createAccountPage.fxml"),
    LOBBY_PAGE("fxml/lobbyPage.fxml"),
    LOGIN_PAGE("fxml/loginPage.fxml"),
    SINGLEPLAYER_PAGE("fxml/singlePlayerPage.fxml"),
    PORTAL_PAGE("fxml/profilePortalPage.fxml"),
    HISTORY_PAGE("fxml/playerHistoryPage.fxml"),
    UPDATE_ACCOUNT_PAGE("fxml/accountUpdate.fxml"),
    REACTIVATE_ACCOUNT_PAGE("fxml/reactivatePage.fxml"),
    WAITING_PAGE("fxml/waitingPage.fxml"),
    MOVE_HISTORY_PAGE("fxml/moveHistoryPage.fxml"),
    STATS_PAGE("fxml/statsPage.fxml");

    private final String value;

    SceneName(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
