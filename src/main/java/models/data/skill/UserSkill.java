package models.data.skill;

public class UserSkill extends Skill {
    private int points;
    public UserSkill(String name, int points) {
        super(name);
        this.points = points;
    }
    public int getPoints()
    {
        return points;
    }

}
