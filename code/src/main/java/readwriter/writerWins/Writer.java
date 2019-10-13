package readwriter.writerWins;
/*
 *
 * @author Danilo Sambugaro created on 13/10/2019 inside the package - readwriter
 *
 */

public class Writer implements Runnable {

    ReaderWriter lock;

    Writer(ReaderWriter lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            lock.startWrite();
            lock.write();
            System.out.println("[ " + Thread.currentThread().getName() + " ] Writing...");
            Thread.sleep(2000);
            lock.endWrite();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
