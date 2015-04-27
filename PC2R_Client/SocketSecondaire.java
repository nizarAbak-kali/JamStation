package music;

import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

public class SocketSecondaire extends Thread{
	public void run(){
		while(Client.channel2.isClosed())
			try {
				sleep(100);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		AudioFormat format = new AudioFormat(8000.0f, 16, 1, true, true);
	    TargetDataLine microphone;
	    try {
	        microphone = AudioSystem.getTargetDataLine(format);

	        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
	        microphone = (TargetDataLine) AudioSystem.getLine(info);
	        microphone.open(format);

	        //ByteArrayOutputStream out = new ByteArrayOutputStream();
	        int numBytesRead;
	        int CHUNK_SIZE = 1024;
	        byte[] data = new byte[microphone.getBufferSize() / 5];
	        microphone.start();

	        int bytesRead = 0;
	        while (bytesRead < 100000) {
	            numBytesRead = microphone.read(data, 0, CHUNK_SIZE);
	            bytesRead += numBytesRead;
	            // write the mic data to a stream for use later
	            //out.write(data, 0, numBytesRead); 
	            try {
					Client.output2.write(data);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	        microphone.close();
	    } catch (LineUnavailableException e) {
	        e.printStackTrace();
	    } 
	}
}
