package readwriter.balanced;
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
    private int queueSize;

    private Semaphore mutexValue = new Semaphore(1);
    private Semaphore mutexReaders = new Semaphore(1);
    private Semaphore mutexWriters = new Semaphore(1);
    private Semaphore priority = new Semaphore(1); // Lock de prioridade
    private Semaphore rlock = new Semaphore(1);
    private Semaphore wlock = new Semaphore(1);

    ReaderWriter(int queueSize){
        this.queueSize = queueSize;
    }

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
        if (numWriters >= queueSize) {
            priority.acquire(); // Quando o número de escritores é maior que um limiar
                                // A prioridade passa a ser dos leitores, impedindo que novos escritores acessem
        }
        rlock.acquire();        // Espera até os escritores atuais sairem (se houver algum)
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
            wlock.release();
            priority.release(); // Devolve a prioridade aos escritores
        }
        mutexReaders.release();
    }

    public void startWrite() throws InterruptedException {
        priority.acquire(); // A principio os escritores possuem prioridade
        mutexWriters.acquire();
        numWriters++;
        if (numWriters == 1) {
            rlock.acquire();
        }
        mutexWriters.release();
        priority.release();
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