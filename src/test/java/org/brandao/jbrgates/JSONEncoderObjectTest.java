/*
 * JBRGates http://jgates.sourceforge.net/
 * Copyright (C) 2010 Afonso Brandao. (afonso.rbn@gmail.com)
 *
 * This library is free software. You can redistribute it
 * and/or modify it under the terms of the GNU General Public
 * License (GPL) version 3.0 or (at your option) any later
 * version.
 * You may obtain a copy of the License at
 *
 * http://www.gnu.org/licenses/gpl.html
 *
 * Distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied.
 *
 */


package org.brandao.jbrgates;

import java.io.IOException;
import java.io.Serializable;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 *
 * @author Afonso Brandao
 */
public class JSONEncoderObjectTest extends TestCase implements Test{

    static class Test1{
    }

    static class Test2 extends Test1 implements Serializable{

        private int f1;
        private String f2;
        private char f3;

        public Test2(){
            this.f1 = 100;
            this.f2 = null;
        }

        public int getF1() {
            return f1;
        }

        public void setF1(int f1) {
            this.f1 = f1;
        }

        public String getF2() {
            return f2;
        }

        public void setF2(String f2) {
            this.f2 = f2;
        }

        public char getF3() {
            return f3;
        }

        public void setF3(char f3) {
            this.f3 = f3;
        }

    }

    static class Test3 implements Serializable{

        private Test3 f;

        public Test3(){
            this.f = this;
        }

        public Test3 getF() {
            return f;
        }

        public void setF(Test3 f) {
            this.f = f;
        }

    }

    static class Test4 extends Test3 implements Serializable{

        private Test4 f;

        public Test4(){
            this.f = this;
        }

        public Test4 getF() {
            return f;
        }

        public void setF(Test4 f) {
            this.f = f;
        }

    }

    public JSONEncoderObjectTest(){
        super();
    }

    public void testNull() throws IOException{
        JSONEncoder jse = new JSONEncoder();
        jse.encode( null );
        assertEquals( "null", jse.toString() );
    }

    public void testClass() throws IOException{
        JSONEncoder jse = new JSONEncoder();
        jse.encode( Integer.class );
        assertEquals( String.format( "\"%s\"" , Integer.class.getName() ), jse.toString() );
    }

    public void testNotSerializable() throws IOException{
        try{
            JSONEncoder jse = new JSONEncoder();
            jse.encode( new Test1() );
            fail( "expected JSONException" );
        }
        catch( Exception e ){
        }
    }

    public void testExtendsNotSerializable() throws IOException{
        JSONEncoder jse = new JSONEncoder();
        jse.encode( new Test2() );

        assertEquals( 
            "{ \"class\" : \"org.brandao.jbrgates.JSONEncoderObjectTest$Test2\", " +
            "\"f1\" : 100, " +
            "\"f3\" : \"\", " +
            "\"f2\" : null }",
            jse.toString()
            );
    }

    public void testCiclico() throws IOException{
        JSONEncoder jse = new JSONEncoder();
        jse.encode( new Test3() );
        assertEquals( 
            "{ \"f\" : null, " +
            "\"class\" : \"org.brandao.jbrgates.JSONEncoderObjectTest$Test3\"" +
            " }",
            jse.toString()
            );
    }

    public void testExtends() throws IOException{
        JSONEncoder jse = new JSONEncoder();
        jse.encode( new Test4() );

        assertEquals( 
            "{ \"f\" : null, " +
            "\"class\" : \"org.brandao.jbrgates.JSONEncoderObjectTest$Test4\" }",
            jse.toString()
            );
    }

}
