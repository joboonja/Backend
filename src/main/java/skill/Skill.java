package skill;

public class Skill {

    private String name;
    private int points;


    public Skill(String _name, int _points){
        name = _name;
        points = _points;
    }
    public String getName()
    {
        return name;
    }
    public int getPoints()
    {
        return points;
    }
}
