package com.example.androcid;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Context context;
    AlertDialog alertDialog;
    String tn;
    String td;
    DBHelper dbHelper;
    int taskCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context=this;
        alertDialog=null;
        dbHelper=new DBHelper(context);

        //dbHelper.deleteAllTask();
        FloatingActionButton addTask = findViewById(R.id.addButton);


        taskCount=dbHelper.getTaskCount();
        //Toast.makeText(context, taskCount+"", Toast.LENGTH_SHORT).show();
        if(taskCount>0){

            TextView count=findViewById(R.id.count);
            count.setText(String.valueOf("Total tasks: "+taskCount));

            TextView txt=findViewById(R.id.txt);
            txt.setTextColor(getResources().getColor(R.color.txtColor));
            txt.setGravity(Gravity.NO_GRAVITY);

            List<Task> taskList=dbHelper.getAllTasks();

            String alltxt="Name: "+taskList.get(0).taskName+"\nDiscription: "+taskList.get(0).taskDiscription;
            txt.setText(alltxt);

        }


        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //dbHelper.deleteAllTask();

                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setCancelable(true);
                View view1=View.inflate(context,R.layout.add_item,null);
                builder.setView(view1);
                alertDialog=builder.show();

                RelativeLayout relativeLayout=view1.findViewById(R.id.rv);
                //relativeLayout.setId(View.generateViewId());
                relativeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "hai", Toast.LENGTH_SHORT).show();
                    }
                });

                Button addButton=(Button) relativeLayout.getChildAt(2);
                EditText taskName=(EditText) relativeLayout.getChildAt(0);
                //taskName.setText("");
                final EditText taskDisc=(EditText) relativeLayout.getChildAt(1);
                //taskDisc.setText("bye");
                tn=taskName.getText().toString();
                td=taskDisc.getText().toString();

                taskName.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void afterTextChanged(Editable s) {}

                    @Override
                    public void beforeTextChanged(CharSequence s, int start,
                                                  int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start,
                                              int before, int count) {
                        if(s.length() != 0)
                            tn=s.toString();
                    }
                });

                taskDisc.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void afterTextChanged(Editable s) {}

                    @Override
                    public void beforeTextChanged(CharSequence s, int start,
                                                  int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start,
                                              int before, int count) {
                        if(s.length() != 0)
                            td=s.toString();
                    }
                });

                addButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Task task = new Task();
                        task.taskId=1;
                        task.taskName=tn;
                        task.taskDiscription=td;
                        task.taskDate= "Date";
                        dbHelper.addTask(task);
                        TextView count=findViewById(R.id.count);
                        taskCount=dbHelper.getTaskCount();
                        count.setText(String.valueOf("Total tasks: "+taskCount));
                        TextView textView=findViewById(R.id.txt);
                        textView.setText("Name: "+tn+"\nDiscription: "+td);
                        alertDialog.dismiss();
                    }
                });

                Toast.makeText(MainActivity.this, "Created new task.", Toast.LENGTH_SHORT).show();
            }
        });

        Button get=findViewById(R.id.get);
        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(taskCount>0){
                    startActivity(new Intent(context,ViewAll.class));
                }else{
                    Toast.makeText(context, "Add a new Task", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}