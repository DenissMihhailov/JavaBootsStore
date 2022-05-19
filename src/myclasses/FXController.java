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

public class FXController {
    private BuyerFacade buyerFacade;
    private SneakerFacade sneakerFacade;
    private BrandFacade brandFacade;
    private HistoryFacade historyFacade;
    
    public void changeScreenButtonPushed(ActionEvent event) throws IOException{
    Parent tableViewParent = FXMLLoader.load(getClass().getResource("/myclasses/sampleUser.fxml"));
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
    private ToggleGroup addfirm;

    @FXML
    private Button btnAddSneaker;

    @FXML
    private Button btnCloseProgram;

    @FXML
    private Button btnMenuBuySneaker;

    @FXML
    private Button btnMenuIncome;

    @FXML
    private Button btnMenuSneaker;
    
    @FXML
    private Button btnReload;

    @FXML
    private Button btnMenuUser;
    
    @FXML
    private ChoiceBox<String> dropDownMenuHide;
   
    @FXML
    private ChoiceBox<Brand> dropDownMenu;
    
    @FXML
    private RadioButton radioBtnNew;

    @FXML
    private RadioButton radioBtnOld;

    @FXML
    private Text txtFieldAddSneakerInfo;

    @FXML
    private TextField txtFieldFirm;

    @FXML
    private TextField txtFieldModel;

    @FXML
    private TextField txtFieldPrice;

    @FXML
    private TextField txtFieldQuantity;

    @FXML
    private TextField txtFieldSize;
    
    @FXML
    private Button tratre;
    
    public FXController(){
      buyerFacade = new BuyerFacade(Buyer.class);
      sneakerFacade = new SneakerFacade(Sneaker.class);
      brandFacade = new BrandFacade(Brand.class);
      historyFacade = new HistoryFacade(History.class);
    }   

    @FXML
    void initialize() {
        System.out.println("*ДОБАВЛЕНИЕ КРОССОВКА*");  
        Sneaker sneaker= new Sneaker();
        Brand brand = new Brand();
        dropDownMenuHide.setVisible(true);
        txtFieldAddSneakerInfo.setVisible(false);
        txtFieldFirm.setEditable(false);
        btnReload.setVisible(false);
        
        List<Brand> brands = brandFacade.findAll();
        dropDownMenu.setConverter(new StringConverter<Brand>(){
            @Override
            public String toString(Brand object) {
                return object.getBrand();
                
            }

            @Override
            public Brand fromString(String string) {
                return null;
            }
            
        });
        for (int i = 0; i < brands.size(); i++) {
            dropDownMenu.getItems().addAll(brands.get(i));
        }
        

// Нажатие на радиокнопки    
        ToggleGroup tg = new ToggleGroup();
        radioBtnNew.setToggleGroup(tg);
        radioBtnOld.setToggleGroup(tg);
        radioBtnNew.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dropDownMenuHide.setVisible(true);
                dropDownMenu.setValue(null);
                txtFieldFirm.setEditable(true);
                txtFieldFirm.setText("");
            }
        });
        radioBtnOld.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dropDownMenuHide.setVisible(false);
                dropDownMenu.setVisible(true);
                txtFieldFirm.setEditable(false);
                txtFieldFirm.setText("");
                txtFieldFirm.setPromptText("Выберите фирму из списка!");
                
            }
        });
        
        txtFieldSize.textProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue.matches("\\d*")) return;
        txtFieldSize.setText(newValue.replaceAll("[^\\d]", ""));  
        });
        txtFieldQuantity.textProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue.matches("\\d*")) return;
        txtFieldQuantity.setText(newValue.replaceAll("[^\\d]", ""));
        });
        txtFieldPrice.textProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue.matches("\\d*")) return;
        txtFieldPrice.setText(newValue.replaceAll("[^\\d]", ""));
        });
        
        
        
// Нажатие на кнопку добавить кроссовок  
        dropDownMenu.setOnAction(this::getFirm);
        btnAddSneaker.setOnAction((event) -> {
            if (validateFields()) {              
            if (radioBtnNew.isSelected()) {
            brand.setBrand(txtFieldFirm.getText());
            brandFacade.create(brand);  
            sneaker.setSneakerFirm(brand);
            }else if(radioBtnOld.isSelected()){
                String firmId=txtFieldFirm.getText();
                int firm= Integer.parseInt(firmId);
                sneaker.setSneakerFirm(brandFacade.find((long)firm));
            }
            sneaker.setSneakerModel(txtFieldModel.getText());
            int size = Integer.parseInt(txtFieldSize.getText());
            int price = Integer.parseInt(txtFieldPrice.getText());
            int quantity = Integer.parseInt(txtFieldQuantity.getText());
            sneaker.setSneakerSize(size);
            sneaker.setSneakerPrice(price);
            sneaker.setSneakerQuantity(quantity); 
            txtFieldAddSneakerInfo.setVisible(true);
            System.out.println("Вы добавили"+sneaker.toString());
            sneakerFacade.create(sneaker);
            btnReload.setVisible(true);
            }
        });
        
        btnReload.setOnAction((event) -> {
            txtFieldAddSneakerInfo.setVisible(false);
            txtFieldFirm.setText("");
            txtFieldModel.setText("");
            txtFieldPrice.setText("");
            txtFieldQuantity.setText("");
            txtFieldSize.setText("");
            dropDownMenu.getValue();
            btnReload.setVisible(false);
        });
        
        
        //------------------------------------
    }
    public void getFirm(ActionEvent event){
        int firmId=dropDownMenu.getSelectionModel().getSelectedIndex()+1;
        String firm=String.valueOf(firmId);
        txtFieldFirm.setText(firm);
    }
    private boolean validateFields(){
        if(txtFieldFirm.getText().isEmpty() | txtFieldModel.getText().isEmpty() | txtFieldPrice.getText().isEmpty() | txtFieldSize.getText().isEmpty() | txtFieldQuantity.getText().isEmpty()) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Ошибка!");
            alert.setHeaderText(null);
            alert.setContentText("Введите все данные!");
            alert.showAndWait();
            return false;
        }
        return true;
        
    }
}
