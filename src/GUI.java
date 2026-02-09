import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

public class GUI extends Application {

    private Stage primaryStage;
    private TextArea consoleOutput; // Replaces System.out

    // Backend Objects
    private UserRegistration userReg = new UserRegistration();
    private Administrator adminTools = new Administrator();
    private Library library = new Library();

    public static void main(String[] args) {

        createFilesIfMissing();

        launch(args);

    }

    private static void createFilesIfMissing() {
        String[] files = {"customers.txt", "admins.txt", "books.txt", "borrowed.txt"};

        for (String f : files) {

            java.io.File file = new java.io.File(f);

            try { file.createNewFile(); } catch (Exception e) {}

        }
    }

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        primaryStage.setTitle("Library Management System");


        consoleOutput = new TextArea();
        consoleOutput.setEditable(false);
        consoleOutput.setWrapText(true);
        consoleOutput.setStyle("-fx-font-family: 'Consolas'; -fx-font-size: 14;");

        redirectSystemOut();


        showLoginScene();

        primaryStage.show();
    }


    private void showLoginScene() {
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        //Login
        Tab loginTab = new Tab("Log In");

        GridPane loginGrid = createGrid();

        TextField loginUser = new TextField();
        PasswordField loginPass = new PasswordField();
        ComboBox<String> roleBox = new ComboBox<>();
        roleBox.getItems().addAll("Customer", "Administrator");
        roleBox.getSelectionModel().selectFirst();

        Button loginBtn = new Button("Login");

        loginGrid.add(new Label("Username:"), 0, 0);
        loginGrid.add(loginUser, 1, 0);
        loginGrid.add(new Label("Password:"), 0, 1);
        loginGrid.add(loginPass, 1, 1);
        loginGrid.add(new Label("Role:"), 0, 2);
        loginGrid.add(roleBox, 1, 2);
        loginGrid.add(loginBtn, 1, 3);

        loginBtn.setOnAction(e -> {
            String u = loginUser.getText();

            String p = loginPass.getText();

            if (roleBox.getValue().equals("Customer")) {

                LogIn.LogInCustomer(u, p);

            } else {

                LogIn.LogInAdmin(u, p);

            }

            if (Main.currentUser != null) {

                showDashboardScene();

            } else {

                showAlert("Login Failed", "Invalid credentials.");

            }

        });

        loginTab.setContent(loginGrid);

        //Register
        Tab regTab = new Tab("Register");

        GridPane regGrid = createGrid();

        TextField regUser = new TextField();
        PasswordField regPass = new PasswordField();
        TextField regEmail = new TextField();
        TextField regPhone = new TextField();

        ComboBox<String> regType = new ComboBox<>();
        regType.getItems().addAll("Customer", "Administrator");
        regType.getSelectionModel().selectFirst();

        Button regBtn = new Button("Register");

        regGrid.add(new Label("Username:"), 0, 0);
        regGrid.add(regUser, 1, 0);
        regGrid.add(new Label("Password:"), 0, 1);
        regGrid.add(regPass, 1, 1);
        regGrid.add(new Label("Email:"), 0, 2);
        regGrid.add(regEmail, 1, 2);
        regGrid.add(new Label("Phone:"), 0, 3);
        regGrid.add(regPhone, 1, 3);
        regGrid.add(new Label("Type:"), 0, 4);
        regGrid.add(regType, 1, 4);
        regGrid.add(regBtn, 1, 5);

        regBtn.setOnAction(e -> {
            if (regType.getValue().equals("Customer")) {

                userReg.registerCustomer(regUser.getText(), regPass.getText(), regEmail.getText(), regPhone.getText(), "Standard");

                showAlert("Success", "Customer registered! Please log in.");

            } else {

                // Ask for admin password
                TextInputDialog dialog = new TextInputDialog();
                dialog.setHeaderText("Enter Master Admin Password ");

                Optional<String> result = dialog.showAndWait();
                if (result.isPresent()) {

                    userReg.checkAdminPass(result.get());

                    if (userReg.getPassed()) {

                        userReg.registerAdmin(regUser.getText(), regPass.getText(), regEmail.getText(), regPhone.getText());

                        showAlert("Success", "Admin registered! Please log in.");

                    } else {

                        showAlert("Error", "Wrong Master Password.");

                    }
                }
            }
        });

        regTab.setContent(regGrid);

        tabPane.getTabs().addAll(loginTab, regTab);

        Scene scene = new Scene(tabPane, 400, 350);

        primaryStage.setScene(scene);

    }

    private void showDashboardScene() {

        BorderPane root = new BorderPane();

        // Position info in top
        Label welcome = new Label("Logged in as: " + Main.currentUser.getUserName() + " (" + Main.currentUser.getRole() + ")");

        welcome.setPadding(new Insets(10));

        welcome.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");

        root.setTop(welcome);

        // Position output in center
        root.setCenter(consoleOutput);

        // Button positioning
        VBox buttons = new VBox(10);
        buttons.setPadding(new Insets(10));
        buttons.setStyle("-fx-background-color: #f0f0f0;");
        buttons.setPrefWidth(200);


        //Add book button
        if (Main.currentUser instanceof Administrator) {

            Button btnAdd = new Button("Add Book");

            btnAdd.setMaxWidth(Double.MAX_VALUE);

            btnAdd.setOnAction(e -> showAddBookDialog());

            buttons.getChildren().add(btnAdd);

        }

        //borrow book button
        if (Main.currentUser instanceof Customer) {

            Button btnBorrow = new Button("Borrow Book");

            btnBorrow.setMaxWidth(Double.MAX_VALUE);

            btnBorrow.setOnAction(e -> showBorrowDialog());

            buttons.getChildren().add(btnBorrow);

        }

        // return book button
        if (Main.currentUser instanceof Customer) {

            Button btnReturn = new Button("Return Book");

            btnReturn.setMaxWidth(Double.MAX_VALUE);

            btnReturn.setOnAction(e -> showReturnDialog());

            buttons.getChildren().add(btnReturn);

        }

        // remove book button
        if (Main.currentUser instanceof Administrator) {

            Button btnRemove = new Button("Remove Book");

            btnRemove.setMaxWidth(Double.MAX_VALUE);

            btnRemove.setOnAction(e -> showRemoveDialog());

            buttons.getChildren().add(btnRemove);

        }

        // filter button
        Button btnFilter = new Button("Filter Books");

        btnFilter.setMaxWidth(Double.MAX_VALUE);

        btnFilter.setOnAction(e -> showFilterDialog());

        buttons.getChildren().add(btnFilter);


        // search book button
        Button btnSearch = new Button("Search Book Title");

        btnSearch.setMaxWidth(Double.MAX_VALUE);

        btnSearch.setOnAction(e -> showSearchDialog());

        buttons.getChildren().add(btnSearch);

        // profile button
        Button btnProfile = new Button("Show Profile");

        btnProfile.setMaxWidth(Double.MAX_VALUE);

        btnProfile.setOnAction(e -> System.out.println(Main.currentUser));

        buttons.getChildren().add(btnProfile);

        // update book button
        if (Main.currentUser instanceof Administrator) {

            Button btnUpdate = new Button("Update Book");

            btnUpdate.setMaxWidth(Double.MAX_VALUE);

            btnUpdate.setOnAction(e -> showUpdateDialog());

            buttons.getChildren().add(btnUpdate);

        }

        // fines button
        if (Main.currentUser instanceof Administrator) {

            Button btnFines = new Button("Check Fines");

            btnFines.setMaxWidth(Double.MAX_VALUE);

            btnFines.setOnAction(e -> adminTools.calculateOverdueFines());

            buttons.getChildren().add(btnFines);

        }

        // search users button
        if (Main.currentUser instanceof Administrator) {

            Button btnSearchUsers = new Button("Search Users");

            btnSearchUsers.setMaxWidth(Double.MAX_VALUE);

            btnSearchUsers.setOnAction(e -> showUserSearchDialog());

            buttons.getChildren().add(btnSearchUsers);

        }

        // extend borrow time button
        if (Main.currentUser instanceof Customer) {

            Button btnExtend = new Button("Extend Book");

            btnExtend.setMaxWidth(Double.MAX_VALUE);

            btnExtend.setOnAction(e -> showExtendDialog());

            buttons.getChildren().add(btnExtend);

        }

        // log out button
        Button btnLogout = new Button("Log Out");

        btnLogout.setStyle("-fx-background-color: #ffcccc;");

        btnLogout.setMaxWidth(Double.MAX_VALUE);

        btnLogout.setOnAction(e -> {

            LogIn.LogOut();

            consoleOutput.clear();

            showLoginScene();

        });

        buttons.getChildren().add(new Separator());

        buttons.getChildren().add(btnLogout);

        ScrollPane scrollButtons = new ScrollPane(buttons);

        scrollButtons.setFitToWidth(true);

        root.setRight(scrollButtons);

        Scene scene = new Scene(root, 900, 600);

        primaryStage.setScene(scene);

        primaryStage.centerOnScreen();
    }


    // pop up window where user enters the info

    private void showAddBookDialog() {

        Dialog<ButtonType> dialog = new Dialog<>();

        dialog.setTitle("Add Book");

        GridPane grid = createGrid();

        TextField t1 = new TextField(); t1.setPromptText("Title");

        TextField t2 = new TextField(); t2.setPromptText("Author");

        TextField t3 = new TextField(); t3.setPromptText("Genre");

        TextField t4 = new TextField(); t4.setPromptText("ISBN");

        TextField t5 = new TextField(); t5.setPromptText("Year");

        grid.add(new Label("Title:"), 0, 0); grid.add(t1, 1, 0);

        grid.add(new Label("Author:"), 0, 1); grid.add(t2, 1, 1);

        grid.add(new Label("Genre:"), 0, 2); grid.add(t3, 1, 2);

        grid.add(new Label("ISBN:"), 0, 3); grid.add(t4, 1, 3);

        grid.add(new Label("Year:"), 0, 4); grid.add(t5, 1, 4);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.showAndWait().ifPresent(response -> {

            if (response == ButtonType.OK) {

                try {

                    adminTools.addBook(t1.getText(), t2.getText(), t3.getText(), t4.getText(), Integer.parseInt(t5.getText()));

                } catch (Exception e) { System.out.println("Invalid Year format."); }
            }
        });
    }

    private void showBorrowDialog() {

        Dialog<ButtonType> dialog = new Dialog<>();

           dialog.setTitle("Borrow Book");

        GridPane grid = createGrid();

         TextField t1 = new TextField(); t1.setPromptText("Title");
          TextField t2 = new TextField(); t2.setPromptText("Author");
        TextField t3 = new TextField(); t3.setPromptText("Days");

         grid.add(new Label("Title:"), 0, 0); grid.add(t1, 1, 0);
          grid.add(new Label("Author:"), 0, 1); grid.add(t2, 1, 1);
        grid.add(new Label("Days:"), 0, 2); grid.add(t3, 1, 2);


          dialog.getDialogPane().setContent(grid);

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.showAndWait().ifPresent(response -> {

            if (response == ButtonType.OK) {

                try {

                    ((Customer)Main.currentUser).borrowBook(t1.getText(), t2.getText(), LocalDate.now(), Integer.parseInt(t3.getText()));

                } catch (Exception e) { System.out.println("Invalid Day format."); }

            }
        });
    }

        private void showReturnDialog() {

        TextInputDialog td = new TextInputDialog();

        td.setHeaderText("Enter Title to Return:");

              td.showAndWait().ifPresent(title -> {

            TextInputDialog ad = new TextInputDialog();

            ad.setHeaderText("Enter Author:");

            ad.showAndWait().ifPresent(author -> {

                ((Customer)Main.currentUser).returnBook(title, author);

            });
        });
    }

    private void showRemoveDialog() {

        TextInputDialog td = new TextInputDialog();

          td.setHeaderText("Remove Book Title:");

        td.showAndWait().ifPresent(title -> {

            TextInputDialog ad = new TextInputDialog();

            ad.setHeaderText("Author:");


            ad.showAndWait().ifPresent(author -> {

                adminTools.removeBook(title, author);

            });
        });
    }

    private void showSearchDialog() {

        TextInputDialog td = new TextInputDialog();

             td.setHeaderText("Enter Title to Search:");

        td.showAndWait().ifPresent(search -> {

            System.out.println("--- SEARCH RESULTS ---");


            try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader("books.txt"))) {
                String line;

                while ((line = reader.readLine()) != null) {

                       if (line.toLowerCase().contains(search.toLowerCase())) System.out.println(line);

                }
            } catch (IOException e) { System.out.println("Error."); }
            System.out.println("----------------------");
        });
    }

    private void showFilterDialog() {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Filter");

        alert.setHeaderText("Choose Filter Type");

         ButtonType genreBtn = new ButtonType("Genre");
             ButtonType yearBtn = new ButtonType("Year");
        ButtonType availBtn = new ButtonType("Available");
              ButtonType cancelBtn = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(genreBtn, yearBtn, availBtn, cancelBtn);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {

            System.out.println("\n--- FILTER RESULTS ---");

            if (result.get() == genreBtn) {

                TextInputDialog d = new TextInputDialog();

                d.setHeaderText("Enter Genre:");

                d.showAndWait().ifPresent(g -> printList(library.filterByGenre(g)));
            }

            else if (result.get() == yearBtn) {

                      TextInputDialog d = new TextInputDialog();

                   d.setHeaderText("Enter Start Year:");

                d.showAndWait().ifPresent(start -> {

                       TextInputDialog d2 = new TextInputDialog();

                    d2.setHeaderText("Enter End Year:");

                       d2.showAndWait().ifPresent(end -> {

                        printList(library.filterByYear(Integer.parseInt(start), Integer.parseInt(end)));

                    });
                });
            }

            else if (result.get() == availBtn) {

                    printList(library.getAvailableBooks());

            }

            System.out.println("----------------------");
        }
    }

    private void showUserSearchDialog() {

              TextInputDialog td = new TextInputDialog();


        td.setHeaderText("Enter Username to Search:");

        td.showAndWait().ifPresent(u -> {


               System.out.println("Searching for: " + u);

            searchFile("customers.txt", u);

              searchFile("admins.txt", u);
        });
    }

     private void searchFile(String file, String user) {

             try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(file))) {
               String line;

                 while((line = br.readLine()) != null){

                if(line.contains("Username: " + user)) System.out.println("Found: " + line);

            }


        } catch(Exception e){}
    }

        private void showExtendDialog() {

        TextInputDialog td = new TextInputDialog();

               td.setHeaderText("Enter Book Title to Extend:");

                     td.showAndWait().ifPresent(t -> ((Customer)Main.currentUser).requestExtension(t));
    }

    private void showUpdateDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();

        dialog.setTitle("Update Book");

        GridPane grid = createGrid();

        TextField tTitle = new TextField(); tTitle.setPromptText("Current Title");

              TextField tAuthor = new TextField(); tAuthor.setPromptText("Current Author");
            ComboBox<String> type = new ComboBox<>();
        type.getItems().addAll("Title", "Author", "Genre", "Year", "Availability");
        TextField tNewVal = new TextField(); tNewVal.setPromptText("New Value");


        grid.add(new Label("Book Title:"), 0, 0); grid.add(tTitle, 1, 0);

         grid.add(new Label("Book Author:"), 0, 1); grid.add(tAuthor, 1, 1);

        grid.add(new Label("Update Field:"), 0, 2); grid.add(type, 1, 2);

            grid.add(new Label("New Value:"), 0, 3); grid.add(tNewVal, 1, 3);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.showAndWait().ifPresent(r -> {
            if (r == ButtonType.OK && type.getValue() != null) {
                String val = tNewVal.getText();
                switch (type.getValue()) {

                     case "Title": adminTools.updateBookTitle(tTitle.getText(), tAuthor.getText(), val); break;

                        case "Author": adminTools.updateBookAuthor(tTitle.getText(), tAuthor.getText(), val); break;

                       case "Genre": adminTools.updateBookGenre(tTitle.getText(), tAuthor.getText(), val); break;

                        case "Year": adminTools.updateBookYear(tTitle.getText(), tAuthor.getText(), Integer.parseInt(val)); break;

                        case "Availability": adminTools.updateBookAvailability(tTitle.getText(), tAuthor.getText(), Boolean.parseBoolean(val)); break;
                }
            }
        });
    }


    private void printList(ArrayList<Book> list) {

        if(list.isEmpty()) System.out.println("No books found.");

        else for(Book b : list) System.out.println(b);
    }

    private GridPane createGrid() {

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setAlignment(Pos.CENTER);

        return grid;
    }

    private void showAlert(String title, String content) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle(title);

        alert.setContentText(content);

        alert.showAndWait();
    }


    private void redirectSystemOut() {

        OutputStream out = new OutputStream() {

            @Override
            public void write(int b) throws IOException {

                appendText(String.valueOf((char) b));

            }
            @Override
            public void write(byte[] b, int off, int len) throws IOException {

                appendText(new String(b, off, len));

            }
            private void appendText(String text) {

                Platform.runLater(() -> consoleOutput.appendText(text));

            }

        };

        System.setOut(new PrintStream(out, true));
    }
}