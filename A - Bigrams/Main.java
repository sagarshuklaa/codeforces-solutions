import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int t = Integer.parseInt(st.nextToken());

        while (t-- > 0) {
            int k = Integer.parseInt(br.readLine());
            st = new StringTokenizer(br.readLine());

            int countThreeOrMore = 0;
            int countTwoOrMore = 0;

            for (int i = 0; i < k; i++) {
                int c = Integer.parseInt(st.nextToken());

                if (c >= 3) countThreeOrMore++;
                if (c >= 2) countTwoOrMore++;
            }

            if (countThreeOrMore >= 1 || countTwoOrMore >= 2)
                System.out.println("YES");
            else
                System.out.println("NO");
        }
    }
}
