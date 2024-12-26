package org.example.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Notifications {
    private  EmailNotDTO emailNotDTO;
    private  MoreActivityDTO moreActivityDTO;
}
