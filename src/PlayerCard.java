public class PlayerCard {
    private Player player;
    private int goalNum;

    public PlayerCard(Player player)
    {
        this.player = player;
        this.goalNum = 0;
    }

    public void addGoal(){
        this.goalNum++;
    }

    public int getGoalNum() {
        return goalNum;
    }

    public Player getPlayer() {
        return player;
    }

    public void setGoalNum(int goalNum) {
        this.goalNum = goalNum;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
