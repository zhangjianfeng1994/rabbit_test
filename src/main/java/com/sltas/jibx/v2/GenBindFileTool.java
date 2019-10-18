package com.sltas.jibx.v2;

import java.io.IOException;

import org.jibx.binding.Compile;
import org.jibx.binding.generator.BindGen;
import org.jibx.runtime.JiBXException;

public class GenBindFileTool {

	

    public static void main(String[] args) throws JiBXException, IOException {

//        genBindFiles();
        compile();
    }

    private static void compile() {
        String[] args = new String[2];

        // 打印生成过程的详细信息。可选
        args[0] = "-v";

        // 指定 binding 和 schema 文件的路径。必须
        args[1] = "./src/main/java/com/sltas/jibx/v2/binding.xml";

        Compile.main(args);
    }

    private static void genBindFiles() throws JiBXException, IOException {
        String[] args = new String[8];

        // 指定pojo源码路径（指定父包也是可以的）。必须
        args[0] = "-s";
        args[1] = "src";

        // 自定义生成的binding文件名，默认文件名binding.xml。可选
        args[2] = "-b";
        args[3] = "binding.xml";

        // 打印生成过程的一些信息。可选
        args[4] = "-v";

        // 如果目录已经存在，就删除目录。可选
//        args[5] = "-w";

        // 指定输出路径。默认路径 .（当前目录,即根目录）。可选
        args[5] = "-t";
        args[6] = "./src/main/java/com/sltas/jibx/v2";

        // 告诉 BindGen 使用下面的类作为 root 生成 binding 和 schema。必须
        args[7] = "com.sltas.jibx.v2.Order";

        BindGen.main(args);
    }
}
