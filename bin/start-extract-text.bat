@echo off

rem Windows-31J
chcp 932 > nul

rem �萔�̐ݒ�
set CURRENT_DIR=%CD%
set BATFILE_DIR=%~dp0
pushd %~dp0..
set TOP_DIR=%CD%
popd

rem Java�֌W�̐ݒ�
set JAVA_JAR_FILE=%TOP_DIR%\build\ExtractText.jar
set JAVA_CLASSPATH=%JAVA_JAR_FILE%

rem JAVA �R�}���h���s�Ȃ�
java -classpath "%JAVA_CLASSPATH%" net.natade.app.ExtractText %*
