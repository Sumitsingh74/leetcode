import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DiningPhilosophers {

    private Lock leftForkLock = new ReentrantLock();
    private Lock rightForkLock = new ReentrantLock();

    public DiningPhilosophers() {

    }

    public void wantsToEat(int philosopher,
                           Runnable pickLeftFork,
                           Runnable pickRightFork,
                           Runnable eat,
                           Runnable putLeftFork,
                           Runnable putRightFork) throws InterruptedException {


        while(true) {
            if(leftForkLock.tryLock(100, TimeUnit.MILLISECONDS)) {
                try {
                    pickLeftFork.run();
                    if (rightForkLock.tryLock(100, TimeUnit.MILLISECONDS)) {
                        try {
                            pickRightFork.run();
                            eat.run();
                            putRightFork.run();
                            return;
                        } finally {
                            rightForkLock.unlock();
                        }
                    }


                } finally {
                    putLeftFork.run();
                    leftForkLock.unlock();
                }
            }
        }
    }
}