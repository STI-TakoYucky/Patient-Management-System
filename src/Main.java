import mvc.views.App;
import mvc.views.LogIn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

public class Main extends JFrame {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        new LogIn();
        //comment
    }
}
