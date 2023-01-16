import java.util.ArrayList;

public class Team {
    private Faculty faculty;
    private int score;
    private ArrayList<PlayerCard> players;

    public Team(Faculty faculty)
    {
        this.faculty = faculty;
        this.score = 0;
        this.players = new ArrayList<PlayerCard>();
    }

    public void addPlayerToTeam(PlayerCard playerCard)
    {
        this.players.add(playerCard);
    }

    public void removePlayerFromTeam(PlayerCard playerCard)
    {
        this.players.remove(playerCard);
    }

    public PlayerCard getPlayerCard(int playerId)
    {
        for (PlayerCard playerCard : players)
        {
            if (playerCard.getPlayer().getId() == playerId)
            {
                return playerCard;
            }
        }
        return null;
    }

    public void addScore(int x)
    {
        this.score += x;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public int getScore() {
        return score;
    }

    public ArrayList<PlayerCard> getPlayers() {
        return players;
    }
}
