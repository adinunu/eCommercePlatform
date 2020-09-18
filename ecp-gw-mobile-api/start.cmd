set PROFILES=%1

mvnw.cmd spring-boot:run -Drun.profiles=%PROFILES% -Dapp.name=WHEE-POS-RESTFUL
