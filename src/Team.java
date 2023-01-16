public class Team {
    private Faculty faculty;
    private int score;
    private PlayerCard[] players;

    public Team(Faculty faculty)
    {
        this.faculty = faculty;
        this.score = 0;
        this.players = new PlayerCard[11];
    }

    public void addPlayerToTeam(PlayerCard playerCard)
    {
        for(PlayerCard player : players)
        {
            if (player == null)
            {
                player = playerCard;
                return;
            }
        }
    }

    public void removePlayerFromTeam(PlayerCard playerCard)
    {
        for (PlayerCard player : players)
        {
            if (player == playerCard)
            {
                player = playerCard;
                return;
            }
        }
    }

    //66

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
