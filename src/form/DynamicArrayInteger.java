/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package form;

/**
 *
 * @author resar
 */
public class DynamicArrayInteger {
    public int arr[];
    private int count;

        public DynamicArrayInteger(int length) { arr = new int[length]; }
        public void insert(int element)
        {
            if (arr.length == count) {
                int newArr[] = new int[1 + count];
                for (int i = 0; i < count; i++) {
                    newArr[i] = arr[i];
                }
                arr = newArr;
            }
            arr[count++] = element;
        }
}
