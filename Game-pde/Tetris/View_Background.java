

public class View_Background extends View_Abstract
{
    public float BACKGROUND_RED = 230;
    public float BACKGROUND_GREEN = 230;
    public float BACKGROUND_BLUE = 230;
    public float BACKGROUND_RECTANGLE_STROKE_RED = 0;
    public float BACKGROUND_RECTANGLE_STROKE_GREEN = 0;
    public float BACKGROUND_RECTANGLE_STROKE_BLUE = 0;
    public float BACKGROUND_PADDING = 5;

    /**
     * Create view.
     */
    public View_Background(Controller_Background controller_background, View_Tetris view) {
        super(controller_background, view);
    }

    public View_Tetris getParentView() {
        return (View_Tetris) super.getParentView();
    }

    public Controller_Background getController() {
        return (Controller_Background)super.getController();
    }

    public void draw() {
        // Background.
        this.getParentView().getApp().background(BACKGROUND_RED, BACKGROUND_GREEN, BACKGROUND_BLUE);

        // Rectangle.
        this.getParentView().getApp().noFill();
        this.getParentView().getApp().stroke(BACKGROUND_RECTANGLE_STROKE_RED,
                BACKGROUND_RECTANGLE_STROKE_GREEN,
                BACKGROUND_RECTANGLE_STROKE_BLUE
        );
        this.getParentView().getApp().rect(BACKGROUND_PADDING, BACKGROUND_PADDING,
                this.getParentView().WIDTH - BACKGROUND_PADDING * 2, this.getParentView().HEIGHT - BACKGROUND_PADDING * 2);

        /*
        if (this.getController().getParentController().isGameOver() == true && this.getParentView().resultsPopup == 1){
            var players = [];for (Controller_PlayerAbstract cp : this.getController().getParentController().getPlayerControllers()) {players.push({n: cp.getPlayerName(), s: cp.getScore(), m : cp.getNumberOfMoves(), ti: new Date().getTime(), ty: cp.getModel().getType(), l: Controller_Tetris.NUMBER_OF_LINES_TETRIS_GRID, c: Controller_Tetris.NUMBER_OF_COLUMNS_TETRIS_GRID, d: View_Player_Grid.DRAW_YOUR_OWN_BLOCKS ? 1 :0});}this.getParentView().resultsPopup++;players.sort(function(a,b){if (a.score < b.score) { return -1; } return a.score == b.score ? 0 : 1;});
            gameOver(players);
        }
        */
    }
}
