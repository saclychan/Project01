package ngocamha.com.project01.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;

import ngocamha.com.project01.R;
import ngocamha.com.project01.model.ItemModelStatisTransaction;

public class AddTransction extends AppCompatActivity implements View.OnClickListener{
    TextView tvDate;
    TextView tvTime;
    Spinner sp;
    Calendar calendar = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transction);

        tvDate = (TextView) findViewById(R.id.tvDate);
        tvDate.setOnClickListener(this);
        tvTime = (TextView) findViewById(R.id.tvTime);
        tvTime.setOnClickListener(this);
        sp  = (Spinner) findViewById(R.id.sp);

        ArrayList<String> data =  new ArrayList<String>();
        data.add("Tien mat");
        data.add("The tiet kiem");
        data.add("So tiet kiem");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddTransction.this,android.R.layout.simple_list_item_1, data);

        sp.setAdapter(adapter);




    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tvDate:

                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH)+1;
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(AddTransction.this, onDateSetListener, year, month, day).show();
                break;

            case R.id.tvTime:
                int hour = calendar.get(Calendar.HOUR);
                int minute = calendar.get(Calendar.MINUTE);
                new TimePickerDialog(AddTransction.this, onTimeSetListener, hour, minute, true).show();
                break;
        }
    }

    DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            int month = i1 + 1;
            tvDate.setText(i2 + "/" + month + "/" + i);
        }
    };

    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {
            tvTime.setText(i + ":" + i1);
        }
    };
}
