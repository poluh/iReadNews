package com.application.settings.type;

import com.application.App;

public class SettingAction {

    public static void changeTheme(char key) {
        if (key == 's') {
            App.PATH_TO_STYLE = "style/style.css";
        } else App.PATH_TO_STYLE = "style/styleBlack.css";
    }
}
