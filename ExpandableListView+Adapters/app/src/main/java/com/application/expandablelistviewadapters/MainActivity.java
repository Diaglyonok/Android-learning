package com.application.expandablelistviewadapters;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {

    String[] groups = new String[] {"HTC", "LG", "Samsung"};

    String[] phonesHTC = new String[] {"Sensation", "Desire", "Wildfire", "Hero"};
    String[] phonesSams = new String[] {"Galaxy S II", "Galaxy Nexus", "Wave"};
    String[] phonesLG = new String[] {"Optimus", "Optimus Link", "Optimus Black", "Optimus One"};

    ArrayList<Map<String, String>> groupData;    // коллекция для групп

    ArrayList<Map<String, String>> childDataItem;  //  коллекция для элементов одной группы

    ArrayList<ArrayList<Map<String, String>>> childData;  //общая коллекция для коллекций элементов

    Map<String, String> map;  // список атрибутов группы или элемента

    ExpandableListView mainList;

    TextView infoView;

    SimpleExpandableListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        infoView = (TextView) findViewById(R.id.actionView);

        mainList = (ExpandableListView)findViewById(R.id.expandableListView);

        groupData = new ArrayList<Map<String, String>>();
        for (String groupCounter : groups){
            map = new HashMap<String, String>();
            map.put("groupName", groupCounter);
            groupData.add(map);
        }

        String[] groupFrom = new String[] {"groupName"};
        int[] groupTo = new int[] {android.R.id.text1};

        childData = new ArrayList<ArrayList<Map<String,String>>>();

        childDataItem = new ArrayList<Map<String, String>>();
        for (String itemCoutner : phonesHTC){
            map = new HashMap<String, String>();
            map.put("phoneName", itemCoutner);
            childDataItem.add(map);
        }
        childData.add(childDataItem);

        childDataItem = new ArrayList<Map<String, String>>();
        for (String itemCoutner : phonesLG){
            map = new HashMap<String, String>();
            map.put("phoneName", itemCoutner);
            childDataItem.add(map);
        }
        childData.add(childDataItem);

        childDataItem = new ArrayList<Map<String, String>>();
        for (String itemCoutner : phonesSams){
            map = new HashMap<String, String>();
            map.put("phoneName", itemCoutner);
            childDataItem.add(map);
        }
        childData.add(childDataItem);

        String[] childFrom = new String[] {"phoneName"};
        int[] childTo = new int[] {android.R.id.text1};

        adapter = new SimpleExpandableListAdapter(
                this,
                groupData, R.layout.my_group_text, groupFrom, groupTo,
                childData, android.R.layout.simple_list_item_1, childFrom, childTo);

        mainList.setAdapter(adapter);

        mainList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int GroupPos, int ChildPos, long id) {
                infoView.setText(getGroupChildText(GroupPos, ChildPos));
                return false;
            }
        });

        mainList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPos) {
                infoView.setText("Группа "+ getGroupText(groupPos) +" развернута");
            }
        });

        mainList.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPos) {
                infoView.setText("Группа "+ getGroupText(groupPos) +" свернута");
            }
        });
    }

    String getGroupText (int groupPos) {
        return ((Map<String, String>) (adapter.getGroup(groupPos))).get("groupName");
    }

    String getChildText (int groupPos, int childPos){
        return ((Map<String, String>) (adapter.getChild(groupPos, childPos))).get("phoneName");
    }

    String getGroupChildText (int groupPos, int childPos){
        return getGroupText(groupPos) + " " + getChildText(groupPos, childPos);
    }

    
}
