package live.livecirc;

/**
 * Created by anwar on 1/21/2018.
 */

public class ScheduleModel {
    private String date;
    private String team_1;
    private String team_2;

    public ScheduleModel(String date, String team_1, String team_2) {
        this.date = date;
        this.team_1 = team_1;
        this.team_2 = team_2;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTeam_1() {
        return team_1;
    }

    public void setTeam_1(String team_1) {
        this.team_1 = team_1;
    }

    public String getTeam_2() {
        return team_2;
    }

    public void setTeam_2(String team_2) {
        this.team_2 = team_2;
    }
}
