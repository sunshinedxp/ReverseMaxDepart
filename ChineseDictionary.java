package com.amoy17;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

//词典
class ChineseDictionary
{
	private ArrayList<String> dictionary ;
	private static int[] number = {0, 5854, 25603, 47842, 67755, 70545, 86981, 104300, 125685, 125685, 153767,
			162890, 184139, 195714, 202330, 202633, 208794, 225505, 232778, 266440, 279302, 279302, 279302, 294646,
			317661, 352631, 378494}; //保存分别以26个字母开头的第一个词语在数组中下标
	private boolean flag = false ;
	private char c1 = ' ',c2 = ' ';

	public ChineseDictionary()
	{
		dictionary = new ArrayList<String>();

		try {
			FileInputStream fileInputStream = new FileInputStream("dict.txt");
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "GBK");
			BufferedReader reader = new BufferedReader(inputStreamReader);
			String lineContent = "";
			int j = 0;
			for (;(lineContent = reader.readLine()) != null;j++) {
				dictionary.add(j,lineContent);
			}//for
			fileInputStream.close();
			inputStreamReader.close();
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			Throwable e = null;
			e.printStackTrace();
		}

	}

	//得到词典的词语的个数
	public int getSize()
	{
		return dictionary.size();
	}

	//比较词典是否存在这个词语
	public boolean compareWorlds(String str,int num,int a)
	{
		int i = num;
		for(;i < 392790;i++) {
			if(str.equals(dictionary.get(i))) {
				return true;
			}
		}
		return false;
	}
}
