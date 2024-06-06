package com.eduapp.edumanagerapp.Controller;
import com.eduapp.edumanagerapp.Views.RegistrasiViews;
import com.eduapp.edumanagerapp.models.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.*;
import java.io.IOException;
import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
public class DashboardAdmin extends Dashboard {
    private static user dataUser;
    @FXML
    private Label username;
    @FXML
    private TextArea chatTextArea;
    @FXML
    private TextField inputPassword;
    @FXML
    private VBox FormGantiPassword;
    @FXML
    private ListView<Message> chatListView;
    @FXML
    private HBox chatHBox;
    @FXML
    private BorderPane dashboard;
    @FXML
    private TableView<Absensi> tableAbsen;
    @FXML
    private TableView<KasAdmin> tableKas;

    @FXML
    void initialize() {
        chatListView.setVisible(true);
        chatListView.setManaged(true);
        chatHBox.setVisible(true);
        chatHBox.setManaged(true);
        tableAbsen.setVisible(false);
        tableAbsen.setManaged(false);
        tableKas.setVisible(false);
        tableKas.setManaged(false);
        FormGantiPassword.setVisible(false);
        FormGantiPassword.setManaged(false);
        username.setText(getUserData().getNama());
        chatListView.setStyle("-fx-background-color: transparent;");
        chatListView.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(Message message, boolean empty) {
                super.updateItem(message, empty);
                if (empty || message == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    Label senderLabel = new Label(message.getSender() + ": ");
                    Label contentLabel = new Label(message.getContent());
                    Label dateLabel = new Label(message.getDate());
                    senderLabel.setStyle("-fx-font-weight: bold;-fx-text-fill: white ;-fx-font-size: 14");
                    contentLabel.setStyle("-fx-text-fill: white");
                    dateLabel.setStyle("-fx-text-fill: white");
                    VBox messageBox = new VBox(senderLabel, dateLabel, contentLabel);
                    messageBox.setMaxWidth(Double.MAX_VALUE);
                    HBox messageContainer = new HBox(messageBox);
                    messageContainer.setSpacing(10);
                    messageBox.setStyle("-fx-padding: 5;-fx-background-radius: 20");
                    setGraphic(messageContainer);
                    HBox.setHgrow(messageContainer, Priority.ALWAYS);
                    if (message.getSender().equals(dataUser.getNama())) {
                        messageContainer.setAlignment(Pos.CENTER_RIGHT);
                        messageContainer.setStyle("-fx-background-color: white;");
                        messageBox.setAlignment(Pos.CENTER_RIGHT);
                        senderLabel.setAlignment(Pos.CENTER_RIGHT);
                        contentLabel.setAlignment(Pos.CENTER_RIGHT);
                        dateLabel.setAlignment(Pos.CENTER_RIGHT);
                        senderLabel.setStyle("-fx-text-fill: #401F71;-fx-font-weight: bold");
                        contentLabel.setStyle("-fx-text-fill: #401F71;-fx-font-weight: bold");
                        dateLabel.setStyle("-fx-text-fill: #401F71;-fx-font-weight: bold");
                    } else {
                        messageContainer.setStyle("-fx-background-color: #401F71;");
                        messageBox.setAlignment(Pos.CENTER_LEFT);
                        messageContainer.setAlignment(Pos.CENTER_LEFT);
                        senderLabel.setAlignment(Pos.CENTER_LEFT);
                        contentLabel.setAlignment(Pos.CENTER_LEFT);
                        dateLabel.setAlignment(Pos.CENTER_LEFT);
                    }
                }
            }
        });
        loadMessagesFromDatabase();
        startDataUpdateTimeline();
    }

    void loadMessagesFromDatabase() {
        List<Message> messages = new ArrayList<>();
        try (Connection connection = Database.getConnection()) {
            String query = "SELECT nama,pesan,tanggal FROM chat";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String sender = resultSet.getString("nama");
                    String content = resultSet.getString("pesan");
                    String date = resultSet.getString("tanggal");
                    messages.add(new Message(sender, date, content));
                }
            }
            Database.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        chatListView.getItems().setAll(messages);
    }

    void startDataUpdateTimeline() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
            loadMessagesFromDatabase();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public static void setUserData(user user) {
        dataUser = user;
    }

    public static user getUserData() {
        return dataUser;
    }

    public void OnClickButtonDashboard(ActionEvent actionEvent) {
        chatListView.setVisible(true);
        chatListView.setManaged(true);
        chatHBox.setVisible(true);
        chatHBox.setManaged(true);
        tableAbsen.setVisible(false);
        tableAbsen.setManaged(false);
        tableKas.setVisible(false);
        tableKas.setManaged(false);
        FormGantiPassword.setVisible(false);
        FormGantiPassword.setManaged(false);
        loadMessagesFromDatabase();
    }

    public void onClickTableAbsen(ActionEvent actionEvent) {
        chatListView.setVisible(false);
        chatListView.setManaged(false);
        chatHBox.setVisible(false);
        chatHBox.setManaged(false);
        tableAbsen.setVisible(true);
        tableAbsen.setManaged(true);
        tableKas.setVisible(false);
        tableKas.setManaged(false);
        FormGantiPassword.setVisible(false);
        FormGantiPassword.setManaged(false);
        TableAbsen();
    }

    public void onClickTableKas(ActionEvent actionEvent) {
        chatListView.setVisible(false);
        chatListView.setManaged(false);
        chatHBox.setVisible(false);
        chatHBox.setManaged(false);
        tableAbsen.setVisible(false);
        tableAbsen.setManaged(false);
        tableKas.setVisible(true);
        tableKas.setManaged(true);
        FormGantiPassword.setVisible(false);
        FormGantiPassword.setManaged(false);
        TablePembayaran();
    }

    void TablePembayaran() {
        tableKas.getItems().clear();
        tableKas.getColumns().clear();
        try (Connection connection = Database.getConnection()) {
            String query = "SELECT Id, Nama, role FROM users";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("Id");
                    String nama = resultSet.getString("Nama");
                    String role = resultSet.getString("role");
                    tableKas.getItems().add(new KasAdmin(id, nama, role));
                }
            }
            Database.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        TableColumn<KasAdmin, String> first = new TableColumn<>("Nama");
        first.setCellValueFactory(new PropertyValueFactory<>("nama"));

        TableColumn<KasAdmin, String> second = new TableColumn<>("Role");
        second.setCellValueFactory(new PropertyValueFactory<>("role"));

        // Kolom-kolom untuk tombol-tombol Lunas dan Belum Bayar
        TableColumn<KasAdmin, Void> lunasColumn = createKasButtonColumn("Lunas", "-fx-background-color: green;-fx-text-fill:white;");
        TableColumn<KasAdmin, Void> belumBayarColumn = createKasButtonColumn("Belum Bayar", "-fx-background-color: red; -fx-text-fill:white;");

        // Tambahkan kolom-kolom ke dalam tabel
        tableKas.getColumns().addAll(first, second, lunasColumn, belumBayarColumn);
        tableKas.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    }

    TableColumn<KasAdmin, Void> createKasButtonColumn(String buttonText, String buttonStyle) {
        TableColumn<KasAdmin, Void> column = new TableColumn<>(buttonText);
        column.setCellFactory(param -> new TableCell<>() {
            private final Button button = createButton(buttonText, buttonStyle);

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(button);
                    button.setOnAction(event -> {
                        TableRow<KasAdmin> row = getTableRow();
                        if (row != null) {
                            int index = row.getIndex();
                            KasAdmin pembayaran = getTableView().getItems().get(index);
                            if (PaymentValidation.isPaymentValid(pembayaran.getId())) {
                                if (!isPaymentExists(pembayaran.getId())) {
                                    int answer = JOptionPane.showConfirmDialog(null, "Are you sure you want to add the payment status " + buttonText + "?", "Confirmation", JOptionPane.YES_NO_OPTION);
                                    if ((answer == JOptionPane.YES_OPTION)) {
                                        updatePaymentInDatabase(pembayaran.getId(), buttonText);
                                        JOptionPane.showMessageDialog(null, "Payment status " + buttonText + " added successfully");
                                    }
                                } else {
                                    updatePaymentInDatabase(pembayaran.getId(), buttonText);
                                    JOptionPane.showMessageDialog(null, "The payment for today has been added. Please wait for one week");
                                }
                            } else {
                                if (PaymentValidation.getPaymentStatus(pembayaran.getId()).equals("Belum Bayar") && buttonText.equals("Lunas")) {
                                    int answer = JOptionPane.showConfirmDialog(null, "Are you sure you want to change the payment status " + buttonText + "?", "Confirmation", JOptionPane.YES_NO_OPTION);
                                    if ((answer == JOptionPane.YES_OPTION)) {
                                        PaymentValidation.updatePaymentStatus(pembayaran.getId(), "Lunas",getIdTransaksi(pembayaran.getId()));
                                        JOptionPane.showMessageDialog(null, "The payment status has been updated to Paid successfully");
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null, "You cannot make payment again yet. Please wait for one week.");
                                }
                            }
                        }
                    });
                }
            }
        });
        return column;
    }

    boolean isPaymentExists(int userId) {
        try (Connection connection = Database.getConnection()) {
            String query = "SELECT COUNT(*) FROM kas WHERE Id_User = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, userId);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
            Database.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Fungsi untuk menyisipkan pembayaran baru untuk minggu ini jika belum ada
    void insertPaymentForThisWeek(Connection connection, int userId, String newPaymentStatus) {
        String query = "INSERT INTO kas (Id_user, Keterangan, Tanggal) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, newPaymentStatus);
            preparedStatement.setDate(3, Date.valueOf(LocalDate.now()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void updatePaymentInDatabase(int userId, String newPaymentStatus) {
        try (Connection connection = Database.getConnection()) {
            boolean paymentExistsThisWeek = isPaymentExistsForThisWeek(userId);
            if (!paymentExistsThisWeek) {
                insertPaymentForThisWeek(connection, userId, newPaymentStatus);
            } else {
                boolean paymentPendingLastWeek = isPaymentPendingForLastWeek(userId);
                if (paymentPendingLastWeek && newPaymentStatus.equals("Lunas")) {
                    return;
                }
                updatePaymentStatusForThisWeek(userId);
            }
            Database.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    boolean isPaymentExistsForThisWeek(int userId) {
        LocalDate startDateOfWeek = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endDateOfWeek = startDateOfWeek.plusDays(6);
        boolean paymentExists = false;
        try (Connection connection = Database.getConnection()) {
            String query = "SELECT COUNT(*) FROM kas WHERE id_user = ? AND tanggal BETWEEN ? AND ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, userId);
                preparedStatement.setDate(2, Date.valueOf(startDateOfWeek));
                preparedStatement.setDate(3, Date.valueOf(endDateOfWeek));
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    paymentExists = count > 0;
                }
            }
            Database.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return paymentExists;
    }

    boolean isPaymentPendingForLastWeek(int userId) {
        boolean paymentPending = false;
        try (Connection connection = Database.getConnection()) {
            LocalDate startDateOfLastWeek = LocalDate.now().minusWeeks(1).with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
            String query = "SELECT COUNT(*) FROM kas WHERE Id_User = ? AND Tanggal BETWEEN '?' AND '?' AND Keterangan = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, userId);
                preparedStatement.setDate(2, Date.valueOf(startDateOfLastWeek));
                preparedStatement.setDate(3, Date.valueOf(startDateOfLastWeek.plusDays(6)));
                preparedStatement.setString(4, "Belum Bayar");

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int count = resultSet.getInt(1);
                        paymentPending = count > 0;
                    }
                }
            }
            Database.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return paymentPending;
    }

    void updatePaymentStatusForThisWeek(int userId) {
        try (Connection connection = Database.getConnection()) {
            String updateQuery = "UPDATE kas SET keterangan = 'Lunas' WHERE id_user = ? AND id_transaksi = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setInt(1, userId);
                preparedStatement.setInt(2, userId);
                preparedStatement.executeUpdate();
            }
            Database.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    int getIdTransaksi(int userId) {
        int id = 0;
        try (Connection connection = Database.getConnection()) {
            String query = "SELECT id_transaksi FROM kas WHERE id_user = ? AND tanggal = (SELECT MIN(tanggal) FROM kas WHERE keterangan = 'Belum Bayar' AND id_user = ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, userId);
                preparedStatement.setInt(2, userId);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    id = resultSet.getInt(1);
                }
            }
            Database.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    void TableAbsen() {
        tableAbsen.getItems().clear();
        tableAbsen.getColumns().clear();
        try (Connection connection = Database.getConnection()) {
            String query = "SELECT id, nama, role FROM users";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String nama = resultSet.getString("nama");
                    String role = resultSet.getString("role");
                    tableAbsen.getItems().add(new Absensi(id, nama, role));
                }
            }
            Database.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        TableColumn<Absensi, String> first = new TableColumn<>("Nama");
        first.setCellValueFactory(new PropertyValueFactory<>("nama"));
        TableColumn<Absensi, String> second = new TableColumn<>("Role");
        second.setCellValueFactory(new PropertyValueFactory<>("role"));
        // Kolom-kolom untuk tombol-tombol hadir, izin, sakit, dan alpha
        TableColumn<Absensi, Void> hadirColumn = createButtonColumn("Hadir", "-fx-background-color: green; -fx-text-fill: white;");
        TableColumn<Absensi, Void> izinColumn = createButtonColumn("Izin", "-fx-background-color: gray; -fx-text-fill: white;");
        TableColumn<Absensi, Void> sakitColumn = createButtonColumn("Sakit", "-fx-background-color: red; -fx-text-fill: white;");
        TableColumn<Absensi, Void> alphaColumn = createButtonColumn("Alpha", "-fx-background-color: black; -fx-text-fill: white;");
        // Tambahkan kolom-kolom ke dalam tabel
        tableAbsen.getColumns().addAll(first, second, hadirColumn, izinColumn, sakitColumn, alphaColumn);
        tableAbsen.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    }

    TableColumn<Absensi, Void> createButtonColumn(String buttonText, String buttonStyle) {
        TableColumn<Absensi, Void> column = new TableColumn<>(buttonText);
        column.setCellFactory(param -> new TableCell<>() {
            private final Button button = createButton(buttonText, buttonStyle);

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(button);
                    button.setOnAction(event -> {
                        TableRow<Absensi> row = getTableRow();
                        if (row != null) {
                            int index = row.getIndex();
                            Absensi absensi = getTableView().getItems().get(index);
                            // Lakukan pengecekan apakah data yang sama sudah ada di database sebelum menambahkan entri baru
                            if (!isAbsensiExists(absensi.getId())) {
                                updateAbsensiInDatabase(absensi.getId(), buttonText);
                                JOptionPane.showMessageDialog(null, "Absensi " + buttonText + " Added Successfully");
                            } else {
                                JOptionPane.showMessageDialog(null, "The user has been absent today");
                            }
                        }
                    });
                }
            }
        });
        return column;
    }
    Button createButton(String text, String style) {
        Button button = new Button(text);
        button.setStyle(style);
        return button;
    }
    boolean isAbsensiExists(int userId) {
        boolean exists = false;
        try (Connection connection = Database.getConnection()) {
            String query = "SELECT COUNT(*) FROM tb_absen WHERE id_user = ? AND Tanggal = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, userId);
                preparedStatement.setDate(2, java.sql.Date.valueOf(java.time.LocalDate.now()));
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    exists = count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }
    void updateAbsensiInDatabase(int userId, String newAbsensi) {
        try (Connection connection = Database.getConnection()) {
            String query = "INSERT INTO tb_absen (id_user, keterangan, tanggal) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, userId);
                preparedStatement.setString(2, newAbsensi);
                preparedStatement.setDate(3, Date.valueOf(LocalDate.now()));
                preparedStatement.executeUpdate();
            }
            Database.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void onSendButtonClick(ActionEvent actionEvent) {
        String message = chatTextArea.getText();
        if (!message.isEmpty()) {
            try (Connection connection = Database.getConnection()) {
                String query = "INSERT INTO chat (nama,Id_user,Pesan,tanggal) VALUES (?,?,?,?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, getUserData().getNama());
                    preparedStatement.setInt(2, getUserData().getId());
                    preparedStatement.setString(3, message);
                    LocalDateTime dateTime = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    String timestamp = dateTime.format(formatter);
                    preparedStatement.setString(4, timestamp);
                    preparedStatement.executeUpdate();
                }
                Database.closeConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            chatTextArea.clear();
            loadMessagesFromDatabase();
        }
    }
    void GantiPassword() {
        try (Connection connection = Database.getConnection()) {
            String query = "UPDATE users SET Password = ? WHERE Id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, inputPassword.getText());
                preparedStatement.setInt(2, getUserData().getId());
                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(null, "Password Changed Successfully");
            }
            Database.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void onClickGantiPassword(ActionEvent actionEvent) {
        int answer = JOptionPane.showConfirmDialog(null, "Are you sure you want to change the password?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if ((answer == JOptionPane.YES_OPTION)) {
            GantiPassword();
        } else {
            inputPassword.clear();
        }
    }
    public void onClickButtonShowFormGantiPassword(ActionEvent actionEvent) {
        chatListView.setVisible(false);
        chatListView.setManaged(false);
        chatHBox.setVisible(false);
        chatHBox.setManaged(false);
        FormGantiPassword.setVisible(true);
        FormGantiPassword.setManaged(true);
        tableAbsen.setVisible(false);
        tableAbsen.setManaged(false);
        tableKas.setVisible(false);
        tableKas.setManaged(false);
        FormGantiPassword.setMaxWidth(450);
        inputPassword.clear();
    }
    public void onClickLogout(ActionEvent actionEvent) throws IOException {
        int answer = JOptionPane.showConfirmDialog(null, "Are you sure you want to logout?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if ((answer == JOptionPane.YES_OPTION)) {
            Stage currentStage = (Stage) dashboard.getScene().getWindow();
            currentStage.close();
            RegistrasiViews helloApp = new RegistrasiViews(new Stage());
        }
    }
}