package com.easykotlin.kor;

public class KorGenerateJava {

    public void doGenerate(String projectDir, String packageName,String entityName){

        KorGenerateKotlin korGenerateKotlin = new KorGenerateKotlin();
        korGenerateKotlin.doGenerate(projectDir,packageName,entityName);

    }

}
