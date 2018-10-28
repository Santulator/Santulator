set PACKAGER=%1
set INSTALLER_TYPE=%2
set MODULE_PATH=%3
set INPUT=%4
set OUTPUT=%5
set JAR=%6
set VERSION=%7

call %PACKAGER% ^
  create-installer %INSTALLER_TYPE% ^
  --module-path %MODULE_PATH% ^
  --verbose ^
  --echo-mode ^
  --add-modules java.base,java.datatransfer,java.desktop,java.scripting,java.xml,jdk.jsobject,jdk.unsupported,jdk.unsupported.desktop,jdk.xml.dom,javafx.controls,javafx.fxml,java.naming,java.sql ^
  --input "%INPUT%" ^
  --output "%OUTPUT%" ^
  --name Santulator ^
  --main-jar %JAR% ^
  --version %VERSION% ^
  --jvm-args '--add-opens javafx.base/com.sun.javafx.reflect=ALL-UNNAMED' ^
  --class io.github.santulator.gui.main.SantulatorGuiExecutable
