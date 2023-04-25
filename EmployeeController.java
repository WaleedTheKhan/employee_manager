/* Employee Management System 2023
 * Created by: Waleed Khan
 * Last modified: April 10, 2023
 * This is the Controller class for a JavaFX FXML project for managing employees. */
package employee_manager_Khan;

import employee_manager_Khan.EmployeeModel.Employee;

import java.io.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;

public class EmployeeController {

    @FXML
    private Button btnAddEmployees;

    @FXML
    private Button btnClearInputs;

    @FXML
    private Button btnDeleteEmployees;

    @FXML
    private Button btnLoadFromFile;

    @FXML
    private Button btnSaveToFile;

    @FXML
    private CheckBox chkboxFulltimeInfo;

    @FXML
    private ComboBox<String> cmboxJobTitle;

    @FXML
    private ScrollPane employeeScrollPane;

    @FXML
    private Label lbTotalNumEmployees;

    @FXML
    private ListView<EmployeeModel.Employee> lvEmployees;

    @FXML
    private RadioButton rbFemale;

    @FXML
    private RadioButton rbMale;

    @FXML
    private RadioButton rbOther;

    private ToggleGroup genderRbGroup;

    @FXML
    private TextField tfAddEmployeeId;

    @FXML
    private TextField tfAddEmployeeName;

    public void initialize() {
        //grouping RadioButtons to allow singular selection
        genderRbGroup = new ToggleGroup();
        rbMale.setToggleGroup(genderRbGroup);
        rbFemale.setToggleGroup(genderRbGroup);
        rbOther.setToggleGroup(genderRbGroup);

        //adding pre-determined roles to existing ComboBox
        ObservableList<String> jobTitles = FXCollections.observableArrayList("Director","Manager","Developer","Tester","Salesman");
        cmboxJobTitle.setItems(jobTitles);

        //load in existing employee data from specified CSV file when the application is launched
        try {
            Scanner scanTheExistingFile = new Scanner(new File("EmployeesSample.csv"));
                    
            //looping through each line of the existing CSV file for data
            while (scanTheExistingFile.hasNextLine()) {
                String[] existingEmployees = scanTheExistingFile.nextLine().split(",");
                int existingId = Integer.parseInt(existingEmployees[0]);
                String existingName = existingEmployees[1];
                String existingJobTitle = existingEmployees[2];
                boolean existingFullTime = Boolean.parseBoolean(existingEmployees[3]);
                String existingGender = existingEmployees[4];

                //creating a new Employee object from the existing CSV file's data post-processing, then adding it to the ListView
                Employee existingEmployee = new Employee(existingId, existingName, existingJobTitle, existingFullTime, existingGender);
                lvEmployees.getItems().add(existingEmployee);
            }
            scanTheExistingFile.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Whoops! There was a problem pulling the preset file: " + ex.getMessage());
        }

        //updating "TOTAL EMPLOYEES" Label from the get-go, when the application is launched
        int totalNumEmployees = lvEmployees.getItems().size();
        lbTotalNumEmployees.setText(Integer.toString(totalNumEmployees));

    }//end initialize

    @FXML
    void handleBtnAddEmployees(ActionEvent event) {
        //begin reading values from all "ADD EMPLOYEE" data entry fields
        String name = tfAddEmployeeName.getText();
        String enteredEmployeeId = tfAddEmployeeId.getText();
        String jobTitle = cmboxJobTitle.getValue();
        boolean fullTime = chkboxFulltimeInfo.isSelected();
        String gender = ""; //placeholder variable until validation

        //checking if name field is empty
        if (name.isEmpty()) {
            System.out.println("Please enter the employee's name.");
            return;
        }

        //checking if employee ID field is empty
        if (enteredEmployeeId.isEmpty()) {
            System.out.println("Please enter the employee's ID.");
            return;
        }

        //PREVENTATIVE: checking if employee ID field is an non-decimal numerical value before further validation
        int actualEmployeeId = 0;

        try {
            actualEmployeeId = Integer.parseInt(enteredEmployeeId);
        } catch (NumberFormatException e) {
            System.out.println("Invalid! Please enter a positive numerical value (no decimals) for the ID!");
            return;
        }

        //checking if employee ID is a non-positive integer
        if (actualEmployeeId <= 0) {
            System.out.println("Negative values are an invalid ID format! Try again.");
            return;
        }

        //checking if entered employee ID is already in use by an existing employee
        for (Employee anEmployee : lvEmployees.getItems()) {
            if (anEmployee.getId() == actualEmployeeId) {
                System.out.println("Invalid ID! Already in use by an existing employee.");
                return;
            }
        }

        //checking if job title is empty (i.e., not selected)
        if (jobTitle == null || jobTitle.isEmpty()) {
            System.out.println("Please select the employee's job title.");
            return;
        }

        //setting gender from selected RadioButton, or prompting for selection if empty
        if (rbMale.isSelected()) {
            gender = "Male";
        }
        else if (rbFemale.isSelected()) {
            gender = "Female";
        }
        else if (rbOther.isSelected()) {
            gender = "Other";
        }
        else {
            System.out.println("Please select a gender.");
            return;
        }

        //processing data fields post-validation, adding employee to list, and likewise updating ListView and "TOTAL EMPLOYEES"
        EmployeeModel modelAdd = new EmployeeModel(); //creating an instance of the EmployeeModel class for this method
        Employee newEmployee = new Employee(actualEmployeeId, name, jobTitle, fullTime, gender);
        modelAdd.addEmployee(newEmployee);
        lvEmployees.getItems().add(newEmployee);
        int totalNumEmployees = lvEmployees.getItems().size();
        lbTotalNumEmployees.setText(Integer.toString(totalNumEmployees));

        //clearing all of the input fields so that they are ready for re-entry
        tfAddEmployeeName.setText("");
        tfAddEmployeeId.setText("");
        cmboxJobTitle.getSelectionModel().clearSelection();
        chkboxFulltimeInfo.setSelected(false);
        rbMale.setSelected(false);
        rbFemale.setSelected(false);
        rbOther.setSelected(false);

    }//end handleBtnAddEmployees

    @FXML
    void handleBtnClearInputs(ActionEvent event) {
        //clearing all of the input fields
        tfAddEmployeeName.setText("");
        tfAddEmployeeId.setText("");
        cmboxJobTitle.getSelectionModel().clearSelection();
        chkboxFulltimeInfo.setSelected(false);
        rbMale.setSelected(false);
        rbFemale.setSelected(false);
        rbOther.setSelected(false);

    }//end handleBtnClearInputs

    @FXML
    void handleBtnDeleteEmployee(ActionEvent event) {
        //creating an instance of the EmployeeModel class for this method
        EmployeeModel modelDel = new EmployeeModel();

        //getting the employee that the user has currently selected
        EmployeeModel.Employee selectedEmployee = lvEmployees.getSelectionModel().getSelectedItem();

        //removing the user's selected employee from the ListView and clearing the selection
        if (selectedEmployee != null) {
            modelDel.removeEmployee(selectedEmployee);
            lvEmployees.getItems().remove(selectedEmployee);
            lvEmployees.getSelectionModel().clearSelection();
        }

        //updating the "TOTAL EMPLOYEES" Label count once the employee is deleted
        int totalNumEmployees = lvEmployees.getItems().size();
        lbTotalNumEmployees.setText(Integer.toString(totalNumEmployees));

    }//end handleBtnDeleteEmployee

    @FXML
    void handleBtnLoadFromFile(ActionEvent event) {
        //using FileChooser to open up file explorer on the user's PC
        FileChooser chooseYourFile = new FileChooser();
        chooseYourFile.setTitle("Choose a CSV file!");
        
        //restricting the user to CSV files only
        chooseYourFile.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

        //storing whichever file the user picks
        File loadEmployeesFile = chooseYourFile.showOpenDialog(null);

        //creating Scanner object for reading the CSV file's employees' data
        Scanner scanChosenFile = null;

        //reading the employees' data inside of the user's chosen file if it is valid
        if (loadEmployeesFile != null) {
            try {
                //using Scanner to read the file's data
                scanChosenFile = new Scanner(loadEmployeesFile);

                /* NOTE: to deal with a case of duplicate IDs (i.e., an employee in the CSV file
                 * has the same ID number as an employee currently displayed in the ListView), 
                 * pulling all ListView employees' ID numbers and storing them in an ArrayList, 
                 * which will be used to check for matches with any employees in the CSV file */
                ArrayList<Integer> lvEmployeeIds = new ArrayList<Integer>();
                for (Employee theCurrentEmployee : lvEmployees.getItems()) {
                    lvEmployeeIds.add(theCurrentEmployee.getId());
                }

                //looping through and pulling data from each line of the file
                while (scanChosenFile.hasNextLine()) {
                    //storing the current line of the file into a String to validate later, specifying comma as the splitter
                    String currentLine = scanChosenFile.nextLine();
                    String[] employeesData = currentLine.split(",");                  
                    
                    //checking if current line has the necessary five attributes
                    if (employeesData.length != 5) {
                        System.out.println("Sorry, your CSV file is not formatted correctly. Fix it and try again.");
                        scanChosenFile.close();
                        return;
                    }

                    //once five fields are confirmed, checking if they are empty
                    for (String theCurrentField : employeesData) {
                        if (theCurrentField.trim().isEmpty()) {
                            System.out.println("Sorry. Your CSV file's employees' data fields are not formatted correctly. Fix them and try again.");
                            scanChosenFile.close();
                            return;
                        }
                    }

                    //checking if the CSV file's employees' ID numbers are positive integer values with no decimals
                    try {
                        int existingFileId = Integer.parseInt(employeesData[0]);
                        if (existingFileId <= 0) {
                            System.out.println("Sorry. Your CSV file's employees' ID numbers must be positive integer values. Fix them and try again.");
                            scanChosenFile.close();
                            return;
                        }
                    } catch (NumberFormatException ex) {
                        System.out.println("Sorry. Your CSV file's employees' ID numbers must be integer (non-decimal) values. Fix them and try again.");
                        scanChosenFile.close();
                        return;
                    }

                    //fields are not empty and not in an invalid format, begin processing
                    int existingFileId = Integer.parseInt(employeesData[0]);
                    String existingFileName = employeesData[1];
                    String existingFileJobTitle = employeesData[2];
                    boolean existingFileFullTime;
                    String existingFileGender = employeesData[4];

                    //checking if the full-time field is formatted correctly (i.e., true or false)
                    if ("true".equalsIgnoreCase(employeesData[3]) || "false".equalsIgnoreCase(employeesData[3])) {
                        existingFileFullTime = Boolean.parseBoolean(employeesData[3]);
                    }
                    else {
                        System.out.println("Sorry, the employee's full-time status must be either true or false.");
                        scanChosenFile.close();
                        return;
                    }

                    //checking if the gender field is formatted correctly (Male, Female, Other)
                    if(!"Male".equalsIgnoreCase(existingFileGender) && !"Female".equalsIgnoreCase(existingFileGender) && !"Other".equalsIgnoreCase(existingFileGender)) {
                        System.out.println("Sorry, the employee's gender must be Male, Female, or Other.");
                        scanChosenFile.close();
                        return;
                    }

                    //checking if the employee's ID in the file matches with any existing ListView IDs stored in the ArrayList
                    if (lvEmployeeIds.contains(existingFileId)) {
                        System.out.println("An employee in the list already has the ID " + existingFileId + ".\n The corresponding employee in your chosen CSV file was not added.");
                    }
                    else {
                    //creating a new Employee object from the chosen CSV file's data post-processing, then adding it to the ListView
                    Employee chosenEmployee = new Employee(existingFileId, existingFileName, existingFileJobTitle, existingFileFullTime, existingFileGender);
                    lvEmployees.getItems().add(chosenEmployee);

                    //updating "TOTAL EMPLOYEES" after new bunch of employees are added from the user's file
                    int totalNumEmployees = lvEmployees.getItems().size();
                    lbTotalNumEmployees.setText(Integer.toString(totalNumEmployees));
                    }
                }
                scanChosenFile.close();
                scanChosenFile = new Scanner(loadEmployeesFile);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } finally { //final close to avoid Scanner resource leak
                if (scanChosenFile != null) {
                    scanChosenFile.close();
                }
            }
        }

    }//end handleBtnLoadFromFile

    @FXML
    void handleBtnSaveToFile(ActionEvent event) {
        try {
            PrintWriter writeToFile = new PrintWriter(new File("UpdatedEmployees.csv"));

            //pulling current employee data from the ListView
            ObservableList<Employee> yourUpdatedEmployees = lvEmployees.getItems();

            //looping through each employee in the list, writing their data to a CSV file
            for (Employee currentEmployee : yourUpdatedEmployees) {
                writeToFile.write(currentEmployee.getId() + "," + currentEmployee.getName() + "," + currentEmployee.getJob() + ","
                + currentEmployee.isFulltime() + "," + currentEmployee.getGender() + "\n");
            }
            writeToFile.close();
            System.out.println("Saved current employees' list to CSV file.");
        } catch (FileNotFoundException ex) {
            System.out.println("Whoops! Failed to write existing data to CSV file: " + ex.getMessage());
        }
        
    }//end handleBtnSaveToFile

}//end class EmployeeController
