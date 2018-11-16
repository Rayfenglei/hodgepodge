// IBookManager.aidl
package com.example.ray.myapplication;
import com.example.ray.myapplication.Book;
// Declare any non-default types here with import statements

interface IBookManager {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
     List<Book>getBookList();
     void addBook(in Book book);
}
