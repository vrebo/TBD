

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author VREBO
 */
public class NewClass {
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0, c = 5, sum = c, t = 15; i < 52; i++) {
            System.out.println(t);
            sum += t;
            t += c;
            System.out.println(sum);
        }
    }
}
