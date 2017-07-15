package com.example.luisbalmant.justjava;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    /**
     * Global Variables
     */
    int quantity            = 0;
    int priceCoffee         = 5;
    int priceWhippedCream   = 1;
    int priceChocolate      = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Buttons +1 ; -1
     */
    public void increment(View view) {
        if (quantity == 100){
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    public void decrement(View view) {
        if (quantity == 1){
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * Display
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean haswhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        EditText name = (EditText) findViewById(R.id.name);
        String newName = name.getText().toString();

        int price = calculatePrice(haswhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(price, haswhippedCream, hasChocolate, newName);


        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject_order_for) + " " + newName);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    /**
     * Calculates the price of the order
     *
     * @param addWhippedCream is Whetter or not the user wants whipped cream topping.
     * @param addChocolate is Wheter or not the user wants chocolate topping.
     * @return total price
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        // Price of 1 cup of coffee
        int basePrice = priceCoffee;

        // Add priceWhippedCream if the user wants Whipped Cream
        if (addWhippedCream){
            basePrice = basePrice + priceWhippedCream;
        }

        // Add priceChocolate if the user wants Chocolate
        if (addChocolate){
            basePrice = basePrice + priceChocolate;
        }

        // Calculate the total order price by multiplying by quantity
        return quantity * basePrice;

    }

    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String name) {

        String priceMessage = getString(R.string.order_name)+ " " + name;
        priceMessage += "\n" + getString(R.string.order_add_whipped_cream)+ " " + addWhippedCream;
        priceMessage += "\n" + getString(R.string.order_add_chocolate)+ " " + addChocolate;
        priceMessage += "\n" + getString(R.string.order_quantity)+ " " + quantity;
        priceMessage += "\n" + getString(R.string.order_total)+ " " + price;
        priceMessage += "\n" + getString(R.string.order_thank_you);
        return priceMessage;
    }

    /**
     * Method Display Quantity
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }

}