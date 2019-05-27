package au.edu.jcu.cp3406.shoppinglist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private EditText itemET;
    private TextView textView;

    private ArrayList<String> products;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        itemET = findViewById(R.id.newproduct);
        Button btn = findViewById(R.id.add_button);
        ListView itemsList = findViewById(R.id.product_list);
        products = FileChecker.readData(this);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, products);
        itemsList.setAdapter(adapter);
        btn.setOnClickListener(this);
        itemsList.setOnItemClickListener(this);
        itemET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_DONE) {
                    addProduct();
                    return true;
                }
                return false;
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_button:
                addProduct();
                break;
        }
    }

    public void addProduct(){
        String productEntered = itemET.getText().toString();
        adapter.add(productEntered);
        itemET.setText("");
        FileChecker.writeData(products, this);
        Toast.makeText(this, "Product was added!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        products.remove(position);
        adapter.notifyDataSetChanged();
        Toast.makeText(this, "Product was discarded", Toast.LENGTH_SHORT).show();
    }

}
