package org.example.project.model;


import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public enum Times {
    THIS_WEEK("This week") ,
    THIS_MONTH("This month"),
    THIS_YEAR("This year"),
    ALL_TIME("All time");
    private String times;
    Times (String times) {
        this.times = times;
    }
        public String getTimes() {
            return times;
        }
}
