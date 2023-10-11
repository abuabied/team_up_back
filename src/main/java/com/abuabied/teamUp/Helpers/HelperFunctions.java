package com.abuabied.teamUp.Helpers;

import com.abuabied.teamUp.Entities.Game;
import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Set;

public abstract class HelperFunctions {
    public static int comparePasswords(String unhashedPassword, String hashedPassword) {
        String newHashedPassword = hashPassword(unhashedPassword);
        return newHashedPassword.compareTo(hashedPassword);
    }

    public static String hashPassword(String password) {
        return Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString();
    }

    public static String getGameCollectionItemsAsIDString(Map<String, Game> gameCollection) {
        String gameIDsString = "";
        Set<String> gameIDSet = gameCollection.keySet();
        for (String gameID : gameIDSet) {
            gameIDsString += gameID + ", ";
        }
        if (gameIDsString.length() > 0) {
            gameIDsString = gameIDsString.substring(0, gameIDsString.length() - 2);
        }
        return gameIDsString;
    }
}