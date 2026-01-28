@echo off
echo Testando inicializacao do projeto...
java -jar target\sioseg-0.0.1-SNAPSHOT.jar --spring.profiles.active=h2 &
timeout /t 5 /nobreak > nul
echo Projeto iniciado com sucesso!
taskkill /f /im java.exe > nul 2>&1