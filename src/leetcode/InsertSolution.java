package leetcode;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
public class InsertSolution{
    static String WORKING_DIR = "/home/igor/projects/algos/src/leetcode";
    static String SOLUTION_PREFIX = "    static class s";
    static Logger log = Logger.getLogger("InsertSolution");

    public static void main(String[] args) throws IOException{
        log.info("Reading a.java");
        List<String> insertLines = solutionFromA();
        int problemNo = problemNo(insertLines.get(0));
        String updateFile = "p" + (problemNo / 100 * 100) + ".java";
        log.info(String.format("Inserting problem %s into %s", problemNo, updateFile));
        List<String> oldLines = Files.readAllLines(Path.of(WORKING_DIR, updateFile)), newLines = new ArrayList<>();
        boolean inserted = false;
        for(String line : oldLines){
            if(!inserted && line.startsWith(SOLUTION_PREFIX) && problemNo(line) >= problemNo){
                if(problemNo(line) == problemNo){
                    log.warning(String.format("solution s%s already exists in %s", problemNo, updateFile));
                    return;
                }
                log.info("Inserting before " + line);
                inserted = true;
                newLines.addAll(insertLines);
                newLines.add("");
            }
            newLines.add(line);
        }
        log.info("Updating the file...");
        Files.write(Path.of(WORKING_DIR, updateFile), newLines);
    }

    static List<String> solutionFromA() throws IOException{
        List<String> lines = Files.readAllLines(Path.of(WORKING_DIR, "a.java")), solutionLines = new ArrayList<>();
        for(int i = 0; i < lines.size() && solutionLines.isEmpty(); i++)
            if(lines.get(i).startsWith(SOLUTION_PREFIX))
                for(int j = i; j < lines.size(); j++)
                    if(lines.get(j).startsWith("}"))
                        break;
                    else solutionLines.add(lines.get(j));
        return solutionLines;
    }

    static int problemNo(String s){
        int n = 0;
        for(int i = SOLUTION_PREFIX.length(); s.charAt(i) != '{'; i++)
            n = 10 * n + s.charAt(i) - '0';
        return n;
    }
}