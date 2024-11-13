module co.edu.uniquindio.herramientagestionderiesgos {
    requires javafx.controls;
    requires javafx.fxml;
    requires kernel;
    requires layout;
    requires io;
    requires java.desktop;
    requires javafx.swing;


    opens co.edu.uniquindio.herramientagestionderiesgos to javafx.fxml;
    exports co.edu.uniquindio.herramientagestionderiesgos;
    exports co.edu.uniquindio.herramientagestionderiesgos.gestorRiesgos;
    opens co.edu.uniquindio.herramientagestionderiesgos.gestorRiesgos to javafx.fxml;
}