@echo off

rem Windows-31J
chcp 932 > nul

rem 定数の設定
set CURRENT_DIR=%CD%
set BATFILE_DIR=%~dp0
set TOP_DIR=%BATFILE_DIR%\..

rem トップディレクトリへ移動
cd %TOP_DIR%
echo %CD%

rmdir /s /q .\tmp\net

rem ディレクトリを戻す
cd %CURRENT_DIR%

