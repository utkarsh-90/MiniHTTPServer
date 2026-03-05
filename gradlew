#!/bin/sh
# Delegate to Gradle when wrapper jar is not present; otherwise use wrapper.
# Generate wrapper jar with: gradle wrapper
SCRIPT_DIR=$(cd "$(dirname "$0")" && pwd)
WRAPPER_JAR="$SCRIPT_DIR/gradle/wrapper/gradle-wrapper.jar"
if [ -f "$WRAPPER_JAR" ]; then
  exec java -Dfile.encoding=UTF-8 -jar "$WRAPPER_JAR" "$@"
else
  exec gradle "$@"
fi
