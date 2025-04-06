package hcmus.com.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

import hcmus.com.calculator.utils.Calculator;

public class MainActivity extends AppCompatActivity {

    private TextView txtResult;
    private TextView txtHistory;
    private String currentInput = "";
    private String lastInput = "";
    private String operator = "";
    private boolean isNewInput = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        txtResult = findViewById(R.id.txtResult);
        txtHistory = findViewById(R.id.txtHistory);

        MaterialButton btn0 = findViewById(R.id.number0);
        MaterialButton btn1 = findViewById(R.id.number1);
        MaterialButton btn2 = findViewById(R.id.number2);
        MaterialButton btn3 = findViewById(R.id.number3);
        MaterialButton btn4 = findViewById(R.id.number4);
        MaterialButton btn5 = findViewById(R.id.number5);
        MaterialButton btn6 = findViewById(R.id.number6);
        MaterialButton btn7 = findViewById(R.id.number7);
        MaterialButton btn8 = findViewById(R.id.number8);
        MaterialButton btn9 = findViewById(R.id.number9);

        MaterialButton btnAdd = findViewById(R.id.btnAdd);
        MaterialButton btnSub = findViewById(R.id.btnSub);
        MaterialButton btnMul = findViewById(R.id.btnMul);
        MaterialButton btnDiv = findViewById(R.id.btnDiv);

        MaterialButton btnClear = findViewById(R.id.btnClear);
        MaterialButton btnDelete = findViewById(R.id.btnDelete);
        MaterialButton btnPercent = findViewById(R.id.btnPercent);
        MaterialButton btnEqual = findViewById(R.id.btn_equal);
        MaterialButton btnDot = findViewById(R.id.numberDot);

        btn0.setOnClickListener(v -> appendToInput("0"));
        btn1.setOnClickListener(v -> appendToInput("1"));
        btn2.setOnClickListener(v -> appendToInput("2"));
        btn3.setOnClickListener(v -> appendToInput("3"));
        btn4.setOnClickListener(v -> appendToInput("4"));
        btn5.setOnClickListener(v -> appendToInput("5"));
        btn6.setOnClickListener(v -> appendToInput("6"));
        btn7.setOnClickListener(v -> appendToInput("7"));
        btn8.setOnClickListener(v -> appendToInput("8"));
        btn9.setOnClickListener(v -> appendToInput("9"));

        btnAdd.setOnClickListener(v -> setOperator("+"));
        btnSub.setOnClickListener(v -> setOperator("-"));
        btnMul.setOnClickListener(v -> setOperator("*"));
        btnDiv.setOnClickListener(v -> setOperator("/"));

        btnClear.setOnClickListener(v -> clearInput());
        btnDelete.setOnClickListener(v -> deleteLastInput());
        btnPercent.setOnClickListener(v -> applyPercent());
        btnEqual.setOnClickListener(v -> calculateResult());
        btnDot.setOnClickListener(v -> appendDot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void appendToInput(String number) {
        if (isNewInput) {
            currentInput = number;
            isNewInput = false;
        } else {
            currentInput += number;
        }
        txtResult.setText(lastInput + " " + operator + " " + currentInput);
    }

    private void setOperator(String op) {
        if (!currentInput.isEmpty()) {
            if (!lastInput.isEmpty() && !operator.isEmpty()) {
                calculateResult();
            }
            lastInput = currentInput;
            operator = op;
            currentInput = "";
            txtResult.setText(lastInput + " " + operator);
        }
    }

    private void clearInput() {
        currentInput = "";
        lastInput = "";
        operator = "";
        txtResult.setText("0");
        isNewInput = true;
    }

    private void deleteLastInput() {
        if (!currentInput.isEmpty()) {
            currentInput = currentInput.substring(0, currentInput.length() - 1);
            txtResult.setText(lastInput + " " + operator + " " + currentInput);
        }
    }

    private void applyPercent() {
        if (!currentInput.isEmpty()) {
            double value = Double.parseDouble(currentInput);
            currentInput = String.valueOf(value / 100);
            txtResult.setText(lastInput + " " + operator + " " + currentInput);
        }
    }

    private void appendDot() {
        if (!currentInput.contains(".")) {
            currentInput += ".";
            txtResult.setText(lastInput + " " + operator + " " + currentInput);
        }
    }

    private void calculateResult() {
        if (!lastInput.isEmpty() && !currentInput.isEmpty()) {
            try {
                double result = Calculator.evaluateExpression(lastInput + " " + operator + " " + currentInput);
                currentInput = String.valueOf(result);
                String historyEntry = lastInput + " " + operator + " " + currentInput + " = " + result;
                txtResult.setText(currentInput);
                addToHistory(historyEntry);
                operator = "";
                lastInput = "";
                isNewInput = true;
            } catch (Exception e) {
                Toast.makeText(this, "Invalid calculation", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void addToHistory(String entry) {
        String currentHistory = txtHistory.getText().toString();
        currentHistory += entry + "\n";
        txtHistory.setText(currentHistory);
        ScrollView scrollView = findViewById(R.id.scrollView);
        scrollView.fullScroll(View.FOCUS_UP);
    }
}