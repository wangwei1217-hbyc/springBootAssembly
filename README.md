# springBootAssembly
利用appassembler-maven-plugin<插件(自动生成启动脚本)、maven-assembly-plugin插件(打包)打包springBoot项目

### 注意点
- 1、先运行maven命令appassembler:assemble打包生成可执行启动脚本:startup.bat/startup.sh。
目录如下:
```
target
    |-springBootAssembly
        |-bin
            |-startup.bat
            |-startup.sh
        |-conf
        |-lib
```
- 2、再运行maven命令：package进行打包，则会再target目录下生成名为springBootAssembly-1.0-SNAPSHOT-assembly.tar.gz的压缩文件。
解压，运行bin目录下的startup脚本命令(Linux:"sh startup.sh &" window:"startup.bat")。
