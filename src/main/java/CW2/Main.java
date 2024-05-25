package CW2;

import com.sun.source.tree.BreakTree;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    private static boolean[] done = new boolean[8];

    public static void main(String[] args) throws IOException {
        String finishProduct = "v8.png";
        String[] files = {"D:\\JetBrains\\IdeaProjects\\CW2\\src\\main\\java\\CW2\\6a0bf34c-1293-4397-b5bd-f3bffffff26f.png",
                "D:\\JetBrains\\IdeaProjects\\CW2\\src\\main\\java\\CW2\\9a5c3251-1230-446f-b302-ada6113c727c.png",
                "D:\\JetBrains\\IdeaProjects\\CW2\\src\\main\\java\\CW2\\9e3e718d-5019-4c1e-b53a-f990c37cbed1.png",
                "D:\\JetBrains\\IdeaProjects\\CW2\\src\\main\\java\\CW2\\52e66f6f-5aae-457e-8556-53751b264a5d.png",
                "D:\\JetBrains\\IdeaProjects\\CW2\\src\\main\\java\\CW2\\58b38db9-b1e3-4090-981b-714cb479a12d.png",
                "D:\\JetBrains\\IdeaProjects\\CW2\\src\\main\\java\\CW2\\69b99e3c-73ac-43ae-84dc-fce7999109f5.png",
                "D:\\JetBrains\\IdeaProjects\\CW2\\src\\main\\java\\CW2\\1021fd86-743a-49ee-a79e-2661c6269c60.png",
                "D:\\JetBrains\\IdeaProjects\\CW2\\src\\main\\java\\CW2\\75582132-40c7-459a-aee5-577523828b20.png"};
        ExecutorService poolService = Executors.newFixedThreadPool(files.length);
        for (String file : files) {
            Future<Boolean> future = poolService.submit(new Task(file, finishProduct));
        }
        poolService.shutdown();
        if (done[done.length - 1]) {
            BufferedImage in = ImageIO.read(new File(finishProduct));
            BufferedImage newImage = new BufferedImage(in.getWidth(), in.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = newImage.createGraphics();
            g.drawImage(in, 0, 0, null);
            ImageIO.write(newImage, "PNG", new FileOutputStream(finishProduct));
        }
    }

    public static int evenOrOdd(byte b) {
        return Integer.bitCount((int) b);
    }

    public static class Task implements Callable<Boolean> {
        private final String file;
        private final String finishProduct;

        Task(String file, String finishProduct) {
            this.file = file;
            this.finishProduct = finishProduct;
        }


        public Boolean call() throws IOException {
            DataInputStream dis = new DataInputStream(new FileInputStream(file));
            int sz = dis.readInt();
            byte[] picturePart = new byte[sz];
            dis.read(picturePart);
            int even = dis.readInt();
            int part = dis.readInt();
            return (writing(picturePart, part) ? 0 : 1) == even;
        }

        public int fourBytesToInt(Reader is) throws IOException {
            int[] bytes = new int[4];
            int b;
            for (int i = 0; i < 4; i++) {
                b = is.read();
                bytes[i] = b;
            }
            return ((bytes[0] & 0xFF) << 24) | ((bytes[1] & 0xFF) << 16) | ((bytes[2] & 0xFF) << 8) | (bytes[3] & 0xFF);
        }

        public synchronized Boolean writing(byte[] picturePart, int order) {
            int evenOrNo = -1;
            try (Writer fw = new FileWriter(finishProduct, true)) {
                if (order != 0) {
                    while (!done[order - 1]) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                System.out.println(order);

                for (int i = 0; i < picturePart.length; i++) {
                    evenOrNo += evenOrOdd(picturePart[i]);
                    fw.write(picturePart[i]);
                }
                evenOrNo %= 2;
                done[order] = true;
                fw.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return evenOrNo == 0;
        }

    }
}