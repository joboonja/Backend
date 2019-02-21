package config;

import user.User;

public class AuctionConfig {
    public static String WINNER_MSG(User user)
    {
        if(user == null)
            return "-> There is no Winner!";
        return "-> winner:" + user.getUsername();
    }
}
