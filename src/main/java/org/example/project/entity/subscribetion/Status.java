package org.example.project.entity.subscribetion;


import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor

public enum Status {

    SUCCESS("SUCCESS"),
    EXPIRED("EXPIRED");

    private String status;
Status (String status) {
    this.status = status;
}
    public String getStatus() {
        return status;
    }


// Constructor

}
