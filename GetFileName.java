package com.amoy17;

import java.io.File;

public class GetFileName {
    public static String[] main(String[] args) {
        //String path = "/Users/s17/Documents/学业/第四学期/数字内容安全/实验3/2013211699-龚柯宇-实验三/体育分类测试文档/34冰雪项目/";
        //getFile(args[0]);
        File file = new File(args[0]);
        File[] array = file.listFiles();
        String[] allFileName = new String[array.length];
        for(int i=0;i<array.length;i++){
            allFileName[i] = array[i].getName();
            //System.out.println(array[i].getName());
        }
        return allFileName;
    }

    private static void getFile(String path){
        File file = new File(path);
        File[] array = file.listFiles();
        String[] allFileName = new String[array.length];
        for(int i=0;i<array.length;i++){
            allFileName[i] = array[i].getName();
            //System.out.println(array[i].getName());
        }
    }
}

