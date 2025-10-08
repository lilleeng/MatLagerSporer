import controller.Controller;
import model.DataBase;
import view.TerminalView;

public class Main {

    public static void main(String args[]) {
        // EventBus bus = new EventBus();
        DataBase db = new DataBase();
        TerminalView view = new TerminalView(db);
        view.flushTerminal();
        view.printLoadingScreen();
        db.loadDataBase();
        view.openDefaultScreen();
        Controller cont = new Controller(view);
        while (cont.PROGRAM_IS_RUNNING) {
            cont.takeCommand();
        }
        

        /*
         * How to update ECA values?
         * -> need association table of items to expiration rate.
         * 
         * Functionality to auto-create expiration rates?
         * Later improvement: update expiration rates by weighted average
         * 
         * Removed timing cases:
         * Expected timing does not exist   -> Create new timing based on available data
         * At expected timing               -> Do nothing / Strengthen weight
         * Differing timing                 -> Weighted average (constant ratio / calculated ratio)
         * 
         * Item     Exp. rate
         * Gulost   1/14
         * 
         * 
         * 
         * 
         * :: the eventbus paradigm is not strictly necessary.
         *    cont needs only send signals to view, view only to
         *    model. they need only a field variable of each other
         * 
         * :: If uncareful, the eventbus paradigm can create a loop
         *    of ever deeper subroutines. If the program is
         *    sufficiently large, this could cause a problem.
         * 
         * :: with terminal-view the eventbus paradigm is a liability.
         *    a while-loop is much simpler.
         */
        
        
    }
}
