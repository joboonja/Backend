package services.user;

import user.User;
import user.UserRepo;

public class UserService {
    public static String getUserByIDHtml(String userID) throws Exception {
            User user = UserRepo.getInstance().getUserById(userID);
            String page = "<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <title>User</title>\n" +
                    "</head>";
            page += "<body>\n" +
                    "<div style = \"direction : rtl;\">"+
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
                    +"</div>"
                    + "</body>\n"
                    + "</html>";
            return page;
    }
}
