#!/usr/bin/env pwsh

# Rebuild script for sioseg-java
cd 'c:\Users\Kyoshiro\Downloads\sioseg-java'

Write-Host "==== Compilando aplicação ====" -ForegroundColor Cyan
$env:JAVA_HOME = "$env:USERPROFILE\jdk"
$env:Path += ";$env:JAVA_HOME\bin;$env:USERPROFILE\tools\maven\bin"

# Clean and package
& "$env:USERPROFILE\tools\maven\bin\mvn.cmd" clean package -DskipTests

if ($LASTEXITCODE -eq 0) {
    Write-Host "Build bem-sucedido!" -ForegroundColor Green
    Write-Host "`nIniciando aplicação com Maven..." -ForegroundColor Cyan
    & "$env:USERPROFILE\tools\maven\bin\mvn.cmd" spring-boot:run
} else {
    Write-Host "Build falhou!" -ForegroundColor Red
}
