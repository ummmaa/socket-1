import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.BindException;
import java.net.Socket; //ネットワーク関連のパッケージを利用する
import java.util.Scanner;

public class HalloweenTCPClient {
    public static void main(String arg[]) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("ポートを入力してください(5000など) → ");
            int port = scanner.nextInt();
            System.out.println("localhostの" + port + "番ポートに接続を要求します");
            Socket socket = new Socket("localhost", port);
            System.out.println("接続されました");

            System.out.println("小さな子供のサーバー君が来ました。お菓子をあげるかどうか決めます");
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            boolean loop = true;

            while (loop) {
                System.out.println("あげる場合は半角で0を、あげない場合は半角で1を入力してください。終了するには'quit'と入力してください↓");
                String message = scanner.next();
                
                if (message.equals("quit")) {
                    HalloweenPresent present = new HalloweenPresent();
                    present.setMessage("quit");
                    present.setContent("");
                    oos.writeObject(present);
                    oos.flush();

                    HalloweenPresent response = (HalloweenPresent) ois.readObject();
                    System.out.println("サーバからのメッセージは、「" + response.getMessage() + "」");
                    loop = false;
                } else if(message.equals("0")){
                    System.out.println("お菓子の内容を入力してください(例:candy) ↓");
                    String content = scanner.next();

                    HalloweenPresent present = new HalloweenPresent();
                    present.setMessage(message);
                    present.setContent(content);

                    oos.writeObject(present);
                    oos.flush();

                    HalloweenPresent okaeshiPresent = (HalloweenPresent) ois.readObject();
                    String replayMsg = okaeshiPresent.getMessage();
                    System.out.println("サーバからのメッセージは、「" + replayMsg + "」");

                    String replayContent = okaeshiPresent.getContent();
                    if (replayContent != null && Integer.parseInt(replayContent) != 0) {
                        System.out.println("サーバー君に悪戯された");
                        loop = false;
                    }
                } else {
                    HalloweenPresent present = new HalloweenPresent();
                    present.setMessage(message);
                    present.setContent("1");

                    oos.writeObject(present);
                    oos.flush();

                    HalloweenPresent okaeshiPresent = (HalloweenPresent) ois.readObject();
                    String replayMsg = okaeshiPresent.getMessage();
                    System.out.println("サーバからのメッセージは、「" + replayMsg + "」");

                    String replayContent = okaeshiPresent.getContent();
                    if (replayContent != null && Integer.parseInt(replayContent) != 0) {
                        System.out.println("サーバー君に悪戯された。");
                        loop = false;
                    }
                }
            }

            ois.close();
            oos.close();
            socket.close();
            scanner.close();

        } catch (BindException be) {
            be.printStackTrace();
            System.err.println("ポート番号が不正か、サーバが起動していません");
            System.err.println("サーバが起動しているか確認してください");
            System.err.println("別のポート番号を指定してください(6000など)");
        } catch (Exception e) {
            System.err.println("エラーが発生したのでプログラムを終了します");
            throw new RuntimeException(e);
        }
    }
}
