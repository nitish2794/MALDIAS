/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaapplication10;

/**
 *
 * @author Sony
 */
 /*
 * Copyright (c) 2011 - Georgios Gousios <gousiosg@gmail.com>
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *
 *     * Redistributions in binary form must reproduce the above
 *       copyright notice, this list of conditions and the following
 *       disclaimer in the documentation and/or other materials provided
 *       with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */


import com.sun.org.apache.bcel.internal.classfile.ClassParser;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;



/**
 * Constructs a callgraph out of a JAR archive. Can combine multiple archives
 * into a single call graph.
 * 
 * @author Georgios Gousios <gousiosg@gmail.com>
 * 
 */
public class JavaApplication10 {

    public static void mainCall(String args) throws FileNotFoundException, IOException {
        String projectPath=System.getProperty("user.dir");
      
       String [] k={projectPath+"\\dex2jar-2.0\\dex2jar-2.0\\"+args };
       File gvfile=new File("\""+projectPath+"\\graph1.gv\"");
        System.out.println("File created : "+gvfile);
        
       PrintWriter gout;
	
       gout = new PrintWriter(new BufferedWriter(new FileWriter(projectPath+"\\graph1.gv", true)));
       	         
      ClassParser cp;
        
        gout.println("digraph G {");
        gout.println("edge [color=blue];rankdir=LR;");
        gout.close();
        try {
            for (String arg : k) {

                File f = new File(arg);
                
                if (!f.exists()) {
                    System.err.println("Jar file " + arg + " does not exist");
                }
                
                JarFile jar = new JarFile(f);
            
            
                Enumeration<JarEntry> entries = jar.entries();
                
               
                
                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    if (entry.isDirectory())
                        continue;

                    if (!entry.getName().endsWith(".class"))
                        continue;

                    cp = new ClassParser(arg,entry.getName());
                    ClassVisitor visitor = new ClassVisitor(cp.parse());
                    visitor.start();
                    
                }
            }
        } catch (IOException e) {
            System.err.println("Error while processing jar: " + e.getMessage());
            e.printStackTrace();
        }
        PrintWriter hash;
         hash = new PrintWriter(new BufferedWriter(new FileWriter(projectPath+"\\graph1.gv", true)));
            
        hash.println("}");
        hash.close();
    }
}
