package com.abuabied.teamUp.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Session {
    private String username;
    private Date sessionCreationTime;
}
