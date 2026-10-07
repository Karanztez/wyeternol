@echo off
title Build WyEternol Server JAR
echo ========================================================
echo  Building Fat JAR directly to build\ folder
echo ========================================================
call gradlew.bat :wyeternol-server:shadowJar
echo.
echo Build finished! Check: build\wyeternol-server.jar
pause
