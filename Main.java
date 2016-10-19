package com.amoy17;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws IOException {
		GetFileName getFileName = new GetFileName();
		String[] dirName = new String[1];
		dirName[0] = "txt/";
		String all[] = getFileName.main(dirName);
		int fileNumber;

		if (all[0].contentEquals(".DS_Store")) {
			fileNumber = all.length - 1;
		} else {
			fileNumber = all.length;
		}

		int[][] count = new int[fileNumber][2];

		ChineseDictionary dictionary = new ChineseDictionary();

		long startTime=System.currentTimeMillis();//获取开始时间
		for (int i = 0; i < all.length; i++) {
			if (!all[i].contentEquals(".DS_Store")){
				DepartWorld departworld = new DepartWorld();
				System.out.println("Processing " + all[i]);
				departworld.setFileName(dirName[0]+all[i]);
				if (fileNumber < all.length) {
					count[i - 1] = departworld.ReverseDepartWorld("result/"+"seg_"+all[i]);
					System.out.println(i + "/" + fileNumber);
				} else {
					count[i] = departworld.ReverseDepartWorld("result/" + "seg_" + all[i]);
					System.out.println(i+1 + "/" + fileNumber);
				}
			}
		}

		TfDf out = new TfDf();
		if (all[0].contentEquals(".DS_Store")) {
			String[] para = new String[all.length - 1];
			for (int i = 0; i < para.length; i++) {
				para[i] = all[i+1];
			}
			out.main(para);
		} else {
			String[] para = new String[all.length];
			for (int i = 0; i < para.length; i++) {
				para[i] = all[i];
			}
			out.main(para);
		}

		long endTime=System.currentTimeMillis(); //获取结束时间

		int correct = 0, allWord = 0;

		for (int i = 0; i < fileNumber; i++) {
			correct += count[i][0];
			allWord += count[i][0] + count[i][1];
		}

		double correctRate = (double)correct/allWord;
		System.out.println("总分词数:" + allWord);
		System.out.println(String.format("准确率：%.4f", correctRate));
		System.out.print("词典的容量：");
		System.out.println(dictionary.getSize());
		System.out.print("运行时间：");
		System.out.println((endTime-startTime)/1000+"s");
	}
}


