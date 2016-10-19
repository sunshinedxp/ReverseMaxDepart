package com.amoy17;

public class WordCount {
    public String word;
    int count;
    WordCount next;
    int fileCount;
    String idf;

    public WordCount() {
        this.next = null;
    }

    public WordCount(String word, int i) {
        this.word = word;
        this.count = i;
        this.next = null;
        this.fileCount = 1;
        this.idf = null;
    }

    public WordCount(String word, int i, int j) {
        this.word = word;
        this.count = i;
        this.next = null;
        this.fileCount = j;
        this.idf = null;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getWord() {
        return word;
    }

    public int getCount() {
        return count;
    }

    public void setFileCount(int fileCount) {
        this.fileCount = fileCount;
    }

    public int getFileCount() {
        return fileCount;
    }

    public void setIdf(String idf) {
        this.idf = idf;
    }

    public String getIdf() {
        return idf;
    }

    public void setNext(WordCount next) {
        this.next = next;
    }

    public WordCount getNext() {
        return next;
    }
}
