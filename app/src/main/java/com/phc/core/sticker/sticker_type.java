package com.phc.core.sticker;

public class sticker_type {

    /*void xPrint(int Sel, List<ModelSterileDetail> sterile,String xIP) throws Exception {

        if(sterile == null || sterile.size() == 0){

            return;
        }

        com.phc.cssd_4_sd.usb.TscWifi TscLAN = new com.phc.cssd_4_sd.usb.TscWifi();
        TscWifi Tsc = new TscWifi();
        //System.out.println("Select = " + Sel);

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
        String qty_print = "1";
        //System.out.println("Sel = " + Sel);

        //if (mUsbManager.hasPermission(TscDevice_1)) {

        String DATA="";

        switch(Sel) {
            //==================================================================================================================================
            // Sticker Big Hot Air Steam
            //==================================================================================================================================

            case 0:

                TscLAN.openport("192.168.1.60", 9100);
                TscLAN.setup(50, 70, PSpeed, PDensity, 0, 2, 1);
                TscLAN.sendcommand("DIRECTION 1,0\r\n");
                TscLAN.clearbuffer();

                TscLAN.printerfont(10, 10, "0", 0, 20, 20, ".");
                TscLAN.printerfont(20, 10, "0", 0, 20, 20, ".");
                TscLAN.printerfont(40, 10, "0", 0, 20, 20, ".");
                TscLAN.printerfont(60, 10, "0", 0, 20, 20, ".");
                TscLAN.printerfont(100, 10, "0", 0, 20, 20, ".");
                TscLAN.printerfont(150, 10, "0", 0, 20, 20, ".");
                TscLAN.printerfont(200, 10, "0", 0, 20, 20, ".");
                TscLAN.printerfont(300, 10, "0", 0, 20, 20, ".");
                TscLAN.printerfont(400, 10, "0", 0, 20, 20, ".");
                TscLAN.printerfont(500, 10, "0", 0, 20, 20, ".");
                TscLAN.printerfont(600, 10, "0", 0, 20, 20, ".");
                TscLAN.printerfont(700, 10, "0", 0, 20, 20, ".");
                TscLAN.printerfont(800, 10, "0", 0, 20, 20, ".");

                TscLAN.printerfont(10, 20, "0", 0, 20, 20, ".");
                TscLAN.printerfont(10, 30, "0", 0, 20, 20, ".");
                TscLAN.printerfont(10, 60, "0", 0, 20, 20, ".");
                TscLAN.printerfont(10, 100, "0", 0, 20, 20, ".");
                TscLAN.printerfont(10, 150, "0", 0, 20, 20, ".");
                TscLAN.printerfont(10, 200, "0", 0, 20, 20, ".");
                TscLAN.printerfont(10, 300, "0", 0, 20, 20, ".");
                TscLAN.printerfont(10, 400, "0", 0, 20, 20, ".");
                TscLAN.printerfont(10, 500, "0", 0, 20, 20, ".");
                TscLAN.printerfont(10, 600, "0", 0, 20, 20, ".");
                TscLAN.printerfont(10, 700, "0", 0, 20, 20, ".");
                TscLAN.printerfont(10, 800, "0", 0, 20, 20, ".");
                TscLAN.sendcommand("PRINT 1," + 1 + "\r\n");
                break;


            //==================================================================================================================================
            // Sticker Non Indicator Grey 5x2.5
            //==================================================================================================================================
            case 1:

                List<ModelSterileDetail> DATA_MODEL_1 = sterile;
                Iterator li1 = DATA_MODEL_1.iterator();
                TscLAN.openport(xIP, 9100);

                while (li1.hasNext()) {
                    try {

                        ModelSterileDetail m = (ModelSterileDetail) li1.next();

                        TscLAN.setup(51, 26, PSpeed, PDensity, 0, 1, 1);
                        TscLAN.sendcommand("DIRECTION 1,0\r\n");
                        TscLAN.clearbuffer();


                        //ฟ้าเล็กแบบเก่า
                        String Itemname[] = TextTwoLine.make2line(m.getItemname());
                        Log.d("getItemname: ", m.getItemname());
                        if( (Itemname[0].length() > 1) && (Itemname[1].length() == 0) ){
                            TscLAN.sendpicture(70, 15, TextAsBitmap.getTextBitmap(Itemname[0], 20));
                        }else{
                            TscLAN.sendpicture(70, 10, TextAsBitmap.getTextBitmap(Itemname[0], 20));
                            TscLAN.sendpicture(70, 35, TextAsBitmap.getTextBitmap(Itemname[1], 20));
                        }
                        //TscLANLAN.sendpicture(60, 30, TextAsBitmap.getTextBitmap(m.getItemname(), 30));
                        TscLAN.sendpicture(20, 60, TextAsBitmap.getTextBitmap(m.getDepName2(), 14));
                        TscLAN.sendpicture(25, 77, TextAsBitmap.getTextBitmap("เครื่อง:" + (PMachine ? m.getMachineName() : "") + "  รอบ:" + (PRound ? m.getSterileRoundNumber() : ""), 16));
                        TscLAN.sendpicture(25, 105, TextAsBitmap.getTextBitmap("Packing Date: "+ FormatDate.dateString(m.getSterileDate())+"("+m.getAge()+"วัน)", 16));
                        TscLAN.qrcode(290, 67, "H", "4", "A", "0", "M1", "S1", m.getUsageCode());
                        TscLAN.sendpicture(100, 125, TextAsBitmap.getTextBitmap(FormatDate.dateString(m.getExpireDate()), 35));
                        //คนเครียม ตรวจ
                        TscLAN.sendpicture(25, 163, TextAsBitmap.getTextBitmap((PEmp1 ? TextSplitName.SplitName(m.getUsr_prepare())+"-เตรียม"  : "-เตรียม") + "/" + (PEmp2 ? TextSplitName.SplitName(m.getUsr_approve())+"-ห่อ"  : "-ห่อ") + "/" + (PEmp3 ? TextSplitName.SplitName(m.getUsr_sterile())+"-นึ่ง"  : "-นึ่ง"), 16));


                        TscLAN.sendcommand("PRINT 1," + m.getQty()+ "\r\n");
                        DATA += m.getID() + "@";

                        System.out.println("DATA = " + DATA);

                        System.gc();
                        System.runFinalization();
                        System.gc();


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                TscLAN.closeport();

                break;




            //==================================================================================================================================
            // Sticker Indicator Grey 5x5cm
            //==================================================================================================================================
            case 2:


                List<ModelSterileDetail> DATA_MODEL_2= sterile;

                Iterator li2 = DATA_MODEL_2.iterator();
                TscLAN.openport(xIP, 9100);

                while (li2.hasNext()) {

                    try {

                        ModelSterileDetail m = (ModelSterileDetail) li2.next();

                        TscLAN.setup(51 ,51, PSpeed, PDensity, 0, 1, 1);
                        TscLAN.sendcommand("DIRECTION 1,0\r\n");
                        TscLAN.clearbuffer();
                        //ส่วนบนสติ๊กเกอร์
                        //ชื่ออุปกรณ์

                        String Itemname[] = TextTwoLine.make2line(m.getItemname());

                        if( (Itemname[0].length() > 1) && (Itemname[1].length() == 0) ){
                            TscLAN.sendpicture(30, 10, TextAsBitmap.getTextBitmap(Itemname[0], 22));
                        }else{
                            TscLAN.sendpicture(30, 10, TextAsBitmap.getTextBitmap(Itemname[0], 20));
                            TscLAN.sendpicture(30, 35, TextAsBitmap.getTextBitmap(Itemname[1], 20));
                        }
                        //ราคา
                        if (PPrice) {
                            TscLAN.printerfont(280, 85, "1", 0, 1, 1, "Price: " + m.getPrice());
                        }
                        //แผนก
                        if (PDept) {
                            TscLAN.sendpicture(30, 75, TextAsBitmap.getTextBitmap(m.getDepName2(), 22));
                        }
                        //ชื่อเซ็ท
                        TscLAN.sendpicture(30, 110, TextAsBitmap.getTextBitmap("No."+(POption.equals("1") ? m.getItemcode() : m.getUsageCode()), 20));
                        //QR_Code
                        TscLAN.qrcode(20, 150, "H", "6", "A", "0", "M1", "S1", m.getUsageCode());
                        //เครื่อง
                        TscLAN.sendpicture(180, 145, TextAsBitmap.getTextBitmap("เครื่อง: "+m.getMachineName(), 18));
                        //รอบ
                        TscLAN.sendpicture(310, 145, TextAsBitmap.getTextBitmap("รอบ: "+m.getSterileRoundNumber(), 18));
                        //วันแพค
                        TscLAN.printerfont(180, 170, "0", 0, 7, 7, "PackDate:");
                        TscLAN.printerfont(180, 190, "0", 0, 9, 9, FormatDate.dateString(m.getSterileDate()));
                        //TscLAN.printerfont(210, 200, "0", 0, 9, 9, m.getSterileDate() + m.getTime());
                        //วันหมดอายุ
                        TscLAN.sendpicture(240, 220, TextAsBitmap.getTextBitmap(FormatDate.dateString(m.getExpireDate()) , 26));
                        //TscLANLAN.printerfont(210,250,"0",0,12,12, m.getExpireDate());
                        //คนเครียม ตรวจ
                        if (PEmp1 || PEmp2) {
                            TscLAN.sendpicture(190, 260, TextAsBitmap.getTextBitmap(m.getUsr_prepare()+"เตรียม" + "/" + m.getUsr_approve()+"ตรวจ", 18));
                        }
                        //ฆ่าเชื้อ
                        if (PEmp3) {
                            TscLAN.sendpicture(190, 288, TextAsBitmap.getTextBitmap(m.getUsr_sterile()+"ฆ่าเชื้อ", 18));
                        }

                        //TscLANLAN.sendcommand("PRINT 1\r\n");
                        TscLAN.sendcommand("PRINT 1," + m.getQty() + "\r\n");
                        //TscLANLAN.closeport();

                        DATA += m.getID() + "@";

                        System.out.println("DATA = " + DATA);

                        System.gc();
                        System.runFinalization();
                        System.gc();



                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                TscLAN.closeport();


                break;

            //==================================================================================================================================
            // Indicator blue 5x7cm
            //==================================================================================================================================
            case 3:
                List<ModelSterileDetail> DATA_MODEL_3 = sterile;

                Iterator li3 = DATA_MODEL_3.iterator();
                Tsc.openport(xIP, 9100);


                while (li3.hasNext()) {

                    try {
                        ModelSterileDetail m = (ModelSterileDetail) li3.next();

                        Tsc.setup(50, 70, PSpeed, PDensity, 0, 1, 1);
                        Tsc.sendcommand("DIRECTION 1,0\r\n");
                        Tsc.clearbuffer();
                        String Itemname[] = TextTwoLine.make2line(m.getItemname());

                        if( (Itemname[0].length() > 1) && (Itemname[1].length() == 0) ){
                            Tsc.sendpicture(30, 15, TextAsBitmap.getTextBitmap(Itemname[0], 26));
                        }else{
                            Tsc.sendpicture(30, 12, TextAsBitmap.getTextBitmap(Itemname[0], 22));
                            Tsc.sendpicture(30, 36, TextAsBitmap.getTextBitmap(Itemname[1], 22));
                        }
                       *//* //ราคา
                        if (PPrice) {
                            Tsc.printerfont(300, 85, "1", 0, 1, 1, "Price: " + m.getPrice());
                        }*//*
                        //แผนก
                        if (PDept) {
                            Tsc.sendpicture(30, 80, TextAsBitmap.getTextBitmap(m.getDepName2(), 20));
                        }
                        //ชื่อเซ็ท
                        Tsc.sendpicture(30, 112, TextAsBitmap.getTextBitmap(POption.equals("1") ? "No." +m.getItemcode() : "No." +m.getUsageCode(), 20));
                        //QR_Code
                        Tsc.qrcode(20, 160, "H", "6", "A", "0", "M2", "S1", m.getUsageCode());
                        //เครื่อง
                        if (PMachine) {
                            Tsc.sendpicture(180, 145, TextAsBitmap.getTextBitmap("เครื่อง:" + m.getMachineName(), 18));
                        }
                        //รอบ
                        if (PRound) {
                            Tsc.sendpicture(310, 145, TextAsBitmap.getTextBitmap("รอบ:" + m.getSterileRoundNumber(), 18));
                        }
                        //วันแพค
                        Tsc.sendpicture(180, 170, TextAsBitmap.getTextBitmap("Packing Date:", 16));
                        Tsc.sendpicture(200, 195, TextAsBitmap.getTextBitmap(FormatDate.dateString(m.getSterileDate())+ " (" +m.getAgeDay()+"วัน)", 16));
                        //Tsc.printerfont(200, 195, "0", 0, 9, 9, FormatDate.dateString(m.getSterileDate()));

                        //วันหมดอายุ
                        Tsc.sendpicture(225, 230, TextAsBitmap.getTextBitmap(FormatDate.dateString(m.getExpireDate()), 30));

                        //ผู้เตรียม - ู้ฆ่าเชื้อ
                        if (PEmp1 || PEmp2) {
                            Tsc.sendpicture(180, 260, TextAsBitmap.getTextBitmap(m.getUsr_prepare()+"เตรียม"+" / "+m.getUsr_approve()+"ตรวจ", 18));
                        }

                        //ผู้ตรวจ
                        if (PEmp3) {
                            Tsc.sendpicture(180, 290, TextAsBitmap.getTextBitmap(m.getUsr_sterile()+"ฆ่าเชื้อ", 18));
                        }

                        //ส่วนล่างสติ๊กเกอร์
                        //ชื่ออุปกรณ์
                        if( (Itemname[0].length() > 1) && (Itemname[1].length() == 0) ){
                            Tsc.sendpicture(30, 410, TextAsBitmap.getTextBitmap(Itemname[0], 26));
                        }else{
                            Tsc.sendpicture(30, 410, TextAsBitmap.getTextBitmap(Itemname[0], 20));
                            Tsc.sendpicture(30, 435, TextAsBitmap.getTextBitmap(Itemname[1], 20));
                        }

                        //แผนก
                        Tsc.sendpicture(190, 465, TextAsBitmap.getTextBitmap(m.getDepName2(), 18));
                        //Tsc.sendpicture(50,465,TextAsBitmap.getTextBitmap(m.getDepName2(),18));
                        //ชื่อเซ็ท
                        Tsc.sendpicture(20, 465, TextAsBitmap.getTextBitmap("No."+m.getUsageCode(), 18));
                        //QR_Code
                        Tsc.qrcode(295, 445, "H", "4", "A", "0", "M2", "S1", m.getUsageCode());
                        //วันหมดอายุ

                        Tsc.printerfont(40, 490, "0", 0, 9, 9, "Packing Date:"+FormatDate.dateString(m.getSterileDate()));
                        //วันหมดอายุ
                        Tsc.printerfont(120, 520, "0", 0, 12, 12, FormatDate.dateString(m.getExpireDate()));
                        Tsc.sendcommand("PRINT 1," + m.getQty() + "\r\n");

                        DATA += m.getID() + "@";


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


                Tsc.closeport();

                break;

            //==================================================================================================================================
            // Sticker Indicator Grey 5x7cm
            //==================================================================================================================================
            case 4:

                System.out.println(" -----------------> 6");

                List<ModelSterileDetail> DATA_MODEL_4= sterile;

                Iterator li4 = DATA_MODEL_4.iterator();

                //TscLAN.openport(mUsbManager, getPrinter(6));
                TscLAN.openport(xIP, 9100);
                int x = 15;
                int y = 100;
                int inc = 30;
                int x_ = 330;
                int s = 22;

                while (li4.hasNext()) {
                    try {
                        //======================================================================
                        // Model
                        //======================================================================
                        ModelSterileDetail m = (ModelSterileDetail) li4.next();
                        itemname =  m.getItemname().substring(0,  m.getItemname().length()-4);
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
                            TscLAN.setup(51, 80, PSpeed, PDensity, 0, 1, 1+1/2);
                            TscLAN.sendcommand("DIRECTION 1,0\r\n");
                            TscLAN.clearbuffer();

                            // Header
                            TscLAN.sendpicture(60, 15, TextAsBitmap.getTextBitmap(DepName2, 26));
                            TscLAN.sendpicture(20, 65, TextAsBitmap.getTextBitmap(itemname, 24));
                            //TscLAN.sendpicture(285, 10, TextAsBitmap.getTextBitmap("(" + ((i/10)+1) + "/" +  ( ( parts.length / 11) + 1 ) + ")", s) );
                            // TscLAN.printerfont(x, 45, "0", 0, 10, 10, "------------------------------------------------");

                            // Detail (Set)
                            try {
                                String[] set = parts[i].split("#");
                                TscLAN.sendpicture(x, y, TextAsBitmap.getTextBitmap((i + 1) + ". " + set[0], s));
                                TscLAN.sendpicture(x_, y, TextAsBitmap.getTextBitmap(set[1], s));

                                set = parts[i + 1].split("#");
                                TscLAN.sendpicture(x, y + (inc * 1), TextAsBitmap.getTextBitmap((i + 2) + ". " + set[0], s));
                                TscLAN.sendpicture(x_, y + (inc * 1), TextAsBitmap.getTextBitmap(set[1], s));

                                set = parts[i + 2].split("#");
                                TscLAN.sendpicture(x, y + (inc * 2), TextAsBitmap.getTextBitmap((i + 3) + ". " + set[0], s));
                                TscLAN.sendpicture(x_, y + (inc * 2), TextAsBitmap.getTextBitmap(set[1], s));

                                set = parts[i + 3].split("#");
                                TscLAN.sendpicture(x, y + (inc * 3), TextAsBitmap.getTextBitmap((i + 4) + ". " + set[0], s));
                                TscLAN.sendpicture(x_, y + (inc * 3), TextAsBitmap.getTextBitmap(set[1], s));

                                set = parts[i + 4].split("#");
                                TscLAN.sendpicture(x, y + (inc * 4), TextAsBitmap.getTextBitmap((i + 5) + ". " + set[0], s));
                                TscLAN.sendpicture(x_, y + (inc * 4), TextAsBitmap.getTextBitmap(set[1], s));

                                set = parts[i + 5].split("#");
                                TscLAN.sendpicture(x, y + (inc * 5), TextAsBitmap.getTextBitmap((i + 6) + ". " + set[0], s));
                                TscLAN.sendpicture(x_, y + (inc * 5), TextAsBitmap.getTextBitmap(set[1], s));

                                set = parts[i + 6].split("#");
                                TscLAN.sendpicture(x, y + (inc * 6), TextAsBitmap.getTextBitmap((i + 7) + ". " + set[0], s));
                                TscLAN.sendpicture(x_, y + (inc * 6), TextAsBitmap.getTextBitmap(set[1], s));

                                set = parts[i + 7].split("#");
                                TscLAN.sendpicture(x, y + (inc * 7), TextAsBitmap.getTextBitmap((i + 8) + ". " + set[0], s));
                                TscLAN.sendpicture(x_, y + (inc * 7), TextAsBitmap.getTextBitmap(set[1], s));

                                set = parts[i + 8].split("#");
                                TscLAN.sendpicture(x, y + (inc * 8), TextAsBitmap.getTextBitmap((i + 9) + ". " + set[0], s));
                                TscLAN.sendpicture(x_, y + (inc * 8), TextAsBitmap.getTextBitmap(set[1], s));

                                set = parts[i + 9].split("#");
                                TscLAN.sendpicture(x, y + (inc * 9), TextAsBitmap.getTextBitmap((i + 10) + ". " + set[0], s));
                                TscLAN.sendpicture(x_, y + (inc * 9), TextAsBitmap.getTextBitmap(set[1], s));
                            }catch(Exception e){

                            }

                            // Footer
                            TscLAN.qrcode(290, 425, "H", "4", "A", "0", "M1", "S1", m.getUsageCode());
                            //TscLAN.sendpicture(300, 445,  TextAsBitmap.getTextBitmap(POption.equals("1") ? m.getItemcode() : m.getUsageCode(), 20));


                            //TscLAN.sendpicture(x, 490, TextAsBitmap.getTextBitmap((PEmp3 ? usr_sterile : ""), 20));
                            TscLAN.sendpicture(x, 505, TextAsBitmap.getTextBitmap("Packing Date: "+FormatDate.dateString(SterileDate), 22));
                            TscLAN.sendpicture(150, 535, TextAsBitmap.getTextBitmap(FormatDate.dateString(ExpireDate), 45));
                            TscLAN.sendpicture(x, 588, TextAsBitmap.getTextBitmap((PEmp1 ? TextSplitName.SplitName(m.getUsr_prepare())+"-เตรียม"  : "-เตรียม") + "/" + (PEmp2 ? TextSplitName.SplitName(m.getUsr_approve())+"-ตรวจ"  : "-ตรวจ") + "/" + (PEmp3 ? TextSplitName.SplitName(m.getUsr_sterile())+"-ฆ่าเชื้อ"  : "-ฆ่าเชื้อ") , 15));
                            TscLAN.sendpicture(x, 610, TextAsBitmap.getTextBitmap("เครื่อง:" + (PMachine ? MachineName : "") + "  รอบ:" + (PRound ? SterileRoundNumber : ""), 15));
                            TscLAN.sendpicture(260, 605, TextAsBitmap.getTextBitmap("("+m.getAge()+"วัน)", 22));
                            //TscLAN.sendpicture(250, 600, TextAsBitmap.getTextBitmap(age, s));


                            // Print
                            TscLAN.sendcommand("PRINT 1," + qty + "\r\n");
                        }

                        DATA += m.getID() + "@";

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                TscLAN.closeport();
                break;

            //==================================================================================================================================
            // Sticker Yellow (Pasin)
            //==================================================================================================================================
            case 5:

                //TscLAN.openport(mUsbManager, getPrinter(5));
                TscLAN.openport(xIP, 9100);
                TscLAN.setup(50, 49, PSpeed, PDensity, 0, 1, 1);
                TscLAN.sendcommand("DIRECTION 1,0\r\n");

                System.out.println(" -----------------> 5");

                List<ModelSterileDetail> DATA_MODEL = sterile;

                Iterator li = DATA_MODEL.iterator();


                while (li.hasNext()) {
                    try {
                        ModelSterileDetail m = (ModelSterileDetail) li.next();

                        String Itemname[] = TextTwoLine.make2line(m.getItemname());
                        TscLAN.clearbuffer();
                        TscLAN.printerfont(30,20,"0",0,9,9, m.getDepName2());

                        //แผนก
                        if (PDept) {
                            TscLAN.sendpicture(30, 20, TextAsBitmap.getTextBitmap(m.getDepName2(), 26));
                        }

                        if( (Itemname[0].length() > 1) && (Itemname[1].length() == 0) ){
                            TscLAN.sendpicture(30, 70, TextAsBitmap.getTextBitmap(Itemname[0], 36));
                        }else{
                            TscLAN.sendpicture(30, 70, TextAsBitmap.getTextBitmap(Itemname[0], 30));
                            TscLAN.sendpicture(30, 95, TextAsBitmap.getTextBitmap(Itemname[1], 30));
                        }
                        //เครื่อง
                        if (PMachine) {
                            TscLAN.sendpicture(30, 160, TextAsBitmap.getTextBitmap("เครื่อง:" + m.getMachineName(), 24));
                        }

                        //รอบ
                        if (PRound) {
                            TscLAN.sendpicture(190, 160, TextAsBitmap.getTextBitmap("รอบ:" + m.getSterileRoundNumber(), 24));
                        }

                        //วันแพค
                        TscLAN.sendpicture(30, 190, TextAsBitmap.getTextBitmap("ผลิต "+m.getSterileDate() + m.getExpDay(), 26));

                        //วันหมดอายุ
                        TscLAN.sendpicture(30, 230, TextAsBitmap.getTextBitmap("หมด", 30));
                        TscLAN.sendpicture(30, 260, TextAsBitmap.getTextBitmap("อายุ", 30));
                        TscLAN.sendpicture(120, 240, TextAsBitmap.getTextBitmap(FormatDate.dateString(m.getExpireDate()), 45));

                        //คนเครียม ตรวจ
                        if (PEmp1||PEmp2){TscLAN.sendpicture(30,310, TextAsBitmap.getTextBitmap(TextSplitName.SplitName(m.getUsr_prepare())+"-เตรียม",22));}
                        //ฆ่าเชื้อ
                        if (PEmp3){TscLAN.sendpicture(130,340, TextAsBitmap.getTextBitmap( TextSplitName.SplitName(m.getUsr_approve())+"-ห่อ"+"/"+ TextSplitName.SplitName(m.getUsr_sterile())+"-นึ่ง",22));}



                        TscLAN.sendcommand("PRINT 1," + m.getQty() + "\r\n");

                        DATA += m.getID() + "@";

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                TscLAN.closeport();

                break;

            //==================================================================================================================================
            // Sticker Indicator Grey 5x2.5cm
            //==================================================================================================================================
            case 6:
                List<ModelSterileDetail> DATA_MODEL_6 = sterile;

                Iterator li6 = DATA_MODEL_6.iterator();
                TscLAN.openport(xIP, 9100);

                while (li6.hasNext()) {
                    try {
                        ModelSterileDetail m = (ModelSterileDetail) li6.next();

                        TscLAN.setup(51, 26, PSpeed, PDensity, 0, 1, 1);
                        TscLAN.sendcommand("DIRECTION 1,0\r\n");
                        TscLAN.clearbuffer();

                        String Itemname[] = TextTwoLine.make2line(m.getItemname());

                        if( (Itemname[0].length() > 1) && (Itemname[1].length() == 0) ){
                            TscLAN.sendpicture(20, 20, TextAsBitmap.getTextBitmap(Itemname[0], 20));
                        }else{
                            TscLAN.sendpicture(20, 20, TextAsBitmap.getTextBitmap(Itemname[0], 20));
                            TscLAN.sendpicture(20, 45, TextAsBitmap.getTextBitmap(Itemname[1], 20));
                        }
                        //TscLAN.sendpicture(60, 30, TextAsBitmap.getTextBitmap(m.getItemname(), 30));
                        TscLAN.sendpicture(130, 70, TextAsBitmap.getTextBitmap(m.getDepName2()+"   เครื่อง:"+m.getMachineName()+"  รอบ:"+m.getSterileRoundNumber(), 14));
                        TscLAN.sendpicture(130, 90, TextAsBitmap.getTextBitmap("No."+m.getUsageCode(), 20));
                        TscLAN.sendpicture(130, 118, TextAsBitmap.getTextBitmap("Packing Date: "+FormatDate.dateString(m.getSterileDate())+" ("+m.getAge()+"วัน)", 16));
                        TscLAN.qrcode(20, 75, "H", "4", "A", "0", "M1", "S1", m.getUsageCode());
                        TscLAN.sendpicture(210, 148, TextAsBitmap.getTextBitmap(FormatDate.dateString(m.getExpireDate()), 35));
                        //คนเครียม ตรวจ
                        TscLAN.sendpicture(25, 185, TextAsBitmap.getTextBitmap((PEmp1 ? TextSplitName.SplitName(m.getUsr_prepare())+"-เตรียม"  : "-เตรียม") + "/" + (PEmp2 ? TextSplitName.SplitName(m.getUsr_approve())+"-ตรวจ"  : "-ตรวจ") + "/" + (PEmp3 ? TextSplitName.SplitName(m.getUsr_sterile())+"-ฆ่าเชื้อ"  : "-ฆ่าเชื้อ"), 16));

                        TscLAN.sendcommand("PRINT 1," + m.getQty() + "\r\n");
                        DATA += m.getID() + "@";

                        System.out.println("DATA = " + DATA);

                        System.gc();
                        System.runFinalization();
                        System.gc();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                TscLAN.closeport();

                break;
            //==================================================================================================================================
            // Sticker Non Indicator Blue 5x4.5cm
            //==================================================================================================================================

            case 7:
                List<ModelSterileDetail> DATA_MODEL_7 = sterile;

                Iterator li7 = DATA_MODEL_7.iterator();

                TscLAN.openport(xIP, 9100);

                while (li7.hasNext()) {
                    try {
                        //สีฟ้าล่าสุด
                        ModelSterileDetail m = (ModelSterileDetail) li7.next();

                        TscLAN.setup(50, 45, PSpeed, PDensity, 0, 1, 1);
                        TscLAN.sendcommand("DIRECTION 1,0\r\n");
                        TscLAN.clearbuffer();

                        String Itemname[] = TextTwoLine.make2line(m.getItemname());

                        if( (Itemname[0].length() > 1) && (Itemname[1].length() == 0) ){
                            TscLAN.sendpicture(30, 15, TextAsBitmap.getTextBitmap(Itemname[0], 26));
                        }else{
                            TscLAN.sendpicture(30, 12, TextAsBitmap.getTextBitmap(Itemname[0], 22));
                            TscLAN.sendpicture(30, 36, TextAsBitmap.getTextBitmap(Itemname[1], 22));
                        }
                        TscLAN.sendpicture(130, 65, TextAsBitmap.getTextBitmap(m.getDepName2()+"    เครื่อง:"+m.getMachineName()+"  รอบ:"+m.getSterileRoundNumber(), 14));
                        TscLAN.sendpicture(130, 85, TextAsBitmap.getTextBitmap("No."+m.getUsageCode(), 20));
                        TscLAN.sendpicture(130, 113, TextAsBitmap.getTextBitmap("Packing Date: "+FormatDate.dateString(m.getSterileDate()), 16));
                        TscLAN.qrcode(10, 75, "H", "4", "A", "0", "M1", "S1", m.getUsageCode());
                        TscLAN.sendpicture(210, 140, TextAsBitmap.getTextBitmap(FormatDate.dateString(m.getExpireDate()), 35));
                        //คนเครียม ตรวจ
                        TscLAN.sendpicture(25, 185, TextAsBitmap.getTextBitmap((PEmp1 ? TextSplitName.SplitName(m.getUsr_prepare())+"-เตรียม"  : "-เตรียม") + "/" + (PEmp2 ? TextSplitName.SplitName(m.getUsr_approve())+"-ตรวจ"  : "-ตรวจ") + "/" + (PEmp3 ? TextSplitName.SplitName(m.getUsr_sterile())+"-ฆ่าเชื้อ"  : "-ฆ่าเชื้อ"), 16));

                        //ส่วนล่างสติ๊กเกอร์
                        //ชื่ออุปกรณ์
                        if( (Itemname[0].length() > 1) && (Itemname[1].length() == 0) ){
                            TscLAN.sendpicture(30, 210, TextAsBitmap.getTextBitmap(Itemname[0], 26));
                        }else{
                            TscLAN.sendpicture(30, 210, TextAsBitmap.getTextBitmap(Itemname[0], 20));
                            TscLAN.sendpicture(30, 240, TextAsBitmap.getTextBitmap(Itemname[1], 20));
                        }

                        //แผนก
                        TscLAN.sendpicture(190, 270, TextAsBitmap.getTextBitmap(m.getDepName2(), 18));
                        //Tsc.sendpicture(50,465,TextAsBitmap.getTextBitmap(m.getDepName2(),18));
                        //ชื่อเซ็ท
                        TscLAN.sendpicture(20, 270, TextAsBitmap.getTextBitmap("No."+m.getUsageCode(), 18));
                        //QR_Code
                        TscLAN.qrcode(295, 250, "H", "4", "A", "0", "M2", "S1", m.getUsageCode());
                        //วันหมดอายุ
                        TscLAN.sendpicture(40, 295, TextAsBitmap.getTextBitmap("Packing Date:"+FormatDate.dateString(m.getSterileDate()), 18));
                        //Tsc.printerfont(40, 295, "0", 0, 9, 9, "Packing Date:"+TextAsBitmap.getTextBitmap(FormatDate.dateString(m.getSterileDate()));
                        //วันหมดอายุ
                        TscLAN.sendpicture(120, 315, TextAsBitmap.getTextBitmap(FormatDate.dateString(m.getExpireDate()), 35));
                        //Tsc.printerfont(120, 325, "0", 0, 12, 12, m.getExpireDate());
                        TscLAN.sendcommand("PRINT 1," + m.getQty() + "\r\n");
                        //Tsc.closeport();

                        DATA += m.getID() + "@";

                        System.out.println("DATA = " + DATA);

                        System.gc();
                        System.runFinalization();
                        System.gc();

                    } catch (Exception e) {
                        e.printStackTrace();
                        //continue;
                    }

                }

                TscLAN.closeport();

                break;

            //==================================================================================================================================
            // Sticker Non Indicator Blue 5x8cm
            //==================================================================================================================================
            case 8:

                System.out.println(" -----------------> 6");

                List<ModelSterileDetail> DATA_MODEL_8 = sterile;

                Iterator li8 = DATA_MODEL_8.iterator();

                //TscLAN.openport(mUsbManager, getPrinter(6));
                TscLAN.openport(xIP, 9100);
                x = 15;
                y = 100;
                inc = 28;
                x_ = 330;
                s = 22;

                while (li8.hasNext()) {
                    try {
                        //======================================================================
                        // Model
                        //======================================================================
                        ModelSterileDetail m = (ModelSterileDetail) li8.next();

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
                            TscLAN.setup(51, 80, PSpeed, PDensity, 0, 1, 1+1/2);
                            TscLAN.sendcommand("DIRECTION 1,0\r\n");
                            TscLAN.clearbuffer();

                            // Header
                            TscLAN.sendpicture(60, 10, TextAsBitmap.getTextBitmap(DepName2, 26));
                            TscLAN.sendpicture(20, 60, TextAsBitmap.getTextBitmap(itemname, 24));
                            //TscLAN.sendpicture(285, 10, TextAsBitmap.getTextBitmap("(" + ((i/10)+1) + "/" +  ( ( parts.length / 11) + 1 ) + ")", s) );
                            // TscLAN.printerfont(x, 45, "0", 0, 10, 10, "------------------------------------------------");

                            // Detail (Set)
                            try {
                                String[] set = parts[i].split("#");
                                TscLAN.sendpicture(x, y, TextAsBitmap.getTextBitmap((i + 1) + ". " + set[0], s));
                                TscLAN.sendpicture(x_, y, TextAsBitmap.getTextBitmap(set[1], s));

                                set = parts[i + 1].split("#");
                                TscLAN.sendpicture(x, y + (inc * 1), TextAsBitmap.getTextBitmap((i + 2) + ". " + set[0], s));
                                TscLAN.sendpicture(x_, y + (inc * 1), TextAsBitmap.getTextBitmap(set[1], s));

                                set = parts[i + 2].split("#");
                                TscLAN.sendpicture(x, y + (inc * 2), TextAsBitmap.getTextBitmap((i + 3) + ". " + set[0], s));
                                TscLAN.sendpicture(x_, y + (inc * 2), TextAsBitmap.getTextBitmap(set[1], s));

                                set = parts[i + 3].split("#");
                                TscLAN.sendpicture(x, y + (inc * 3), TextAsBitmap.getTextBitmap((i + 4) + ". " + set[0], s));
                                TscLAN.sendpicture(x_, y + (inc * 3), TextAsBitmap.getTextBitmap(set[1], s));

                                set = parts[i + 4].split("#");
                                TscLAN.sendpicture(x, y + (inc * 4), TextAsBitmap.getTextBitmap((i + 5) + ". " + set[0], s));
                                TscLAN.sendpicture(x_, y + (inc * 4), TextAsBitmap.getTextBitmap(set[1], s));

                                set = parts[i + 5].split("#");
                                TscLAN.sendpicture(x, y + (inc * 5), TextAsBitmap.getTextBitmap((i + 6) + ". " + set[0], s));
                                TscLAN.sendpicture(x_, y + (inc * 5), TextAsBitmap.getTextBitmap(set[1], s));

                                set = parts[i + 6].split("#");
                                TscLAN.sendpicture(x, y + (inc * 6), TextAsBitmap.getTextBitmap((i + 7) + ". " + set[0], s));
                                TscLAN.sendpicture(x_, y + (inc * 6), TextAsBitmap.getTextBitmap(set[1], s));

                                set = parts[i + 7].split("#");
                                TscLAN.sendpicture(x, y + (inc * 7), TextAsBitmap.getTextBitmap((i + 8) + ". " + set[0], s));
                                TscLAN.sendpicture(x_, y + (inc * 7), TextAsBitmap.getTextBitmap(set[1], s));

                                set = parts[i + 8].split("#");
                                TscLAN.sendpicture(x, y + (inc * 8), TextAsBitmap.getTextBitmap((i + 9) + ". " + set[0], s));
                                TscLAN.sendpicture(x_, y + (inc * 8), TextAsBitmap.getTextBitmap(set[1], s));

                                set = parts[i + 9].split("#");
                                TscLAN.sendpicture(x, y + (inc * 9), TextAsBitmap.getTextBitmap((i + 10) + ". " + set[0], s));
                                TscLAN.sendpicture(x_, y + (inc * 9), TextAsBitmap.getTextBitmap(set[1], s));
                            }catch(Exception e){

                            }

                            // Footer
                            TscLAN.qrcode(290, 420, "H", "4", "A", "0", "M1", "S1", m.getUsageCode());
                            //TscLAN.sendpicture(300, 445,  TextAsBitmap.getTextBitmap(POption.equals("1") ? m.getItemcode() : m.getUsageCode(), 20));
                            TscLAN.sendpicture(15, 400, TextAsBitmap.getTextBitmap("No."+itemcode, 26));
                            TscLAN.sendpicture(x, 430, TextAsBitmap.getTextBitmap("เครื่อง:" + (PMachine ? MachineName : "") + "  รอบ:" + (PRound ? SterileRoundNumber : ""), 20));
                            TscLAN.sendpicture(x, 460, TextAsBitmap.getTextBitmap((PEmp1 ? usr_prepare : "") + "," + (PEmp2 ?usr_approve  : "") , 20));
                            TscLAN.sendpicture(x, 490, TextAsBitmap.getTextBitmap((PEmp3 ? usr_sterile : ""), 20));
                            TscLAN.sendpicture(x, 515, TextAsBitmap.getTextBitmap("PACKING DATE: "+FormatDate.dateString(SterileDate), 22));
                            TscLAN.sendpicture(150, 550, TextAsBitmap.getTextBitmap(FormatDate.dateString(ExpireDate), 45));
                            //TscLAN.sendpicture(250, 600, TextAsBitmap.getTextBitmap(age, s));

                            // Print
                            TscLAN.sendcommand("PRINT 1," + qty + "\r\n");
                        }

                        DATA += m.getID() + "@";

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                TscLAN.closeport();
                break;

            //==================================================================================================================================
            // Sticker Blue (Pasin)
            //==================================================================================================================================
            case 9:

                System.out.println(" -----------------> 7");

                //TscLAN.openport(mUsbManager, getPrinter(7));
                TscLAN.openport(xIP, 9100);
                TscLAN.setup(50, 25, PSpeed, PDensity, 0, 2, 1);
                TscLAN.sendcommand("DIRECTION 1,0\r\n");

                List<ModelSterileDetail> DATA_MODEL_9 = sterile;

                Iterator li9 = DATA_MODEL_9.iterator();

                //System.out.println(" Printer -----> " + getPrinter(7).getDeviceName() );

                while (li9.hasNext()) {
                    try {
                        ModelSterileDetail m = (ModelSterileDetail) li9.next();
                        TscLAN.clearbuffer();

                        TscLAN.sendpicture(60, 40, TextAsBitmap.getTextBitmap(m.getDepName2()+" " + m.getItemname(), 30));
                        TscLAN.sendpicture(20, 70, TextAsBitmap.getTextBitmap("ผลิต "+m.getSterileDate() + "(" + (PMachine ? m.getMachineName() : "") +"/"+ (PRound ? m.getSterileRoundNumber() : "") + ")", 25));
                        TscLAN.sendpicture(25, 100, TextAsBitmap.getTextBitmap("หมด", 25));
                        TscLAN.sendpicture(25, 130, TextAsBitmap.getTextBitmap("อายุ", 25));
                        TscLAN.sendpicture(350, 90, TextRotation.RotateBitmap(TextAsBitmap.getTextBitmap(m.getExpDay(), 25), 90));
                        TscLAN.sendpicture(110, 110, TextAsBitmap.getTextBitmap(FormatDate.dateString(m.getExpireDate()), 40));
                        //คนเครียม ตรวจ
                        TscLAN.sendpicture(25, 170, TextAsBitmap.getTextBitmap((PEmp1 ? TextSplitName.SplitName(m.getUsr_prepare())+"-เตรียม"  : "-เตรียม") + "," + (PEmp2 ? TextSplitName.SplitName(m.getUsr_approve())+"-ห่อ"  : "-ห่อ") + "," + (PEmp3 ? TextSplitName.SplitName(m.getUsr_sterile())+"-นึ่ง"  : "-นึ่ง"), 20));

                        TscLAN.sendcommand("PRINT 1," + m.getQty() + "\r\n");

                        DATA += m.getID() + "@";

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                TscLAN.closeport();

                break;

        }

        updatePrintStatus(DATA);

        //}

    }*/

}
