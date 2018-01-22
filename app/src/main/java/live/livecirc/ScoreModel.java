package live.livecirc;

public class ScoreModel {
   private boolean matchStarted;
    private String team_1;
    private String team_2;
    private String type;
    private String score;
    private String innings_requirement;

 public ScoreModel(boolean matchStarted, String team_1, String team_2, String type, String score, String innings_requirement) {
  this.matchStarted = matchStarted;
  this.team_1 = team_1;
  this.team_2 = team_2;
  this.type = type;
  this.score = score;
  this.innings_requirement = innings_requirement;
 }

 public boolean isMatchStarted() {
  return matchStarted;
 }

 public void setMatchStarted(boolean matchStarted) {
  this.matchStarted = matchStarted;
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

 public String getType() {
  return type;
 }

 public void setType(String type) {
  this.type = type;
 }

 public String getScore() {
  return score;
 }

 public void setScore(String score) {
  this.score = score;
 }

 public String getInnings_requirement() {
  return innings_requirement;
 }

 public void setInnings_requirement(String innings_requirement) {
  this.innings_requirement = innings_requirement;
 }
}
