package readwriter.writerWins;
/*
 *
 * @author Danilo Sambugaro created on 13/10/2019 inside the package - read.writer.fair
 *
 */

public class Main {
    public static void main(String[] args) {
        ReaderWriter lock = new ReaderWriter();
        Reader reader = new Reader(lock);
        Writer writer = new Writer(lock);

        for (int i = 0; i < 5; i++) {
            Thread writerThread = new Thread(writer, "Writer " + i);
            Thread readerThread = new Thread(reader, "Reader " + i);

            readerThread.start();
            writerThread.start();
        }

    }
}
