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
        Leaf<Team> leaf1 = FacultyIdTree.search(faculty_id1, 0);
        Leaf<Team> leaf2 = FacultyIdTree.search(leaf1.getData().getScore(), faculty_id2);

    }

    @Override
    public void getTopScorer(Player player) {

    }

    @Override
    public void getTopScorerInFaculty(int faculty_id, Player player) {

    }

    @Override
    public void getTopKFaculties(ArrayList<Faculty> faculties, int k, boolean ascending) {

    }

    @Override
    public void getTopKScorers(ArrayList<Player> players, int k, boolean ascending) {

    }

    @Override
    public void getTheWinner(Faculty faculty) {

    }

    ///TODO - add below your own variables and methods
}
