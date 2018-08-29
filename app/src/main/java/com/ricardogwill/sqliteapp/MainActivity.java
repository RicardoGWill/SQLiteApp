package com.ricardogwill.sqliteapp;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    EditText nameEditText, surnameEditText, marksEditText, idEditText;
    Button addDataButton;
    Button viewAllDataButton;
    Button updateButton;
    Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);
        nameEditText = findViewById(R.id.name_editText);
        surnameEditText = findViewById(R.id.surname_editText);
        marksEditText = findViewById(R.id.marks_editText);
        idEditText = findViewById(R.id.id_editText);
        addDataButton = findViewById(R.id.add_data_button);
        viewAllDataButton = findViewById(R.id.view_all_data_button);
        updateButton = findViewById(R.id.update_button);
        deleteButton = findViewById(R.id.delete_button);

        addData();
        updateData();
        deleteData();
        viewAllData();
    }

    public void addData() {
        addDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = databaseHelper.insertData(nameEditText.getText().toString(),
                        surnameEditText.getText().toString(), marksEditText.getText().toString());
                if (isInserted == true) {
                    Toast.makeText(MainActivity.this, "Data Added.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Data Not Added.", Toast.LENGTH_SHORT).show();
                }
                eraseEditTextAfterInput();
            }
        });
    }

    public void updateData() {
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdated = databaseHelper.updateData(idEditText.getText().toString(),
                        nameEditText.getText().toString(), surnameEditText.getText().toString(),
                        marksEditText.getText().toString());
                if (isUpdated == true) {
                    Toast.makeText(MainActivity.this, "Data Updated.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Data Not Updated.", Toast.LENGTH_SHORT).show();
                }
                eraseEditTextAfterInput();

            }
        });
    }

    public void deleteData() {
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deletedRows = databaseHelper.deleteData(idEditText.getText().toString());
                if (deletedRows > 0) {
                    Toast.makeText(MainActivity.this, "Data Deleted.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Data Not Deleted.", Toast.LENGTH_SHORT).show();
                }
                eraseEditTextAfterInput();
            }
        });
    }

    public void viewAllData() {
        viewAllDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor resultCursor = databaseHelper.getallData();
                if (resultCursor.getCount() == 0) {
                    // Show message (see method below this one).
                    showMessage("Error.", "No Data Found.");
                    return;
                }
                StringBuffer stringBuffer = new StringBuffer();
                while (resultCursor.moveToNext()) {
                    stringBuffer.append("ID: " + resultCursor.getString(0) + "\n");
                    stringBuffer.append("First Name: " + resultCursor.getString(1) + "\n");
                    stringBuffer.append("Last Name: " + resultCursor.getString(2) + "\n");
                    stringBuffer.append("Grade: " + resultCursor.getString(3) + "\n\n");
                }
                // Show all data.
                showMessage("Data", stringBuffer.toString());
            }
        });
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void eraseEditTextAfterInput() {
        nameEditText.setText("");
        surnameEditText.setText("");
        marksEditText.setText("");
        idEditText.setText("");
    }

}
