module org.juno
{
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.media;

	opens org.juno to javafx.fxml;
	exports org.juno;
	exports org.juno.controller;
	exports org.juno.view;
	opens org.juno.view to javafx.fxml;
	exports org.juno.datapackage;
	opens org.juno.datapackage to javafx.fxml;
}