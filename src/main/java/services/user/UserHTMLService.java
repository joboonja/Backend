package services.user;

import user.User;
import user.UserRepo;

public class UserHTMLService {
    public static String getUserByIdHtml(String userID) {
        User user = null;
        try {
            user = UserRepo.getInstance().getUserById(userID);
            String page = "<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <title>User</title>\n" +
                    "</head>";
            page += "<body>\n" +
                    "    <ul>\n" +
                    "        <li>id: " +
                    user.getId()
                    + "</li>\n <li>firstname: "
                    + user.getFirstName()
                    + "</li>\n "
                    + "<li>lastname: "
                    + user.getLastName()
                    + "</li>\n"
                    + " <li>jobTitle: "
                    + user.getJobTitle()
                    + "</li>\n"
                    + " <li>bio: "
                    + user.getBio()
                    + "</li>\n    "
                    + "</ul>\n"
                    + "</body>\n"
                    + "</html>";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
