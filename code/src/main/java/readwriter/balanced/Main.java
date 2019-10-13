package readwriter.balanced;
/*
 *
 * @author Danilo Sambugaro created on 13/10/2019 inside the package - read.writer.fair
 *
 */



public class Main {
    public static void main(String[] args) {
        ReaderWriter lock = new ReaderWriter(3);
        Reader reader = new Reader(lock);
        Writer writer = new Writer(lock);

        for (int i = 0; i < 6; i++) {
            Thread writerThread = new Thread(writer, "Writer " + i);
            Thread readerThread = new Thread(reader, "Reader " + i);

            readerThread.start();
            writerThread.start();
        }

    }
}
