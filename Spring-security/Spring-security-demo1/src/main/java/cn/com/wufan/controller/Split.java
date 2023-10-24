package cn.com.wufan.controller;

import cn.com.wufan.pojo.Xyz;
import cn.hutool.core.io.FileUtil;
import org.apache.catalina.connector.InputBuffer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
//求 xyz的和以及平均值
public class Split {

    public static void main(String[] args) throws Exception {

        HashMap<Integer, Xyz> totalNUm = new HashMap<>();
        String inpath = "D:\\04_head_400-500.gro";
        String outPath1 = "D:\\sum.txt";
        String outPath2 = "D:\\avg.txt";

        FileInputStream fileInputStream = new FileInputStream(inpath);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(inpath));
        String tempString = "";
        int count = 0;
        while ((tempString = bufferedReader.readLine()) != null){
            String[] s = tempString.trim().split("\\s+");
            if(s.length > 5){
                if("P".equals(s[1]) || "O3".equals(s[1])){
                    Double x = parseDouble(s[3]);
                    Double y = parseDouble(s[4]);
                    Double z = parseDouble(s[5]);
                    Integer num = Integer.valueOf(s[2]);

                    if(!totalNUm.containsKey(num)){
                        totalNUm.put(num,new Xyz(x,y,z));
                    }else {
                        Xyz xyz = totalNUm.get(num);
                        xyz.setX(avgDouble(xyz.getX() + x));
                        xyz.setY(avgDouble(xyz.getY() + y));
                        xyz.setZ(avgDouble(xyz.getZ() + z));
                        totalNUm.put(num,xyz);
                    }

                    if(num == 2){
                        count++;
                    }
                }
            }
        }


        //输出求和文件
        File sumFile = new File(outPath1);
        File avgFile = new File(outPath2);
        if(sumFile.exists()){
            sumFile.delete();
        }
        if(avgFile.exists()){
            avgFile.delete();
        }


        Iterator<Map.Entry<Integer, Xyz>> iterator = totalNUm.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<Integer, Xyz> next = iterator.next();
            Integer key = next.getKey();
            Xyz value = next.getValue();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(key);
            stringBuilder.append(" ");
            stringBuilder.append(avgDouble(value.getX()) +" ");
            stringBuilder.append(avgDouble(value.getY()) +" ");
            stringBuilder.append(avgDouble(value.getZ()));
            stringBuilder.append("\r");
            FileUtil.appendString(stringBuilder.toString(),sumFile, StandardCharsets.UTF_8);


            //求平均
            //对x y z求平均
            StringBuilder aa = new StringBuilder();
            aa.append(key);
            aa.append(" ");
            aa.append(avgDouble(value.getX()/count) +" ");
            aa.append(avgDouble((value.getY() / count)) +" ");
            aa.append(avgDouble((value.getZ() / count)));
            aa.append("\r");
            FileUtil.appendString(aa.toString(),avgFile, StandardCharsets.UTF_8);


        }


    }




    public static Double parseDouble(String str){
        DecimalFormat df = new DecimalFormat("#.###");
        return Double.parseDouble(df.format(Double.parseDouble(str)));
    }

    public static Double avgDouble(Double dd){
        DecimalFormat df = new DecimalFormat("#.###");
        return Double.parseDouble(df.format(dd));
    }



}
