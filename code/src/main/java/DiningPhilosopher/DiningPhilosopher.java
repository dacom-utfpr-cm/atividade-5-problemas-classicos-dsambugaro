package DiningPhilosopher;
/*
 *
 * @author Danilo Sambugaro created on 13/10/2019 inside the package - DiningPhilosopher
 *
 */


class DiningPhilosopher  {
    public static void main(String[] args) {
        int maxHungry = 5;
        int qt = 5;
        Fork[] fork = new Fork[qt];
        Philosopher[] philosopher = new Philosopher[qt];

        for (int i = 0; i < fork.length; i++) {
            fork[i] = new Fork(maxHungry);
        }

        for (int i = 0; i < philosopher.length; i++) {

            if (i != philosopher.length - 1) {
                Thread philosopherThread = new Thread(new Philosopher(fork[i], fork[i + 1], maxHungry), "Philosopher " + i);
                philosopherThread.start();
            } else {
                Thread philosopherThread = new Thread(new Philosopher(fork[0], fork[i], maxHungry), "Philosopher " + i);
                philosopherThread.start();
            }
        }
    }
}
