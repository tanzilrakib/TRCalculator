package com.example.rakib.trcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import org.mariuszgromada.math.mxparser.*;




public class MainActivity extends AppCompatActivity {

    private EditText result;
    private EditText newNumber;
    private TextView displayOperation;

    private Double operand1 = null;
    private Double operand2 = null;
    private String pendingOperation = "=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = (EditText) findViewById(R.id.result);
        newNumber = (EditText) findViewById(R.id.newNumber);
        newNumber.setInputType(0);;
        displayOperation = (TextView) findViewById(R.id.operation);

        Button button0 = (Button) findViewById(R.id.button0);
        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);
        Button button4 = (Button) findViewById(R.id.button4);
        Button button5 = (Button) findViewById(R.id.button5);
        Button button6 = (Button) findViewById(R.id.button6);
        Button button7 = (Button) findViewById(R.id.button7);
        Button button8 = (Button) findViewById(R.id.button8);
        Button button9 = (Button) findViewById(R.id.button9);
        Button buttonPlus = (Button) findViewById(R.id.buttonPlus);
        Button buttonMinus = (Button) findViewById(R.id.buttonMinus);
        Button buttonMultiply = (Button) findViewById(R.id.buttonMultiply);
        Button buttonDivision = (Button) findViewById(R.id.buttonDivision);
        Button buttonDot = (Button) findViewById(R.id.buttonDot);
        Button buttonEquals = (Button) findViewById(R.id.buttonEquals);
        Button buttonModulo = (Button) findViewById(R.id.buttonModulo);
        Button buttonLeftBracket = (Button) findViewById(R.id.buttonLeftBracket);
        Button buttonRightBracket = (Button) findViewById(R.id.buttonRightBracket);
        Button buttonBackspace = (Button) findViewById(R.id.buttonBackspace);
        Button buttonCLR = (Button) findViewById(R.id.buttonCLR);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                Log.d("in", b.getText().toString());
                if (b.getText().toString().equals("×")) {
                    Log.d("mul", b.getText().toString());
                    newNumber.append("*");
                } else {
                    newNumber.append(b.getText().toString());
                }
            }
        };

        button0.setOnClickListener(listener);
        button1.setOnClickListener(listener);
        button2.setOnClickListener(listener);
        button3.setOnClickListener(listener);
        button4.setOnClickListener(listener);
        button5.setOnClickListener(listener);
        button6.setOnClickListener(listener);
        button7.setOnClickListener(listener);
        button8.setOnClickListener(listener);
        button9.setOnClickListener(listener);
        buttonLeftBracket.setOnClickListener(listener);
        buttonRightBracket.setOnClickListener(listener);
        buttonDot.setOnClickListener(listener);
        buttonPlus.setOnClickListener(listener);
        buttonMinus.setOnClickListener(listener);
        buttonMultiply.setOnClickListener(listener);
        buttonDivision.setOnClickListener(listener);
        buttonModulo.setOnClickListener(listener);
        buttonBackspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                clearCalcDisplay = DONT_CLEAR;
                String str = newNumber.getText().toString();
                if (str.length() > 1) {
                    str = str.substring(0, str.length() - 1);
                    newNumber.setText(str);
                } else if (str.length() <= 1) {
                    newNumber.setText("");
                }

            }
        });
        buttonEquals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String str = newNumber.getText().toString();
                if (str.length() >= 1) {
//                    result.setText(str);
                    char op = str.charAt(0);
                    if(result.getText().toString().length()>0 &&  isDouble(result.getText().toString()) && (op == '+' || op == '-' || op == '*' || op == '/' || op == '%'  )){
                        Double previous = Double.parseDouble(result.getText().toString());
                        str = str.substring(1, str.length());
                        totalCalculation(str,previous,op);
                    }
                    else{
                        totalCalculation(str);
                    }

                } else if (str.length() <= 0) {
                    result.setText("");
                }

            }
        });

        buttonCLR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    result.setText("");
                    newNumber.setText("");

            }
        });

//
    }//onCreateEnds

    private void totalCalculation(String value) {
        value = value.replace("%","#");
        Expression e = new Expression(value);
//        mXparser.consolePrintln("Res: " + e.getExpressionString() + " = " + e.calculate());
        Double res = e.calculate();
        if(String.valueOf(res).equals("NaN")){
            result.setText("Invalid Expression");
        }else{

            result.setText(String.valueOf(res));
        }
//        result.setText(String.valueOf(res));

        newNumber.setText("");

    }
    private void totalCalculation(String value, Double previous ,char operator) {


            value = value.replace("%","#");
            if(operator == '%'){
                operator = '#';
            }
            Expression e = new Expression(value);
            StringBuilder op  = new StringBuilder();
            Double res = e.calculate();
            op.append(operator);
            String opr = op.toString();
            String newResult = previous + opr + String.valueOf(res);
    //
            Expression newRes = new Expression(newResult);
            Double newR = newRes.calculate();
            if(String.valueOf(newR).equals("NaN")){
                result.setText("Invalid Expression");
            }else{

                result.setText(String.valueOf(newR));
            }

        newNumber.setText("");

    }

    private boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
