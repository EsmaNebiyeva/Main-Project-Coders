package org.example.project.model;

import org.example.project.entity.other.Setting;

import lombok.Data;
import lombok.RequiredArgsConstructor;
@Data
@RequiredArgsConstructor

public class SettingDTO{

    private String theme;
    private String language;
    private String currency;
    private String timeZone;
    private String size;
    private String icons;

    public static SettingDTO convertToSettingDTO(Setting setting){
        if (setting == null) {
            return null;
        }
        SettingDTO settingDTO=new SettingDTO();
        settingDTO.setCurrency(setting.getCurrency());
        settingDTO.setIcons(setting.getIcons());
        settingDTO.setLanguage(setting.getLanguage());
        settingDTO.setSize(setting.getSize());
        settingDTO.setTheme(setting.getTheme());
        settingDTO.setTimeZone(setting.getTimeZone());
        return settingDTO;
    }
}