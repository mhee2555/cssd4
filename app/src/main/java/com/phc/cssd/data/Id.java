package com.phc.cssd.data;

import com.phc.cssd.R;

public class Id {
    public static int getMachineID(int Machine){
        if(Machine == 1){
            return R.id.machine_1;
        }else if(Machine == 1){
            return R.id.machine_2;
        }else if(Machine == 1){
            return R.id.machine_3;
        }else if(Machine == 1){
            return R.id.machine_4;
        }else if(Machine == 1){
            return R.id.machine_5;
        }else{
            return R.id.machine_0;
        }
    }

    public static int getProcessID(int Process){
        if(Process == 1){
            return R.id.process_1;
        }else if(Process == 1){
            return R.id.process_2;
        }else if(Process == 1){
            return R.id.process_3;
        }else if(Process == 1){
            return R.id.process_4;
        }else if(Process == 1){
            return R.id.process_5;
        }else{
            return R.id.process_0;
        }
    }
}
