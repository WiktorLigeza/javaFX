package sample;

import com.company.DataManagement;
import com.company.Enum.SaleState;
import com.company.GenerateData;
import com.company.Models.FulfillmentCenterContainer;
import com.company.Models.FulfillmentCenterModel;
import com.company.Models.ItemModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;

public class Controller implements Initializable {

    ////declarations
    FulfillmentCenterContainer fcc = new FulfillmentCenterContainer();
    ObservableList<FulfillmentCenterModel> centersList;
    FulfillmentCenterModel selectedCenter;
    ObservableList<ItemModel> selectedCenterItems;
    List<ItemModel> basket = new ArrayList<>();
    Comparator<ItemModel> comparator;



    ////Products site FXML
    @FXML private ComboBox<FulfillmentCenterModel> centersComboBox;
    @FXML private TableView<ItemModel> productTable;
    @FXML private TableColumn<ItemModel, String> nameColumn;
    @FXML private TableColumn<ItemModel, Double> priceColumn;
    @FXML private TableColumn<ItemModel, Integer> quantityColumn;
    @FXML private TableColumn<ItemModel, FulfillmentCenterModel> centerColumn;
    @FXML private TextField search;



    //Basket site FXML
    @FXML private TableView<ItemModel> basketTable;
    @FXML private TableColumn<ItemModel, String> nameColumnBasket;
    @FXML private TableColumn<ItemModel, Double> priceColumnBasket;
    @FXML private TableColumn<ItemModel, FulfillmentCenterModel> centerColumnBasket;
    @FXML private Label totalCost;
    @FXML private Label itemsNum;



    //buttons
    public void setSortByNameBTN(){
        comparator = Comparator.comparing(item -> item.name);
        exhibit(comparator);
    }
    public void setSortByPriceBTN(){
        comparator = Comparator.comparing(item -> item.price);
        exhibit(comparator);
    }
    public void setSortByQuantityBTN(){
        comparator = Comparator.comparing(item -> item.quantity);
        exhibit(comparator);
    }
    public void setAddToBasketBTN(){
        ItemModel selectedItem = productTable.getSelectionModel().getSelectedItem();
        if(selectedItem == null) {MessageBox("you have to select something"); }
        else{

            int j = inputMessageBox(selectedItem);
            for (int i = 0;i<j; i++){
                basket.add(selectedItem);
                selectedItem.parent.getProduct(selectedItem);
            }
            refreshProductsTable();
            basketTable.setItems(FXCollections.observableArrayList(basket));
            totalCost();
        }


    }
    public void setRemoveBTN(){
        ItemModel selectedItem = basketTable.getSelectionModel().getSelectedItem();
        if(selectedItem == null){MessageBox("you have to select something");}

        else {
            selectedItem.parent.addProduct(selectedItem);
            basket.remove(selectedItem);
            basketTable.setItems(FXCollections.observableArrayList(basket));
            totalCost();
            refreshProductsTable();
        }
    }
    public void setPurchaseBTN(){
        basket = new ArrayList<>();
        basketTable.setItems(FXCollections.observableArrayList(basket));
        totalCost();
    }



    ///methods
    public List<ItemModel> getAllItems(){
        List<ItemModel> temp = new ArrayList<>();
        for (FulfillmentCenterModel fcm : fcc.CentersList) {
            if(fcm.itemsList.size()>0){
                temp.addAll(fcm.itemsList);
            }
        }
        return temp;
    }
    public class TooltipTableRow<T> extends TableRow<T> {

        private Function<T, String> toolTipStringFunction;

        public TooltipTableRow(Function<T, String> toolTipStringFunction) {
            this.toolTipStringFunction = toolTipStringFunction;
        }

        @Override
        protected void updateItem(T item, boolean empty) {
            super.updateItem(item, empty);
            if(item == null) {
                setTooltip(null);
            } else {
                Tooltip tooltip = new Tooltip(toolTipStringFunction.apply(item));
                setTooltip(tooltip);
            }
        }
    }
    public void comboBoxWasUpdated(){
        selectedCenter = centersComboBox.getSelectionModel().getSelectedItem();
        if(selectedCenter.name.equals("ALL")){
            selectedCenterItems= FXCollections.observableArrayList(getAllItems());
        }
        else selectedCenterItems = FXCollections.observableArrayList(selectedCenter.itemsList);
        productTable.setItems(selectedCenterItems);

    }
    public void setSearch(){
        List<ItemModel> matchings = new ArrayList<ItemModel>();
        for (ItemModel item : selectedCenterItems) {
            boolean isFound = item.name.contains(search.getText());
            if(isFound) {
                matchings.add(item);
            }
        }
        productTable.setItems(FXCollections.observableArrayList(matchings));
    }
    public void exhibit(Comparator<ItemModel> cmp){
        selectedCenterItems = selectedCenterItems.sorted(comparator);
        productTable.setItems(selectedCenterItems);
    }
    public void totalCost(){
        double total = 0;
        for (ItemModel item : basket) {
            total += item.price;
        }
        itemsNum.setText(basket.size()+" items");
        totalCost.setText("Total cost: "+total);

    }
    private void MessageBox(String output){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message");
        alert.setHeaderText("ERROR!");
        alert.setContentText(output);
        alert.showAndWait();
    }
    private void refreshProductsTable(){
        if(selectedCenter.name.equals("ALL")){
            productTable.refresh();
            selectedCenterItems = FXCollections.observableArrayList(getAllItems());
        }
        else{
            productTable.refresh();
            selectedCenterItems = FXCollections.observableArrayList(selectedCenter.itemsList);
        }
        productTable.setItems(selectedCenterItems);
    }
    private int inputMessageBox(ItemModel item){
        TextInputDialog dialog = new TextInputDialog("0");
        dialog.setTitle("Input Dialog");
        dialog.setHeaderText("Quantity");
        dialog.setContentText("Please enter number of items:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            Integer num = Integer.valueOf(result.get());
            if(num>item.quantity){
                MessageBox("There are not enough products in stock ");
            }
            else return num;
        }
        return 0;
    }
    public void saveCurrentState(){
        try {
            DataManagement.saveCurrentStateToCSV(fcc.CentersList);
        } catch (IOException e) {
            MessageBox("could not save");
        }
    }





//*********************************************** INIT **************************************************\\
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

//////////////////////////////////////////Data access\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
        List<FulfillmentCenterModel> fromFile = new ArrayList<>();
        // save generated data (dummy file already created, if not - do it)
//        try {
//            DataManagement.saveCurrentStateToCSV(GenerateData.getProductData().CentersList);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        // load initial data (from dummy file)
        try {
            fromFile = DataManagement.importDataFromCSV();
        } catch (IOException | ClassNotFoundException e) {
            MessageBox("Ain't no data");
        }
        fcc.CentersList = fromFile;
        centersList = FXCollections.observableArrayList(fcc.CentersList);

//////////////////////////////////////////GUI\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
        //set combobox
        centersList.add(new FulfillmentCenterModel("ALL",0,""));
        centersComboBox.setItems(centersList);
        centersComboBox.getSelectionModel().select(centersList.size()-1);
        comboBoxWasUpdated();

        //set products table
        setCustomRowFactory(productTable, nameColumn, priceColumn, quantityColumn, centerColumn);
        productTable.setItems(selectedCenterItems = FXCollections.observableArrayList(getAllItems()));

        //set textfield
        search.textProperty().addListener((observable, oldValue, newValue) -> {
            setSearch();
        });

        //set basket table
        setCustomRowFactory(basketTable, nameColumnBasket, priceColumnBasket, quantityColumn, centerColumnBasket);

    }


    //set column factory
    private void setCustomRowFactory(TableView<ItemModel> TableModel,
                                     TableColumn<ItemModel, String> nameColumnModel,
                                     TableColumn<ItemModel, Double> priceColumnModel,
                                     TableColumn<ItemModel, Integer> quantityColumnModel,
                                     TableColumn<ItemModel, FulfillmentCenterModel> centerColumnModel) {
        TableModel.setRowFactory((tableView) -> {
            //set tooltip
            return new TooltipTableRow<ItemModel>((ItemModel item) -> {
                return  "state: "+item.state+
                        "\nweight: "+item.weight+
                        "\n------------center info------------"+
                        "\ncenter: " + item.parent.name+
                        "\ncity: "+item.parent.City+
                        "\nfill lvl: "+(int)item.parent.fillLvl()+"%"+
                        "\nmax capacity: "+item.parent.maximumCapacity;
            });
        });
        nameColumnModel.setCellValueFactory(new PropertyValueFactory<ItemModel,String>("name"));
        priceColumnModel.setCellValueFactory(new PropertyValueFactory<ItemModel,Double>("price"));
        quantityColumnModel.setCellValueFactory(new PropertyValueFactory<ItemModel,Integer>("quantity"));
        centerColumnModel.setCellValueFactory(new PropertyValueFactory<ItemModel,FulfillmentCenterModel>("parent"));
    }

}


