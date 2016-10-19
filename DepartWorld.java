package com.amoy17;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

class DepartWorld {
	private final static int Maxlen = 5 ;  //分词的最大长度
	private int len; //保存当前长度
	private String filename;  //要进行分词的文件名
	private String seg_filename; //分词后的内容保存在这个文件
	private ArrayList<String> str; //将文本的内容读到数组中保存
	private ArrayList<String> seg_str; //
	private String tempStr;  //当前进行分割的句子
	private String temp;    //保存正在进行分割的词语
	private String segStr;  //保存分割后的句子
	private double correctRate ;
	private static int[] Num = {0, 5854, 25603, 47842, 67755, 70545, 86981, 104300, 125685, 125685, 153767,
			162890, 184139, 195714, 202330, 202633, 208794, 225505, 232778, 266440, 279302, 279302, 279302, 294646,
			317661, 352631, 378494};

	public DepartWorld() {
		len = 0;
		str = new  ArrayList<String>();
		seg_str =  new ArrayList<String>();
		tempStr = new String();
		temp = new String();
		segStr = new String();
		filename = new String();
		seg_filename = new String();
	}
	ChineseDictionary dictionary = new ChineseDictionary();

	//选择文件
	public void setFileName(String name)
	{
		filename = name;
		seg_filename = "seg_"+name;
	}
	//分词
	public int[] ReverseDepartWorld(String fileName) throws IOException
	{
		OutputStreamWriter oWriter = new OutputStreamWriter(new FileOutputStream(new File(fileName)),"GBK");
		FirstLetterUtil covert = new FirstLetterUtil();
		ChineseDictionary dictionary = new ChineseDictionary();
		int singleWord = 0,_CorrectWord = 0;   //分别保存单字的个数和正确分出的词的个数

		int data;  //判断字符串的第一个是汉字还是字母或者数字
		int i=0;
		char c ; //保存首字母
		boolean flag = true; //用于判断字符串是否是数字或者字母，true代表是
		//存放 以该字母开头的词语在词典的下标

		//将文本读到数组中保存
		setText();
		for(;i < str.size();i++) {
			//得到文本的一个句子
			tempStr = str.get(i);
			segStr = "";
			while(tempStr.length()>0) {
				if(tempStr.length()>Maxlen) {
					temp = tempStr.substring(tempStr.length()-Maxlen, tempStr.length());
					len = Maxlen;
				} else {
					temp= tempStr;
					len = temp.length();
				}

				while(len >0) {
					data = covert.Judge(temp);
					//System.out.println(data);
					flag = true;
					//如果是汉字
					if(data == 0) {
						c = covert.getFirstLetter(temp).charAt(0);
						if(c != '*') {
							if(dictionary.compareWorlds(temp, Num[c-97],(c-97)) ||len == 1) {
								if(len == 1 ) {
									singleWord++;
								} else {
									_CorrectWord++;
								}
								segStr = " "+temp+" "+segStr;
								break;
							} else {
								len = len - 1;
								temp = temp.substring(temp.length() - len, temp.length());
							}
						} else if(c == '*') {
							if(dictionary.compareWorlds(temp,Num[26],26)|| len == 1) {
								if(len == 1 ) {
									singleWord++;
								} else {
									_CorrectWord++;
								}
								segStr = " "+temp+" "+segStr;
								break;
							} else {
								len = len - 1;
								temp = temp.substring(temp.length() - len, temp.length());
							}
						}
					}

					//判断是否是数字
					else if(data == 1)
					{
						int m;
						flag = true;
						for(m = 1;m < temp.length();m++) {
							if(!Character.isDigit(temp.charAt(m))) {
								flag  = false;
								break;
							}
						}

						//该字符串为数字
						if(flag == true||len == 1) {
							if(len == 1 ) {
								singleWord++;
							} else {
								_CorrectWord++;
							}
							segStr = temp+segStr;
							break;
						} else {
							len = len - m;
							temp = temp.substring(temp.length() - len, temp.length());
						}
					}//如果是数字

					//判断是否是字母
					else if(data == 2) {
						int m;
						flag = true;
						for( m = 1;m < temp.length();m++) {
							if(!Character.isLetter(temp.charAt(m))) {
								flag  = false;
								break;
							}
						}

						//该字符串为数字
						if(flag == true||len == 1) {
							if(len == 1 ) {
								singleWord++;
							} else {
								_CorrectWord++;
							}
							segStr = " "+temp+segStr;
							break;
						} else {
							len = len -m;
							temp = temp.substring(temp.length() - len, temp.length());
						}
					}//如果是字母

					else if(data == -1) {
						if(len == 1) {
							singleWord++;
							segStr = " "+temp+" "+segStr;
							break;
						} else {
							len = len - 1;
							temp = temp.substring(temp.length() - len, temp.length());
						}
					}//是其他的字符
				}//while
				//System.out.println(segStr);
				tempStr = tempStr.substring(0, tempStr.length()- temp.length());
			}//while
			seg_str.add(segStr);
		}//for
		//writeFileByFileWriter(seg_filename,seg_str);
		System.out.print("Output " + fileName);
		for (int j = 0; j < seg_str.size(); j++) {
			oWriter.write(seg_str.get(j));//写入文件 oWriter
		}
		oWriter.flush();//缓冲区清空
		oWriter.close();
		System.out.print("  Finish  ");
		correctRate = (double)(_CorrectWord)/(singleWord + _CorrectWord);
		int[] Word = new int[2];
		Word[0] = _CorrectWord;
		Word[1] = singleWord;
		return Word;
	}

	public double getRate() {
		return correctRate;
	}
	//将文件中的文本读到数组中
	public void setText() {
		File file = new File(filename);
		try {
			FileInputStream fileInputStream = new FileInputStream(file);
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "GBK");
			BufferedReader reader = new BufferedReader(inputStreamReader);
			String lineContent = "";
			for (int j = 0;(lineContent = reader.readLine()) != null;j++) {
				str.add(j,lineContent);
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
}
