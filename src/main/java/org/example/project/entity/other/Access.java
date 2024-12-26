package org.example.project.entity.other;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Access {
    REZERV("rezerv"),
    SOON("soon"),
    AVALIABLE("avaliable");

    private String status;
Access (String status) {
    this.status = status;
}
    public String getStatus() {
        return status;
    }
}
