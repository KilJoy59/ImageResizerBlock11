import org.imgscalr.Scalr;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Project ImageResizer
 * Created by End on сент., 2019
 */
public class Main {
    public static void main(String[] args) {
        String srcFoleder = "D:/src";
        String dstFolder = "D:/dst";
        File srcDir = new File(srcFoleder);
        long start = System.currentTimeMillis();
        File[] files = srcDir.listFiles();

        ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        for (File file : files) {
            pool.submit(new ImageResizer(file, dstFolder, start));
        }
        //метод закрывающий pool
        shutdownAndAwaitTermination(pool);
    }

    private static void shutdownAndAwaitTermination(ExecutorService pool) {
        //отключаем новые задачи которые были отправлены
        pool.shutdown();
        try {
            //ждем завершения существующих задач
            if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
                //отменяем текущие задачи
                pool.shutdownNow();
                //ждем реакцию на отмену
                if (!pool.awaitTermination(60, TimeUnit.SECONDS))
                    System.err.println("Pool did not terminate");
            }
        } catch (InterruptedException ie) {
            // повторяем
            pool.shutdownNow();
            // сохраянем статус
            Thread.currentThread().interrupt();
        }
    }
}
