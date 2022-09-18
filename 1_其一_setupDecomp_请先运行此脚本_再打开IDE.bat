@echo off
"C:\Windows\System32\chcp.com" 65001
cd /d "%~dp0"
echo %JAVA_TOOL_OPTIONS% | "C:\Windows\System32\findstr.exe" /C:"-Dfile.encoding=" > nul
if errorlevel 1 (
    if defined JAVA_TOOL_OPTIONS (
        "C:\Windows\System32\setx.exe" JAVA_TOOL_OPTIONS "%JAVA_TOOL_OPTIONS% -Dfile.encoding=UTF-8"
        set "JAVA_TOOL_OPTIONS=%JAVA_TOOL_OPTIONS% -Dfile.encoding=UTF-8"
    ) else (
        "C:\Windows\System32\setx.exe" JAVA_TOOL_OPTIONS "-Dfile.encoding=UTF-8"
        set "JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF-8"
    )
)
call gradlew.bat setupDecompWorkspace genIntellijRuns
pause
