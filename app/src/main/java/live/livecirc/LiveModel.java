package live.livecirc;



public class LiveModel {
    private String unique_key;
    private String team_1;
    private String team_2;
    private String winner_team;
    private String type;
    private boolean matchStarted;

    public LiveModel(String unique_key, String team_1, String team_2, boolean matchStarted) {
        this.unique_key = unique_key;
        this.team_1 = team_1;
        this.team_2 = team_2;
        this.matchStarted = matchStarted;
    }

    public LiveModel(String unique_key, String team_1, String team_2, String winner_team, String type, boolean matchStarted) {
        this.unique_key = unique_key;
        this.team_1 = team_1;
        this.team_2 = team_2;
        this.winner_team = winner_team;
        this.type = type;
        this.matchStarted = matchStarted;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUnique_key() {
        return unique_key;
    }

    public String getWinner_team() {
        return winner_team;
    }

    public void setWinner_team(String winner_team) {
        this.winner_team = winner_team;
    }

    public void setUnique_key(String unique_key) {
        this.unique_key = unique_key;
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

    public boolean isMatchStarted() {
        return matchStarted;
    }

    public void setMatchStarted(boolean matchStarted) {
        this.matchStarted = matchStarted;
    }
}
