package io.github.santulator.gui.i18n;

public enum I18nGuiKey {
    MAIN_WINDOW_UNSAVED("main.window.unsaved"),
    MAIN_WINDOW_UNTITLED("main.window.untitled"),

    SESSION_DEFAULT_DRAW("session.default.draw"),
    SESSION_DEFAULT_PASSWORD("session.default.password"),
    SESSION_ROLE_GIVER("session.role.giver"),
    SESSION_ROLE_RECEIVER("session.role.receiver"),
    SESSION_ROLE_BOTH("session.role.both"),

    DRAW1_FAILURE("draw1.failure"),
    DRAW1_SUCCESS("draw1.success"),

    DRAW2_RESULTS("draw2.results"),

    DRAW3_PASSWORD_SET("draw3.password.set"),

    ABOUT_TITLE("about.title"),
    ABOUT_VERSION("about.version"),

    ERROR_DETAILS("error.details"),
    ERROR_SESSION_OPEN_DETAILS("error.session.open.details"),
    ERROR_SESSION_OPEN_TITLE("error.session.open.title"),
    ERROR_SESSION_SAVE_DETAILS("error.session.save.details"),
    ERROR_SESSION_SAVE_TITLE("error.session.save.title"),
    ERROR_RESULTS_SAVE_DETAILS("error.results.save.details"),
    ERROR_RESULTS_SAVE_TITLE("error.results.save.title"),

    FILE_OPEN("file.open"),
    FILE_SAVE("file.save"),
    FILE_DISCARD("file.discard"),
    FILE_CANCEL("file.cancel"),
    FILE_RUN("file.run"),
    FILE_TYPE_SESSION("file.type.session"),
    FILE_TYPE_DRAW("file.type.draw"),
    FILE_MODIFIED("file.modified"),
    FILE_UNSAVED("file.unsaved"),

    ACTION_NEW("action.new"),
    ACTION_OPEN("action.open"),
    ACTION_SAVE("action.save"),
    ACTION_RUN("action.run"),
    ACTION_EXIT("action.exit"),
    ACTION_ABOUT("action.about"),

    VALIDATION_COUNT("validation.count"),
    VALIDATION_NAME("validation.name");

    private final String key;

    I18nGuiKey(final String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
