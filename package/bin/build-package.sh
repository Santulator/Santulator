#!/bin/bash

set -e

PACKAGER=${1}
INSTALLER_TYPE=${2}
MODULE_PATH=${3}
MODULES=${4}
INPUT=${5}
OUTPUT=${6}
JAR=${7}
VERSION=${8}
FILE_ASSOCIATIONS=${9}
APP_ICON=${10}
EXTRA_BUNDLER_ARGUMENTS=${11}

${PACKAGER} \
  create-installer ${INSTALLER_TYPE} \
  --module-path ${MODULE_PATH} \
  --verbose \
  --echo-mode \
  --add-modules "${MODULES}" \
  --input "${INPUT}" \
  --output "${OUTPUT}" \
  --name Santulator \
  --main-jar ${JAR} \
  --version ${VERSION} \
  --jvm-args '--add-opens javafx.base/com.sun.javafx.reflect=ALL-UNNAMED' \
  --file-associations ${FILE_ASSOCIATIONS} \
  --icon $APP_ICON \
  $EXTRA_BUNDLER_ARGUMENTS \
  --class io.github.santulator.gui.main.SantulatorGuiExecutable
