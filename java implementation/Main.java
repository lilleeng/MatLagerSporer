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

