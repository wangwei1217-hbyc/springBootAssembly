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
- 2、
