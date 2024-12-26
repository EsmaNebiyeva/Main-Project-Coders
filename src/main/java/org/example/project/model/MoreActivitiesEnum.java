package org.example.project.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MoreActivitiesEnum {
    ALLREMINDERS("ALLREMINDERS"),
    ACTIVITY("ACTIVITY"),
    IMPORTANT("IMPORTANT");

     private String not;

     MoreActivitiesEnum (String not) {
                this.not = not;
            }

    public String getNots() {
        return not;
    }
}
