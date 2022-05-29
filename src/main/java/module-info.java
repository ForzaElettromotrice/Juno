module org.juno {
	requires javafx.controls;
	requires javafx.fxml;


	opens org.juno to javafx.fxml;
	exports org.juno;
}