package DiningPhilosopher;
/*
 *
 * @author Danilo Sambugaro created on 13/10/2019 inside the package - DiningPhilosopher
 *
 */

import java.util.concurrent.Semaphore;

class Fork {

    private boolean hasHungry;
    private Semaphore fork = new Semaphore(1);
    private Semaphore mutex = new Semaphore(1);
    private int maxHungry;

    Fork(int maxHungry){
        this.maxHungry = maxHungry;
    }

    /**
     * Pega o garfo, dando prioridade a famintos que chegam primeiro:
     * Um filosofo faminto não solta o garfo, caso outro filosofo faminto queira
     * ele não consegue pegar.
     *
     * @param hungry    Nível de fome de um filosofo
     * @return          Verdadeiro se o pegou o garfo ou falso se não pegou.
     * @author          Danilo Sambugaro
     */
    public boolean pickup(int hungry) throws InterruptedException {
        mutex.acquire();
        if (hasHungry) {
            mutex.release();
            return false;
        } else if (hungry >= maxHungry){
            hasHungry = true;
            mutex.release();
            fork.acquire();
            return true;
        } else {
            mutex.release();
            return fork.tryAcquire();
        }
    }

    /**
     * Solta o garfo
     *
     * @author          Danilo Sambugaro
     */
    public void drop() throws InterruptedException {
        mutex.acquire();
        hasHungry = false;
        fork.release();
        mutex.release();
    }
}
