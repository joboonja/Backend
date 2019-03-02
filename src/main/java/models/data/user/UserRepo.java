package models.data.user;

import config.UserConfig;
import models.data.skill.UserSkill;

import java.util.ArrayList;
import java.util.HashMap;

public class UserRepo {
    private static UserRepo ourInstance = new UserRepo();
    private ArrayList<User> users = new ArrayList<User>();

    public static UserRepo getInstance() {
        return ourInstance;
    }

    private UserRepo() {
        users = new ArrayList<User>();
    }
    public User getUserById(String id) throws Exception
    {
        for (User user : users) {
            if (user.getId().equals(id))
                return user;
        }
        throw new Exception(UserConfig.USER_NOT_FOUND_ERROR);
    }
    public void registerNewUser(User newUser)
    {
        for (User user : users) {
            if (user.getId().equals(newUser.getId())) {
                System.out.println(UserConfig.USERNAME_ALREADY_EXISTS_ERROR);
                return;
            }
        }
        users.add(newUser);
    }
    public void addDefaultUser()
    {
        String id = "1";
        String firstName = "علی";
        String lastName = "شریف‌زاده";
        HashMap<String, UserSkill> skills = new HashMap<String, UserSkill>();
        skills.put("HTML", new UserSkill("HTML", 5));
        skills.put("Javascript", new UserSkill("Javascript", 4));
        skills.put("C++", new UserSkill("C++", 2));
        skills.put("Java", new UserSkill("Java", 3));
        String jobTitle = "برنامه‌نویس وب";
        String bio = "روی سنگ قبرم بنویسید: خدا بیامرز میخواست خیلی کارا بکنه ولی پول نداشت";
        users.add(new User(id, skills, firstName, lastName, jobTitle, bio));

        id = "2";
        firstName = "فرزاد";
        lastName = "حبیبی";
        skills = new HashMap<String, UserSkill>();
        skills.put("HTML", new UserSkill("HTML", 5));
        skills.put("Javascript", new UserSkill("Javascript", 4));
        skills.put("C++", new UserSkill("C++", 2));
        skills.put("Java", new UserSkill("Java", 3));
        jobTitle = "دانشجو";
        bio = "بیو ندارم";
        users.add(new User(id, skills, firstName, lastName, jobTitle, bio));

        id = "3";
        firstName = "یاسمن";
        lastName = "جعفری";
        skills = new HashMap<String, UserSkill>();
        skills.put("HTML", new UserSkill("HTML", 5));
        skills.put("Javascript", new UserSkill("Javascript", 4));
        skills.put("C++", new UserSkill("C++", 2));
        skills.put("Java", new UserSkill("Java", 3));
        jobTitle = "دانشجو";
        bio = "بیو؟";
        users.add(new User(id, skills, firstName, lastName, jobTitle, bio));

        id = "4";
        firstName = "ممد";
        lastName = "فراهانی";
        skills = new HashMap<String, UserSkill>();
        skills.put("HTML", new UserSkill("HTML", 5));
        skills.put("Javascript", new UserSkill("Javascript", 4));
        skills.put("C++", new UserSkill("C++", 2));
        skills.put("Java", new UserSkill("Java", 3));
        jobTitle = "برنامه‌نویس";
        bio = "دنبال کار می‌گردم";
        users.add(new User(id, skills, firstName, lastName, jobTitle, bio));

    }
}
