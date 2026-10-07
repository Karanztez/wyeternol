@echo off
title WyEternol Server (Port 25598)
echo ========================================================
echo  Starting WyEternol Server on port 25598
echo ========================================================
call gradlew.bat :wyeternol-server:run
pause
