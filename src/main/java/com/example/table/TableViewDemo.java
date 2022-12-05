package com.example.table;
import com.example.table.model.PersonList;
import java.sql.SQLException;
import org.sqlite.JDBC;
import com.example.table.model.Person;
import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.io.IOException;
import java.util.List;

public class TableViewDemo extends Application {

    /*
    *Vladimir Berinchik
    */
    private TableColumn<Person, String> userNameCol;
    private TableColumn<Person, String> emailCol;
    private TableColumn<Person, Boolean> activeCol;
    private ObservableList<Person> users;

    private PersonList persons;

    @Override
    public void start(Stage stage) throws IOException, SQLException {

        TableView<Person> table = new TableView<Person>();

        table.setEditable(true);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        createTable(table);

        users = getUserList();
        table.setItems(users);

        StackPane root = new StackPane();
        root.setPadding(new Insets(5));
        root.getChildren().add(table);

        stage.setTitle("Таблица");

        Scene scene = new Scene(root, 500, 300);
        stage.setScene(scene);
        stage.show();
    }

    private ObservableList<Person> getUserList() throws IOException, SQLException {
        DbHandler dbHandler = DbHandler.getInstance();
        List<Person> persons = dbHandler.getAllPersons();
        ObservableList<Person> list = FXCollections.observableArrayList(persons);
        return list;
    }

    private void createTable(TableView<Person> table) {

        userNameCol = new TableColumn<Person, String>("Имя");
        emailCol = new TableColumn<Person, String>("E-mail");
        activeCol = new TableColumn<Person, Boolean>("Активный");

        table.getColumns().addAll(userNameCol, emailCol, activeCol);

        userNameCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        activeCol.setCellValueFactory(new PropertyValueFactory<>("active"));


        userNameCol.setCellFactory(TextFieldTableCell.<Person> forTableColumn());

        userNameCol.setOnEditCommit((TableColumn.CellEditEvent<Person, String> event) -> {

            TablePosition<Person, String> pos = event.getTablePosition();
            String newUserName = event.getNewValue();
            int row = pos.getRow();
            Person person = event.getTableView().getItems().get(row);
            Person newPerson = event.getTableView().getItems().get(row);

            newPerson.setUserName(newUserName);
            try {
                DbHandler dbHandler = DbHandler.getInstance();
                dbHandler.addPerson(newPerson, person);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        userNameCol.setSortType(TableColumn.SortType.ASCENDING);

        activeCol.setCellValueFactory(new Callback<>() {

            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Person, Boolean> param) {
                Person person = param.getValue();

                SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(person.isActive());

                booleanProp.addListener(new ChangeListener<Boolean>() {

                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
                                        Boolean newValue) {
                        person.setActive(newValue);

                        Person newPerson = param.getValue();

                        try {
                            DbHandler dbHandler = DbHandler.getInstance();
                            dbHandler.addPerson(newPerson, person);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                return booleanProp;
            }
        });

        activeCol.setCellFactory(p -> {
            CheckBoxTableCell<Person, Boolean> cell = new CheckBoxTableCell<Person, Boolean>();
            cell.setAlignment(Pos.CENTER);
            return cell;
        });

    }

    public static void main(String[] args) {
        launch(args);
    }
}