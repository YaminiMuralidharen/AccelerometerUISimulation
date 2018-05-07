package com.cmpe277.yaminimuralidharen.accelerometeruisimulation;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private AsyncTask<Integer,Double,Void> generateAxisTask;
    private EditText simulation_count,Xvalue,Yvalue,Zvalue;
    private TextView log_status_view;
    private double Xaxis,Yaxis,Zaxis;
    private int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        simulation_count=findViewById(R.id.simulation_ct_edit);
        log_status_view=findViewById(R.id.log_status_view);
        Xvalue=findViewById(R.id.x_edit);
        Yvalue=findViewById(R.id.y_edit);
        Zvalue=findViewById(R.id.z_edit);
        log_status_view.setText("");

    }
    /* To cancel Async Task */
    public void cancelSimulation(View view) {
       generateAxisTask.cancel(true);
        Xvalue.setText("");
        Yvalue.setText("");
        Zvalue.setText("");
        simulation_count.setText("");
    //    log_status_view.setText("");

    }
    /* To start the Async Task */
    public void GenerateSimulation(View view) {
        generateAxisTask=  new GenerateAxisTask();
        Toast.makeText(this, simulation_count.getText().toString(),Toast.LENGTH_SHORT).show();
        count= Integer.valueOf(simulation_count.getText().toString());
        Log.d("simulation count"," " + count);
        generateAxisTask.execute(count);

    }


    /* Extending Async Task base class */
    private class GenerateAxisTask extends AsyncTask<Integer,Double,Void> {

        @Override
        protected Void doInBackground(Integer... params) {
           double min=-360.0;
           double max=360.0;
            Random r = new Random();
            for (int i=1;i<=params[0];i++) {
                try {
                    if(!isCancelled()) {
                        Thread.sleep(1000);
                        Xaxis = Math.round(((max -min)  * r.nextDouble() + min) * 100.0)/100.0;
                        Yaxis = Math.round(((max -min)  * r.nextDouble() + min) * 100.0)/100.0;
                        Zaxis =Math.round(((max -min)  * r.nextDouble() + min) * 100.0)/100.0;
                        publishProgress((double)i, Xaxis, Yaxis, Zaxis);
                    }
                    else
                        break;
                }
                catch (InterruptedException e) {

                }


             Log.d("async task ", "incremented value " + Xaxis + " " + Yaxis + " " + Zaxis );
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Double... values) {
            super.onProgressUpdate(values);
            log_status_view.append("\n Simulation Count:" + Math.round(values[0]) + "\n" + "X:" + values[1].toString() +" " + "Y:" + values[2].toString() +" "+ "Z:" + values[3].toString());
            Xvalue.setText(values[1].toString());
            Yvalue.setText(values[2].toString());
            Zvalue.setText(values[3].toString());

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
