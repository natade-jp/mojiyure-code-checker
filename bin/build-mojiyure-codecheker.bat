@echo off

rem Windows-31J
chcp 932 > nul

rem 定数の設定
set CURRENT_DIR=%CD%
set BATFILE_DIR=%~dp0
pushd %~dp0..
set TOP_DIR=%CD%
popd

rem Java関係の設定
set JAVA_JAR_FILE=.\build\MojiyureCodeCheker.jar
set JAVA_SRC_DIR=.\src
set JAVA_CLASS_DIR=.\tmp
set JAVA_CLASS_NAME=.\src\net\natade\app\MojiyureCodeCheker.java
set JAVA_ENCODING=UTF-8
set JAVA_MANIFEST=.\bin\MojiyureCodeCheker\MANIFEST.MF
set JAVA_CLASSPATH=.\libs\kuromoji-core\kuromoji-core-0.9.0.jar;.\libs\kuromoji-unidic\kuromoji-unidic-0.9.0.jar

rem トップディレクトリへ移動
pushd %TOP_DIR%

rem JAVA コマンド実行など
@echo on

javac -Xlint:deprecation -classpath "%JAVA_CLASSPATH%" -sourcepath "%JAVA_SRC_DIR%" -encoding "%JAVA_ENCODING%" -d "%JAVA_CLASS_DIR%" "%JAVA_CLASS_NAME%"
jar -c -v -f "%JAVA_JAR_FILE%" -C "%JAVA_CLASS_DIR%" .

@echo off
rem ディレクトリを戻す
popd
