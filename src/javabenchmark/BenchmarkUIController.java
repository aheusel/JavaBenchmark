/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javabenchmark;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

/**
 *
 * @author Alexander Heusel
 */
public class BenchmarkUIController implements Initializable
{
    
    @FXML
    private LineChart lineChart;
    
    @FXML
    private Button startButton;
    
    @FXML
    private ProgressBar progressBar;
        
    @FXML
    private ChoiceBox choiceBox;
    
    @FXML
    private Label description;
    
    @FXML
    private void handleStartButtonAction(ActionEvent event)
    {
        System.out.println("You clicked me!");
        description.setText((String)choiceBox.getSelectionModel().getSelectedItem());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        choiceBox.getItems().clear();
        choiceBox.getItems().addAll("eins", "zwei", "drei", "vier");
    }    
}
