package com.eduapp.edumanagerapp.Controller;
import com.eduapp.edumanagerapp.Views.RegistrasiViews;
import com.eduapp.edumanagerapp.models.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class DashboardAnggota extends Dashboard {
    private static user dataUser;
    @FXML
    private Label username;
    @FXML
    private TextArea chatTextArea;
    @FXML
    private PieChart pieChartAbsen;
    @FXML
    private PieChart pieChartKas;
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
    void initialize() {
        chatListView.setVisible(true);
        chatListView.setManaged(true);
        chatHBox.setVisible(true);
        chatHBox.setManaged(true);
        pieChartAbsen.setVisible(false);
        pieChartAbsen.setManaged(false);
        pieChartKas.setVisible(false);
        pieChartKas.setManaged(false);
        FormGantiPassword.setVisible(false);
        FormGantiPassword.setManaged(false);
        // Atur item factory untuk menampilkan pesan sebagai card chat di ListView
        username.setText(getUserData().getNama());
        chatListView.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(Message message, boolean empty) {
                super.updateItem(message, empty);
                if (empty || message == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    // Membuat card chat
                    Label senderLabel = new Label(message.getSender() + ": ");
                    Label contentLabel = new Label(message.getContent());
                    Label dateLabel = new Label(message.getDate());
                    senderLabel.setStyle("-fx-font-weight: bold;-fx-text-fill: #401F71");
                    contentLabel.setStyle("-fx-text-fill: #401F71");
                    dateLabel.setStyle("-fx-text-fill: #401F71");
                    VBox messageBox = new VBox(senderLabel, dateLabel, contentLabel);
                    messageBox.setMaxWidth(Double.MAX_VALUE); // Mengisi lebar penuh parent
                    HBox messageContainer = new HBox(messageBox);
                    messageBox.setStyle("-fx-padding: 5;-fx-background-radius: 20");
                    setGraphic(messageContainer);
                    HBox.setHgrow(messageContainer, Priority.ALWAYS);
                    if (message.getSender().equals(dataUser.getNama())) {
                        // Atur posisi kontainer pesan ke kanan
                        messageContainer.setAlignment(Pos.CENTER_RIGHT);
                        messageContainer.setStyle("-fx-background-color: #401F71;");
                        messageBox.setAlignment(Pos.CENTER_RIGHT);
                        senderLabel.setAlignment(Pos.CENTER_RIGHT);
                        contentLabel.setAlignment(Pos.CENTER_RIGHT);
                        dateLabel.setAlignment(Pos.CENTER_RIGHT);
                        senderLabel.setStyle("-fx-text-fill: #FFFFFF;-fx-font-weight: bold");
                        contentLabel.setStyle("-fx-text-fill: #FFFFFF;-fx-font-weight: bold");
                        dateLabel.setStyle("-fx-text-fill: #FFFFFF;-fx-font-weight: bold");
                    } else {
                        messageContainer.setStyle("-fx-background-color: #FFFFFF;");
                        messageBox.setAlignment(Pos.CENTER_LEFT);
                        messageContainer.setAlignment(Pos.CENTER_LEFT);
                        senderLabel.setAlignment(Pos.CENTER_LEFT);
                        contentLabel.setAlignment(Pos.CENTER_LEFT);
                        dateLabel.setAlignment(Pos.CENTER_LEFT);
                    }
                }
            }
        });


        // Memuat data pesan dari database saat inisialisasi
        loadMessagesFromDatabase();

        // Memulai Timeline untuk memperbarui data setiap 5 detik
        startDataUpdateTimeline();
    }

    // Method untuk memuat data pesan dari database
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

    // Method untuk memulai Timeline untuk memperbarui data setiap 5 detik
    void startDataUpdateTimeline() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
            // Memuat kembali data pesan dari database setiap 5 detik
            loadMessagesFromDatabase();
            loadAbsensiFromDatabase();
            setPieChartKasData();


        }));
        timeline.setCycleCount(Timeline.INDEFINITE); // Timeline akan berulang terus
        timeline.play(); // Mulai Timeline
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
        pieChartAbsen.setVisible(false);
        pieChartAbsen.setManaged(false);
        pieChartKas.setVisible(false);
        pieChartKas.setManaged(false);
        FormGantiPassword.setVisible(false);
        FormGantiPassword.setManaged(false);
        loadMessagesFromDatabase();
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

    public void onClickButtonAbsen(ActionEvent actionEvent) {
        chatListView.setVisible(false);
        chatListView.setManaged(false);
        chatHBox.setVisible(false);
        chatHBox.setManaged(false);
        pieChartAbsen.setVisible(true);
        pieChartAbsen.setManaged(true);
        pieChartKas.setVisible(false);
        pieChartKas.setManaged(false);
        FormGantiPassword.setVisible(false);
        FormGantiPassword.setManaged(false);
        loadAbsensiFromDatabase();

    }

    void loadAbsensiFromDatabase() {
        try (Connection connection = Database.getConnection()) {
            String query = "SELECT Id_User,Tanggal, Keterangan FROM tb_absen WHERE Id_User = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, getUserData().getId());
                ResultSet resultSet = preparedStatement.executeQuery();
                List<DataAbsensi> dataList = new ArrayList<>();
                while (resultSet.next()) {
                    int idUser = resultSet.getInt("Id_User");
                    String tanggal = resultSet.getString("Tanggal");
                    String keterangan = resultSet.getString("Keterangan");
                    dataList.add(new DataAbsensi(idUser, tanggal, keterangan));
                }
                // Set data absen ke dalam pie chart
                setPieChartDataAbsen(dataList);

            }
            Database.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setPieChartDataAbsen(List<DataAbsensi> dataList) {
        // Inisialisasi peta untuk menghitung jumlah setiap status
        Map<String, Integer> statusCountMap = new HashMap<>();
        int totalData = dataList.size();
        // Iterasi melalui data dan menghitung jumlah setiap status
        for (DataAbsensi data : dataList) {
            String status = data.getKeterangan();
            statusCountMap.put(status, statusCountMap.getOrDefault(status, 0) + 1);
        }
        pieChartAbsen.getData().clear();
        for (Map.Entry<String, Integer> entry : statusCountMap.entrySet()) {
            double percentage = (entry.getValue() / (double) totalData) * 100;
            String label = String.format("%s %.2f%% (%d)", entry.getKey(), percentage, entry.getValue());
            PieChart.Data pieChartData = new PieChart.Data(label, entry.getValue());
            pieChartAbsen.getData().add(pieChartData);
        }
        pieChartAbsen.setTitle("Data Absen " + getUserData().getNama());
        pieChartAbsen.setMinHeight(450);
    }

    private List<Kas> loadKasDataFromDatabase() {
        List<Kas> dataList = new ArrayList<>();
        try (Connection connection = Database.getConnection()) {
            String query = "SELECT Id_User, Tanggal, Keterangan FROM kas WHERE Id_User = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, getUserData().getId());
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int idUser = resultSet.getInt("Id_User");
                    String tanggal = resultSet.getString("Tanggal");
                    String keterangan = resultSet.getString("Keterangan");
                    dataList.add(new Kas(idUser, tanggal, keterangan));
                }
            }
            Database.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    private void setPieChartKasData() {
        List<Kas> kasDataList = loadKasDataFromDatabase();

        // Inisialisasi peta untuk menghitung jumlah setiap status kas
        Map<String, Integer> statusCountMap = new HashMap<>();
        int totalData = kasDataList.size();

        // Iterasi melalui data absensi kas dan menghitung jumlah setiap status
        for (Kas data : kasDataList) {
            String status = data.getKeterangan();
            statusCountMap.put(status, statusCountMap.getOrDefault(status, 0) + 1);
        }

        pieChartKas.getData().clear();
        for (Map.Entry<String, Integer> entry : statusCountMap.entrySet()) {
            double percentage = (entry.getValue() / (double) totalData) * 100;
            String label = String.format("%s %.2f%% (%d)", entry.getKey(), percentage, entry.getValue());
            PieChart.Data pieChartData = new PieChart.Data(label, entry.getValue());
            pieChartKas.getData().add(pieChartData);
        }
        pieChartKas.setTitle("Data Kas " + getUserData().getNama());
        pieChartKas.setMinHeight(450);
    }

    public void onClickButtonKas(ActionEvent actionEvent) {
        chatListView.setVisible(false);
        chatListView.setManaged(false);
        chatHBox.setVisible(false);
        chatHBox.setManaged(false);
        pieChartAbsen.setVisible(false);
        pieChartAbsen.setManaged(false);
        pieChartKas.setVisible(true);
        pieChartKas.setManaged(true);
        FormGantiPassword.setVisible(false);
        FormGantiPassword.setManaged(false);
        setPieChartKasData();
    }

    void GantiPassword() {
        try (Connection connection = Database.getConnection()) {
            String query = "UPDATE users SET Password = ? WHERE Id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, inputPassword.getText());
                preparedStatement.setInt(2, getUserData().getId());
                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(null, "Password Successfully Changed");
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
        pieChartAbsen.setVisible(false);
        pieChartAbsen.setManaged(false);
        pieChartKas.setVisible(false);
        pieChartKas.setManaged(false);
        FormGantiPassword.setVisible(true);
        FormGantiPassword.setManaged(true);
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