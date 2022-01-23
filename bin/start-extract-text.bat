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
set JAVA_JAR_FILE=%TOP_DIR%\build\ExtractText.jar
set JAVA_CLASSPATH=%JAVA_JAR_FILE%

rem JAVA コマンド実行など
java -classpath "%JAVA_CLASSPATH%" net.natade.app.ExtractText %*
