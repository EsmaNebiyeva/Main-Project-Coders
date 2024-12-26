package org.example.project.service.general.impl;

import java.util.Optional;

import org.example.project.entity.other.Setting;
import org.example.project.repository.general.SettingRepository;
import org.example.project.service.general.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class SettingServiceImpl  implements SettingService{
    @Autowired
    private final SettingRepository settingRepository;
    @Override
    public Setting getByEmailSetting(String email,Setting setting ) {
        Optional<Setting> byEmail = settingRepository.findByEmail(email);
        if(byEmail.isPresent()){
            Setting setting2 = byEmail.get();
            setting2.setCurrency(setting.getCurrency());
            setting2.setIcons(setting.getIcons());
            setting2.setLanguage(setting.getLanguage());
            setting2.setSize(setting.getSize());
            setting2.setTheme(setting.getTheme());
            setting2.setTimeZone(setting.getTimeZone());
            return setting2;
        }else if(!byEmail.isPresent()){
            Setting save = settingRepository.save(setting);
            return save;
        }

        return null;
    }
    @Override
    public Setting cancelBymail(String email,Setting setting){
        Optional<Setting> byEmail = settingRepository.findByEmail(email);
        if(byEmail.isPresent()){
            return byEmail.get();
        }
        return null;
    
}
}
