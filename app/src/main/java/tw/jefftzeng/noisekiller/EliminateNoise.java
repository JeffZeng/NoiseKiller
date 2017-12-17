package tw.jefftzeng.noisekiller;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;

/**
 * Created by JeffTseng on 2017/12/17.
 */

public class EliminateNoise extends Thread {
    boolean isRecording = false;
    int recBufSize;
    int playBufSize;
    final int channelConfiguration = AudioFormat.CHANNEL_CONFIGURATION_MONO;
    final int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;
    final int frequency = 44100;
    byte [] buffer;
    byte [] prevbuf;
    AudioRecord audioRecord;
    AudioTrack audioTrack;
    @Override
    public void run() {
        super.run();

        recBufSize = AudioRecord.getMinBufferSize(frequency,channelConfiguration, audioEncoding);
        playBufSize= AudioTrack.getMinBufferSize(frequency,channelConfiguration, audioEncoding);
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, frequency, channelConfiguration, audioEncoding, recBufSize);
        audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, frequency, channelConfiguration, audioEncoding, playBufSize, AudioTrack.MODE_STREAM);
        audioRecord.startRecording();
        audioTrack.play();
        audioTrack.setStereoVolume(1.0f, 1.0f);
        while(isRecording) {
            prevbuf = new byte[recBufSize];
            buffer = new byte[recBufSize];
            int bufferReadResult = audioRecord.read(buffer, 0, recBufSize);
            invert(buffer, bufferReadResult);
            audioTrack.write(buffer, 0, bufferReadResult);

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void Start() {
        super.start();
        isRecording = true;
    }

    public void Stop() {
        isRecording = false;
    }

    void invert(byte [] buf, int len) {

        short temp;
        for (int index = 0; index < len; index+=2) {
            temp = (short)(buf[index] |((short)buf[index+1] << 8));
            if( temp < -Short.MAX_VALUE )
            {
                temp = -Short.MAX_VALUE;
            }
            temp =(short)(-temp);
            buf[ index ]     = (byte) (   temp & 0x00FF );
            buf[ index + 1 ] = (byte) ( ( temp >>> 8  ) );
        }
    }
}
