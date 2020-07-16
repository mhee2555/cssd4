package com.phc.cssd.viewbinder;

import com.phc.cssd.properties.Response_Aux_Recall;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump_Recall {
    public static HashMap<String, List<String>> getData(ArrayList<Response_Aux_Recall> getData) {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> data1 = new ArrayList<String>();
        for(int i=0;i<getData.size();i++) {
            if( getData.get(i).getFields7().equals("0") || getData.get(i).getFields7().equals("1") ) {
                data1.add(getData.get(i).getFields1() + "," + getData.get(i).getFields7());
            }
        }

        List<String> data2 = new ArrayList<String>();
        for(int i=0;i<getData.size();i++) {
            if( getData.get(i).getFields7().equals("6") || getData.get(i).getFields7().equals("9") ) {
                data2.add(getData.get(i).getFields1() + "," + getData.get(i).getFields7());
            }
        }

        List<String> data3 = new ArrayList<String>();
        for(int i=0;i<getData.size();i++) {
            if( getData.get(i).getFields7().equals("2") || getData.get(i).getFields7().equals("3") ) {
                data3.add(getData.get(i).getFields1() + "," + getData.get(i).getFields7());
            }
        }

        expandableListDetail.put("1 เอกสารที่ต้องจัด [ " + data1.size() + " ]       ", data1);
        expandableListDetail.put("2 เอกสารที่ค้างจัด [ " + data2.size() + " ]", data2);
        expandableListDetail.put("3 เอกสารจัดแล้ว [ " + data3.size() + " ]", data3);

        return expandableListDetail;
    }
}