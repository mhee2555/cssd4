package com.phc.cssd;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.tscdll.TscWifiActivity;
import com.phc.core.connect.HTTPConnect;
import com.phc.core.data.AsonData;
import com.phc.core.string.FormatDate;
import com.phc.core.string.TextAsBitmap;
import com.phc.core.string.TextRotation;
import com.phc.core.string.TextSplitName;
import com.phc.core.string.TextTwoLine;
import com.phc.cssd.data.PreviewSticker;
import com.phc.cssd.model.ModelSterileDetail;
import com.phc.cssd.url.Url;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class CssdTscPrintLabel {

    private static HTTPConnect httpConnect = new HTTPConnect();
    
    public static void CssdTscPrintLabel(PreviewSticker p, Context context, int label, String ip, List<ModelSterileDetail> sterile) {

        if(sterile == null || sterile.size() == 0){
            Toast.makeText(context, "ไม่พบข้อมูล", Toast.LENGTH_SHORT).show();
            return;
        }

        //==================================================================================================================================

        System.out.println("label = " + label);

        boolean PMachine = p.isPMachine();
        boolean PPrice = p.isPPrice();
        boolean PRound = p.isPRound();
        boolean PDept = p.isPDept();
        boolean PEmp1 = p.isPEmp1();
        boolean PEmp2 = p.isPEmp2();
        boolean PEmp3 = p.isPEmp3();
        int PSpeed = p.getPSpeed();
        int PDensity = p.getPDensity();
        String POption = p.getPOption();

        String itemname = "";
        String Price = "";
        String DepName2 = "";
        String itemcode = "";
        String UsageCode = "";
        String MachineName = "";
        String SterileRoundNumber = "";
        String SterileDate = "";
        String ExpireDate = "";
        String usr_prepare = "";
        String usr_approve = "";
        String usr_sterile = "";
        String qty = "0";
        String age = "0";
        
        String DATA="";

        TscWifiActivity Tsc = new TscWifiActivity();
        
        //==================================================================================================================================

        switch(label) {
            //==================================================================================================================================
            // Sticker Big Hot Air Steam
            //==================================================================================================================================

            case 1:
                List<ModelSterileDetail> DATA_MODEL_4 = sterile;

                Iterator li4 = DATA_MODEL_4.iterator();

                System.out.println("IP : " + ip);
                
                
                Tsc.openport(ip, 9100);

                while (li4.hasNext()) {
                    try {
                        ModelSterileDetail m = (ModelSterileDetail) li4.next();

                        Tsc.setup(60, 76, PSpeed, PDensity, 0, 2, 1);
                        Tsc.sendcommand("DIRECTION 1,0\r\n");
                        Tsc.clearbuffer();


                        String Itemname[] = TextTwoLine.make2line(m.getItemname());

                        if( (Itemname[0].length() > 1) && (Itemname[1].length() == 0) ){
                            Tsc.sendbitmap(50, 20, TextAsBitmap.getTextBitmap(Itemname[0], 26));
                        }else{
                            Tsc.sendbitmap(50, 15, TextAsBitmap.getTextBitmap(Itemname[0], 20));
                            Tsc.sendbitmap(50, 42, TextAsBitmap.getTextBitmap(Itemname[1], 20));
                        }
                        //ราคา
                        if (PPrice) {
                            Tsc.printerfont(300, 105, "1", 0, 1, 1, "Price: " + m.getPrice());
                        }
                        //แผนก
                        if (PDept) {
                            Tsc.sendbitmap(50, 85, TextAsBitmap.getTextBitmap(m.getDepName2(), 20));
                        }
                        //ชื่อเซ็ท
                        Tsc.sendbitmap(100, 117, TextAsBitmap.getTextBitmap(POption.equals("1") ? m.getItemcode() : m.getUsageCode(), 20));
                        //QR_Code
                        Tsc.qrcode(40, 165, "H", "6", "A", "0", "M2", "S1", m.getUsageCode());
                        //เครื่อง
                        if (PMachine) {
                            Tsc.sendbitmap(210, 160, TextAsBitmap.getTextBitmap("เครื่อง:" + m.getMachineName(), 18));
                        }
                        //รอบ
                        if (PRound) {
                            Tsc.sendbitmap(350, 160, TextAsBitmap.getTextBitmap("รอบ:" + m.getSterileRoundNumber(), 18));
                        }
                        //วันแพค
                        Tsc.printerfont(210, 204, "0", 0, 9, 9, m.getSterileDate());

                        //วันหมดอายุ
                        Tsc.sendbitmap(210, 238, TextAsBitmap.getTextBitmap(m.getExpireDate() + " (" + m.getAgeDay() + ")", 25));

                        //ผู้เตรียม - ู้ฆ่าเชื้อ
                        if (PEmp1 || PEmp2) {
                            Tsc.sendbitmap(220, 267, TextAsBitmap.getTextBitmap(m.getUsr_prepare()+" / "+m.getUsr_approve(), 18));
                        }

                        //ผู้ตรวจ
                        if (PEmp3) {
                            Tsc.sendbitmap(220, 293, TextAsBitmap.getTextBitmap(m.getUsr_sterile(), 18));
                        }

                        //ส่วนล่างสติ๊กเกอร์
                        //ชื่ออุปกรณ์
                        if( (Itemname[0].length() > 1) && (Itemname[1].length() == 0) ){
                            Tsc.sendbitmap(50, 450, TextAsBitmap.getTextBitmap(Itemname[0], 26));
                        }else{
                            Tsc.sendbitmap(50, 440, TextAsBitmap.getTextBitmap(Itemname[0], 20));
                            Tsc.sendbitmap(50, 470, TextAsBitmap.getTextBitmap(Itemname[1], 20));
                        }

                        //แผนก
                        Tsc.sendbitmap(230, 500, TextAsBitmap.getTextBitmap(m.getDepName2(), 18));
                        //Tsc.sendbitmap(50,465,TextAsBitmap.getTextBitmap(m.getDepName2(),18));
                        //ชื่อเซ็ท
                        Tsc.sendbitmap(85, 500, TextAsBitmap.getTextBitmap(m.getUsageCode(), 18));
                        //QR_Code
                        Tsc.qrcode(340, 495, "H", "4", "A", "0", "M2", "S1", m.getUsageCode());
                        //วันหมดอายุ
                        Tsc.printerfont(40, 565, "0", 0, 9, 9, m.getSterileDate());
                        //วันหมดอายุ
                        Tsc.printerfont(178, 563, "0", 0, 12, 12, m.getExpireDate());
                        Tsc.sendcommand("PRINT 1," + m.getQty() + "\r\n");
                        //Tsc.closeport();

                        DATA += m.getID() + "@";

                        System.out.println("DATA = " + DATA);

                        

                    } catch (Exception e) {
                        e.printStackTrace();
                        //continue;
                    }

                }

                Tsc.closeport();

                break;


            //==================================================================================================================================
            // Sticker Small Hot Air Steam
            //==================================================================================================================================
            case 2:
                List<ModelSterileDetail> DATA_MODEL_5 = sterile;

                Iterator li5 = DATA_MODEL_5.iterator();

                
                while (li5.hasNext()) {

                    try {

                        Tsc.openport(ip, 9100);
                        Tsc.setup(60, 57, PSpeed, PDensity, 0, 2, 2);
                        Tsc.sendcommand("DIRECTION 0,0\r\n");

                        ModelSterileDetail m = (ModelSterileDetail) li5.next();
                        Tsc.clearbuffer();
                        //ส่วนบนสติ๊กเกอร์
                        //ชื่ออุปกรณ์

                        String Itemname[] = TextTwoLine.make2line(m.getItemname());

                        if( (Itemname[0].length() > 1) && (Itemname[1].length() == 0) ){
                            Tsc.sendbitmap(50, 20, TextAsBitmap.getTextBitmap(Itemname[0], 22));
                        }else{
                            Tsc.sendbitmap(50, 15, TextAsBitmap.getTextBitmap(Itemname[0], 20));
                            Tsc.sendbitmap(50, 42, TextAsBitmap.getTextBitmap(Itemname[1], 20));
                        }
                        //ราคา
                        if (PPrice) {
                            Tsc.printerfont(300, 100, "1", 0, 1, 1, "Price: " + m.getPrice());
                        }
                        //แผนก
                        if (PDept) {
                            Tsc.sendbitmap(50, 85, TextAsBitmap.getTextBitmap(m.getDepName2(), 22));
                        }
                        //ชื่อเซ็ท
                        Tsc.sendbitmap(100, 120, TextAsBitmap.getTextBitmap(POption.equals("1") ? m.getItemcode() : m.getUsageCode(), 20));

                        //QR_Code
                        Tsc.qrcode(30, 160, "H", "6", "A", "0", "M1", "S1", m.getUsageCode());
                        //เครื่อง
                        if (PMachine) {
                            Tsc.sendbitmap(210, 155, TextAsBitmap.getTextBitmap(m.getMachineName(), 18));
                        }
                        //รอบ
                        if (PRound) {
                            Tsc.sendbitmap(310, 155, TextAsBitmap.getTextBitmap(m.getSterileRoundNumber(), 18));
                        }
                        //วันแพค
                        Tsc.printerfont(210, 210, "0", 0, 9, 9, m.getSterileDate() + m.getTime());
                        //วันหมดอายุ
                        Tsc.sendbitmap(210, 245, TextAsBitmap.getTextBitmap(m.getExpireDate() , 26));
                        //Tsc.printerfont(210,250,"0",0,12,12, m.getExpireDate());
                        //คนเครียม ตรวจ
                        if (PEmp1 || PEmp2) {
                            Tsc.sendbitmap(220, 280, TextAsBitmap.getTextBitmap(m.getUsr_prepare() + "/" + m.getUsr_approve(), 18));
                        }
                        //ฆ่าเชื้อ
                        if (PEmp3) {
                            Tsc.sendbitmap(220, 308, TextAsBitmap.getTextBitmap(m.getUsr_sterile(), 18));
                        }

                        //Tsc.sendcommand("PRINT 1\r\n");
                        Tsc.sendcommand("PRINT 1," + m.getQty() + "\r\n");
                        Tsc.closeport();

                        DATA += m.getID() + "@";


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }



                break;

            //==================================================================================================================================
            // Sticker Big Gas Steam
            //==================================================================================================================================
            case 3:
                List<ModelSterileDetail> DATA_MODEL_6 = sterile;

                Iterator li6 = DATA_MODEL_6.iterator();

                //Tsc.openport(mUsbManager, getPrinter(3));




                while (li6.hasNext()) {
                    Tsc.openport(ip, 9100);
                    Tsc.setup(55, 56, PSpeed, PDensity, 0, 2, 1);
                    Tsc.sendcommand("DIRECTION 1,0\r\n");
                    Tsc.clearbuffer();

                    ModelSterileDetail m = (ModelSterileDetail) li6.next();

                    itemname = m.getItemname();
                    Price = m.getPrice();
                    DepName2 = m.getDepName2();
                    itemcode = m.getItemcode();
                    UsageCode = m.getUsageCode();
                    MachineName = m.getMachineName();
                    SterileRoundNumber = m.getSterileRoundNumber();
                    SterileDate = m.getSterileDate();
                    ExpireDate = m.getExpireDate();
                    usr_prepare = m.getUsr_prepare();
                    usr_approve = m.getUsr_approve();
                    usr_sterile = m.getUsr_sterile();
                    qty = m.getQty();
                    age = m.getExpDay();


                    try {

                        String Itemname[] = TextTwoLine.make2line(itemname);
                        if( (Itemname[0].length() > 1) && (Itemname[1].length() == 0) ){
                            Tsc.sendbitmap(40, 20, TextAsBitmap.getTextBitmap(Itemname[0], 26));
                        }else{
                            Tsc.sendbitmap(40, 15, TextAsBitmap.getTextBitmap(Itemname[0], 20));
                            Tsc.sendbitmap(40, 42, TextAsBitmap.getTextBitmap(Itemname[1], 20));
                        }
                        if (PPrice) {
                            Tsc.printerfont(300, 85, "1", 0, 1, 1, "Price:" + Price + ".-");
                        }

                        if (PDept) {
                            Tsc.sendbitmap(30, 65, TextAsBitmap.getTextBitmap(DepName2, 20));
                        }
                        Tsc.sendbitmap(80, 95, TextAsBitmap.getTextBitmap(POption.equals("1") ? itemcode : UsageCode, 20));
                        if (PMachine) {
                            Tsc.sendbitmap(180, 120, TextAsBitmap.getTextBitmap("เครื่อง: " + MachineName, 20));
                        }
                        if (PRound) {
                            Tsc.sendbitmap(320, 120, TextAsBitmap.getTextBitmap("รอบ: " + SterileRoundNumber, 20));
                        }

                        Tsc.qrcode(30, 125, "H", "5", "A", "0", "M2", "S1", UsageCode);
                        Tsc.printerfont(200, 165, "0", 0, 9, 9, SterileDate);
                        Tsc.sendbitmap(200, 215, TextAsBitmap.getTextBitmap(m.getExpireDate() + " (" + m.getAgeDay() + ")", 26));

                        if (PEmp1 || PEmp2) {
                            Tsc.sendbitmap(180, 245, TextAsBitmap.getTextBitmap(usr_prepare + "/" + usr_approve + "/" + usr_sterile, 15));
                        }

                        //ส่วนล่างสติ๊กเกอร์
                        //ชื่ออุปกรณ์
                        if( (Itemname[0].length() > 1) && (Itemname[1].length() == 0) ){
                            Tsc.sendbitmap(30, 285, TextAsBitmap.getTextBitmap(Itemname[0], 26));
                        }else{
                            Tsc.sendbitmap(30, 285, TextAsBitmap.getTextBitmap(Itemname[0], 20));
                            Tsc.sendbitmap(30, 305, TextAsBitmap.getTextBitmap(Itemname[1], 20));
                        }
                        //แผนก
                        if (PDept) {
                            Tsc.sendbitmap(210, 350, TextAsBitmap.getTextBitmap(DepName2, 18));
                        }

                        //ชื่อเซต
                        Tsc.sendbitmap(65, 350, TextAsBitmap.getTextBitmap(UsageCode, 18));
                        //QR_Code
                        Tsc.qrcode(310, 330, "H", "4", "A", "0", "M2", "S1", UsageCode);
                        //วันหมดอายุ
                        Tsc.printerfont(25, 405, "0", 0, 9, 9, SterileDate);
                        //วันหมดอายุ
                        Tsc.printerfont(150, 400, "0", 0, 12, 12, ExpireDate);


                        Tsc.sendcommand("PRINT 1," + qty + "\r\n");
                        Tsc.closeport();

                        DATA += m.getID() + "@";

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }



                break;

            //==================================================================================================================================
            // Sticker Small Gas Steam
            //==================================================================================================================================
            case 4:
                List<ModelSterileDetail> DATA_MODEL_7 = sterile;

                Iterator li7 = DATA_MODEL_7.iterator();



                while (li7.hasNext()) {
                    Tsc.clearbuffer();

                    try {
                        ModelSterileDetail m = (ModelSterileDetail) li7.next();

                        //Tsc.openport(mUsbManager, getPrinter(4));
                        Tsc.openport(ip, 9100);
                        Tsc.setup(55, 56, PSpeed, PDensity, 0, 2, 1);
                        Tsc.sendcommand("DIRECTION 1,0\r\n");
                        Tsc.clearbuffer();

                        String Itemname[] = TextTwoLine.make2line(m.getItemname());
                        if( (Itemname[0].length() > 1) && (Itemname[1].length() == 0) ){
                            Tsc.sendbitmap(50, 5, TextAsBitmap.getTextBitmap(Itemname[0], 26));
                        }else{
                            Tsc.sendbitmap(50, 5, TextAsBitmap.getTextBitmap(Itemname[0], 20));
                            Tsc.sendbitmap(50, 25, TextAsBitmap.getTextBitmap(Itemname[1], 20));
                        }
                        Tsc.sendbitmap(60, 60, TextAsBitmap.getTextBitmap(m.getDepName2(), 26));
                        Tsc.sendbitmap(120, 105, TextAsBitmap.getTextBitmap(POption.equals("1") ? m.getItemcode() : m.getUsageCode(), 20));
                        Tsc.sendbitmap(180, 130, TextAsBitmap.getTextBitmap(m.getMachineName(), 20));
                        Tsc.sendbitmap(280, 130, TextAsBitmap.getTextBitmap(m.getSterileRoundNumber(), 20));
                        Tsc.sendbitmap(350, 130, TextAsBitmap.getTextBitmap(m.getTime(), 20));
                        Tsc.qrcode(60, 150, "H", "4", "A", "0", "M1", "S1", m.getUsageCode());
                        Tsc.printerfont(170, 190, "0", 0, 9, 9, m.getSterileDate());
                        Tsc.printerfont(310, 190, "0", 0, 9, 9, m.getExpireDate());
                        Tsc.sendbitmap(180, 220, TextAsBitmap.getTextBitmap(m.getUsr_prepare() + "/" + m.getUsr_approve() + "/" + m.getUsr_sterile(), 15));

                        Tsc.sendcommand("PRINT 1," + m.getQty() + "\r\n");
                        Tsc.closeport();

                        DATA += m.getID() + "@";

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }



                break;

            //==================================================================================================================================
            // Sticker Yellow (Pasin)
            //==================================================================================================================================
            case 5:

                //Tsc.openport(mUsbManager, getPrinter(5));
                Tsc.openport(ip, 9100);
                Tsc.setup(50, 49, PSpeed, PDensity, 0, 1, 1);
                Tsc.sendcommand("DIRECTION 1,0\r\n");

                System.out.println(" -----------------> 5");

                List<ModelSterileDetail> DATA_MODEL = sterile;

                Iterator li = DATA_MODEL.iterator();


                while (li.hasNext()) {
                    try {
                        ModelSterileDetail m = (ModelSterileDetail) li.next();

                        String Itemname[] = TextTwoLine.make2line(m.getItemname());
                        Tsc.clearbuffer();
                        Tsc.printerfont(30,20,"0",0,9,9, m.getDepName2());

                        //แผนก
                        if (PDept) {
                            Tsc.sendbitmap(30, 20, TextAsBitmap.getTextBitmap(m.getDepName2(), 26));
                        }

                        if( (Itemname[0].length() > 1) && (Itemname[1].length() == 0) ){
                            Tsc.sendbitmap(30, 70, TextAsBitmap.getTextBitmap(Itemname[0], 36));
                        }else{
                            Tsc.sendbitmap(30, 70, TextAsBitmap.getTextBitmap(Itemname[0], 30));
                            Tsc.sendbitmap(30, 95, TextAsBitmap.getTextBitmap(Itemname[1], 30));
                        }
                        //เครื่อง
                        if (PMachine) {
                            Tsc.sendbitmap(30, 160, TextAsBitmap.getTextBitmap("เครื่อง:" + m.getMachineName(), 24));
                        }

                        //รอบ
                        if (PRound) {
                            Tsc.sendbitmap(190, 160, TextAsBitmap.getTextBitmap("รอบ:" + m.getSterileRoundNumber(), 24));
                        }

                        //วันแพค
                        Tsc.sendbitmap(30, 190, TextAsBitmap.getTextBitmap("ผลิต "+m.getSterileDate() + m.getExpDay(), 26));

                        //วันหมดอายุ
                        Tsc.sendbitmap(30, 230, TextAsBitmap.getTextBitmap("หมด", 30));
                        Tsc.sendbitmap(30, 260, TextAsBitmap.getTextBitmap("อายุ", 30));
                        Tsc.sendbitmap(120, 240, TextAsBitmap.getTextBitmap(FormatDate.dateString(m.getExpireDate()), 45));

                        //คนเครียม ตรวจ
                        if (PEmp1||PEmp2){Tsc.sendbitmap(30,310,TextAsBitmap.getTextBitmap(TextSplitName.SplitName(m.getUsr_prepare())+"-เตรียม",22));}
                        //ฆ่าเชื้อ
                        if (PEmp3){Tsc.sendbitmap(130,340,TextAsBitmap.getTextBitmap( TextSplitName.SplitName(m.getUsr_approve())+"-ห่อ"+"/"+TextSplitName.SplitName(m.getUsr_sterile())+"-นึ่ง",22));}



                        Tsc.sendcommand("PRINT 1," + m.getQty() + "\r\n");

                        DATA += m.getID() + "@";

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                Tsc.closeport();

                break;

            //==================================================================================================================================
            // Sticker Red (Pasin)
            //==================================================================================================================================
            case 6:

                System.out.println(" -----------------> 6");

                List<ModelSterileDetail> DATA_MODEL_2 = sterile;

                Iterator li2 = DATA_MODEL_2.iterator();

                //Tsc.openport(mUsbManager, getPrinter(6));
                Tsc.openport(ip, 9100);
                int x = 15;
                int y = 60;
                final int inc = 40;
                final int x_ = 300;
                final int s = 26;

                while (li2.hasNext()) {
                    try {
                        //======================================================================
                        // Model
                        //======================================================================
                        ModelSterileDetail m = (ModelSterileDetail) li2.next();

                        itemname = m.getItemname();
                        Price = m.getPrice();
                        DepName2 = m.getDepName2();
                        itemcode = m.getItemcode();
                        UsageCode = m.getUsageCode();
                        MachineName = m.getMachineName();
                        SterileRoundNumber = m.getSterileRoundNumber();
                        SterileDate = m.getSterileDate();
                        ExpireDate = m.getExpireDate();
                        usr_prepare = m.getUsr_prepare();
                        usr_approve = m.getUsr_approve();
                        usr_sterile = m.getUsr_sterile();
                        qty = m.getQty();
                        age = m.getExpDay();

                        String[] parts = m.getItemSetData().split("%");

                        //======================================================================
                        // Sticker
                        //======================================================================
                        for (int i = 0; i < parts.length; i = i + 10) {
                            // Setup
                            Tsc.setup(47, 83, PSpeed, PDensity, 0, 1, 1);
                            Tsc.sendcommand("DIRECTION 0,0\r\n");
                            Tsc.clearbuffer();

                            // Header
                            Tsc.sendbitmap(20, 10, TextAsBitmap.getTextBitmap(itemname, 30));
                            Tsc.sendbitmap(285, 10, TextAsBitmap.getTextBitmap("(" + ((i/10)+1) + "/" +  ( ( parts.length / 11) + 1 ) + ")", s) );
                            Tsc.printerfont(x, 45, "0", 0, 10, 10, "------------------------------------------------");

                            // Detail (Set)
                            try {
                                String[] set = parts[i].split("#");
                                Tsc.sendbitmap(x, y, TextAsBitmap.getTextBitmap((i + 1) + ". " + set[0], s));
                                Tsc.sendbitmap(x_, y, TextAsBitmap.getTextBitmap(set[1], s));

                                set = parts[i + 1].split("#");
                                Tsc.sendbitmap(x, y + (inc * 1), TextAsBitmap.getTextBitmap((i + 2) + ". " + set[0], s));
                                Tsc.sendbitmap(x_, y + (inc * 1), TextAsBitmap.getTextBitmap(set[1], s));

                                set = parts[i + 2].split("#");
                                Tsc.sendbitmap(x, y + (inc * 2), TextAsBitmap.getTextBitmap((i + 3) + ". " + set[0], s));
                                Tsc.sendbitmap(x_, y + (inc * 2), TextAsBitmap.getTextBitmap(set[1], s));

                                set = parts[i + 3].split("#");
                                Tsc.sendbitmap(x, y + (inc * 3), TextAsBitmap.getTextBitmap((i + 4) + ". " + set[0], s));
                                Tsc.sendbitmap(x_, y + (inc * 3), TextAsBitmap.getTextBitmap(set[1], s));

                                set = parts[i + 4].split("#");
                                Tsc.sendbitmap(x, y + (inc * 4), TextAsBitmap.getTextBitmap((i + 5) + ". " + set[0], s));
                                Tsc.sendbitmap(x_, y + (inc * 4), TextAsBitmap.getTextBitmap(set[1], s));

                                set = parts[i + 5].split("#");
                                Tsc.sendbitmap(x, y + (inc * 5), TextAsBitmap.getTextBitmap((i + 6) + ". " + set[0], s));
                                Tsc.sendbitmap(x_, y + (inc * 5), TextAsBitmap.getTextBitmap(set[1], s));

                                set = parts[i + 6].split("#");
                                Tsc.sendbitmap(x, y + (inc * 6), TextAsBitmap.getTextBitmap((i + 7) + ". " + set[0], s));
                                Tsc.sendbitmap(x_, y + (inc * 6), TextAsBitmap.getTextBitmap(set[1], s));

                                set = parts[i + 7].split("#");
                                Tsc.sendbitmap(x, y + (inc * 7), TextAsBitmap.getTextBitmap((i + 8) + ". " + set[0], s));
                                Tsc.sendbitmap(x_, y + (inc * 7), TextAsBitmap.getTextBitmap(set[1], s));

                                set = parts[i + 8].split("#");
                                Tsc.sendbitmap(x, y + (inc * 8), TextAsBitmap.getTextBitmap((i + 9) + ". " + set[0], s));
                                Tsc.sendbitmap(x_, y + (inc * 8), TextAsBitmap.getTextBitmap(set[1], s));

                                set = parts[i + 9].split("#");
                                Tsc.sendbitmap(x, y + (inc * 9), TextAsBitmap.getTextBitmap((i + 10) + ". " + set[0], s));
                                Tsc.sendbitmap(x_, y + (inc * 9), TextAsBitmap.getTextBitmap(set[1], s));
                            }catch(Exception e){

                            }

                            // Footer
                            Tsc.sendbitmap(x, 465, TextAsBitmap.getTextBitmap("หมดอายุ", 30));
                            Tsc.sendbitmap(150, 465, TextAsBitmap.getTextBitmap(FormatDate.dateString(ExpireDate), 45));

                            Tsc.sendbitmap(x, 515, TextAsBitmap.getTextBitmap(SterileDate + "  (ค-" + (PMachine ? MachineName : "") + "/ร-" + (PRound ? SterileRoundNumber : "") + ")", s));
                            Tsc.sendbitmap(x, 565, TextAsBitmap.getTextBitmap((PEmp1 ? usr_prepare : "") + "," + (PEmp2 ?usr_approve  : "") + "," + (PEmp3 ? usr_sterile : ""), 20));

                            Tsc.sendbitmap(250, 600, TextAsBitmap.getTextBitmap(age, s));

                            // Print
                            Tsc.sendcommand("PRINT 1," + qty + "\r\n");
                        }

                        DATA += m.getID() + "@";

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                Tsc.closeport();
                break;

            //==================================================================================================================================
            // Sticker Blue (Pasin)
            //==================================================================================================================================
            case 7:

                System.out.println(" -----------------> 7");

                //Tsc.openport(mUsbManager, getPrinter(7));
                Tsc.openport(ip, 9100);
                Tsc.setup(50, 25, PSpeed, PDensity, 0, 2, 1);
                Tsc.sendcommand("DIRECTION 1,0\r\n");

                List<ModelSterileDetail> DATA_MODEL_3 = sterile;

                Iterator li3 = DATA_MODEL_3.iterator();

                //System.out.println(" Printer -----> " + getPrinter(7).getDeviceName() );

                while (li3.hasNext()) {
                    try {
                        ModelSterileDetail m = (ModelSterileDetail) li3.next();
                        Tsc.clearbuffer();

                        Tsc.sendbitmap(60, 40, TextAsBitmap.getTextBitmap(m.getDepName2()+" " + m.getItemname(), 30));
                        Tsc.sendbitmap(20, 70, TextAsBitmap.getTextBitmap("ผลิต "+m.getSterileDate() + "(" + (PMachine ? m.getMachineName() : "") +"/"+ (PRound ? m.getSterileRoundNumber() : "") + ")", 25));
                        Tsc.sendbitmap(25, 100, TextAsBitmap.getTextBitmap("หมด", 25));
                        Tsc.sendbitmap(25, 130, TextAsBitmap.getTextBitmap("อายุ", 25));
                        Tsc.sendbitmap(350, 90, TextRotation.RotateBitmap(TextAsBitmap.getTextBitmap(m.getExpDay(), 25), 90));
                        Tsc.sendbitmap(110, 110, TextAsBitmap.getTextBitmap(FormatDate.dateString(m.getExpireDate()), 40));
                        //คนเครียม ตรวจ
                        Tsc.sendbitmap(25, 170, TextAsBitmap.getTextBitmap((PEmp1 ? TextSplitName.SplitName(m.getUsr_prepare())+"-เตรียม"  : "-เตรียม") + "," + (PEmp2 ? TextSplitName.SplitName(m.getUsr_approve())+"-ห่อ"  : "-ห่อ") + "," + (PEmp3 ? TextSplitName.SplitName(m.getUsr_sterile())+"-นึ่ง"  : "-นึ่ง"), 20));

                        Tsc.sendcommand("PRINT 1," + m.getQty() + "\r\n");

                        DATA += m.getID() + "@";

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                Tsc.closeport();

                break;

        }

        updatePrintStatus(DATA);

        System.gc();
        System.runFinalization();
        System.gc();

    }

    // =============================================================================================
    // -- Add Sterile Detail
    // =============================================================================================

    public static void updatePrintStatus(final String p_data) {

        class UpdatePrintStatus extends AsyncTask<String, Void, String> {

            //------------------------------------------------
            // Background Worker Process Variable
            private boolean Success = false;
            private ArrayList<String> data = null;
            private int size = 0;
            //------------------------------------------------

            // variable
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                AsonData ason = new AsonData(result);

                Success = ason.isSuccess();
                size = ason.getSize();
                data = ason.getASONData();

                if(Success && data != null) {

                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();

                data.put("p_data", p_data);

                String result = httpConnect.sendPostRequest(Url.URL + "cssd_update_sterile_detail_print_status.php", data);

                System.out.println("URL = " + result);

                return result;
            }

            // =========================================================================================
        }

        UpdatePrintStatus obj = new UpdatePrintStatus();
        obj.execute();
    }

}
