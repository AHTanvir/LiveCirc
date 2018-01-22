package live.livecirc;

/**
 * Created by anwar on 1/21/2018.
 */

public class PlayerModel {
    private String name;
    private String pid;

    public PlayerModel(String name, String pid) {
        this.name = name;
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PlayerModel(String pid) {
        this.pid = pid;
    }

    public String getPid() {
        return pid;
    }
}
