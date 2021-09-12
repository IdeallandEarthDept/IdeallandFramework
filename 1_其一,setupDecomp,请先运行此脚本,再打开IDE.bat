@echo off
echo %JAVA_TOOL_OPTIONS% | find "-Dfile.encoding=" > nul
if errorlevel 1 (
    setx JAVA_TOOL_OPTIONS "%JAVA_TOOL_OPTIONS% -Dfile.encoding=UTF-8"
    set "JAVA_TOOL_OPTIONS=%JAVA_TOOL_OPTIONS% -Dfile.encoding=UTF-8"
)
chcp 65001
call gradlew.bat setupDecompWorkspace idea genIntellijRuns
pause
