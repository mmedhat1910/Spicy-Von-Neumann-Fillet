package Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Parser {
    final ArrayList<String> codeText;

    public Parser() throws IOException {
        this.codeText = loadCodeText("code.txt");
    }

    public ArrayList<String> loadCodeText(String filename) throws IOException {
        ArrayList<String> code = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line = br.readLine();
        while (line != null)
        {
            code.add(line); line = br.readLine();
        }
        br.close();
        return code;
    }

    public ArrayList<String> getCodeText() {
        return codeText;
    }

    public static void main(String[] args) throws IOException {
        Parser p = new Parser();
        System.out.println(p.getCodeText());
    }
}
