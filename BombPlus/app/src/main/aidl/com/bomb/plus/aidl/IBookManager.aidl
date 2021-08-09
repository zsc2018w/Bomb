// IBookManager.aidl
package com.bomb.plus.aidl;

import com.bomb.plus.aidl.Book;

 interface IBookManager {


    void addBook(in Book book);



    List<Book> getBookList();


    oneway void  checkStatus(int num);

}