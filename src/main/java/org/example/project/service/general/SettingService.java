package org.example.project.service.general;

import org.example.project.entity.other.Setting;

public interface SettingService {
    Setting getByEmailSetting(String email,Setting setting);
    Setting cancelBymail(String email,Setting setting);
}