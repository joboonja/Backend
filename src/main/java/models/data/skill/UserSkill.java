package models.data.skill;

import java.util.ArrayList;

public class UserSkill extends Skill {
    private int points;
    private ArrayList<String> peopleWhoEndrosed;
    public UserSkill(String name, int points) {
        super(name);
        this.points = points;
        peopleWhoEndrosed = new ArrayList<>();
    }
    public int getPoints()
    {
        return points;
    }
    public boolean canEndorse(String userID)
    {
        return !peopleWhoEndrosed.contains(userID);
    }
    public void endorse(String userID) throws Exception
    {
        if(canEndorse(userID)){
            peopleWhoEndrosed.add(userID);
            points ++;
        }
        else
            throw new Exception("This User Endorsed Already");
    }

}
