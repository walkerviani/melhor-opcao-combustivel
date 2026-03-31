package com.example.melhoropcaocombustivel;

import static androidx.core.widget.TextViewKt.addTextChangedListener;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText inputEtanol, inputGasolina;
    private Button btnVerificar;
    private TextView painel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        inputEtanol = findViewById(R.id.inputEtanol);
        inputGasolina = findViewById(R.id.inputGasolina);
        btnVerificar = findViewById(R.id.verificarBtn);
        painel = findViewById(R.id.painel);
        btnVerificar.setOnClickListener(v -> calcular());

        inputEtanol.addTextChangedListener(criarTextWatcher(inputEtanol));
        inputGasolina.addTextChangedListener(criarTextWatcher(inputGasolina));
    }

    private void calcular() {
        String v1 = inputEtanol.getText().toString();
        String v2 = inputGasolina.getText().toString();

        if (v1.isEmpty() || v2.isEmpty()) {
            painel.setText("Preencha todos os campos!");
            return;
        }
        try {
            double valorEtanol = Double.parseDouble(v1);
            double valorGasolina = Double.parseDouble(v2);
            double resultado = 0;
            if (valorEtanol <= 0 || valorGasolina <= 0) {
                painel.setText("O valor precisa ser maior que 0!");
                return;
            }
            resultado = valorEtanol / valorGasolina;
            if (resultado <= 0.70) {
                painel.setText("O Etanol está mais vantajoso: " + String.format("%.2f", resultado));
            } else {
                painel.setText("A Gasolina está mais vantajosa: " + String.format("%.2f", resultado));
            }
        } catch (NumberFormatException e) {
            painel.setText("Digite valores válidos!");
        }

    }

    private TextWatcher criarTextWatcher(EditText editText) {
        return new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                String texto = s.toString();

                if (texto.startsWith(".")) {
                    editText.removeTextChangedListener(this);
                    String corrigido = "0" + texto;
                    editText.setText(corrigido);
                    editText.setSelection(corrigido.length());
                    editText.addTextChangedListener(this);
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        };
    }
}