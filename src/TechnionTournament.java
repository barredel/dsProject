import java.util.ArrayList;

public class TechnionTournament implements Tournament{
    TwoThreeTree<Team> FacultyScoreTree;
    TwoThreeTree<Team> FacultyIdTree;
    TwoThreeTree<PlayerCard> PlayerScoreTree;


    TechnionTournament(){};

    @Override
    public void init() {
        FacultyScoreTree = new TwoThreeTree<Team>();
        FacultyIdTree = new TwoThreeTree<Team>();
        PlayerScoreTree = new TwoThreeTree<PlayerCard>();
    }

    @Override
    public void addFacultyToTournament(Faculty faculty) {
        Team newTeam = new Team(faculty);
        Leaf<Team> idLeaf = new Leaf<Team>(faculty.getId(),0, newTeam);
        Leaf<Team> scoreLeaf = new Leaf<Team>(0, faculty.getId(), newTeam);
        FacultyIdTree.insert(idLeaf);
        FacultyScoreTree.insert(scoreLeaf);
    }

    @Override
    public void removeFacultyFromTournament(int faculty_id){
		Leaf<Team> leaf = FacultyIdTree.search(faculty_id, 0);
        int score = leaf.getData().getScore();
        FacultyIdTree.delete(leaf);
        FacultyScoreTree.delete(FacultyScoreTree.search(score, faculty_id));
    }

    @Override
    public void addPlayerToFaculty(int faculty_id,Player player) {
        PlayerCard newPlayer = new PlayerCard(player);
        Leaf<PlayerCard> playerLeaf = new Leaf<PlayerCard>(0,player.getId(), newPlayer);
        PlayerScoreTree.insert(playerLeaf);
        Leaf<Team> teamLeaf = FacultyIdTree.search(faculty_id, 0);
        teamLeaf.getData().addPlayerToTeam(newPlayer);
    }

    @Override
    public void removePlayerFromFaculty(int faculty_id, int player_id) {
        Leaf<Team> leaf = FacultyIdTree.search(faculty_id, 0);
        PlayerCard player = leaf.getData().getPlayerCard(player_id);
        int score = player.getGoalNum();
        leaf.getData().removePlayerFromTeam(player);
        PlayerScoreTree.delete(PlayerScoreTree.search(score, player_id));
    }

    @Override
    public void playGame(int faculty_id1, int faculty_id2, int winner,
                         ArrayList<Integer> faculty1_goals, ArrayList<Integer> faculty2_goals) {
        Leaf<Team> leaf1id = FacultyIdTree.search(faculty_id1, 0);
        Leaf<Team> leaf2id = FacultyIdTree.search(faculty_id2, 0);
        Leaf<Team> leaf1score = FacultyScoreTree.search(leaf1id.getData().getScore(), faculty_id1);
        Leaf<Team> leaf2score = FacultyScoreTree.search(leaf2id.getData().getScore(), faculty_id2);
        if(winner == 1)
        {
            leaf1score.getData().addScore(3);
        }
        else if(winner == 2)
        {
            leaf2score.getData().addScore(3);
        }
        else
        {
            leaf1id.getData().addScore(1);
            leaf2id.getData().addScore(1);
        }
        leaf1score.setPrimaryKey(leaf1score.getData().getScore());
        leaf2score.setPrimaryKey(leaf2score.getData().getScore());
        FacultyScoreTree.delete(leaf1score);
        FacultyScoreTree.delete(leaf2score);
        FacultyScoreTree.insert(leaf1score);
        FacultyScoreTree.insert(leaf2score);

        for (int j = 0; j<2; j++)
        {
            Leaf<Team> teamLeaf= leaf1id;
            ArrayList<Integer> goals = faculty1_goals;
            if (j==1)
            {
                teamLeaf= leaf2id;
                goals = faculty2_goals;
            }
            int[] totalScore = new int[11];
            for(int i = 0; i<11; i++)
                totalScore[i] = 0;
            for (int id : goals)
            {
                for (int i = 0; i < teamLeaf.getData().getPlayers().size(); i++)
                {
                    if(id == teamLeaf.getData().getPlayers().get(i).getPlayer().getId())
                    {
                        totalScore[i]++;
                        break;
                    }
                }
            }
            for (int i = 0; i < leaf1id.getData().getPlayers().size(); i++)
            {
                if (totalScore[i] != 0)
                {
                    PlayerCard player = leaf1id.getData().getPlayers().get(i);
                    Leaf<PlayerCard> playerLeaf = PlayerScoreTree.search(player.getGoalNum(), player.getPlayer().getId());
                    player.setGoalNum(player.getGoalNum()+totalScore[i]);
                    playerLeaf.setPrimaryKey(player.getGoalNum());
                    PlayerScoreTree.delete(playerLeaf);
                    PlayerScoreTree.insert(playerLeaf);
                }
            }
        }

    }


    @Override
    public void getTopScorer(Player player) {
        player =  PlayerScoreTree.getMax().getData().getPlayer();
    }

    @Override
    public void getTopScorerInFaculty(int faculty_id, Player player) {
        Team team = FacultyIdTree.search(faculty_id, 0).getData();
        PlayerCard maxPlayer = team.getPlayers().get(0);
        for (PlayerCard playerCard: team.getPlayers())
        {
            if (playerCard.getGoalNum() > maxPlayer.getGoalNum() ||
                    (playerCard.getGoalNum() == maxPlayer.getGoalNum() &&
                            playerCard.getPlayer().getId() < maxPlayer.getPlayer().getId()))
            {
                maxPlayer = playerCard;
            }
        }
        player = maxPlayer.getPlayer();
    }

    @Override
    public void getTopKFaculties(ArrayList<Faculty> faculties, int k, boolean ascending) {
        Leaf<Team> topFaculty = FacultyIdTree.getMax();
        faculties.add(topFaculty.getData().getFaculty());
        if (!ascending)
        {
            for(int i=1; i<k; i++)
            {
                topFaculty = topFaculty.getPredecessor();
                faculties.add(topFaculty.getData().getFaculty());
            }
        }
        else
        {
            for(int i=1; i<k; i++)
            {
                topFaculty = topFaculty.getPredecessor();
                faculties.add(0, topFaculty.getData().getFaculty());
            }
        }

    }

    @Override
    public void getTopKScorers(ArrayList<Player> players, int k, boolean ascending) {
        Leaf<PlayerCard> topScorer = PlayerScoreTree.getMax();
        players.add(topScorer.getData().getPlayer());
        if(!ascending)
        {
            for(int i=1; i<k; i++)
            {
                topScorer = topScorer.getPredecessor();
                players.add(topScorer.getData().getPlayer());
            }
        }
        else
        {
            for(int i=1; i<k; i++)
            {
                topScorer = topScorer.getPredecessor();
                players.add(0, topScorer.getData().getPlayer());
            }
        }
    }

    @Override
    public void getTheWinner(Faculty faculty) {
        faculty = FacultyScoreTree.getMax().getData().getFaculty();
    }

    ///TODO - add below your own variables and methods
}
