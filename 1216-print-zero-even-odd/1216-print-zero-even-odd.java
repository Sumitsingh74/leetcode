import  java.util.*;
import java.util.concurrent.*;
import java.util.function.*;

class ZeroEvenOdd {
    private int n;
    private Semaphore zero;
    private Semaphore even;
    private Semaphore odd;


    public ZeroEvenOdd(int n) {
        this.n = n;
        zero=new Semaphore(1);
        odd=new Semaphore(0);
        even=new Semaphore(0);
    }

    public void zero(IntConsumer printNumber) throws InterruptedException {
        boolean isOdd=true;
        for(int i=1;i<=n;i++){
            zero.acquire();
            printNumber.accept(0);
            if(isOdd){
                odd.release();
            }else {
                even.release();
            }
            isOdd=!isOdd;
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        for(int i=2;i<=n;i+=2) {
            even.acquire();
            printNumber.accept(i);
            zero.release();
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        for(int i=1;i<=n;i+=2) {
            odd.acquire();
            printNumber.accept(i);
            zero.release();
        }
    }
}