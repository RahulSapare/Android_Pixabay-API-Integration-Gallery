package com.example.androcid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ViewAll extends AppCompatActivity {

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);

        dbHelper=new DBHelper(this);

        TextView txt=findViewById(R.id.allTxt);
        txt.setTextColor(getResources().getColor(R.color.txtColor));

        List<Task> taskList = new ArrayList<Task>();
        taskList=dbHelper.getAllTasks();

        String alltxt="";

        for(Task task:taskList){
            alltxt+="Name: "+taskList.get(0).taskName+"\nDiscription: "+taskList.get(0).taskDiscription+"\nDate:"+taskList.get(0).taskDate+"\n\n";
        }
        txt.setText(alltxt);
    }
}