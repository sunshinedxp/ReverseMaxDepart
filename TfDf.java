package com.amoy17;

import java.io.*;
import java.math.*;

public class TfDf {
    public static void main(String[] args) throws IOException {

        WordCount headDf = new WordCount();
        WordCount df = headDf;
        WordCount[] head = new WordCount[args.length];
        WordCount[] p = new WordCount[args.length];

        for (int j = 0; j < args.length; j++) {
            p[j] = head[j] = new WordCount();
        }

        for (int i = 0; i < args.length; i++) {
            BufferedReader fReader = null;
            try {
                fReader = new BufferedReader(new InputStreamReader(new FileInputStream("result/"+"seg_"+args[i]), "GBK"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            String s = null;
            String wholeText = "";
            while((s=fReader.readLine())!=null) {
                wholeText += s;
            }
            fReader.close();

            String word[] = wholeText.split(" ");

            boolean mark = true;

            for (int j = 0; j < word.length; j++) {//遍历所有的词
                while (p[i].getNext() != null) {//遍历链表
                    p[i] = p[i].getNext();//下一个元素
                    if (p[i].getWord().contentEquals(word[j]) && !word[j].isEmpty()) {//对比word
                        p[i].setCount(p[i].getCount()+1);//如果相等,tf加一
                        mark = false;//新增元素设为否
                    }
                }
                boolean flag = true;
                if (mark && !word[j].isEmpty()) {
                    p[i].setNext(new WordCount(word[j], 1));//如果是新词,新添加元素
                    p[i] = head[i];//重置p
                    while (df.getNext() != null) {//遍历链表
                        df = df.getNext();//下一个元素
                        if (df.getWord().contentEquals(word[j])) {//对比word
                            df.setFileCount(df.getFileCount()+1);//如果相等,df加一
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        df.setNext(new WordCount(word[j], 1, 1));
                    }
                    df = headDf;
                }

                p[i] = head[i];//重置p
                mark = true;//重置mark
            }
        }//for

        for (int j = 0; j < args.length; j++) {
            p[j] = head[j];
        }

        //计算idf

        for (int i = 0; i < args.length; i++) {
            df = headDf;
            String input = "";
            System.out.print("Output result/" + "seg_w_" + args[i]);
            OutputStreamWriter oWriter = new OutputStreamWriter(new FileOutputStream(new File("result/"+"seg_w_"+args[i])),"GBK");
            while (p[i].getNext() != null) {
                p[i] = p[i].getNext();
                while(df.getNext() != null){
                    df = df.getNext();

                    if (df.getWord().contentEquals(p[i].getWord())) {
                        Double idf = Math.log10((double)args.length/df.getFileCount());
                        Double w = p[i].getCount() * idf;
                        String ws = Double.toString(w);
                        if (ws.length() >= 6)
                            ws = ws.substring(0,6);
                        //System.out.println(p[i].getWord() + ":" + idfs);


                        input += p[i].getWord() + ":" + ws + "\r\n";
                        break;
                    }
                }
                df = headDf;
            }
            oWriter.write(input);
            oWriter.flush();//缓冲区清空
            oWriter.close();
            System.out.println("  Finish");
        }
    }
}
