package chapter3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/*
This class is demonstrating the execute-around pattern.
-- Setup
-- Main logic : parameterized with lambda
-- Cleanup
 */
public class FileProcessingUsingLambdas {

    public interface BufferedReaderProcessor {
        public String process(BufferedReader br) throws IOException;
    }

    public static void main(String [] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("/Users/pbanga/Documents/ManningJava8InAction/src/chapter3/input"));

        /*/
        There are 2 ways to handle the checked exception thrown by lambda code :
        1. Surround by try-catch - shown by 1
        2. Update the method signature of the abstract interface method the lambda represents. - shown by 2
         */
        System.out.println("First BR Processor");
        processBufferedReader(br, (BufferedReader b) ->  {
            try {
                return b.readLine();
            } catch(IOException ex) {
                ex.printStackTrace();
            }
            return null;
        }
       );

        System.out.println("Second BR Processor");
        processBufferedReader(br, (BufferedReader b) -> b.readLine() + b.readLine());

        br.close();
    }

    private static void processBufferedReader(BufferedReader br, BufferedReaderProcessor p) throws IOException {
        System.out.println(p.process(br));
    }

}
