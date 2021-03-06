package myclasses;

import entity.Brand;
import entity.Buyer;
import entity.History;
import entity.Sneaker;
import facade.BrandFacade;
import facade.BuyerFacade;
import facade.HistoryFacade;
import facade.SneakerFacade;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


    
public class FXControllerBuy {
    private BuyerFacade buyerFacade;
    private SneakerFacade sneakerFacade;
    private BrandFacade brandFacade;
    private HistoryFacade historyFacade;
    
    
    @FXML
    void changeScreenButtonPushed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/myclasses/sample.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
}
    
    @FXML
    void changeScreenButtonPushedUser(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/myclasses/sampleUser.fxml"));
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
    private Button btnAddPerhapse;

    @FXML
    private Button btnCloseProgram;

    @FXML
    private Button btnMenuIncome;

    @FXML
    private Button btnReload;

    @FXML
    private AnchorPane dropDownEmpty;

    @FXML
    private AnchorPane gggg;

    @FXML
    private ListView<Sneaker> listSneaker;

    @FXML
    private ListView<Buyer> listUser;

    @FXML
    private Text txtFieldAddPerhapseInfo;

    public FXControllerBuy(){
      buyerFacade = new BuyerFacade(Buyer.class);
      sneakerFacade = new SneakerFacade(Sneaker.class);
      brandFacade = new BrandFacade(Brand.class);
      historyFacade = new HistoryFacade(History.class);
    }   

    @FXML
    void initialize() {
        btnReload.setVisible(true);
        txtFieldAddPerhapseInfo.setVisible(false);
        
        btnAddPerhapse.setOnAction((event) -> {
            Long sneakerNum = listSneaker.getSelectionModel().getSelectedItem().getId();
            Long buyerNum = listUser.getSelectionModel().getSelectedItem().getId();
            validateFields();
            
            int n=0;
            while(n==0){
                History history= new History();
                history.setSneaker(sneakerFacade.find((long) sneakerNum));
                history.setBuyer(buyerFacade.find((long)buyerNum));
                Calendar c = new GregorianCalendar();
                history.setGivenSneaker(c.getTime());
                if(history.getBuyer().getBuyerMoney()>=history.getSneaker().getSneakerPrice() && history.getSneaker().getSneakerQuantity()!=0){
                txtFieldAddPerhapseInfo.setStyle("-fx-fill: #00ff2b;");
                txtFieldAddPerhapseInfo.setText("???? ???????????????? ?????????????? ?? ????????!");
                txtFieldAddPerhapseInfo.setVisible(true);
                history.getBuyer().setBuyerMoney(history.getBuyer().getBuyerMoney()-history.getSneaker().getSneakerPrice());
                history.getSneaker().setSneakerQuantity(history.getSneaker().getSneakerQuantity()-1);
                sneakerFacade.edit(history.getSneaker());
                buyerFacade.edit(history.getBuyer());
                historyFacade.create(history);
                btnReload.setVisible(true);

                    n++;
                }else if(history.getBuyer().getBuyerMoney()<history.getSneaker().getSneakerPrice()){
                    txtFieldAddPerhapseInfo.setStyle("-fx-fill: red;");
                    txtFieldAddPerhapseInfo.setText("???????? ???????????????????? ???? ?????????? ?????????????????? ??????????????, ?????? ?????? ?? ???????? ???? ?????????????? ??????????!");
                    txtFieldAddPerhapseInfo.setVisible(true);
                    n++;
                }else if(history.getSneaker().getSneakerQuantity()==0){
                    txtFieldAddPerhapseInfo.setStyle("-fx-fill: red;");
                    txtFieldAddPerhapseInfo.setText("?????????? ???????????? ?????? ?? ??????????????, ???????????????? ????????????!");
                    txtFieldAddPerhapseInfo.setVisible(true);
                    n++;
                }
            }   
        });
        
        btnReload.setOnAction((event) -> {
            btnReload.setVisible(false);
            txtFieldAddPerhapseInfo.setVisible(false);
            List<Sneaker> sneakers = sneakerFacade.findAll();
            for (int i = 0; i < sneakers.size(); i++) {
               listSneaker.getItems().addAll(sneakers.get(i));
            }
            List<Buyer> buyers = buyerFacade.findAll();
                for (int i = 0; i < buyers.size(); i++) {
                    listUser.getItems().addAll(buyers.get(i));
            }
        });

}
    private boolean validateFields(){
        if(listUser.getItems().isEmpty() | listSneaker.getItems().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("????????????!");
            alert.setHeaderText(null);
            alert.setContentText("?????????????? ?????? ????????????!");
            alert.showAndWait();
            return false;
        }
        return true;
        
        }
}

