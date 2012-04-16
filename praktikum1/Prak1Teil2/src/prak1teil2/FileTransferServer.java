package prak1teil2;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;




public class FileTransferServer {
    private static final String serverPath = "/home/chris/NetBeansProjects/Prak1Teil2/src/server/";

    public static void main(String[] args) throws Exception{
        UdpConnection udpc = new UdpConnection(1337);
	while (true) {
            byte[] msg = udpc.receiveRequest();

            Core m = new Core();//msg);
            m.deserialize(msg);
            FileOutputStream fos;
            DataOutputStream dos;

            System.out.println(m.getOp());

            switch(m.getOp()) {
                case READ:
                    System.out.println("reading " + m.getFilename());
                    break;
                case WRITE:
                    System.out.println("writing " + m.getFilename());
                    fos = new FileOutputStream(serverPath+m.getFilename(),false);
                    dos = new DataOutputStream(fos);
                    dos.write(m.getData());
                    dos.close();
                    fos.close();
                    break;
                case DELETE:
                    System.out.println("deleting " + m.getFilename());
                    break;
                case NOTFIRSTBLOCK:
                    System.out.println("not first block " + m.getFilename());
                    fos = new FileOutputStream(serverPath+m.getFilename(),true);
                    dos = new DataOutputStream(fos);
                    dos.write(m.getData());
                    dos.close();
                    fos.close();
                    break;
            }
        }
    }
}

