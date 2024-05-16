import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class HalloweenTCPServ {

    public static void main(String arg[]) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("ポートを入力してください(5000など) → ");
            int port = scanner.nextInt();
            scanner.close();
            System.out.println("localhostの" + port + "番ポートで待機します");
            ServerSocket server = new ServerSocket(port);

            Socket socket = server.accept();
            System.out.println("接続しました。相手の入力を待っています......");

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

            boolean loop = true;
            while (loop) {
                HalloweenPresent present = (HalloweenPresent) ois.readObject();
                String msgPresent = present.getMessage();
                System.out.println("メッセージは" + msgPresent);
                String presentFromClient = present.getContent();
                System.out.println("プレゼントの内容は" + presentFromClient);

                HalloweenPresent response = new HalloweenPresent();
                if (msgPresent.equals("quit")) {
                    response.setMessage("ばいばーい！");
                    response.setContent("quit");
                    oos.writeObject(response);
                    oos.flush();
                    loop = false;
                } else if (Integer.parseInt(msgPresent) == 0) {
                    response.setMessage("サーバーだよ！ハッピーハロウィン！\n" + presentFromClient + "ありがとう！");
                    response.setContent("0");
                } else {
                    response.setMessage("サーバーです。お菓子をくれなかったので悪戯します。");
                    response.setContent("1");
                }
                oos.writeObject(response);
                oos.flush();
            }

            ois.close();
            oos.close();
            socket.close();
            server.close();

        } catch (BindException be) {
            be.printStackTrace();
            System.out.println("ポート番号が不正、ポートが使用中です");
            System.err.println("別のポート番号を指定してください(6000など)");
        } catch (Exception e) {
            System.err.println("エラーが発生したのでプログラムを終了します");
            throw new RuntimeException(e);
        }
    }
}