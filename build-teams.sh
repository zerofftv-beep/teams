#!/bin/bash

# Скрипт сборки SBW Teams

set -e

echo "=== Сборка Superb Warfare Teams ==="

cd sbw-teams

./gradlew clean build --quiet

JAR_FILE=$(find build/libs -name "*.jar" ! -name "*-sources.jar" ! -name "*-dev.jar" | head -n 1)

if [ -f "$JAR_FILE" ]; then
    mkdir -p ../builds
    cp "$JAR_FILE" "../builds/"
    echo "✓ SBW Teams собран: $(basename "$JAR_FILE")"
else
    echo "✗ Ошибка при сборке SBW Teams"
fi

cd ..
echo "Готово! Файл находится в папке builds/"
