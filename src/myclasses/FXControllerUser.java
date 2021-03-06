package myclasses;

import entity.Brand;
import entity.Buyer;
import entity.History;
import entity.Sneaker;
import static entity.UserRoles_.user;
import facade.BrandFacade;
import facade.BuyerFacade;
import facade.HistoryFacade;
import facade.RoleFacade;
import facade.SneakerFacade;
import facade.UserFacade;
import facade.UserRolesFacade;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.NumberStringConverter;

public class FXControllerUser {
    private BuyerFacade buyerFacade;
    private SneakerFacade sneakerFacade;
    private BrandFacade brandFacade;
    private HistoryFacade historyFacade;
    
    public void changeScreenButtonPushed(ActionEvent event) throws IOException{
    Parent tableViewParent = FXMLLoader.load(getClass().getResource("/myclasses/sample.fxml"));
    Scene tableViewScene = new Scene(tableViewParent);

    Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

    window.setScene(tableViewScene);
    window.show();
    }
    public void changeScreenButtonPushedBuy(ActionEvent event) throws IOException{
    Parent tableViewParent = FXMLLoader.load(getClass().getResource("/myclasses/sampleBuy.fxml"));
    Scene tableViewScene = new Scene(tableViewParent);

    Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

    window.setScene(tableViewScene);
    window.show();
    }
    
    @FXML
    public void handleCloseButtonAction(ActionEvent event) {
    ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }  
    
     @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnAddUser;

    @FXML
    private Button btnCloseProgram;

    @FXML
    private Button btnMenuBuySneaker;

    @FXML
    private Button btnMenuIncome;

    @FXML
    private Button btnReload;

    @FXML
    private AnchorPane dropDownEmpty;

    @FXML
    private AnchorPane gggg;

    @FXML
    private Text txtFieldAddSneakerInfo;

    @FXML
    private TextField txtFieldFirstName;

    @FXML
    private TextField txtFieldLastName;

    @FXML
    private TextField txtFieldPhone;
    
    @FXML
    private TextField txtFieldMoney;
    
    public FXControllerUser(){
      buyerFacade = new BuyerFacade(Buyer.class);
      sneakerFacade = new SneakerFacade(Sneaker.class);
      brandFacade = new BrandFacade(Brand.class);
      historyFacade = new HistoryFacade(History.class);
    }   

    @FXML
    void initialize() {
        txtFieldAddSneakerInfo.setVisible(false);
        btnReload.setVisible(false);
        
        txtFieldMoney.textProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue.matches("\\d*")) return;
        txtFieldMoney.setText(newValue.replaceAll("[^\\d]", ""));  
        });
        
        btnAddUser.setOnAction((event) -> {
            validateFields();
            System.out.println("*???????????????????? ????????????????????*");
            Buyer buyer= new Buyer();
            buyer.setBuyerFirstName(txtFieldFirstName.getText());
            buyer.setBuyerLastName(txtFieldLastName.getText());
            buyer.setBuyerPhone(txtFieldPhone.getText());
            buyer.setBuyerMoney(Integer.parseInt(txtFieldMoney.getText()));
            System.out.println("???? ???????????????? "+buyer.toString());
            txtFieldAddSneakerInfo.setVisible(true);
            btnReload.setVisible(true);
            buyerFacade.create(buyer);
        });
        btnReload.setOnAction((event) -> {
            txtFieldAddSneakerInfo.setVisible(false);
            btnReload.setVisible(false);
            txtFieldFirstName.setText("");
            txtFieldLastName.setText("");
            txtFieldMoney.setText("");
            txtFieldPhone.setText("");
        });
        
    }
        private boolean validateFields(){
        if(txtFieldFirstName.getText().isEmpty() | txtFieldLastName.getText().isEmpty() | txtFieldMoney.getText().isEmpty() | txtFieldPhone.getText().isEmpty()) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("????????????!");
            alert.setHeaderText(null);
            alert.setContentText("?????????????? ?????? ????????????!");
            alert.showAndWait();
            return false;
        }
        return true;
        
        }}

