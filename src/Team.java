public class Team {
    private Faculty faculty;
    private int score;
    private PlayerCard[] players;

    public Faculty getFaculty() {
        return faculty;
    }

    public int getScore() {
        return score;
    }

    public PlayerCard[] getPlayers() {
        return players;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public void setPlayers(PlayerCard[] players) {
        this.players = players;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
