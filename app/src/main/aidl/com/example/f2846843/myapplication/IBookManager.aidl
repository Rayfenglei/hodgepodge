// IBookManager.aidl
package com.example.f2846843.myapplication;
import com.example.f2846843.myapplication.Book;
// Declare any non-default types here with import statements

interface IBookManager {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
     List<Book>getBookList();
     void addBook(in Book book);
}
