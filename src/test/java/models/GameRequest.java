package models;

import lombok.Data;

@Data
public class GameRequest {
    private String company;
    private String description;
    private String genre;
    private String title;
}
