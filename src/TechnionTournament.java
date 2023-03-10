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
        Node<Team> idLeaf = new Node<Team>(faculty.getId(),0, newTeam);
        Node<Team> scoreLeaf = new Node<Team>(0, faculty.getId(), newTeam);
        FacultyIdTree.insert(idLeaf);
        FacultyScoreTree.insert(scoreLeaf);
    }

    @Override
    public void removeFacultyFromTournament(int faculty_id){
        Node<Team> leaf = FacultyIdTree.search(faculty_id, 0);
        int score = leaf.getData().getScore();
        FacultyIdTree.delete(leaf);
        FacultyScoreTree.delete(FacultyScoreTree.search(score, faculty_id));
    }

    @Override
    public void addPlayerToFaculty(int faculty_id,Player player) {
        Player newPlayer = new Player(player.getId(), player.getName());
        PlayerCard newPlayerCard = new PlayerCard(newPlayer);
        Node<PlayerCard> playerLeaf = new Node<PlayerCard>(0,newPlayer.getId(), newPlayerCard);
        PlayerScoreTree.insert(playerLeaf);
        Node<Team> teamLeaf = FacultyIdTree.search(faculty_id, 0);
        teamLeaf.getData().addPlayerToTeam(newPlayerCard);
    }

    @Override
    public void removePlayerFromFaculty(int faculty_id, int player_id) {
        Node<Team> leaf = FacultyIdTree.search(faculty_id, 0);
        PlayerCard player = leaf.getData().getPlayerCard(player_id);
        //int score = player.getGoalNum();
        leaf.getData().removePlayerFromTeam(player);
    }

    @Override
    public void playGame(int faculty_id1, int faculty_id2, int winner,
                         ArrayList<Integer> faculty1_goals, ArrayList<Integer> faculty2_goals) {
        Node<Team> leaf1id = FacultyIdTree.search(faculty_id1, 0);
        Node<Team> leaf2id = FacultyIdTree.search(faculty_id2, 0);
        Node<Team> leaf1score = FacultyScoreTree.search(leaf1id.getData().getScore(), faculty_id1);
        Node<Team> leaf2score = FacultyScoreTree.search(leaf2id.getData().getScore(), faculty_id2);
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
            Node<Team> teamLeaf= leaf1id;
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
            for (int i = 0; i < teamLeaf.getData().getPlayers().size(); i++)
            {
                if (totalScore[i] != 0)
                {
                    PlayerCard player = teamLeaf.getData().getPlayers().get(i);
                    Node<PlayerCard> playerLeaf = PlayerScoreTree.search(player.getGoalNum(), player.getPlayer().getId());
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
        Player topScorer =  PlayerScoreTree.getMax().getData().getPlayer();
        player.setName(topScorer.getName());
        player.setId(topScorer.getId());
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
        player.setId(maxPlayer.getPlayer().getId());
        player.setName(maxPlayer.getPlayer().getName());
    }

    @Override
    public void getTopKFaculties(ArrayList<Faculty> faculties, int k, boolean ascending) {
        Node<Team> topFaculty = FacultyScoreTree.getMax();
        if (!ascending)
        {
            faculties.add(topFaculty.getData().getFaculty());
            for(int i=1; i<k; i++)
            {
                topFaculty = topFaculty.getPredecessor();
                faculties.add(topFaculty.getData().getFaculty());
            }
        }
        else
        {
            ArrayList<Faculty> helper = new ArrayList<Faculty>();
            helper.add(topFaculty.getData().getFaculty());
            for(int i=1; i<k; i++)
            {
                topFaculty = topFaculty.getPredecessor();
                helper.add(topFaculty.getData().getFaculty());
            }
            for(int i = k-1; i >= 0; i--)
            {
                faculties.add(helper.get(i));
            }
        }

    }

    @Override
    public void getTopKScorers(ArrayList<Player> players, int k, boolean ascending) {
        Node<PlayerCard> topScorer = PlayerScoreTree.getMax();
        if(!ascending)
        {
            players.add(topScorer.getData().getPlayer());
            for(int i=1; i<k; i++)
            {
                topScorer = topScorer.getPredecessor();
                players.add(topScorer.getData().getPlayer());
            }
        }
        else
        {
            ArrayList<Player> helper = new ArrayList<Player>();
            helper.add(topScorer.getData().getPlayer());
            for(int i=1; i<k; i++)
            {
                topScorer = topScorer.getPredecessor();
                helper.add(topScorer.getData().getPlayer());
            }
            for(int i = k-1; i >= 0; i--)
            {
                players.add(helper.get(i));
            }
        }
    }

    @Override
    public void getTheWinner(Faculty faculty) {
        Faculty topFaculty = FacultyScoreTree.getMax().getData().getFaculty();
        faculty.setId(topFaculty.getId());
        faculty.setName(topFaculty.getName());
    }

    ///TODO - add below your own variables and methods
}
