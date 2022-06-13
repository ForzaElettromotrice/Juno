module org.juno
{
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.media;

	opens org.juno to javafx.fxml;
	exports org.juno;
	exports org.juno.controller;
}