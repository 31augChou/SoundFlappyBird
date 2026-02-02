package game;

import javax.sound.sampled.*;

public class AudioInput {

    private TargetDataLine line;
    private boolean loud = false;
    private final int THRESHOLD = 2000;

    public void start() {
        try {
            AudioFormat format = new AudioFormat(44100, 16, 1, true, true);
            line = AudioSystem.getTargetDataLine(format);
            line.open(format);
            line.start();

            new Thread(() -> {
                byte[] buffer = new byte[1024];
                while (line.isOpen()) {
                    int bytesRead = line.read(buffer, 0, buffer.length);
                    long sum = 0;

                    for (int i = 0; i < bytesRead; i += 2) {
                        int sample = (buffer[i] << 8) | buffer[i + 1];
                        sum += Math.abs(sample);
                    }

                    if (bytesRead > 0) {
                        loud = (sum / Math.max(1, bytesRead / 2)) > THRESHOLD;
                    } else {
                        loud = false;
                    }

                }
            }).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isLoud() {
        return loud;
    }

    public void stop() {
        if (line != null) line.close();
    }
}
