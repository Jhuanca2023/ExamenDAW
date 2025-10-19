@echo off
echo Iniciando solo MySQL en Docker...
docker-compose up -d mysql
echo.
echo MySQL iniciado correctamente en puerto 3306
echo.
echo Para detener MySQL:
echo docker-compose down
echo.
pause
