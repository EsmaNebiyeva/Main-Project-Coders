package org.example.project.model;



import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum EmailNots {
    NEWS("NEWS"),
    TIPS("TIPS"),
    OFFER("OFFER");

     private String not;

    EmailNots (String not) {
                this.not = not;
            }

    public String getNots() {
        return not;
    }
}
