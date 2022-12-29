package mylang;

import mylang.tokenizer.Tokenizer;
import mylang.tokenizer.TokenizerException;
import mylang.tokenizer.Token;
import mylang.parser.Parser;
import mylang.parser.ParseException;
import mylang.parser.Program;
import mylang.codegen.CodeGen;
import mylang.codegen.CodeGenException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class MyLang {
    public static void usage() {
        System.out.println("Takes:");
        System.out.println("-Input mylang file");
        System.out.println("-Output JavaScript file");
    }

    public static String readFileToString(final String fileName) throws IOException {
        return Files.readString(new File(fileName).toPath());
    }
    
    public static void main(String[] args)
        throws IOException,
               TokenizerException,
               ParseException,
               CodeGenException {
        if (args.length != 2) {
            usage();
        } else {
            final String input = readFileToString(args[0]);
            final Token[] tokens = Tokenizer.tokenize(input);
            final Program program = Parser.parseProgram(tokens);
            CodeGen.writeProgram(program, new File(args[1]));
        }
    }
}
