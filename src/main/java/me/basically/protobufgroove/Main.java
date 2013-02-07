package me.basically.protobufgroove;

import com.google.protobuf.DescriptorProtos;
import groovy.lang.Writable;
import groovy.text.SimpleTemplateEngine;
import groovy.text.Template;

import java.io.*;
import java.util.Hashtable;
import java.util.Map;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        Main main = new Main();
        main.run();
    }

    void run() throws IOException, ClassNotFoundException
    {
        Properties props = new Properties();

        DescriptorProtos.FileDescriptorSet descriptorProto = readFileDescriptorSet(props.getPb());
        String renderedText = processTemplate(descriptorProto, new File(props.getTemplate()));

        //Files.write(FileSystems.getDefault().getPath(props.getOut()), renderedText, Charset.forName("UTF-8"), null);

        System.out.println(renderedText);
    }


    String processTemplate(DescriptorProtos.FileDescriptorSet fileDescriptorSet, File templateFilePath)
            throws IOException, ClassNotFoundException
    {
        SimpleTemplateEngine templateEngine = new SimpleTemplateEngine();
        Template template = templateEngine.createTemplate(templateFilePath);

        Map<String, Object> bindings = new Hashtable<>();
        bindings.put("desc", fileDescriptorSet);

        StringWriter stringWriter = new StringWriter();
        final Writable writable = template.make(bindings);
        writable.writeTo(stringWriter);

        return stringWriter.toString();
    }


    DescriptorProtos.FileDescriptorSet readFileDescriptorSet(String path) throws IOException
    {
        InputStream inputStream = null;
        DescriptorProtos.FileDescriptorSet fileDescriptorSet = null;

        try
        {
            inputStream = new FileInputStream(path);
            fileDescriptorSet = DescriptorProtos.FileDescriptorSet.parseFrom(inputStream);
        }
        finally
        {
            if (inputStream != null)
            {
                inputStream.close();
            }
        }

        return fileDescriptorSet;
    }
}

class Properties
{
    String pb;
    String template;
    String out;

    Properties()
    {
        pb = System.getProperty("pb");
        // if (isNullOrEmpty(pb))
        // {
        //     throw new Exception("Specify pb!");
        // }
        template = System.getProperty("t");
        out = System.getProperty("out");
    }


    public String getPb()
    {
        return pb;
    }

    public String getTemplate()
    {
        return template;
    }

    public String getOut()
    {
        return out;
    }

    static boolean isNullOrEmpty(String str)
    {
        return str == null || str.isEmpty();
    }
}
