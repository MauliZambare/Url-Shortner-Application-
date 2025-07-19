import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Compiler {
    public static void main(String[] args) {
        File srcDir = new File("src");
        File libDir = new File("lib");
        File binDir = new File("bin");

        if (!binDir.exists()) {
            binDir.mkdir();
        }

        List<String> sourceFiles = new ArrayList<>();
        findJavaFiles(srcDir, sourceFiles);

        String classpath = "src" + File.pathSeparator + new File(libDir, "json.jar").getAbsolutePath();

        List<String> command = new ArrayList<>();
        command.add("javac");
        command.add("--release");
        command.add("17");
        command.add("-d");
        command.add("bin");
        command.add("-classpath");
        command.add(classpath);
        command.addAll(sourceFiles);

        try {
            ProcessBuilder pb = new ProcessBuilder(command);
            pb.inheritIO();
            Process process = pb.start();
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void findJavaFiles(File dir, List<String> fileList) {
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                findJavaFiles(file, fileList);
            } else if (file.getName().endsWith(".java")) {
                fileList.add(file.getAbsolutePath());
            }
        }
    }
}
