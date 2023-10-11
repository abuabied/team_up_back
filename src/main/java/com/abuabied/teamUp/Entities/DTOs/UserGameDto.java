package com.abuabied.teamUp.Entities.DTOs;

import com.abuabied.teamUp.Entities.Game;
import com.abuabied.teamUp.Entities.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserGameDto {
    private User user;
    private Game game;
}
