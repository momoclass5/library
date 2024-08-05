package common;

import java.util.Scanner;

public class ScannerUtil {
    private static Scanner scan = new Scanner(System.in);

    // 생성할수 없음!
    private ScannerUtil() {
    }

    // 사용자로부터 입력받은 숫자를 반환
    public static int getInt(String msg) {

        int num = 0;
        // 예외발생시 값을 다시 받아 올수 있도록 반복처리
        while (true) {
            // 예외(오류)가 발생할 수 있는 코드를 try-catch문장으로 묶어줍니다!!!
            // 예외처리를 하지 않으면 비정상 종료가 되니까
            try {
                System.out.print(msg + " : ");
                num = scan.nextInt();
                // 남아있는 엔터를 제거
                scan.nextLine();
                return num;
                // 예외(Exception) 이름을 적어줌
            } catch (Exception e) {
                // 예외가 발생
                System.out.println("숫자만 입력 가능합니다.");
                // 입력된 값을 처리
                // (처리하지 않으면 계속 남아 있어요 ==== 무한반복의 원인)
                scan.nextLine();
            }
        }
        // return num;
    }

    public static String getString(String str) {
        System.out.print(str + " : ");
        return scan.nextLine();
    }

}
