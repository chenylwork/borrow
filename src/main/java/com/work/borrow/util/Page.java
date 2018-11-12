package com.work.borrow.util;

import java.util.List;

public class Page <T>{
    private int no; //页码
    private int pages; // 总页数
    private int size; // 总个数
    private int length; // 每页个数
    private List<T> data; // 当前页数据

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getPages() {
        if(size != 0 && length != 00) {
            pages = (size%length == 0) ? size/length : size/length + 1;
        }
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    @Override
    public String toString() {
        return "Page{" +
                "no=" + no +
                ", pages=" + pages +
                ", size=" + size +
                ", length=" + length +
                ", data=" + data +
                '}';
    }
}
