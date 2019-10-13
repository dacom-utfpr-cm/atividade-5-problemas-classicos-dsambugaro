package readwriter.balanced;
/*
 *
 * @author Danilo Sambugaro created on 13/10/2019 inside the package - readwriter
 *
 */

public class Reader implements Runnable {

    ReaderWriter lock;

    Reader(ReaderWriter lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            lock.startRead();
            System.out.println("[ " + Thread.currentThread().getName() + " ] Reading... " + lock.read());
            Thread.sleep(2000);
            lock.endRead();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
