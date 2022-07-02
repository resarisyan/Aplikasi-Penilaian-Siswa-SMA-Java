/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package form;

/**
 *
 * @author resar
 */
public class DynamicArrayString {
        public String arr[];
        private int count;

        public DynamicArrayString(int length) { arr = new String[length]; }
        public void insert(String element)
        {
            if (arr.length == count) {
                String newArr[] = new String[1 + count];
                for (int i = 0; i < count; i++) {
                    newArr[i] = arr[i];
                }
                arr = newArr;
            }
            arr[count++] = element;
        }
}
