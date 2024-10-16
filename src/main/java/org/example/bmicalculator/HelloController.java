package org.example.bmicalculator;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class HelloController {

    @FXML
    private TextField heightField;

    @FXML
    private TextField weightField;

    @FXML
    private TextField bmiResultField;

    @FXML
    private TextField idealWeightField;

    @FXML
    private TextField bmiCategoryField;

    @FXML
    private TextField bodyFatField;

    @FXML
    public void calculateBMI() {
        try {
            String heightText = heightField.getText().trim();
            String weightText = weightField.getText().trim();

            double height = 0.0;
            double weight = 0.0;

            // Check if height is in feet and inches or cm
            if (heightText.contains("'") || heightText.contains("\"")) {
                // Assuming input is in feet and inches (e.g., 5'10")
                String[] heightParts = heightText.split("[\"' ]");
                double feet = Double.parseDouble(heightParts[0].trim());
                double inches = Double.parseDouble(heightParts[1].trim());
                height = (feet * 12 + inches) * 2.54; // Convert feet and inches to cm
            } else {
                // Assuming input is in cm
                height = Double.parseDouble(heightText);
            }

            // Check if weight is in pounds or kg
            if (weightText.toLowerCase().contains("lbs")) {
                weight = Double.parseDouble(weightText.toLowerCase().replace("lbs", "").trim()) * 0.453592; // Convert lbs to kg
            } else {
                weight = Double.parseDouble(weightText); // Assuming input is in kg
            }

            double heightInMeters = height / 100; // Convert height from cm to meters
            double bmi = weight / (heightInMeters * heightInMeters);

            // Calculate ideal weight range
            double minIdealWeight = 18.5 * (heightInMeters * heightInMeters);
            double maxIdealWeight = 24.9 * (heightInMeters * heightInMeters);

            // Display results
            bmiResultField.setText(String.format("%.2f", bmi));
            idealWeightField.setText(String.format("%.1f to %.1f", minIdealWeight, maxIdealWeight));

            // Determine BMI category
            String bmiCategory = getBMICategory(bmi);
            bmiCategoryField.setText(bmiCategory);

            // Estimate Body Fat Percentage
            double bodyFatPercentage = estimateBodyFatPercentage(bmi);
            bodyFatField.setText(String.format("%.1f%%", bodyFatPercentage));

        } catch (NumberFormatException e) {
            // Handle input errors with an alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText("Invalid Input");
            alert.setContentText("Please enter valid height and weight values.");
            alert.showAndWait();
        } catch (Exception e) {
            // Handle any other exceptions
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Unexpected Error");
            alert.setHeaderText("An error occurred");
            alert.setContentText("Please check your inputs and try again.");
            alert.showAndWait();
        }
    }

    // Method to classify BMI
    private String getBMICategory(double bmi) {
        if (bmi < 18.5) {
            return "Underweight";
        } else if (bmi >= 18.5 && bmi <= 24.9) {
            return "Normal Weight";
        } else if (bmi >= 25 && bmi <= 29.9) {
            return "Overweight";
        } else {
            return "Obesity";
        }
    }

    // Method to estimate Body Fat Percentage (simple estimation)
    private double estimateBodyFatPercentage(double bmi) {
        if (bmi < 18.5) {
            return 10 + (bmi - 18.5) * 1.5;  // Just an estimation
        } else if (bmi >= 18.5 && bmi <= 24.9) {
            return 18.5 + (bmi - 18.5);  // Example body fat range for normal weight
        } else if (bmi >= 25 && bmi <= 29.9) {
            return 25 + (bmi - 25);  // Example range for overweight
        } else {
            return 30 + (bmi - 30) * 1.2;  // Example range for obesity
        }
    }

    // Method to handle Exit menu item
    @FXML
    public void handleExit() {
        System.exit(0);
    }

    @FXML
    public void handleClear() {
        heightField.clear();
        weightField.clear();
        bmiResultField.clear();
        idealWeightField.clear();
        bmiCategoryField.clear();
        bodyFatField.clear();
    }

    // Method to handle About menu item
    @FXML
    public void handleAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About BMI Calculator");
        alert.setHeaderText(null);
        alert.setContentText("This BMI Calculator helps you calculate your BMI and ideal weight range.");
        alert.showAndWait();
    }
}