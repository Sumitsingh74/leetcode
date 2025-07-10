import  java.util.*;
import java.util.concurrent.*;
import java.util.function.*;

class FizzBuzz {
    private int n;
    private Semaphore number;
    private Semaphore fizz;
    private Semaphore buzz;
    private Semaphore fizzbuzz;

    public FizzBuzz(int n) {
        this.n = n;
        number=new Semaphore(1);
        fizz=new Semaphore(0);
        buzz=new Semaphore(0);
        fizzbuzz=new Semaphore(0);
    }

    public void fizz(Runnable printFizz) throws InterruptedException {
    for (int i = 1; i <= n; i++) {
        if (i % 3 == 0 && i % 5 != 0) {
            fizz.acquire();
            printFizz.run();
            number.release();
        }
    }
    }


    public void buzz(Runnable printBuzz) throws InterruptedException {
    for (int i = 1; i <= n; i++) {
        if (i % 5 == 0 && i % 3 != 0) {
            buzz.acquire();
            printBuzz.run();
            number.release();
        }
    }
}

public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
    for (int i = 1; i <= n; i++) {
        if (i % 15 == 0) {
            fizzbuzz.acquire();
            printFizzBuzz.run();
            number.release();
        }
    }
}


    public void number(IntConsumer printNumber) throws InterruptedException {
        for(int i=1;i<=n;i++){
            number.acquire();
            if(i%3!=0&&i%5!=0){
                printNumber.accept(i);
                number.release();
            }else if(i%3==0&&i%5==0){
                fizzbuzz.release();
            }else if(i%5==0&&i%3!=0){
                buzz.release();
            }else if(i%3==0&&i%5!=0) {
                fizz.release(); 
            }
        }
    }
}