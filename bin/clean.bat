@echo off

rem Windows-31J
chcp 932 > nul

rem �萔�̐ݒ�
set CURRENT_DIR=%CD%
set BATFILE_DIR=%~dp0
set TOP_DIR=%BATFILE_DIR%\..

rem �g�b�v�f�B���N�g���ֈړ�
cd %TOP_DIR%
echo %CD%

rmdir /s /q .\tmp\net

rem �f�B���N�g����߂�
cd %CURRENT_DIR%

