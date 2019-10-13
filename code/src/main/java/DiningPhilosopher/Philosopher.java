package DiningPhilosopher;
/*
 *
 * @author Danilo Sambugaro created on 13/10/2019 inside the package - DiningPhilosopher
 *
 */

class Philosopher implements Runnable {

    private int hungry = 0;
    private Fork forkLeft;
    private Fork forkRight;
    private int maxHungry;

    Philosopher(Fork fork_left, Fork fork_right, int maxHungry) {
        this.forkLeft = fork_left;
        this.forkRight = fork_right;
        this.maxHungry = maxHungry;
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (hungry >= maxHungry) {
                    System.out.println("[" + Thread.currentThread().getName() + "] Is hungry...");
                    Thread.sleep(1000);
                    eat();
                } else {
                    System.out.println("[" + Thread.currentThread().getName() + "] Is thinking...");
                    Thread.sleep(3000);
                    hungry++; // Incrementa o nível de fome enquanto pensa
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void eat() throws InterruptedException {
        if (forkLeft.pickup(hungry)) {
            if (forkRight.pickup(hungry)) {
                try {
                    System.out.println("[" + Thread.currentThread().getName() + "] Is eating...");
                    Thread.sleep(5000);
                    hungry = 0; // Após comer, o nível de fome retorna ao valor inicial
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                forkRight.drop();
                forkLeft.drop();
            } else {
                forkLeft.drop();
                hungry++; // Incrementa o nível de fome, pois não conseguiu comer ainda
            }
        }
    }
}
