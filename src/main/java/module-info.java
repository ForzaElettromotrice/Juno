module org.juno
{
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.media;

	opens org.juno to javafx.fxml;
	exports org.juno;
	exports org.juno.model.table;
	exports org.juno.model.table.classic;
	exports org.juno.controller;
	exports org.juno.view;
	exports org.juno.model.deck;
	opens org.juno.view to javafx.fxml;
	exports org.juno.datapackage;
	opens org.juno.datapackage to javafx.fxml;
}