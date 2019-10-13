package readwriter.writerWins;
/*
 *
 * @author Danilo Sambugaro created on 30/09/2019 inside the package - readWrite
 *
 */

import java.util.concurrent.Semaphore;

class ReaderWriter {
    private int numReaders = 0;
    private int numWriters = 0;
    private int value = 0;

    private Semaphore mutexValue = new Semaphore(1);
    private Semaphore mutexReaders = new Semaphore(1);
    private Semaphore mutexWriters = new Semaphore(1);
    private Semaphore rlock = new Semaphore(1); // lock de leitores
    private Semaphore wlock = new Semaphore(1); // lock de escritores

    public int read() throws InterruptedException {
        mutexValue.acquire();
        int v = this.value;
        mutexValue.release();
        return v;
    }

    public void write() throws InterruptedException {
        mutexValue.acquire();
        this.value++;
        mutexValue.release();
    }

    public void startRead() throws InterruptedException {
        rlock.acquire();
        mutexReaders.acquire();
        numReaders++;
        if (numReaders == 1) {
            wlock.acquire();
        }
        mutexReaders.release();
        rlock.release();
    }

    public void endRead() throws InterruptedException {
        mutexReaders.acquire();
        numReaders--;
        if (numReaders == 0) {
            wlock.release(); // Libera os escritores
        }
        mutexReaders.release();
    }

    public void startWrite() throws InterruptedException {
        mutexWriters.acquire();
        numWriters++;
        if (numWriters == 1) {
            // Impede que novos leitores entrem enquanto aguarda o lock de escritor para poder escrever
            rlock.acquire();
        }
        mutexWriters.release();
        wlock.acquire();
    }

    public void endWrite() throws InterruptedException {
        mutexWriters.acquire();
        numWriters--;
        if (numWriters == 0) {
            rlock.release(); // Libera os leitores
        }
        mutexWriters.release();
        wlock.release();
    }
}