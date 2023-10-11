package com.abuabied.teamUp.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Game {
    private String gameID;
    private String name;
    private String imageURL;

    public Game(Game game) {
        this.gameID = game.gameID;
        this.name = game.name;
        this.imageURL = game.imageURL;
    }
}
