package mvc.views;

import javax.swing.*;
import connectmongodb.ConnectMongoDb;

public class App extends JFrame {
    public App() {
        setVisible(true);
        setSize(600, 600);
        setTitle("PatientManagementSystem");
        setLocationRelativeTo(null);
        new ConnectMongoDb();
    }
}
